package com.example.objectspane;

import com.example.messagebox.MessageBox;
import javafx.geometry.Point2D;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

public class Box extends VBox {
    private final Datatype type;
    private final String name;
    private String value;
    private String message;

    public Box() {
        this.type = Datatype.NONE;
        this.name = "";
        this.value = "";
    }

    public Box(Datatype type, String name, String value, MessageBox messageBox) {
        this.type = type;
        this.name = name;
        this.value = value;

        createMessage();

        ContentBox contentBox = new ContentBox(type, value);
        TextBox nameBox = new TextBox(name, Font.font("Verdana", FontPosture.ITALIC, 12), Color.LIGHTGRAY);

        this.setOnMousePressed(event -> {
            this.setUserData(new Point2D(event.getSceneX(), event.getSceneY()));
        });

        this.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                messageBox.addText(message);
            }
        });

        this.setOnMouseDragged(event -> {
            Point2D start = (Point2D) this.getUserData();
            this.setLayoutX(this.getLayoutX() + event.getSceneX() - start.getX());
            this.setLayoutY(this.getLayoutY() + event.getSceneY() - start.getY());
            this.setUserData(new Point2D(event.getSceneX(), event.getSceneY()));
        });

        this.getChildren().addAll(contentBox, nameBox);
    }

    public Box(Datatype type, String value) {
        this.type = type;
        this.name = "";
        this.value = value;

        ContentBox contentBox = new ContentBox(type, value);

        this.getChildren().add(contentBox);
    }

    private void createMessage() {
        message = "Datatype: " + type + "\n" + "Value: " + value + "\n";
    }

    public String getMessage() {
        return message;
    }

    public double get_width() {
        return ((ContentBox) this.getChildren().getFirst()).get_width();
    }

    public double get_height() {
        double contentHeight = ((ContentBox) this.getChildren().getFirst()).get_height();
        double padding = 10;
        double charHeight = 10;
        double textHeight = 2 * padding + charHeight;
        if (this.name.isEmpty()) {
            return contentHeight;
        } else {
            return textHeight + contentHeight;
        }
    }

    public Datatype getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public void setValue(String newValue) {
        this.value = newValue;
//        ((Rectangle) ((ContentBox) this.getChildren().getFirst()).getChildren().getFirst()).setHeight(get_height());
//        ((Rectangle) ((ContentBox) this.getChildren().getFirst()).getChildren().getFirst()).setWidth(get_width());
        ((ContentBox) this.getChildren().getFirst()).setValue(newValue);
    }

    public void setColor(Color newColor) {
        ((ContentBox) this.getChildren().getFirst()).setColor(newColor);
    }

    public void revokeMouseListener() {
        this.setOnMousePressed(event -> {});
        this.setOnMouseDragged(event -> {});
    }

    @Override
    public String toString() {
        return this.type + " " + this.name + " " + this.value;
    }
}
