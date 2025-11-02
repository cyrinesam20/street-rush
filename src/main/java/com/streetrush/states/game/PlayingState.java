package com.streetrush.states.game;

import com.streetrush.main.Game;
import com.streetrush.entities.*;
import com.streetrush.obstacles.Obstacle;
import com.streetrush.powerups.PowerUp;
import com.streetrush.composite.GameObjectComposite;
import com.streetrush.factories.*;
import com.streetrush.decorators.*;
import com.streetrush.utils.*;
import java.awt.*;
import java.util.Random;

public class PlayingState implements GameState {
    private Game game;
    private Player player;
    private GameObjectComposite world;
    private RandomObstacleFactory obstacleFactory;
    private RandomPowerUpFactory powerUpFactory;
    private Random random;
    private int frameCount;
    private int distance;
    private float scrollOffset;
    private Renderer3D renderer;
    private Camera3D camera;

    public PlayingState(Game game) {
        this.game = game;
        this.camera = new Camera3D(800, 600);
        this.renderer = new Renderer3D(camera);
        this.player = new Player();
        this.world = new GameObjectComposite();
        this.obstacleFactory = new RandomObstacleFactory();
        this.powerUpFactory = new RandomPowerUpFactory();
        this.random = new Random();
        this.frameCount = 0;
        this.distance = 0;
        this.scrollOffset = 0;
    }

    @Override
    public void enter() {
        GameLogger.getInstance().logState("Game: MENU -> PLAYING");
    }

    @Override
    public void exit() {
        world.clear();
    }

    @Override
    public void handleInput() {
        // Géré par le Player
    }

    @Override
    public void update() {
        if (!player.isAlive()) {
            game.changeState(new GameOverState(game, player.getCoins(), distance));
            return;
        }

        player.update();
        world.update();
        camera.update(player.getPosition());

        frameCount++;
        distance = (int)(player.getPosition().z * 10);
        scrollOffset = player.getPosition().z;

        // Difficulté progressive
        if (frameCount % 400 == 0) {
            player.setSpeed(player.getSpeed() * 1.08f);
            GameLogger.getInstance().logEvent("Speed increased to " + player.getSpeed());
        }

        // Spawner obstacles
        if (frameCount % 50 == 0) {
            float spawnZ = player.getPosition().z + 35;
            Obstacle obs = obstacleFactory.createObstacle(spawnZ);
            world.add(obs);
            GameLogger.getInstance().logFactory("Obstacle created at z=" + spawnZ);
        }

        // Spawner coins (groupes)
        if (frameCount % 25 == 0) {
            spawnCoinGroup();
        }

        // Spawner power-ups
        if (frameCount % 150 == 0) {
            float spawnZ = player.getPosition().z + 30;
            PowerUp powerUp = powerUpFactory.createPowerUp(spawnZ);
            world.add(powerUp);
            GameLogger.getInstance().logFactory("PowerUp created at z=" + spawnZ);
        }

        checkCollisions();
        cleanupBehindObjects();
    }

    private void spawnCoinGroup() {
        int groupSize = random.nextInt(3) + 3; // 3-5 coins
        int lane = random.nextInt(3) - 1;
        float startZ = player.getPosition().z + 28;

        for (int i = 0; i < groupSize; i++) {
            world.add(new Coin(lane, startZ + i * 2));
        }
    }

    private void checkCollisions() {
        Rectangle playerBounds = player.getBounds3D();
        float playerZ = player.getPosition().z;

        for (GameObject obj : world.getChildren()) {
            if (obj instanceof Obstacle obstacle) {
                if (obstacle.isActive()) {
                    float distZ = Math.abs(obstacle.getPosition().z - playerZ);

                    if (distZ < 1.5f && playerBounds.intersects(obstacle.getBounds3D())) {
                        // Vérifier si on évite (saut ou slide)
                        if (player.getPosition().y > obstacle.getHeight() + 0.3f) {
                            GameLogger.getInstance().logEvent("Obstacle jumped over!");
                        } else if (player.getCurrentState() instanceof com.streetrush.states.player.SlidingState
                                && obstacle.getHeight() > 1.5f) {
                            GameLogger.getInstance().logEvent("Obstacle slid under!");
                        } else {
                            GameLogger.getInstance().logCollision("Player hit " + obstacle.getClass().getSimpleName());
                            player.crash();
                        }
                        obstacle.setActive(false);
                    }
                }
            }

            if (obj instanceof Coin coin) {
                if (coin.isActive()) {
                    float dist = player.getPosition().distance(coin.getPosition());

                    // Magnet attracts coins from farther away
                    if (player instanceof MagnetDecorator magnet) {
                        if (dist < magnet.getMagnetRange()) {
                            coin.setActive(false);
                            player.collectCoin();
                            GameLogger.getInstance().logCollision("Coin collected (magnet)");
                        }
                    } else if (dist < 1.2f && playerBounds.intersects(coin.getBounds3D())) {
                        coin.setActive(false);
                        player.collectCoin();
                        GameLogger.getInstance().logCollision("Coin collected");
                    }
                }
            }

            if (obj instanceof PowerUp powerUp) {
                if (powerUp.isActive()) {
                    float dist = player.getPosition().distance(powerUp.getPosition());
                    if (dist < 1.2f && playerBounds.intersects(powerUp.getBounds3D())) {
                        player = powerUp.apply(player);
                        powerUp.setActive(false);
                        GameLogger.getInstance().logDecorator(powerUp.getType() + " applied to Player");
                    }
                }
            }
        }
    }

    private void cleanupBehindObjects() {
        float playerZ = player.getPosition().z;
        world.getChildren().removeIf(obj -> {
            float objZ = obj.getPosition().z;
            return objZ < playerZ - 15 || !obj.isActive();
        });
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Sol avec effet 3D (utilise le renderer existant)
        renderer.drawGround(g2d, 800, 600, scrollOffset);

        // Monde (obstacles, coins, power-ups) - Le composite gère le tri Z
        world.render(g2d, renderer);

        // Joueur
        player.render(g2d, renderer);

        // HUD
        renderModernHUD(g2d);
    }

    private void renderModernHUD(Graphics2D g) {
        drawScorePanel(g);
        drawDistanceBar(g);
        drawActivePowerUps(g);

        if (frameCount < 300) {
            drawControlsHint(g);
        }
    }

    private void drawScorePanel(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRoundRect(15, 15, 200, 90, 20, 20);

        g.setColor(new Color(255, 215, 0));
        g.setStroke(new BasicStroke(3));
        g.drawRoundRect(15, 15, 200, 90, 20, 20);

        g.setColor(Color.YELLOW);
        g.fillOval(30, 30, 25, 25);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 28));
        g.drawString("" + player.getCoins(), 65, 50);

        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.setColor(new Color(100, 255, 255));
        g.drawString("Distance: " + distance + "m", 25, 85);
    }

    private void drawDistanceBar(Graphics2D g) {
        int barWidth = 300;
        int barHeight = 8;
        int barX = (800 - barWidth) / 2;
        int barY = 20;

        g.setColor(new Color(50, 50, 50, 200));
        g.fillRoundRect(barX, barY, barWidth, barHeight, barHeight, barHeight);

        int progress = (distance % 500) * barWidth / 500;
        GradientPaint gradient = new GradientPaint(
                barX, 0, new Color(0, 255, 0),
                barX + progress, 0, new Color(255, 255, 0)
        );
        g.setPaint(gradient);
        g.fillRoundRect(barX, barY, progress, barHeight, barHeight, barHeight);
    }

    private void drawActivePowerUps(Graphics2D g) {
        int yPos = 500;
        int index = 0;

        String[] powerUps = {
                player instanceof SpeedBoostDecorator ? "⚡ SPEED BOOST" : null,
                player instanceof MagnetDecorator ? "🧲 MAGNET" : null,
                player instanceof ShieldDecorator ? "🛡️ SHIELD" : null,
                player instanceof DoubleCoinDecorator ? "2️⃣ 2X COINS" : null
        };

        Color[] colors = {
                new Color(255, 255, 0),
                new Color(255, 0, 255),
                new Color(0, 255, 255),
                new Color(255, 215, 0)
        };

        for (int i = 0; i < powerUps.length; i++) {
            if (powerUps[i] != null) {
                float pulse = (float)(Math.sin(frameCount * 0.1 + i) * 0.2 + 0.8);
                g.setColor(new Color(
                        colors[i].getRed(),
                        colors[i].getGreen(),
                        colors[i].getBlue(),
                        (int)(200 * pulse)
                ));
                g.fillRoundRect(15, yPos + index * 50, 220, 40, 15, 15);

                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 18));
                g.drawString(powerUps[i], 25, yPos + index * 50 + 26);

                index++;
            }
        }
    }

    private void drawControlsHint(Graphics2D g) {
        int alpha = Math.max(0, 255 - frameCount);
        g.setColor(new Color(255, 255, 255, alpha));
        g.setFont(new Font("Arial", Font.BOLD, 16));

        String[] hints = {
                "← → : Change Lane",
                "↑ : Jump",
                "↓ : Slide"
        };

        int yStart = 250;
        for (int i = 0; i < hints.length; i++) {
            g.drawString(hints[i], 320, yStart + i * 30);
        }
    }

    public Player getPlayer() {
        return player;
    }
}