package com.asteroids;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public abstract class Character {
    private Polygon character;
    private Point2D movement;

    public Character(Polygon polygon, int x, int y) {
        this.character = polygon;
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

    public void move() {
        this.character.setTranslateX(this.character.getTranslateX() + this.movement.getX());
        this.character.setTranslateY(this.character.getTranslateY() + this.movement.getY());
    }

    public void accelerate() {
        double angleInRadians = Math.toRadians(this.character.getRotate());
        double changeX = Math.cos(angleInRadians) * 0.05;
        double changeY = Math.sin(angleInRadians) * 0.05;

        this.movement = this.movement.add(changeX, changeY);
    }

    public boolean collide(Character other) {
        Shape collisionArea = Shape.intersect(this.character, other.getCharacter());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }

    public Point2D getMovement() {
        return this.movement;
    }

    public void setMovement(Point2D movement) {
        this.movement = movement;
    }

}
