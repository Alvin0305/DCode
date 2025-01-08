package com.example.helpwindow;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Objects;
import java.util.function.Consumer;

public class LeftBar extends VBox {

    private FontIcon goBackIcon = new FontIcon("fas-arrow-left");
    private Button goBackButton = new Button();

    private Label heading = new Label("LEARN ANYTHING");


    private ListView<String> topicsList = new ListView<>();

    private ListView<String> languageList = new ListView<>();

    private Separator separator = new Separator(Orientation.HORIZONTAL);

    HBox buttonAndHeading = new HBox(goBackButton, heading);

    public LeftBar(Consumer<String> selectionCallBack) {
        buttonAndHeading.setSpacing(20);
        buttonAndHeading.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(20);
        goBackIcon.setIconColor(Color.WHITE);
        goBackIcon.setIconSize(20);
        goBackButton.setGraphic(goBackIcon);
        topicsList.getItems().addAll("Array", "Queue", "Stack", "SLL", "DLL", "CLL", "Heap", "BST", "Graph", "Trie");
        languageList.getItems().addAll("C", "C++", "Java", "Python", "Flutter", "JavaScript", "C#", "Kotlin");

        topicsList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectionCallBack.accept(newValue);
                System.out.println(newValue);
            }
        });
        separator.getStyleClass().add("separator");

        this.getChildren().addAll(buttonAndHeading, topicsList, separator, languageList);

        VBox.setVgrow(topicsList, Priority.NEVER);
        VBox.setVgrow(languageList, Priority.NEVER);

        buttonAndHeading.getStyleClass().add("top");
        heading.getStyleClass().add("heading");
        goBackButton.getStyleClass().add("button");

        topicsList.getStyleClass().add("list");
        languageList.getStyleClass().add("list");
        this.getStyleClass().add("vbox");
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/helpwindow/left_bar.css")).toExternalForm());

    }
}
