package com.asteroids;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AsteroidsApplication extends Application {
    Map<KeyCode, Boolean> pressedKeys = new HashMap<>();

    @Override
    public void start(Stage stage) throws IOException {
        // layout
        Pane pane = new Pane();
        pane.setPrefSize(600, 400);

        // create a ship
        Ship ship = new Ship(300, 200);
        pane.getChildren().add(ship.getCharacter());

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
                if (pressedKeys.getOrDefault(KeyCode.LEFT, false)) {
                    ship.turnLeft();
                }

                if (pressedKeys.getOrDefault(KeyCode.RIGHT, false)) {
                    ship.turnRight();
                }

                if (pressedKeys.getOrDefault(KeyCode.UP, false)) {
                    ship.accelerate();
                }

                ship.move();

            }
        }.start();

        stage.setTitle("Asteroids");
        stage.setScene(scene);
        stage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}