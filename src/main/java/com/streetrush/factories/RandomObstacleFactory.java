package com.streetrush.factories;

import com.streetrush.obstacles.*;
import com.streetrush.utils.GameLogger;
import java.util.Random;

public class RandomObstacleFactory implements ObstacleFactory {
    private Random random = new Random();

    @Override
    public Obstacle createObstacle(float z) {
        int type = random.nextInt(3);
        int lane = random.nextInt(3) - 1; // -1, 0, 1

        Obstacle obstacle = switch (type) {
            case 0 -> new Barrier(lane, z);
            case 1 -> new Train(lane, z);
            default -> new Cone(lane, z);
        };

        GameLogger.getInstance().logEvent(
                "Created " + obstacle.getClass().getSimpleName() + " at lane " + lane + ", z=" + z
        );

        return obstacle;
    }
}
