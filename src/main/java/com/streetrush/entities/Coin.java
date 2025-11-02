package com.streetrush.entities;

import com.streetrush.utils.Renderer3D;
import com.streetrush.utils.Vector;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class Coin implements GameObject {
    private Vector position;
    private boolean active;
    private int value;

    public Coin(int lane, float z) {
        this.position = new Vector(lane * 2f, 0.5f, z);
        this.active = true;
        this.value = 10;
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

    public int getValue() {
        return value;
    }

    @Override
    public Rectangle getBounds3D() {
        return new Rectangle(
                (int)(position.x - 0.3f),
                (int)(position.y - 0.3f),
                1,
                1
        );
    }

    @Override
    public void update() {
        // Les pièces ne bougent pas
    }

    @Override
    public void render(Graphics2D g, Renderer3D renderer) {
        if (!active) return;

        // Utiliser le renderer existant pour dessiner une sphère dorée
        renderer.drawSphere(g, position, 0.3f, Color.YELLOW);
    }
}