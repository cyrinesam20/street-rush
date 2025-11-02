package com.streetrush.obstacles;

import com.streetrush.entities.GameObject;
import com.streetrush.utils.Renderer3D;
import com.streetrush.utils.Vector;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class Obstacle implements GameObject {
    protected Vector position;
    protected boolean active;
    protected float height;
    protected float width;

    public Obstacle(float x, float y, float z, float height, float width) {
        this.position = new Vector(x, y, z);
        this.active = true;
        this.height = height;
        this.width = width;
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

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    @Override
    public Rectangle getBounds3D() {
        return new Rectangle(
                (int)(position.x - width/2),
                (int)position.y,
                (int)width,
                (int)height
        );
    }

    @Override
    public void update() {
        // Les obstacles ne bougent pas, c'est le joueur qui avance
    }

    @Override
    public abstract void render(Graphics2D g, Renderer3D renderer);
}