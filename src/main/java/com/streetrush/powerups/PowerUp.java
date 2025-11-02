package com.streetrush.powerups;

import com.streetrush.entities.*;
import com.streetrush.utils.*;
import java.awt.*;

public abstract class PowerUp implements GameObject {
    protected Vector position;
    protected int lane;
    protected boolean active;
    protected float rotationAngle;
    protected Color color;

    // Constructeur à 3 paramètres (standard)
    public PowerUp(int lane, float z, Color color) {
        this.lane = lane;
        this.position = new Vector(lane * 2.0f, 1.2f, z);
        this.active = true;
        this.rotationAngle = 0;
        this.color = color;
    }

    @Override
    public void update() {
        rotationAngle += 3;
        if (rotationAngle >= 360) rotationAngle = 0;

        // Float effect
        position.y = 1.2f + (float)(Math.sin(rotationAngle * Math.PI / 180) * 0.15);
    }

    @Override
    public void render(Graphics2D g, Renderer3D renderer) {
        if (!active) return;

        // Power-up avec effet de glow
        renderer.drawSphere(g, position, 0.4f, color);

        // Glow effect
        Point screenPos = renderer.camera.project3DTo2D(position);
        float scale = renderer.camera.getScale(position.z);
        int size = (int)(0.8f * scale * 100);

        // Outer glow
        g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 50));
        g.fillOval(screenPos.x - size/2, screenPos.y - size/2, size, size);

        // Inner bright
        g.setColor(new Color(255, 255, 255, 180));
        g.fillOval(screenPos.x - size/6, screenPos.y - size/6, size/3, size/3);

        // Letter indicator
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, (int)(scale * 20)));
        String letter = getLetterIndicator();
        int textWidth = g.getFontMetrics().stringWidth(letter);
        g.drawString(letter, screenPos.x - textWidth/2, screenPos.y + 5);
    }

    protected abstract String getLetterIndicator();

    @Override
    public Rectangle getBounds3D() {
        return new Rectangle(
                (int)((position.x - 0.4f) * 100),
                (int)((position.z - 0.4f) * 100),
                80, 80
        );
    }

    @Override
    public Vector getPosition() {
        return position;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public abstract Player apply(Player player);
}