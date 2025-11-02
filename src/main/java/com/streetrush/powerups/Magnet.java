package com.streetrush.powerups;

import com.streetrush.entities.Player;
import com.streetrush.decorators.MagnetDecorator;
import java.awt.Color;

public class Magnet extends PowerUp {
    public Magnet(int lane, float z) {
        super(lane, z, new Color(255, 0, 255));
    }

    @Override
    protected String getLetterIndicator() {
        return "M";
    }

    @Override
    public Player apply(Player player) {
        return new MagnetDecorator(player);
    }
}