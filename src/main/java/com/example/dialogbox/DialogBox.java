package com.example.dialogbox;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class DialogBox extends Application {
    @Override
    public void start(Stage primaryStage) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("This is a Header");
        alert.setContentText("This is an information dialog box.");
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
