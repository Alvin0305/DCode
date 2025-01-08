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

public class MatrixData extends VBox {
    private final int N = 100;
    private String[][] values = new String[N][N];
    private Datatype type;
    private String name;
    private int m;
    private int n;
    private MessageBox messageBox;
    private String message;

    private ContextMenu contextMenu = new ContextMenu();
    private MenuItem toGraphItem = new MenuItem("Convert to Graph");

    public MatrixData(Datatype type, String name, String[][] values, MessageBox messageBox) {
        this.type = type;
        this.name = name;
        this.values = values;
        this.m = values.length;
        int n = values[0].length;
        this.messageBox = messageBox;
        createMessage();

        this.setSpacing(5);

        for (int i = 0; i < m; i++) {
            HBox hBox = new HBox(5);
            for (int j = 0; j < n; j++) {
                Box box = new Box(type, values[i][j]);
                hBox.getChildren().add(box);
            }
            this.getChildren().add(hBox);
        }

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
        this.getChildren().add(nameBox);

        toGraphItem.setOnAction(event -> toGraph());
        contextMenu.getItems().add(toGraphItem);

        this.setOnContextMenuRequested(event -> contextMenu.show(this, event.getScreenX(), event.getScreenY()));
    }

    private void createMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append("[\n");
        for (String[] strings: values) {
            builder.append("\t[ ");
            for (String string: strings) {
                builder.append(string).append(" ");
            }
            builder.append("]\n");
        }
        builder.append("]");
        message = "Datatype: " + type + "\nValue: " + builder.toString()+ "\n";
    }

    private void toGraph() {
        Pane parent = ((Pane) this.getParent());
        parent.getChildren().remove(this);
        parent.getChildren().add(new Graph(type, name, values, messageBox));
    }

    public int getN() {
        return N;
    }

    public Datatype getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getM() {
        return m;
    }

    public String[][] getValues() {
        return values;
    }

     public void setValues(int i, int j, String newValue) {
        this.values[i][j] = newValue;
        ((Box) ((HBox) this.getChildren().get(i)).getChildren().get(j)).setValue(newValue);
     }

     public String toString() {
         return this.type + " " + this.name;
     }
}
