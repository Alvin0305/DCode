package com.example.objectspane;

import com.example.messagebox.MessageBox;
import javafx.geometry.Point2D;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

import java.util.Arrays;

public class ListData extends VBox {

    private final Datatype type;
    private final String name;
    private String[] values;
    private int n;

    private String message;
    private MessageBox messageBox;

    HBox contents = new HBox();

    ContextMenu contextMenu = new ContextMenu();
    MenuItem toHeapItem = new MenuItem("Convert to Heap");

    public ListData(Datatype type, String name, String[] values, MessageBox messageBox) {
        this.type = type;
        this.name = name;
        this.values = values;
        this.n = values.length;
        this.messageBox = messageBox;
        createMessage();

        for (String value: values) {
            Box box = new Box(type, value);
            contents.getChildren().add(box);
        }
        contents.setSpacing(5);
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

        toHeapItem.setOnAction(event -> toHeap());
        contextMenu.getItems().add(toHeapItem);

        this.setOnContextMenuRequested(event -> contextMenu.show(this, event.getScreenX(), event.getScreenY()));

        TextBox nameBox = new TextBox(name, Font.font("Verdana", FontPosture.ITALIC, 12), Color.LIGHTGRAY);

        this.getChildren().addAll(contents, nameBox);
    }

    private void createMessage() {
        message = "Datatype: " + type + "\n" + "Value: " + Arrays.toString(values) + "\n";
    }

    public Datatype getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String[] getValues() {
        return values;
    }

    public int getN() {
        return n;
    }

    public String getValue(int i) {
        return values[i];
    }

    public void setValue(int i, String newValue) {
        this.values[i] = newValue;
        ((Box) ((HBox) this.getChildren().getFirst()).getChildren().get(i)).setValue(newValue);
    }

    public void toHeap() {
        Pane parent = (Pane) this.getParent();
        parent.getChildren().remove(this);
        parent.getChildren().add(new Heap(type, name, values, messageBox));
    }

    public String toString() {
        return this.type + " " + this.name;
    }

}
