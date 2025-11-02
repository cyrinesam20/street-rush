package com.streetrush.factories;

import com.streetrush.powerups.*;
import com.streetrush.utils.GameLogger;
import java.util.Random;

public class RandomPowerUpFactory implements PowerUpFactory {
    private Random random = new Random();

    @Override
    public PowerUp createPowerUp(float z) {
        int type = random.nextInt(4);
        int lane = random.nextInt(3) - 1;

        PowerUp powerUp = switch (type) {
            case 0 -> new SpeedBoost(lane, z);
            case 1 -> new Magnet(lane, z);
            case 2 -> new Shield(lane, z);
            default -> new DoubleCoin(lane, z);
        };

        GameLogger.getInstance().logEvent(
                "Created " + powerUp.getClass().getSimpleName() + " at lane " + lane + ", z=" + z
        );

        return powerUp;
    }
}