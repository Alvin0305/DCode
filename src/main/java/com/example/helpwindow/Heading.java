package com.example.helpwindow;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Heading extends Text {

    public Heading(String text) {
        this.setText(text);
        this.setFill(Color.WHITE);
        this.getStyleClass().add("_heading");
    }
}
