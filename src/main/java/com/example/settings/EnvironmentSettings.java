package com.example.settings;

import com.example.GlobalVariables;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Objects;

public class EnvironmentSettings extends VBox {
    public EnvironmentSettings() {
        this.setSpacing(20);

        Text programmingLanguageLabel = new Text("Programming Language");
        ComboBox<String> programmingLanguageBox = new ComboBox<>();
        programmingLanguageBox.setValue(GlobalVariables.getProgrammingLanguage());
        programmingLanguageBox.getItems().addAll("Java", "Kotlin");
        HBox programmingLanguageOption = new HBox(20, programmingLanguageLabel, programmingLanguageBox);

        Text compilerVersionLabel = new Text("Compiler Version");
        TextField compilerVersionField = new TextField(GlobalVariables.getCompilerVersion());
        HBox compilerVersionOption = new HBox(20, compilerVersionLabel, compilerVersionField);

        Text pathToJavaSDK = new Text("Path to Java SDk");
        TextField pathToJavaSDKField = new TextField(GlobalVariables.getJavaSDKPath());
        HBox pathToJavaSDKOption = new HBox(20, pathToJavaSDK, pathToJavaSDKField);

        Text selectBuildTools = new Text("Select Build Tools");
        ComboBox<String> selectBuildToolsBox = new ComboBox<>();
        selectBuildToolsBox.setValue(GlobalVariables.getBuildTool());
        selectBuildToolsBox.getItems().addAll("Maven", "Gradle");
        HBox selectBuildToolsOption = new HBox(20, selectBuildTools, selectBuildToolsBox);

        Text hostNameLabel = new Text("HostName");
        TextField hostNameField = new TextField(GlobalVariables.getHostName());
        HBox hostNameOption = new HBox(20, hostNameLabel, hostNameField);

        compilerVersionLabel.getStyleClass().add("text");
        programmingLanguageLabel.getStyleClass().add("text");
        pathToJavaSDK.getStyleClass().add("text");
        selectBuildTools.getStyleClass().add("text");
        hostNameLabel.getStyleClass().add("text");

        compilerVersionOption.getStyleClass().add("hbox");
        hostNameOption.getStyleClass().add("hbox");
        pathToJavaSDKOption.getStyleClass().add("hbox");
        programmingLanguageOption.getStyleClass().add("hbox");
        selectBuildToolsOption.getStyleClass().add("hbox");

        Region spacer1 = new Region();
        VBox.setVgrow(spacer1, Priority.ALWAYS);
        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        Button saveButton = new Button("Save");
        saveButton.getStyleClass().add("button");
        saveButton.setOnAction(event -> {
            GlobalVariables.setProgrammingLanguage(programmingLanguageBox.getValue());
            GlobalVariables.setCompilerVersion(compilerVersionField.getText());
            GlobalVariables.setJavaSDKPath(pathToJavaSDKField.getText());
            GlobalVariables.setBuildTool(selectBuildToolsBox.getValue());
            GlobalVariables.setHostName(hostNameField.getText());
        });
        HBox buttons = new HBox(spacer2, saveButton);

        this.getChildren().addAll(programmingLanguageOption, compilerVersionOption, pathToJavaSDKOption, selectBuildToolsOption, hostNameOption, spacer1, buttons);

        this.getStyleClass().add("settings");
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/settings/settings_dark.css")).toExternalForm());
    }
}
