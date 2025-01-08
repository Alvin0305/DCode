package com.example.settings;

import com.example.about.About;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

public class MiscellaneousSettings extends VBox {

    public MiscellaneousSettings() {
        this.setSpacing(20);

        Button showAbout = new Button("Show About");
        showAbout.setOnAction(event -> handleShowAbout());

        Text resetSettingsLabel = new Text("Reset Settings");
        Button resetSettingsButton = new Button("Reset");
        resetSettingsButton.setOnAction(event -> handleResetSettings());

        HBox resetSettingsOption = new HBox(20, resetSettingsLabel, resetSettingsButton);

        resetSettingsLabel.getStyleClass().add("text");

        showAbout.getStyleClass().add("button");
        resetSettingsButton.getStyleClass().add("button");

        resetSettingsOption.getStyleClass().add("hbox");

        this.getChildren().addAll(showAbout, resetSettingsOption);

        this.getStyleClass().add("settings");
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/settings/settings_dark.css")).toExternalForm());
    }

    private void handleShowAbout() {
        Stage stage = new Stage();
        Scene about = new Scene(new About());
        stage.setTitle("About");
        stage.setScene(about);
        stage.setResizable(false);
        stage.setHeight(330);
        stage.setWidth(600);
        stage.show();
    }

    private void handleResetSettings() {
        File initialConfig = new File("src/main/resources/com/example/files/config_copy.properties");
        if (!initialConfig.exists()) {
            initialConfig = new File("classes/com/example/config_copy.properties");
        }
        File currentConfig = new File("src/main/resources/com/example/files/config.properties");
        if (!currentConfig.exists()) {
            currentConfig = new File("classes/com/example/config.properties");
        }
        try {
            List<String> content = Files.readAllLines(initialConfig.toPath());
            Files.write(currentConfig.toPath(), content.stream().toList());
        } catch (IOException e) {
            System.out.println("Error");
        }
    }
}
