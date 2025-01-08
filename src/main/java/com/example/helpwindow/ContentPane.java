package com.example.helpwindow;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class ContentPane extends ScrollPane {

    private final VBox contentContainer;

    public ContentPane() {
        contentContainer = new VBox();
        contentContainer.setSpacing(10);
        contentContainer.setPrefWidth(1000);
        contentContainer.setFillWidth(false);
        VBox.setVgrow(contentContainer, Priority.ALWAYS);
        this.setContent(contentContainer);
        this.setFitToWidth(true);
        this.setFitToHeight(true);
        contentContainer.getStyleClass().add("root");
        this.getStyleClass().add("root");
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/helpwindow/content.css")).toExternalForm());
    }

    public void updateContent(String newValue) {
        contentContainer.getChildren().clear();
        switch (newValue) {
            case "Array" -> contentContainer.getChildren().addAll(new ArrayContent(this).getChildren());
            case "Stack" -> contentContainer.getChildren().addAll(new StackContent().getChildren());
            case "Queue" -> contentContainer.getChildren().addAll(new QueueContent().getChildren());
            default -> contentContainer.getChildren().add(new VBox());
        }
    }
}
