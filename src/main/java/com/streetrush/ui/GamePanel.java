package com.streetrush.ui;

import com.streetrush.main.Game;
import com.streetrush.states.game.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel {
    private Game game;

    public GamePanel(Game game) {
        this.game = game;
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        setFocusable(true);
        addKeyListener(new GameKeyListener());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        GameState currentState = game.getCurrentState();
        if (currentState != null) {
            currentState.render(g2d);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private class GameKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            GameState state = game.getCurrentState();

            if (state == null) return;

            // Menu State
            if (state instanceof MenuState) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    game.changeState(new PlayingState(game));
                }
            }

            // Playing State
            else if (state instanceof PlayingState playingState) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        playingState.getPlayer().moveLeft();
                        break;
                    case KeyEvent.VK_RIGHT:
                        playingState.getPlayer().moveRight();
                        break;
                    case KeyEvent.VK_UP:
                        playingState.getPlayer().jump();
                        break;
                    case KeyEvent.VK_DOWN:
                        playingState.getPlayer().slide();
                        break;
                    case KeyEvent.VK_ESCAPE:
                        game.changeState(new PausedState(game, playingState));
                        break;
                }
            }

            // Paused State
            else if (state instanceof PausedState pausedState) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    game.changeState(pausedState.getPreviousState());
                } else if (e.getKeyCode() == KeyEvent.VK_Q) {
                    System.exit(0);
                }
            }

            // Game Over State
            else if (state instanceof GameOverState) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    game.changeState(new PlayingState(game));
                } else if (e.getKeyCode() == KeyEvent.VK_Q) {
                    System.exit(0);
                }
            }
        }
    }
}