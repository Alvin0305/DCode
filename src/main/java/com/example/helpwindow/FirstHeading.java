package com.example.helpwindow;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class FirstHeading extends Text {

    public FirstHeading(String text) {
        this.setText(text);
        this.setFill(Color.GRAY);
        this.getStyleClass().add("first-heading");
    }
}
