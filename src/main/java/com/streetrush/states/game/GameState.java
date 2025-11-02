package com.streetrush.states.game;

import java.awt.Graphics;

public interface GameState {
    void handleInput();
    void update();
    void render(Graphics g);
    void enter();
    void exit();
}
