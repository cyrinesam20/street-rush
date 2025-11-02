package com.streetrush.decorators;

import com.streetrush.entities.Player;
import com.streetrush.states.player.PlayerState;
import com.streetrush.utils.*;
import java.awt.*;

/**
 * Decorator Pattern - Décorateur de Player
 * Hérite de Player pour garder la compatibilité
 */
public abstract class PlayerDecorator extends Player {
    protected Player decoratedPlayer;

    public PlayerDecorator(Player player) {
        this.decoratedPlayer = player;
        GameLogger.getInstance().logDecorator(
                this.getClass().getSimpleName() + " applied to Player"
        );
    }

    @Override
    public void update() {
        decoratedPlayer.update();
    }

    @Override
    public void render(Graphics2D g, Renderer3D renderer) {
        decoratedPlayer.render(g, renderer);
    }

    @Override
    public Vector getPosition() {
        return decoratedPlayer.getPosition();
    }

    @Override
    public Vector getVelocity() {
        return decoratedPlayer.getVelocity();
    }

    @Override
    public int getLane() {
        return decoratedPlayer.getLane();
    }

    @Override
    public int getCoins() {
        return decoratedPlayer.getCoins();
    }

    @Override
    public boolean isAlive() {
        return decoratedPlayer.isAlive();
    }

    @Override
    public Rectangle getBounds3D() {
        return decoratedPlayer.getBounds3D();
    }

    @Override
    public void moveLeft() {
        decoratedPlayer.moveLeft();
    }

    @Override
    public void moveRight() {
        decoratedPlayer.moveRight();
    }

    @Override
    public void jump() {
        decoratedPlayer.jump();
    }

    @Override
    public void slide() {
        decoratedPlayer.slide();
    }

    @Override
    public void crash() {
        decoratedPlayer.crash();
    }

    @Override
    public void setState(PlayerState state) {
        decoratedPlayer.setState(state);
    }

    @Override
    public PlayerState getCurrentState() {
        return decoratedPlayer.getCurrentState();
    }

    @Override
    public boolean isGrounded() {
        return decoratedPlayer.isGrounded();
    }

    @Override
    public float getSpeed() {
        return decoratedPlayer.getSpeed();
    }

    @Override
    public void setSpeed(float speed) {
        decoratedPlayer.setSpeed(speed);
    }

    @Override
    public void addCoins(int amount) {
        decoratedPlayer.addCoins(amount);
    }

    @Override
    public void collectCoin() {
        decoratedPlayer.collectCoin();
    }
}