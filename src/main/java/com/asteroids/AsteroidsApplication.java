package com.asteroids;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class AsteroidsApplication extends Application {
    public static int WIDTH = 600;
    public static int HEIGHT = 450;
    Map<KeyCode, Boolean> pressedKeys = new HashMap<>();

    @Override
    public void start(Stage stage) throws IOException {
        // layout
        Pane pane = new Pane();
        pane.setPrefSize(WIDTH, HEIGHT);

        Text pointText = new Text(10, 20, "Points: 0");
        AtomicInteger points = new AtomicInteger();

        // create a ship
        Ship ship = new Ship(WIDTH / 2, HEIGHT / 2);

        // create asteroids
        List<Asteroid> asteroids = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Random rand = new Random();
            Asteroid asteroid = new Asteroid(rand.nextInt(300), rand.nextInt(300));
            asteroids.add(asteroid);
        }

        List<Projectile> projectiles = new ArrayList<>();

        pane.getChildren().add(pointText);
        pane.getChildren().add(ship.getCharacter());
        asteroids.forEach(asteroid -> pane.getChildren().add(asteroid.getCharacter()));

        // gaming window
        Scene scene = new Scene(pane);
        scene.setOnKeyPressed(event -> {
            pressedKeys.put(event.getCode(), Boolean.TRUE);
        });

        scene.setOnKeyReleased(event -> {
            pressedKeys.put(event.getCode(), Boolean.FALSE);
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(Math.random() < 0.005) {
                    Asteroid asteroid = new Asteroid(WIDTH, HEIGHT);
                    if(!asteroid.collide(ship)) {
                        asteroids.add(asteroid);
                        pane.getChildren().add(asteroid.getCharacter());
                    }
                }

                if (pressedKeys.getOrDefault(KeyCode.LEFT, false)) {
                    ship.turnLeft();
                }

                if (pressedKeys.getOrDefault(KeyCode.RIGHT, false)) {
                    ship.turnRight();
                }

                if (pressedKeys.getOrDefault(KeyCode.UP, false)) {
                    ship.accelerate();
                }

                if (pressedKeys.getOrDefault(KeyCode.SPACE, false) && projectiles.size() < 10) {
                    Projectile projectile = new Projectile(
                            (int) ship.getCharacter().getTranslateX(),
                            (int) ship.getCharacter().getTranslateY());
                    projectile.getCharacter().setRotate(ship.getCharacter().getRotate());
                    projectiles.add(projectile);

                    projectile.accelerate();
                    projectile.setMovement(projectile.getMovement().normalize().multiply(3));

                    pane.getChildren().add(projectile.getCharacter());
                }

                ship.move();
                asteroids.forEach(asteroid -> asteroid.move());
                projectiles.forEach(projectile -> projectile.move());

                asteroids.forEach(asteroid -> {
                    if (ship.collide(asteroid)) {
                        stop();
                    }
                });

                projectiles.forEach(projectile -> {
                    asteroids.forEach(asteroid -> {
                        if (projectile.collide(asteroid)) {
                            projectile.setAlive(false);
                            asteroid.setAlive(false);
                        }
                    });

                    if (!projectile.isAlive()) {
                        pointText.setText("Points: " + points.addAndGet(1000));
                    }
                });

                removeDeadEntities(projectiles, pane);
                removeDeadEntities(asteroids, pane);


            }
        }.start();

        stage.setTitle("Asteroids Playground");
        stage.setScene(scene);
        stage.show();
    }

    public void removeDeadEntities(List<? extends Character> entities, Pane pane) {
        entities.stream()
                .filter(entity -> !entity.isAlive())
                .forEach(entity -> pane.getChildren().remove(entity.getCharacter()));

        entities.removeAll(entities.stream()
                                .filter(entity -> !entity.isAlive())
                                .toList());
    }


    public static void main(String[] args) {
        launch(args);
    }
}