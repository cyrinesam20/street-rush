package com.streetrush.composite;

import com.streetrush.entities.GameObject;
import com.streetrush.utils.Renderer3D;
import com.streetrush.utils.Vector;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * Composite Pattern - Gère un groupe de GameObjects
 */
public class GameObjectComposite implements GameObject {
    private List<GameObject> children = new ArrayList<>();

    public void add(GameObject obj) {
        if (obj != null) {
            children.add(obj);
        }
    }

    public void remove(GameObject obj) {
        children.remove(obj);
    }

    public List<GameObject> getChildren() {
        return children;
    }

    public void clear() {
        children.clear();
    }

    @Override
    public void update() {
        // Copie pour éviter ConcurrentModificationException
        List<GameObject> copy = new ArrayList<>(children);
        for (GameObject child : copy) {
            child.update();
        }
    }

    @Override
    public void render(Graphics2D g, Renderer3D renderer) {
        // Tri par profondeur Z (loin vers près)
        List<GameObject> sorted = new ArrayList<>(children);
        sorted.sort((a, b) -> {
            float zA = a.getPosition().z;
            float zB = b.getPosition().z;
            return Float.compare(zB, zA); // Far to near
        });

        for (GameObject child : sorted) {
            child.render(g, renderer);
        }
    }

    @Override
    public Vector getPosition() {
        // Position par défaut pour le composite
        return new Vector(0, 0, 0);
    }

    @Override
    public boolean isActive() {
        // Un composite est toujours actif
        return true;
    }

    @Override
    public Rectangle getBounds3D() {
        // Un composite n'a pas de bounds propres
        return new Rectangle(0, 0, 0, 0);
    }

    public int size() {
        return children.size();
    }

    public boolean isEmpty() {
        return children.isEmpty();
    }
}