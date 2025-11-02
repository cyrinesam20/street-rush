package com.streetrush.factories;

import com.streetrush.obstacles.Obstacle;

public interface ObstacleFactory {
    Obstacle createObstacle(float z);
}