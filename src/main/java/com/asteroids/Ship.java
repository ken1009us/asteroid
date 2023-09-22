package com.asteroids;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class Ship {
    private Polygon character;
    private Point2D movement;

    public Ship(int x, int y) {
        this.character = new Polygon(-10, -10, 30, 0, -10, 10);
        this.character.setTranslateX(x);
        this.character.setTranslateY(y);
        this.movement = new Point2D(0, 0);

    }

    public Polygon getCharacter() {
        return this.character;

    }

    public void turnLeft() {
        this.character.setRotate(this.character.getRotate() - 5);

    }

    public void turnRight() {
        this.character.setRotate(this.character.getRotate() + 5);

    }

    public void accelerate() {
        double changeX = Math.cos(Math.toRadians(this.character.getRotate()));
        double changeY = Math.sin(Math.toRadians(this.character.getRotate()));

        changeX *= 0.05;
        changeY *= 0.05;

        this.movement = this.movement.add(changeX, changeY);
    }

    public void move() {
        this.character.setTranslateX(this.character.getTranslateX() + this.movement.getX());
        this.character.setTranslateY(this.character.getTranslateY() + this.movement.getY());
    }


}
