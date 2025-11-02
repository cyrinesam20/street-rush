package com.streetrush.entities;

import com.streetrush.utils.Renderer3D;
import com.streetrush.utils.Vector;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Interface de base pour tous les objets du jeu
 */
public interface GameObject {
    void update();
    void render(Graphics2D g, Renderer3D renderer);

    // Méthode par défaut pour éviter de casser les implémentations
    default Vector getPosition() {
        return new Vector(0, 0, 0);
    }

    // Méthode par défaut pour isActive
    default boolean isActive() {
        return true;
    }

    // Méthode par défaut pour getBounds3D
    default Rectangle getBounds3D() {
        return new Rectangle(0, 0, 0, 0);
    }
}