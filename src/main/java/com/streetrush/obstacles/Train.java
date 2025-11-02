package com.streetrush.obstacles;

import com.streetrush.utils.*;
import java.awt.*;

public class Train extends Obstacle {
    public Train(int lane, float z) {
        super(lane, z, 1.5f, 2.5f);
    }

    @Override
    public void render(Graphics2D g, Renderer3D renderer) {
        if (!active) return;

        renderer.drawShadow(g, position, width);

        // Corps du train
        Vector bodyPos = position.copy();
        renderer.drawCube(g, bodyPos, width, new Color(139, 69, 19));

        // Fenêtre
        Vector windowPos = position.copy();
        windowPos.y += 0.5f;
        renderer.drawCube(g, windowPos, 0.8f, new Color(135, 206, 250));

        // Toit
        Vector roofPos = position.copy();
        roofPos.y += 1.0f;
        renderer.drawCube(g, roofPos, width * 0.9f, new Color(100, 50, 20));
    }
}
