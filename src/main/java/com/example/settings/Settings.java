package com.example.settings;

import com.example.codeeditor.CodeEditor;
import com.example.messagebox.MessageBox;
import com.example.objectspane.ObjectsPane;
import com.example.projectnavigator.ProjectNavigator;
import com.example.sidebar.SideBar;
import com.example.terminal.Terminal;
import com.example.topmenubar.TopMenuBar;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class Settings extends HBox {

    VBox generalSettings = new VBox();
    public Settings(ProjectNavigator projectNavigator, CodeEditor codeEditor, ObjectsPane objectsPane, Terminal terminal,
                    MessageBox messageBox, TopMenuBar topMenuBar
    ) {
        Label generalSettingsLabel = createLabel("General Settings");
        Label editorSettingsLabel = createLabel("Editor Settings");
        Label environmentSettingsLabel = createLabel("Environment Settings");
        Label miscellaneousSettingsLabel = createLabel("Miscellaneous Settings");

        VBox options = new VBox();
        options.getChildren().addAll(generalSettingsLabel, editorSettingsLabel, environmentSettingsLabel, miscellaneousSettingsLabel);
        options.setMinWidth(300);

        VBox settings = new VBox();

        settings.getChildren().clear();
        settings.getChildren().addAll(new GeneralSettings(
                projectNavigator, codeEditor, objectsPane, terminal, messageBox, topMenuBar, this
        ));

        generalSettingsLabel.setOnMouseClicked(event -> {
            settings.getChildren().clear();
            settings.getChildren().addAll(new GeneralSettings(
                    projectNavigator, codeEditor, objectsPane, terminal, messageBox, topMenuBar, this
            ));
        });

        editorSettingsLabel.setOnMouseClicked(event -> {
            settings.getChildren().clear();
            settings.getChildren().add(new EditorSettings(codeEditor));
        });

        environmentSettingsLabel.setOnMouseClicked(event -> {
            settings.getChildren().clear();
            settings.getChildren().add(new EnvironmentSettings());
        });

        miscellaneousSettingsLabel.setOnMouseClicked(event -> {
            settings.getChildren().clear();
            settings.getChildren().add(new MiscellaneousSettings());
        });


        HBox.setHgrow(settings, Priority.ALWAYS);
        this.getChildren().addAll(options, settings);
        options.getStyleClass().add("options");
        settings.getStyleClass().add("settings");
        this.getStyleClass().add("settings-page");
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/settings/settings_dark.css")).toExternalForm());
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setWrapText(true);
        return label;
    }

    public void setDarkTheme() {
        System.out.println("Dark Theme set settings");
        this.getStylesheets().clear();
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("settings_dark.css")).toExternalForm());
        this.applyCss();
    }

    public void setLightTheme() {
        System.out.println("Light Theme set settings");
        this.getStylesheets().clear();
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("settings_light.css")).toExternalForm());
        this.applyCss();
    }
}
