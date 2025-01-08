package com.example.helpwindow;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class SecondHeading extends Text {

    SecondHeading(String text) {
        this.setText(text);
        this.setFill(Color.LIGHTGRAY);
        this.getStyleClass().add("second-heading");
    }
}
