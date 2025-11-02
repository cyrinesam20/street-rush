package com.streetrush.states.game;

import com.streetrush.main.Game;
import com.streetrush.utils.GameLogger;
import java.awt.*;

public class GameOverState implements GameState {
    private Game game;
    private int finalScore;
    private int distance;
    private float animTime = 0;

    public GameOverState(Game game, int finalScore, int distance) {
        this.game = game;
        this.finalScore = finalScore;
        this.distance = distance;
    }

    @Override
    public void enter() {
        GameLogger.getInstance().logState("Game: PLAYING -> GAME_OVER");
        GameLogger.getInstance().logInfo("Final score: " + finalScore);
        GameLogger.getInstance().logInfo("Distance traveled: " + distance + "m");
    }

    @Override
    public void exit() {}

    @Override
    public void handleInput() {}

    @Override
    public void update() {
        animTime += 0.05f;
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dark gradient
        GradientPaint gradient = new GradientPaint(
                0, 0, new Color(60, 20, 20),
                800, 600, new Color(20, 20, 40)
        );
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, 800, 600);

        // Title
        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Arial", Font.BOLD, 80));
        g2d.drawString("GAME OVER", 150, 130);

        // Stats panel
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRoundRect(200, 200, 400, 200, 20, 20);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 32));
        g2d.drawString("Final Score", 310, 250);

        g2d.setColor(Color.YELLOW);
        g2d.setFont(new Font("Arial", Font.BOLD, 60));
        g2d.drawString("💰 " + finalScore, 290, 310);

        g2d.setColor(Color.CYAN);
        g2d.setFont(new Font("Arial", Font.BOLD, 40));
        g2d.drawString("📏 " + distance + "m", 300, 365);

        // Pulsing restart button
        float pulse = (float)(Math.sin(animTime * 3) * 0.2 + 0.8);
        g2d.setColor(new Color(0, 255, 0, (int)(200 * pulse)));
        g2d.setFont(new Font("Arial", Font.BOLD, (int)(28 * pulse)));
        g2d.drawString("Press SPACE to Restart", 220, 460);

        g2d.setColor(Color.GRAY);
        g2d.setFont(new Font("Arial", Font.PLAIN, 24));
        g2d.drawString("Press Q to Quit", 295, 510);
    }
}