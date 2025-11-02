package com.streetrush.powerups;

import com.streetrush.entities.Player;
import com.streetrush.decorators.ShieldDecorator;
import java.awt.Color;

public class Shield extends PowerUp {
    public Shield(int lane, float z) {
        super(lane, z, Color.CYAN);
    }

    @Override
    protected String getLetterIndicator() {
        return "H";
    }

    @Override
    public Player apply(Player player) {
        return new ShieldDecorator(player);
    }
}