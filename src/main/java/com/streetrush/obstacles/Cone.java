package com.streetrush.obstacles;

import com.streetrush.utils.*;
import java.awt.*;

public class Cone extends Obstacle {
    public Cone(int lane, float z) {
        super(lane, z, 0.8f, 1.0f);
    }

    @Override
    public void render(Graphics2D g, Renderer3D renderer) {
        if (!active) return;

        renderer.drawShadow(g, position, width);

        // Base du cône
        Vector basePos = position.copy();
        basePos.y = 0.15f;
        renderer.drawCube(g, basePos, 0.6f, Color.DARK_GRAY);

        // Cône orange
        renderer.drawCylinder(g, position, width/2, height, Color.ORANGE);

        // Bande blanche
        Vector stripePos = position.copy();
        stripePos.y += 0.2f;
        renderer.drawCylinder(g, stripePos, width/2 + 0.05f, 0.3f, Color.WHITE);
    }
}