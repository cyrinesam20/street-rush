package com.streetrush.states.player;

import com.streetrush.entities.Player;

public class RunningState implements PlayerState {
    @Override
    public void handleInput(Player player) {}

    @Override
    public void update(Player player) {}

    @Override
    public String getName() {
        return "RUNNING";
    }
}