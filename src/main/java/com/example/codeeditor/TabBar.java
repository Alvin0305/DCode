package com.example.codeeditor;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.util.Objects;

public class TabBar extends VBox {
    String fileName;
    HBox bar = new HBox();
    Button nameButton = new Button();
    FontIcon closeIcon = new FontIcon("fas-times");
    Button closeButton = new Button();
    File file;
    public TabBar(File file) {
        this.setSpacing(0);
        this.file = file;
        this.fileName = file.getName();
        System.out.println("file: " + fileName);
        nameButton.setText(fileName.replace("_", "__"));
        closeButton.setGraphic(closeIcon);

        nameButton.getStyleClass().add("name-button");
        closeButton.getStyleClass().add("close-button");
        this.getStyleClass().add("tab-bar");
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("code_editor.css")).toExternalForm());

        bar.getChildren().addAll(nameButton, closeButton);
        this.getChildren().add(bar);
    }
}
