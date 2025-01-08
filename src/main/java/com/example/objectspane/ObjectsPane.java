package com.example.objectspane;

import com.example.messagebox.MessageBox;
import javafx.scene.layout.Pane;

import java.util.Objects;

public class ObjectsPane extends Pane {
    MessageBox messageBox;
    public ObjectsPane(MessageBox messageBox) {
        this.getStyleClass().add("object_pane");
        this.messageBox = messageBox;
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/objectspane/objects_pane.css")).toExternalForm());
    }

    public void clear() {
        this.getChildren().clear();
    }

    public void add(Datatype type, String name, String value) {
        this.getChildren().add(new Box(type, name, value, messageBox));
    }

    public void addList(Datatype type, String name, String[] values) {
        this.getChildren().add(new ListData(type, name, values, messageBox));
    }

    public void add2DList(Datatype type, String name, String[][] values) {
        this.getChildren().add(new MatrixData(type, name, values, messageBox));
    }

    public void setDarkTheme() {
        System.out.println("Dark Theme set");
    }

    public void setLightTheme() {
        System.out.println("Light Theme set");
    }
}
