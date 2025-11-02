package com.streetrush.utils;

import java.awt.*;
import java.awt.geom.*;

public class Renderer3D {
    public Camera3D camera; // ← CHANGÉ de private à public

    public Renderer3D(Camera3D camera) {
        this.camera = camera;
    }

    public void drawCube(Graphics2D g, Vector center, float size, Color color) {
        // 8 sommets du cube
        Vector[] vertices = {
                new Vector(center.x - size/2, center.y - size/2, center.z - size/2),
                new Vector(center.x + size/2, center.y - size/2, center.z - size/2),
                new Vector(center.x + size/2, center.y + size/2, center.z - size/2),
                new Vector(center.x - size/2, center.y + size/2, center.z - size/2),
                new Vector(center.x - size/2, center.y - size/2, center.z + size/2),
                new Vector(center.x + size/2, center.y - size/2, center.z + size/2),
                new Vector(center.x + size/2, center.y + size/2, center.z + size/2),
                new Vector(center.x - size/2, center.y + size/2, center.z + size/2)
        };

        // Projeter les sommets en 2D
        Point[] points = new Point[8];
        for (int i = 0; i < 8; i++) {
            points[i] = camera.project3DTo2D(vertices[i]);
        }

        // Dessiner les faces (avec dégradé pour effet 3D)
        drawFace(g, points, new int[]{0,1,2,3}, color.darker().darker()); // Face avant
        drawFace(g, points, new int[]{1,5,6,2}, color.darker()); // Face droite
        drawFace(g, points, new int[]{4,5,6,7}, color); // Face arrière
        drawFace(g, points, new int[]{0,4,7,3}, color.darker()); // Face gauche
        drawFace(g, points, new int[]{3,2,6,7}, color.brighter()); // Face dessus
        drawFace(g, points, new int[]{0,1,5,4}, color.darker().darker()); // Face dessous
    }

    private void drawFace(Graphics2D g, Point[] points, int[] indices, Color color) {
        int[] xPoints = new int[4];
        int[] yPoints = new int[4];

        for (int i = 0; i < 4; i++) {
            xPoints[i] = points[indices[i]].x;
            yPoints[i] = points[indices[i]].y;
        }

        g.setColor(color);
        g.fillPolygon(xPoints, yPoints, 4);
        g.setColor(color.darker());
        g.drawPolygon(xPoints, yPoints, 4);
    }

    public void drawCylinder(Graphics2D g, Vector center, float radius, float height, Color color) {
        // Base
        Point bottom = camera.project3DTo2D(new Vector(center.x, center.y - height/2, center.z));
        Point top = camera.project3DTo2D(new Vector(center.x, center.y + height/2, center.z));

        float scale = camera.getScale(center.z);
        int width = (int)(radius * 2 * scale * 100);
        int h = Math.abs(top.y - bottom.y);

        // Dégradé
        GradientPaint gradient = new GradientPaint(
                bottom.x, bottom.y, color.darker(),
                bottom.x, top.y, color.brighter()
        );
        g.setPaint(gradient);

        // Corps du cylindre
        g.fillRoundRect(bottom.x - width/2, top.y, width, h, width/3, width/3);

        // Contour
        g.setColor(color.darker().darker());
        g.setStroke(new BasicStroke(2));
        g.drawRoundRect(bottom.x - width/2, top.y, width, h, width/3, width/3);
    }

    public void drawSphere(Graphics2D g, Vector center, float radius, Color color) {
        Point p = camera.project3DTo2D(center);
        float scale = camera.getScale(center.z);
        int size = (int)(radius * 2 * scale * 100);

        // Effet de sphère avec dégradé radial
        int x = p.x - size/2;
        int y = p.y - size/2;

        // Ombre
        g.setColor(new Color(0, 0, 0, 50));
        g.fillOval(x + 2, y + 2, size, size);

        // Dégradé radial pour effet 3D
        RadialGradientPaint gradient = new RadialGradientPaint(
                new Point2D.Float(p.x - size/4, p.y - size/4),
                size/2,
                new float[]{0f, 1f},
                new Color[]{color.brighter().brighter(), color.darker()}
        );
        g.setPaint(gradient);
        g.fillOval(x, y, size, size);

        // Reflet
        g.setColor(new Color(255, 255, 255, 100));
        g.fillOval(x + size/4, y + size/4, size/3, size/3);
    }

    public void drawGround(Graphics2D g, int screenWidth, int screenHeight, float scrollOffset) {
        // Dégradé du ciel
        GradientPaint skyGradient = new GradientPaint(
                0, 0, new Color(135, 206, 250),
                0, screenHeight/2, new Color(70, 130, 180)
        );
        g.setPaint(skyGradient);
        g.fillRect(0, 0, screenWidth, screenHeight/2);

        // Sol
        g.setColor(new Color(40, 40, 40));
        g.fillRect(0, screenHeight/2, screenWidth, screenHeight/2);

        // Grille perspective
        g.setColor(new Color(60, 60, 60));
        g.setStroke(new BasicStroke(2));

        // Lignes horizontales (profondeur)
        for (int i = 0; i < 20; i++) {
            float z = i * 5 + (scrollOffset % 5);
            Vector leftPoint = new Vector(-10, 0, z);
            Vector rightPoint = new Vector(10, 0, z);

            Point left = camera.project3DTo2D(leftPoint);
            Point right = camera.project3DTo2D(rightPoint);

            // Fade avec la distance
            int alpha = Math.max(0, 255 - i * 12);
            g.setColor(new Color(80, 80, 80, alpha));
            g.drawLine(left.x, left.y, right.x, right.y);
        }

        // Lignes verticales (voies)
        for (int lane = -1; lane <= 1; lane++) {
            g.setColor(new Color(100, 100, 100));
            g.setStroke(new BasicStroke(3));

            Vector near = new Vector(lane * 2, 0, -5);
            Vector far = new Vector(lane * 2, 0, 50);

            Point nearPoint = camera.project3DTo2D(near);
            Point farPoint = camera.project3DTo2D(far);

            g.drawLine(nearPoint.x, nearPoint.y, farPoint.x, farPoint.y);
        }

        // Lignes de séparation jaunes
        g.setColor(Color.YELLOW);
        g.setStroke(new BasicStroke(4));
        for (int lane = -1; lane <= 2; lane++) {
            if (lane == 0 || lane == 1) continue; // Skip centre

            float x = lane * 2 - 1;
            Vector near = new Vector(x, 0, -5);
            Vector far = new Vector(x, 0, 50);

            Point nearPoint = camera.project3DTo2D(near);
            Point farPoint = camera.project3DTo2D(far);

            // Ligne en pointillés
            float[] dash = {20f, 20f};
            g.setStroke(new BasicStroke(4, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f, dash, scrollOffset % 40));
            g.drawLine(nearPoint.x, nearPoint.y, farPoint.x, farPoint.y);
        }
    }

    public void drawShadow(Graphics2D g, Vector position, float size) {
        Vector shadowPos = new Vector(position.x, 0.01f, position.z);
        Point p = camera.project3DTo2D(shadowPos);
        float scale = camera.getScale(shadowPos.z);
        int shadowSize = (int)(size * scale * 100);

        // Ombre elliptique
        g.setColor(new Color(0, 0, 0, 80));
        g.fillOval(p.x - shadowSize/2, p.y - shadowSize/4, shadowSize, shadowSize/2);
    }
}