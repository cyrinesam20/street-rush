package com.streetrush.utils;

import java.awt.*;

public class Camera3D {
    private Vector position;
    private Vector target;
    private int screenWidth;
    private int screenHeight;

    public Camera3D(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.position = new Vector(0, 5, -10);
        this.target = new Vector(0, 0, 0);
    }

    public Point project3DTo2D(Vector worldPos) {
        // Position relative à la caméra
        float relX = worldPos.x - position.x;
        float relY = worldPos.y - position.y;
        float relZ = worldPos.z - position.z;

        // Protection
        if (relZ < 0.5f) relZ = 0.5f;

        // Projection simple et efficace
        float scale = 300f / (relZ + 5f);

        float screenX = screenWidth / 2f + (relX * scale);
        float screenY = screenHeight * 0.65f - (relY * scale);

        return new Point((int)screenX, (int)screenY);
    }

    public float getScale(float z) {
        float relZ = z - position.z;
        if (relZ < 0.5f) relZ = 0.5f;
        return 300f / (relZ + 5f);
    }

    public void update(Vector playerPos) {
        // Suit le joueur
        position.x = playerPos.x * 0.3f;
        position.z = playerPos.z - 10;
        target = playerPos;
    }

    public Vector getPosition() {
        return position;
    }
}