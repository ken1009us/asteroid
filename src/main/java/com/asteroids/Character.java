package com.asteroids;

import javafx.geometry.Point2D;
import javafx.scene.control.Control;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public abstract class Character extends Control {
    private Polygon character;
    private Point2D movement;
    private boolean alive;

    public Character(Polygon polygon, int x, int y) {
        this.character = polygon;
        this.character.setTranslateX(x);
        this.character.setTranslateY(y);
        this.movement = new Point2D(0, 0);
        this.alive = true;

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
        double newX = this.character.getTranslateX() + this.movement.getX();
        double newY = this.character.getTranslateY() + this.movement.getY();

        if (newX < 0) {
            newX += AsteroidsApplication.WIDTH;
        } else if (newX > AsteroidsApplication.WIDTH) {
            newX %= AsteroidsApplication.WIDTH;
        }

        if (newY < 0) {
            newY += AsteroidsApplication.HEIGHT;
        } else if (newY > AsteroidsApplication.HEIGHT) {
            newY %= AsteroidsApplication.HEIGHT;
        }

        this.character.setTranslateX(newX);
        this.character.setTranslateY(newY);
    }

    public void accelerate() {
        double angleInRadians = Math.toRadians(this.character.getRotate());
        double changeX = Math.cos(angleInRadians) * 0.03;
        double changeY = Math.sin(angleInRadians) * 0.03;

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

    public boolean isAlive() {
        return this.alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setFill(Color color) {
        this.character.setFill(color);
    }

}
