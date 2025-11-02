package com.streetrush.entities;

import com.streetrush.states.player.*;
import com.streetrush.utils.*;
import java.awt.*;

public class Player implements GameObject {
    protected Vector position;
    protected Vector velocity;
    protected int lane; // -1=gauche, 0=centre, 1=droite
    protected PlayerState currentState;
    protected int coins;
    protected boolean alive;
    protected float speed;
    protected float jumpVelocity;
    protected boolean isGrounded;

    // Animation
    protected float animationTime;
    protected float bobOffset;

    public Player() {
        position = new Vector(0, 1, 0);
        velocity = new Vector(0, 0, 0);
        lane = 0;
        currentState = new RunningState();
        coins = 0;
        alive = true;
        speed = 0.3f;
        jumpVelocity = 0;
        isGrounded = true;
        animationTime = 0;
        bobOffset = 0;
        GameLogger.getInstance().logInfo("Player created");
    }

    public void setState(PlayerState state) {
        String oldState = currentState.getName();
        currentState = state;
        GameLogger.getInstance().logState("Player: " + oldState + " -> " + state.getName());
    }

    public void moveLeft() {
        if (lane > -1 && isGrounded) {
            lane--;
            GameLogger.getInstance().logEvent("Player moved to lane " + lane);
        }
    }

    public void moveRight() {
        if (lane < 1 && isGrounded) {
            lane++;
            GameLogger.getInstance().logEvent("Player moved to lane " + lane);
        }
    }

    public void jump() {
        if (isGrounded && currentState instanceof RunningState) {
            setState(new JumpingState());
            jumpVelocity = 0.4f;
            isGrounded = false;
        }
    }

    public void slide() {
        if (isGrounded && currentState instanceof RunningState) {
            setState(new SlidingState());
        }
    }

    public void collectCoin() {
        coins++;
        GameLogger.getInstance().logEvent("Coin collected! Total: " + coins);
    }

    public void crash() {
        alive = false;
        setState(new CrashedState());
        GameLogger.getInstance().logEvent("Player crashed!");
    }

    @Override
    public void update() {
        currentState.update(this);

        // Mouvement automatique vers l'avant
        position.z += speed;

        // Smooth lane transition
        float targetX = lane * 2.0f;
        position.x += (targetX - position.x) * 0.2f;

        // Jump physics
        if (!isGrounded) {
            position.y += jumpVelocity;
            jumpVelocity -= 0.025f; // Gravité

            if (position.y <= 1.0f) {
                position.y = 1.0f;
                jumpVelocity = 0;
                isGrounded = true;
                if (currentState instanceof JumpingState) {
                    setState(new RunningState());
                }
            }
        }

        // Animation de course (bobbing)
        if (isGrounded && currentState instanceof RunningState) {
            animationTime += 0.3f;
            bobOffset = (float)(Math.sin(animationTime) * 0.1);
        }
    }

    @Override
    public void render(Graphics2D g, Renderer3D renderer) {
        // Ombre
        renderer.drawShadow(g, position, 1.2f);

        Vector renderPos = position.copy();
        renderPos.y += bobOffset;

        // Corps du joueur
        if (currentState instanceof SlidingState) {
            // Position basse pour slide
            renderPos.y = 0.6f;
            renderer.drawCube(g, renderPos, 0.8f, new Color(30, 144, 255));
        } else {
            // Position normale
            // Jambes
            Vector legPos = renderPos.copy();
            legPos.y -= 0.3f;
            renderer.drawCube(g, legPos, 0.6f, new Color(50, 50, 200));

            // Corps
            renderer.drawCube(g, renderPos, 0.8f, new Color(30, 144, 255));

            // Tête
            Vector headPos = renderPos.copy();
            headPos.y += 0.6f;
            renderer.drawSphere(g, headPos, 0.4f, new Color(255, 220, 177));
        }

        // Afficher l'état au-dessus du joueur
        Point screenPos = renderer.camera.project3DTo2D(new Vector(position.x, position.y + 1.5f, position.z));
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 12));
        String stateText = currentState.getName();
        g.drawString(stateText, screenPos.x - stateText.length() * 3, screenPos.y);
    }

    // ✅ MÉTHODE AJOUTÉE
    public Rectangle getBounds3D() {
        float size = (currentState instanceof SlidingState) ? 0.6f : 0.8f;
        float height = (currentState instanceof SlidingState) ? 0.6f : 1.2f;
        return new Rectangle(
                (int)((position.x - size/2) * 100),
                (int)((position.z - size/2) * 100),
                (int)(size * 100),
                (int)(height * 100)
        );
    }

    // Getters
    public Vector getPosition() { return position; }
    public Vector getVelocity() { return velocity; }
    public int getLane() { return lane; }
    public int getCoins() { return coins; }
    public boolean isAlive() { return alive; }
    public float getSpeed() { return speed; }
    public PlayerState getCurrentState() { return currentState; }
    public boolean isGrounded() { return isGrounded; }

    // Setters
    public void setSpeed(float speed) { this.speed = speed; }
    public void addCoins(int amount) { this.coins += amount; }
}