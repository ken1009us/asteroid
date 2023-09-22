package com.asteroids;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Ship extends Character {
    public Ship(int x, int y) {
        super(new Polygon( 20, 0, -10, 10, -4, 5, -4, -5, -10, -10), x ,y);
        super.setFill((Color.CORNFLOWERBLUE));
    }
}
