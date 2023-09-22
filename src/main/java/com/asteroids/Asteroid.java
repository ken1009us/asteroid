package com.asteroids;

import javafx.scene.shape.Polygon;

import java.util.Random;

public class Asteroid extends Character {
    private double rotationalMovement;
    private boolean alive;
    public Asteroid(int x, int y) {
        super(new PolygonFactory().createPolygon(), x, y);

        Random rand = new Random();

        super.getCharacter().setRotate(rand.nextInt(360));

        int accelerationAmount = 1 + rand.nextInt(10);
        for (int i = 0; i < accelerationAmount; i++) {
            accelerate();
        }

        this.alive = true;
        this.rotationalMovement = 0.5 - rand.nextDouble();
    }

    public boolean isAlive() {
        return this.alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    @Override
    public void move() {
        super.move();
        this.getCharacter().setRotate(super.getCharacter().getRotate() + rotationalMovement);
    }
}
