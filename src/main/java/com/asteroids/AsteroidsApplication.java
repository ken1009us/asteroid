package com.asteroids;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class AsteroidsApplication extends Application {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 400;
    private static final int MAX_PROJECTILES = 10;
    private final Map<KeyCode, Boolean> pressedKeys = new HashMap<>();
    private final List<Asteroid> asteroids = new ArrayList<>();
    private final List<Projectile> projectiles = new ArrayList<>();
    private final Text pointText = new Text(10, 20, "Points: 0");
    private final AtomicInteger points = new AtomicInteger();
    private final Ship ship = new Ship(WIDTH / 2, HEIGHT / 2);
    private boolean gameOver = false;

    @Override
    public void start(Stage stage) throws IOException {
        initializeGameObjects();
        // layout
        Pane pane = new Pane();
        pane.setPrefSize(WIDTH, HEIGHT);

        pointText.getStyleClass().add("text");
        pane.getChildren().addAll(pointText, ship.getCharacter());
        asteroids.forEach(asteroid -> {
            pane.getChildren().add(asteroid.getCharacter());
        });

        // gaming window
        Scene scene = new Scene(pane);
        setupKeyListeners(scene);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!gameOver) {
                    updateGameObjects(pane);
                } else {
                    stop();
                }
            }
        }.start();

        setupStage(stage, scene);
    }

    private void initializeGameObjects() {
        // Create asteroids and add them to the list
        Random rand = new Random();
        for (int i = 0; i < 5; i++) {
            Asteroid asteroid = new Asteroid(rand.nextInt(300), rand.nextInt(300));
            if (!asteroid.collide(ship)) {
                asteroids.add(asteroid);
            }
        }
    }

    private void setupKeyListeners(Scene scene) {
        scene.setOnKeyPressed(event -> pressedKeys.put(event.getCode(), Boolean.TRUE));
        scene.setOnKeyReleased(event -> pressedKeys.put(event.getCode(), Boolean.FALSE));
    }

    private void updateGameObjects(Pane pane) {
        if (Math.random() < 0.05) {
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

        if (pressedKeys.getOrDefault(KeyCode.SPACE, false) && projectiles.size() < MAX_PROJECTILES) {
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
        asteroids.forEach(Asteroid::move);
        projectiles.forEach(Character::move);

        asteroids.forEach(asteroid -> {
            if (ship.collide(asteroid)) {
                gameOver = true;
            }
        });

        projectiles.forEach(projectile -> {
            asteroids.forEach(asteroid -> {
                if (projectile.collide(asteroid)) {
                    projectile.setAlive(false);
                    asteroid.setAlive(false);
                    pointText.setText("Points: " + points.addAndGet(1000));
                }
            });
        });

        removeDeadEntities(projectiles, pane);
        removeDeadEntities(asteroids, pane);

    }

    public void removeDeadEntities(List<? extends Character> entities, Pane pane) {
        List<Character> deadEntities = entities.stream()
                .filter(entity -> !entity.isAlive())
                .collect(Collectors.toList());

        pane.getChildren().removeAll(deadEntities.stream()
                .map(Character::getCharacter)
                .toList());

        entities.removeAll(deadEntities);
    }

    private void setupStage(Stage stage, Scene scene) {
        stage.setTitle("Asteroids Playground");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}