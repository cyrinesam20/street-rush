package com.streetrush.states.game;

import com.streetrush.main.Game;
import com.streetrush.utils.GameLogger;
import java.awt.*;
import java.awt.geom.*;

public class MenuState implements GameState {
    private Game game;
    private float animationTime = 0;

    public MenuState(Game game) {
        this.game = game;
    }

    @Override
    public void enter() {
        GameLogger.getInstance().logState("Game: -> MENU");
    }

    @Override
    public void exit() {}

    @Override
    public void handleInput() {}

    @Override
    public void update() {
        animationTime += 0.05f;
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Animated gradient background
        Color color1 = new Color(20, 20, 60);
        Color color2 = new Color(60, 20, 80);
        GradientPaint gradient = new GradientPaint(0, 0, color1, 800, 600, color2);
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, 800, 600);

        // Animated grid
        g2d.setColor(new Color(100, 100, 150, 50));
        g2d.setStroke(new BasicStroke(2));
        for (int i = 0; i < 800; i += 40) {
            int offset = (int)(Math.sin(animationTime + i * 0.01) * 20);
            g2d.drawLine(i, 0, i, 600 + offset);
        }

        // Title with glow
        g2d.setFont(new Font("Arial", Font.BOLD, 80));
        String title = "STREET RUSH 3D";

        // Glow effect
        for (int i = 5; i > 0; i--) {
            g2d.setColor(new Color(255, 215, 0, 30));
            g2d.drawString(title, 130 - i, 150 - i);
            g2d.drawString(title, 130 + i, 150 + i);
        }

        // Main title
        g2d.setColor(Color.YELLOW);
        g2d.drawString(title, 130, 150);

        // Subtitle
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.ITALIC, 24));
        g2d.drawString("Inspired by Subway Surfers", 245, 190);

        // Pulsing start button
        float pulse = (float)(Math.sin(animationTime * 3) * 0.2 + 0.8);
        g2d.setFont(new Font("Arial", Font.BOLD, (int)(32 * pulse)));
        g2d.setColor(new Color(0, 255, 0, (int)(200 * pulse)));
        g2d.drawString("Press SPACE to START", 210, 320);

        // Controls
        g2d.setColor(new Color(200, 200, 255));
        g2d.setFont(new Font("Arial", Font.BOLD, 22));
        g2d.drawString("🎮 Controls:", 320, 400);

        g2d.setFont(new Font("Arial", Font.PLAIN, 18));
        g2d.drawString("← →  Move Left/Right", 290, 435);
        g2d.drawString("↑  Jump", 340, 465);
        g2d.drawString("↓  Slide", 340, 495);
        g2d.drawString("ESC  Pause", 330, 525);

        // Version
        g2d.setColor(new Color(150, 150, 150));
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        g2d.drawString("Design Patterns Project v1.0", 620, 580);
    }
}
