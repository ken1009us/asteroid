package com.asteroids;

import javafx.scene.shape.Polygon;

import java.util.Random;

public class PolygonFactory {

    public Polygon createPolygon() {
        Random rand = new Random();

        // Increase the number of vertices to approximate a circle
        int numPoints = 16 + rand.nextInt(5);
        // Randomly determine the size
        double size = 10 + rand.nextInt(30);

        Polygon asteroid = new Polygon();
        double[] angles = new double[numPoints];

        // Evenly distribute angles to approximate a circle
        for (int i = 0; i < numPoints; i++) {
            double angle = 2 * Math.PI * i / numPoints;
            angles[i] = angle + rand.nextDouble() * Math.PI / 6;
        }

        // Generate vertices based on angles
        for (int i = 0; i < numPoints; i++) {
            double x = size * Math.cos(angles[i]);
            double y = size * Math.sin(angles[i]);
            asteroid.getPoints().addAll(x, y);
        }

        // Randomly offset vertex positions
        for (int i = 0; i < asteroid.getPoints().size(); i++) {
            int change = rand.nextInt(11) - 5;
            asteroid.getPoints().set(i, asteroid.getPoints().get(i) + change);
        }

        return asteroid;

    }
}
