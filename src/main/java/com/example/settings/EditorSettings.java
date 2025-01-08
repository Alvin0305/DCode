package com.example.settings;

import com.example.GlobalVariables;
import com.example.boilerplateswindow.BoilerPlatesWindow;
import com.example.codeeditor.CodeEditor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class EditorSettings extends VBox {

    public EditorSettings(CodeEditor codeEditor) {
        this.setSpacing(20);

        RadioButton autoIndentationButton = new RadioButton();
        autoIndentationButton.setSelected(GlobalVariables.getAutoIndentation());
        Text autoIndentationLabel = new Text("Auto Indentation");
        autoIndentationButton.selectedProperty().addListener((obs, oldValue, newValue) -> {
            GlobalVariables.setAutoBracketClosing(newValue);
        });
        HBox autoIndentationOption = new HBox(20, autoIndentationButton, autoIndentationLabel);

        RadioButton autoBracketClosingButton = new RadioButton();
        autoBracketClosingButton.setSelected(GlobalVariables.getAutoBracketClosing());
        autoBracketClosingButton.selectedProperty().addListener((obs, oldValue, newValue) -> {
            GlobalVariables.setAutoIndentation(newValue);
        });
        Text autoBracketClosingLabel = new Text("Auto Bracket Closing");
        HBox autoBracketClosingOption = new HBox(20, autoBracketClosingButton, autoBracketClosingLabel);

        RadioButton autoCompletionButton = new RadioButton();
        autoCompletionButton.setSelected(GlobalVariables.getAutoCompletion());
        autoCompletionButton.selectedProperty().addListener((obs, oldValue, newValue) -> {
            GlobalVariables.setAutoCompletion(newValue);
        });
        Text autoCompleteLabel = new Text("Auto Complete Code");
        HBox autoCompleteOption = new HBox(20, autoCompletionButton, autoCompleteLabel);

        RadioButton showLineNumbersButton = new RadioButton();
        showLineNumbersButton.setSelected(GlobalVariables.getShowLineNumbers());
        showLineNumbersButton.selectedProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                codeEditor.addLineNumber();
            } else {
                codeEditor.removeLineNumber();
            }
        });
        Text showLineNumbersLabel = new Text("Show Line Numbers");
        HBox showLineNumbersOption = new HBox(20, showLineNumbersButton, showLineNumbersLabel);

        Button showSetAbbreviations = new Button("Set Abbreviations");
        showSetAbbreviations.setOnAction(event -> {
            try {
                handleShowAbbreviations();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        autoCompleteLabel.getStyleClass().add("text");
        autoBracketClosingLabel.getStyleClass().add("text");
        showLineNumbersLabel.getStyleClass().add("text");
        autoIndentationLabel.getStyleClass().add("text");

        autoBracketClosingOption.getStyleClass().add("hbox");
        autoCompleteOption.getStyleClass().add("hbox");
        autoIndentationOption.getStyleClass().add("hbox");
        showLineNumbersOption.getStyleClass().add("hbox");

        showSetAbbreviations.getStyleClass().add("button");

        this.getChildren().addAll(autoIndentationOption, autoBracketClosingOption, autoCompleteOption, showLineNumbersOption, showSetAbbreviations);

        this.getStyleClass().add("settings");
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/settings/settings_dark.css")).toExternalForm());
    }

    private void handleShowAbbreviations() throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(new BoilerPlatesWindow());
        stage.setTitle("Set Abbreviations");
        stage.setResizable(false);
        stage.setHeight(700);
        stage.setWidth(1000);
        stage.setScene(scene);
        stage.show();
    }



}
