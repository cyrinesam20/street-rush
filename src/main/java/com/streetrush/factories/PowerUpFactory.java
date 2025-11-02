package com.streetrush.factories;

import com.streetrush.powerups.PowerUp;

public interface PowerUpFactory {
    PowerUp createPowerUp(float z);
}