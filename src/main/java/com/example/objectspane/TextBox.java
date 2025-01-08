package com.example.objectspane;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TextBox extends Text {

    public TextBox(String text, Font font, Color color) {
        this.setText(text);
        this.setFont(font);
        this.setFill(color);
    }

    public String toString() {
        return this.getText();
    }

}
