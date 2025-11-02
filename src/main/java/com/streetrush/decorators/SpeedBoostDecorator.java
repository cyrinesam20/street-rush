package com.streetrush.decorators;

import com.streetrush.entities.Player;
import com.streetrush.utils.GameLogger;
import com.streetrush.utils.Renderer3D;
import java.awt.*;

public class SpeedBoostDecorator extends PlayerDecorator {
    private float speedMultiplier = 1.5f;

    public SpeedBoostDecorator(Player player, float duration) {
        super(player, duration);
        GameLogger.getInstance().logDecorator(
                String.format("SpeedBoost applied (duration: %.1fs)", duration)
        );
    }

    @Override
    public float getSpeed() {
        return wrappedPlayer.getSpeed() * speedMultiplier;
    }

    @Override
    protected void onExpire() {
        GameLogger.getInstance().logDecorator("SpeedBoost removed (expired)");
    }

    @Override
    protected void renderDecoratorEffect(Graphics2D g, Renderer3D renderer) {
        // Traînée jaune derrière le joueur
        Point screenPos = renderer.camera.project3DTo2D(wrappedPlayer.getPosition());

        g.setColor(new Color(255, 255, 0, 100));
        for (int i = 0; i < 3; i++) {
            int offset = (i + 1) * 10;
            g.fillOval(screenPos.x - 10, screenPos.y + offset, 20, 10);
        }
    }
}