package com.streetrush.states.game;

import com.streetrush.main.Game;
import com.streetrush.utils.GameLogger;
import java.awt.*;

public class PausedState implements GameState {
    private Game game;
    private GameState previousState;

    public PausedState(Game game, GameState previousState) {
        this.game = game;
        this.previousState = previousState;
    }

    @Override
    public void enter() {
        GameLogger.getInstance().logState("Game: PLAYING -> PAUSED");
    }

    @Override
    public void exit() {
        GameLogger.getInstance().logState("Game: PAUSED -> PLAYING");
    }

    @Override
    public void handleInput() {}

    @Override
    public void update() {}

    @Override
    public void render(Graphics g) {
        if (previousState != null) {
            previousState.render(g);
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(0, 0, 0, 180));
        g2d.fillRect(0, 0, 800, 600);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 70));
        g2d.drawString("⏸️ PAUSED", 240, 250);

        g2d.setFont(new Font("Arial", Font.PLAIN, 28));
        g2d.drawString("Press ESC to Resume", 250, 330);
        g2d.drawString("Press Q to Quit", 290, 380);
    }

    public GameState getPreviousState() {
        return previousState;
    }
}
