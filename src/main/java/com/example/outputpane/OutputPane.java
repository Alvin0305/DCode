package com.example.outputpane;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

public class OutputPane extends ScrollPane {
    TextArea textArea = new TextArea();

    public OutputPane() {

    }

    public void appendText(String text) {
        textArea.appendText(text);
    }

    public void setText(String text) {
        textArea.setText(text);
    }

    public void clear() {
        textArea.clear();
    }


}
