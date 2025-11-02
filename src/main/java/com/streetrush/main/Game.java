package com.streetrush.main;

import com.streetrush.states.game.*;
import com.streetrush.ui.GamePanel;
import com.streetrush.utils.GameLogger;
import javax.swing.*;

public class Game {
    private JFrame frame;
    private GamePanel gamePanel;
    private GameState currentState;
    private boolean running;

    public Game() {
        GameLogger.getInstance().logInfo("Street Rush 3D initialized");
        initializeWindow();
        currentState = new MenuState(this);
        currentState.enter();
    }

    private void initializeWindow() {
        frame = new JFrame("Street Rush 3D - Design Patterns Game");
        gamePanel = new GamePanel(this);

        frame.add(gamePanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        gamePanel.requestFocusInWindow();
    }

    public void changeState(GameState newState) {
        if (currentState != null) {
            currentState.exit();
        }
        currentState = newState;
        currentState.enter();
        gamePanel.repaint();
    }

    public void run() {
        running = true;
        GameLogger.getInstance().logInfo("Game loop started");

        gamePanel.repaint();

        Thread gameThread = new Thread(() -> {
            long lastTime = System.nanoTime();
            double nsPerTick = 1000000000.0 / 60.0;
            double delta = 0;
            int frames = 0;
            long timer = System.currentTimeMillis();

            while (running) {
                long now = System.nanoTime();
                delta += (now - lastTime) / nsPerTick;
                lastTime = now;

                if (delta >= 1) {
                    update();
                    gamePanel.repaint();
                    frames++;
                    delta--;
                }

                // FPS counter (optional log)
                if (System.currentTimeMillis() - timer > 1000) {
                    timer += 1000;
                    GameLogger.getInstance().logInfo("FPS: " + frames);
                    frames = 0;
                }

                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            GameLogger.getInstance().logInfo("Game stopped");
            GameLogger.getInstance().close();
        });

        gameThread.start();
    }

    private void update() {
        if (currentState != null) {
            currentState.update();
        }
    }

    public void stop() {
        running = false;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public static void main(String[] args) {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            Game game = new Game();
            game.run();
        });
   }
}