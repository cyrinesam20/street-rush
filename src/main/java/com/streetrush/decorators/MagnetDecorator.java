package com.streetrush.decorators;

import com.streetrush.entities.Player;
import com.streetrush.utils.GameLogger;
import com.streetrush.utils.Renderer3D;
import java.awt.*;

public class MagnetDecorator extends PlayerDecorator {
    private float magnetRange = 5.0f;

    public MagnetDecorator(Player player, float duration) {
        super(player, duration);
        GameLogger.getInstance().logDecorator(
                String.format("Magnet applied (duration: %.1fs, range: %.1f)", duration, magnetRange)
        );
    }

    public float getMagnetRange() {
        return magnetRange;
    }

    @Override
    protected void onExpire() {
        GameLogger.getInstance().logDecorator("Magnet removed (expired)");
    }

    @Override
    protected void renderDecoratorEffect(Graphics2D g, Renderer3D renderer) {
        // Cercle violet pulsant
        Point screenPos = renderer.camera.project3DTo2D(wrappedPlayer.getPosition());
        float scale = renderer.camera.getScale(wrappedPlayer.getPosition().z);
        int size = (int)(magnetRange * scale * 40);

        float pulse = (float)(Math.sin(System.currentTimeMillis() * 0.005) * 0.3 + 0.7);
        g.setColor(new Color(255, 0, 255, (int)(50 * pulse)));
        g.fillOval(screenPos.x - size/2, screenPos.y - size/2, size, size);
    }
}