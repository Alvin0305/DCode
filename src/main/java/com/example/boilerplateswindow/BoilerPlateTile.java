package com.example.boilerplateswindow;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import javax.swing.plaf.basic.BasicButtonUI;
import java.util.Map;
import java.util.Objects;

public class BoilerPlateTile extends HBox {
    private String abbreviation;
    private String template;
    private Text abbreviationText = new Text();
    private Text templateText = new Text();
    Map<String, String> map;
    FontIcon closeIcon = new FontIcon(FontAwesomeSolid.TIMES);

    BoilerPlateTile(String abbreviation, String template, Map<String, String> map) {
        this.map = map;
        this.abbreviation = abbreviation;
        this.template = template;
        abbreviationText.setText(abbreviation);
        templateText.setText(template);

        Button deleteButton = new Button();
        deleteButton.setGraphic(closeIcon);
        deleteButton.setOnAction(event -> delete());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        this.setSpacing(20);
        this.getChildren().addAll(deleteButton, abbreviationText, spacer, templateText);
        abbreviationText.getStyleClass().add("abbreviation");
        templateText.getStyleClass().add("template");
        this.getStyleClass().add("boilerplate-tile");
        deleteButton.getStyleClass().add("delete-button");
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/boilerplateswindow/boiler_plates_window.css")).toExternalForm());
    }

    public void delete() {
        ((ExistingBoilerPlates) this.getParent()).getChildren().remove(this);
        map.remove(abbreviation, template);
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getTemplate() {
        return template;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
        abbreviationText.setText(abbreviation);
    }

    public void setTemplate(String template) {
        this.template = template;
        templateText.setText(template);
    }
}
