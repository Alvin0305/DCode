package com.example.settings;

import com.example.GlobalVariables;
import com.example.codeeditor.CodeEditor;
import com.example.messagebox.MessageBox;
import com.example.objectspane.ObjectsPane;
import com.example.projectnavigator.ProjectNavigator;
import com.example.terminal.Terminal;
import com.example.topmenubar.TopMenuBar;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Objects;
import java.util.Set;

public class GeneralSettings extends VBox {

    GeneralSettings(
            ProjectNavigator projectNavigator, CodeEditor codeEditor, ObjectsPane objectsPane, Terminal terminal,
                    MessageBox messageBox, TopMenuBar topMenuBar, Settings settings
    ) {

        this.setSpacing(20);

        Text autoSaveLabel = new Text("AutoSave");
        RadioButton autoSaveButton = new RadioButton();
        autoSaveButton.setSelected(GlobalVariables.getAutoSave());
        HBox autoSaveOption = new HBox(20, autoSaveButton, autoSaveLabel);

        TextField autoSaveIntervalField = new TextField(String.valueOf(GlobalVariables.getAutoSaveInterval()));

        autoSaveButton.selectedProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                GlobalVariables.setAutoSave(true);
                codeEditor.setAutoSave();
                autoSaveIntervalField.setEditable(true);
            } else {
                GlobalVariables.setAutoSave(false);
                codeEditor.resetAutoSave();
                autoSaveIntervalField.setEditable(false);
            }
        });

        Text fontLabel = new Text("Font Theme");
        ComboBox<String> fontBox = new ComboBox<>();
        fontBox.getItems().addAll("Default", "Monokai", "Solarized_Dark", "Solarized_Light");
        fontBox.setValue(GlobalVariables.getFontTheme());
        fontBox.setOnAction(event -> {
            String selected = fontBox.getValue();
            System.out.println(selected);
            GlobalVariables.setFontTheme(selected);
            codeEditor.editor.setFontTheme();
        });
        HBox fontFamilyOption = new HBox(20, fontLabel, fontBox);

        Text fontSizeLabel = new Text("Font Size");
        ComboBox<String> fontSizeBox = new ComboBox<>();
        fontSizeBox.setValue(GlobalVariables.getFontSize());
        fontSizeBox.getItems().addAll("Small", "Medium", "Large");
        fontSizeBox.setOnAction(event -> {
            String selected = fontSizeBox.getValue();
            GlobalVariables.setFontSize(selected);
            codeEditor.editor.setFontSize();
        });
        HBox fontSizeOption = new HBox(20, fontSizeLabel, fontSizeBox);

        Text autoSaveIntervalLabel = new Text("Auto Save Interval");

        autoSaveIntervalField.setEditable(GlobalVariables.getAutoSave());
        autoSaveIntervalField.setPrefColumnCount(4);
        HBox autoSaveIntervalOption = new HBox(20, autoSaveIntervalLabel, autoSaveIntervalField);

        autoSaveIntervalLabel.getStyleClass().add("text");
        fontLabel.getStyleClass().add("text");
        autoSaveLabel.getStyleClass().add("text");
        fontSizeLabel.getStyleClass().add("text");
//        themeLabel.getStyleClass().add("text");

        autoSaveButton.getStyleClass().add("radio-button");

        fontBox.getStyleClass().add("combo-box");
//        themeBox.getStyleClass().add("combo-box");

        autoSaveIntervalOption.getStyleClass().add("hbox");
        autoSaveOption.getStyleClass().add("hbox");
        fontFamilyOption.getStyleClass().add("hbox");
//        themeOption.getStyleClass().add("hbox");
        fontSizeOption.getStyleClass().add("hbox");

        Region spacer1 = new Region();
        Region spacer2 = new Region();
        VBox.setVgrow(spacer1, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        Button saveButton = new Button("Save");
        HBox buttons = new HBox(spacer2, saveButton);

        saveButton.setOnAction(event -> {
            int newValue = Integer.parseInt(autoSaveIntervalField.getText());
            if (GlobalVariables.getAutoSave()) {
                GlobalVariables.setAutoSaveInterval(newValue);
                codeEditor.getIdleAutoSaveService().setDuration(newValue);
            }
            GlobalVariables.setFontSize(fontSizeBox.getValue());
        });

        this.getChildren().addAll(autoSaveOption, fontFamilyOption, fontSizeOption, autoSaveIntervalOption, spacer1, buttons);

        this.getStyleClass().add("settings");
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/settings/settings_dark.css")).toExternalForm());
    }
}
