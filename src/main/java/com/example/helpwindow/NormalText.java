package com.example.helpwindow;

import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class NormalText extends Text {

    public NormalText(String text, Region parent) {
        super(text);
        this.setText(text);
        this.setFill(Color.LIGHTGRAY);
        this.getStyleClass().add("general-text");
        this.setWrappingWidth(800);
    }
}
