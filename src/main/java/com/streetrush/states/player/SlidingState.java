package com.streetrush.states.player;

import com.streetrush.entities.Player;

public class SlidingState implements PlayerState {
    private int slideTimer = 0;
    private static final int SLIDE_DURATION = 30;

    @Override
    public void handleInput(Player player) {}

    @Override
    public void update(Player player) {
        slideTimer++;
        if (slideTimer >= SLIDE_DURATION && player.isGrounded()) {
            player.setState(new RunningState());
        }
    }

    @Override
    public String getName() {
        return "SLIDING";
    }
}