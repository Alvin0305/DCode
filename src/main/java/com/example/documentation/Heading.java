package com.example.documentation;

import javafx.scene.text.Text;

public class Heading extends Text {

    Heading(String text) {
        this.setText(text);
        this.getStyleClass().add("heading");
    }
}
