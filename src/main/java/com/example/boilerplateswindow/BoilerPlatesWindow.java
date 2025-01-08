package com.example.boilerplateswindow;

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class BoilerPlatesWindow extends VBox {

    BoilerPlates boilerPlates = new BoilerPlates();
    public BoilerPlatesWindow() throws IOException {
        Text existingTemplatesLabel = new Text("Existing Templates");
        VBox existingTemplates = new VBox();
        Map<String, String> map = boilerPlates.getMap();
        System.out.println(map.keySet());

        ScrollPane existingBoilerPlatesPane = new ScrollPane();

        ExistingBoilerPlates existingBoilerPlates = new ExistingBoilerPlates(map);
        existingBoilerPlates.setPrefWidth(Region.USE_COMPUTED_SIZE);
        existingBoilerPlatesPane.setFitToWidth(true);
        existingBoilerPlatesPane.setContent(existingBoilerPlates);

        Text addNewAbbreviationLabel = new Text("Abbreviation");
        TextField newAbbreviationField = new TextField();
        Text addNewTemplateLabel = new Text("Template");
        TextField newTemplateField = new TextField();
        Button addButton = new Button("Add");

        newAbbreviationField.textProperty().addListener((obs, oldText, newText) -> {
            if (map.containsKey(newText)) {
                addButton.setText("Edit");
            } else {
                addButton.setText("Add");
            }
        });

        addButton.setOnAction(event -> {
            String abbreviation = newAbbreviationField.getText();
            String template = newTemplateField.getText();
            if (!abbreviation.isEmpty()) {
                if (addButton.getText().equals("Add")) {
                    existingBoilerPlates.add(new BoilerPlateTile(abbreviation, template, map));

                    newAbbreviationField.clear();
                    newTemplateField.clear();
                } else if (addButton.getText().equals("Edit")) {
                    existingBoilerPlates.edit(abbreviation, template);
                    try {
                        boilerPlates.edit(abbreviation, template);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    newAbbreviationField.clear();
                    newTemplateField.clear();
                }
            }
        });

        HBox addSession = new HBox(10, addNewAbbreviationLabel, newAbbreviationField, addNewTemplateLabel, newTemplateField, addButton);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button cancelButton = new Button("Cancel");
        Button applyButton = new Button("Apply");
        Button okayButton = new Button("Okay");

        cancelButton.setOnAction(event -> {
            ((Stage) this.getScene().getWindow()).close();
        });

        applyButton.setOnAction(event -> {
            try {
                boilerPlates.recreate(map);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        okayButton.setOnAction(event -> {
            ((Stage) this.getScene().getWindow()).close();
        });

        HBox buttons = new HBox(20, spacer, cancelButton, applyButton, okayButton);

        existingTemplatesLabel.getStyleClass().add("existing-templates-heading");
        existingTemplates.getStyleClass().add("existing-templates");
        addSession.getStyleClass().add("add-session");
        buttons.getStyleClass().add("buttons");

        this.getStyleClass().add("root");
        existingBoilerPlatesPane.getStyleClass().add("scroll-pane");

        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/boilerplateswindow/boiler_plates_window.css")).toExternalForm());


        this.getChildren().addAll(existingTemplatesLabel, existingBoilerPlatesPane, addSession, buttons);


    }
}