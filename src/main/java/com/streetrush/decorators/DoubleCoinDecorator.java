package com.streetrush.decorators;

import com.streetrush.entities.Player;
import com.streetrush.utils.GameLogger;
import com.streetrush.utils.Renderer3D;
import java.awt.*;

public class DoubleCoinDecorator extends PlayerDecorator {
    public DoubleCoinDecorator(Player player, float duration) {
        super(player, duration);
        GameLogger.getInstance().logDecorator(
                String.format("DoubleCoin applied (duration: %.1fs)", duration)
        );
    }

    @Override
    public void collectCoin() {
        // Double les pièces
        wrappedPlayer.collectCoin();
        wrappedPlayer.collectCoin();
        GameLogger.getInstance().logCollision("Coin collected (×2 bonus!)");
    }

    @Override
    protected void onExpire() {
        GameLogger.getInstance().logDecorator("DoubleCoin removed (expired)");
    }

    @Override
    protected void renderDecoratorEffect(Graphics2D g, Renderer3D renderer) {
        // Étoiles dorées qui tournent
        Point screenPos = renderer.camera.project3DTo2D(wrappedPlayer.getPosition());

        g.setColor(new Color(255, 215, 0, 150));
        for (int i = 0; i < 4; i++) {
            double angle = System.currentTimeMillis() * 0.003 + (i * Math.PI / 2);
            int x = screenPos.x + (int)(Math.cos(angle) * 30);
            int y = screenPos.y + (int)(Math.sin(angle) * 30);
            g.fillOval(x - 5, y - 5, 10, 10);
        }
    }
}