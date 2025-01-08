package com.example.objectspane;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import com.example.objectspane.Datatype;
import com.example.objectspane.TextBox;

public class ContentBox extends StackPane {

    private final Datatype type;
    private String value;

    public ContentBox(Datatype type, String value) {
        this.type = type;
        this.value = value;
        Rectangle rectangle = new Rectangle();
        rectangle.setFill(selectColor());
        double height = calcHeight();
        double width = calcWidth();
        rectangle.setHeight(height);
        rectangle.setWidth(width);
        rectangle.setArcHeight(height / 2);
        rectangle.setArcWidth(width / 2);

        TextBox valueBox = new TextBox(this.value, Font.font("Arial", 14), Color.WHITE);

        this.getChildren().addAll(rectangle, valueBox);
    }

    public double get_height() {
        return this.calcHeight();
    }

    public double get_width() {
        return this.calcWidth();
    }

    public String getValue() {
        return value;
    }

    public Datatype getType() {
        return type;
    }

    public void setColor(Color newColor) {
        ((Rectangle) this.getChildren().getFirst()).setFill(newColor);
    }

    public void setValue(String newValue) {
        this.value = newValue;
        ((TextBox) this.getChildren().getLast()).setText(newValue);
    }

    private double calcHeight() {
        double padding = 10;
        double charHeight = 10;
        return 2 * padding + charHeight;
    }

    private double calcWidth() {
        double padding = 10;
        double charWidth = 8;
        return 2 * padding + charWidth * value.length();
    }

    private Color selectColor() {
        return switch (type) {
            case INTEGER -> Color.ORANGE;
            case CHARACTER -> Color.RED;
            case FLOAT -> Color.VIOLET;
            case STRING -> Color.GREEN;
            case BOOLEAN -> Color.BLUE;
            default -> Color.PINK;
        };
    }

}
