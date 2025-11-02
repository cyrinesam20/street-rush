package com.streetrush.obstacles;

import com.streetrush.utils.*;
import java.awt.*;

public class Barrier extends Obstacle {
    public Barrier(int lane, float z) {
        super(lane, z, 1.2f, 1.5f);
    }

    @Override
    public void render(Graphics2D g, Renderer3D renderer) {
        if (!active) return;

        // Barrière rouge et blanche
        renderer.drawShadow(g, position, width);

        // Motif de construction
        float segmentHeight = height / 3;
        for (int i = 0; i < 3; i++) {
            Color color = (i % 2 == 0) ? Color.RED : Color.WHITE;
            float yPos = position.y - height/2 + segmentHeight * i + segmentHeight/2;
            renderer.drawCube(g,
                    new Vector(position.x, yPos, position.z),
                    width * 0.8f,
                    color
            );
        }
    }
}