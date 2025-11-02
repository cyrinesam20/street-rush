package com.streetrush.powerups;

import com.streetrush.entities.Player;
import com.streetrush.decorators.SpeedBoostDecorator;
import java.awt.Color;

public class SpeedBoost extends PowerUp {
    public SpeedBoost(int lane, float z) {
        super(lane, z, Color.YELLOW);
    }

    @Override
    protected String getLetterIndicator() {
        return "S";
    }

    @Override
    public Player apply(Player player) {
        return new SpeedBoostDecorator(player);
    }
}