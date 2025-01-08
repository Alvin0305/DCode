package com.example.helpwindow;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class HelpWindow extends HBox {
    public HelpWindow() {
        ContentPane contentPane = new ContentPane();
        LeftBar leftBar = new LeftBar(contentPane::updateContent);
        Region separator = new Region();
        separator.setPrefWidth(1);
        separator.setMaxWidth(1);
        separator.setMinWidth(1);
        separator.getStyleClass().add("separator");
        HBox.setHgrow(separator, Priority.NEVER);
        this.getChildren().addAll(leftBar, separator, contentPane);

        HBox.setHgrow(contentPane, Priority.ALWAYS);
        HBox.setHgrow(this, Priority.ALWAYS);
    }

    public void setDarkTheme() {
        System.out.println("Dark Theme set");
    }

    public void setLightTheme() {
        System.out.println("Light Theme set");
    }
}
