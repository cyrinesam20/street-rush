package com.streetrush.states.player;

import com.streetrush.entities.Player;

public interface PlayerState {
    void handleInput(Player player);
    void update(Player player);
    String getName();
}