package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        App app = new App(stage, this);
        Scene scene = new Scene(app);
        stage.setTitle("DCode");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void setupWindow(Stage stage) throws IOException {
        App app = new App(stage, this);
        Scene scene = new Scene(app);
        stage.setTitle("DCode");
        stage.setScene(scene);
        stage.show();
    }
}
