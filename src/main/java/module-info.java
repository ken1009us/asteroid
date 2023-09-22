module com.asteroids.asteroids {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.asteroids to javafx.fxml;
    exports com.asteroids;
}