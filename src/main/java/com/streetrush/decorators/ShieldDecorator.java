package com.streetrush.decorators;

import com.streetrush.entities.Player;
import com.streetrush.utils.GameLogger;
import com.streetrush.utils.Renderer3D;
import java.awt.*;

public class ShieldDecorator extends PlayerDecorator {
    private boolean shieldActive = true;

    public ShieldDecorator(Player player, float duration) {
        super(player, duration);
        GameLogger.getInstance().logDecorator(
                String.format("Shield applied (duration: %.1fs)", duration)
        );
    }

    @Override
    public void crash() {
        if (shieldActive) {
            GameLogger.getInstance().logDecorator("Shield absorbed damage!");
            shieldActive = false;
            remainingTime = 0; // Retire immédiatement
        } else {
            wrappedPlayer.crash();
        }
    }

    @Override
    protected void onExpire() {
        if (shieldActive) {
            GameLogger.getInstance().logDecorator("Shield removed (expired)");
        }
    }

    @Override
    protected void renderDecoratorEffect(Graphics2D g, Renderer3D renderer) {
        if (!shieldActive) return;

        // Aura bleue autour du joueur
        Point screenPos = renderer.camera.project3DTo2D(wrappedPlayer.getPosition());
        float scale = renderer.camera.getScale(wrappedPlayer.getPosition().z);
        int size = (int)(1.2f * scale * 100);

        g.setColor(new Color(0, 255, 255, 80));
        g.setStroke(new BasicStroke(3));
        g.drawOval(screenPos.x - size/2, screenPos.y - size/2, size, size);
    }
}