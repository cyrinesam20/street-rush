package com.streetrush.powerups;

import com.streetrush.entities.Player;
import com.streetrush.decorators.DoubleCoinDecorator;
import java.awt.Color;

public class DoubleCoin extends PowerUp {
    public DoubleCoin(int lane, float z) {
        super(lane, z, new Color(255, 215, 0));
    }

    @Override
    protected String getLetterIndicator() {
        return "2X";
    }

    @Override
    public Player apply(Player player) {
        return new DoubleCoinDecorator(player);
    }
}