package com.example.objectspane;

import com.example.messagebox.MessageBox;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

import java.util.Arrays;

public class Heap extends VBox {

    private final Datatype type;
    private final String name;
    private String[] values;
    private int n;

    private MessageBox messageBox;

    private VBox vBox = new VBox();
    Group lines = new Group();
    Group nodes = new Group();
    String message;

    ContextMenu contextMenu = new ContextMenu();
    MenuItem toArrayItem = new MenuItem("Convert to Array");

    Heap(Datatype type, String name, String[] values, MessageBox messageBox) {
        this.type = type;
        this.name = name;
        this.values = values;
        this.n = values.length;
        this.messageBox = messageBox;
        createMessage();

        createHeap();

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

        toArrayItem.setOnAction(event -> toArray());
        contextMenu.getItems().add(toArrayItem);

        this.setOnContextMenuRequested(event -> contextMenu.show(this, event.getScreenX(), event.getScreenY()));

        TextBox nameBox = new TextBox(name, Font.font("Verdana", FontPosture.ITALIC, 12), Color.LIGHTGRAY);
        this.getChildren().add(nameBox);
    }

    private void createMessage() {
        message = "Datatype: " + type + "\nValue: " + Arrays.toString(values) + "\n" + "IsMinHeap: " + isMinHeap() + "\nIsMaxHeap: " + isMaxHeap();
    }

    private boolean isMinHeap() {
        for (int i = 0; i <= (n - 2) / 2; i++) {
            if (2 * i + 1 < n && Integer.parseInt(values[i]) > Integer.parseInt(values[2 * i + 1])) {
                return false;
            }
            if (2 * i + 2 < n && Integer.parseInt(values[i]) > Integer.parseInt(values[2 * i + 2])) {
                return false;
            }
        }
        return true;
    }

    private boolean isMaxHeap() {
        for (int i = 0; i <= (n - 2) / 2; i++) {
            if (2 * i + 1 < n && Integer.parseInt(values[i]) < Integer.parseInt(values[2 * i + 1])) {
                return false;
            }
            if (2 * i + 2 < n && Integer.parseInt(values[i]) < Integer.parseInt(values[2 * i + 2])) {
                return false;
            }
        }
        return true;
    }

    public String[] getValues() {
        return values;
    }

    public Datatype getType() {
        return type;
    }

    public int getN() {
        return n;
    }

    public String getName() {
        return name;
    }

    public void setValues(int i, String newValue) {
        this.values[i] = newValue;
        ((Box) this.nodes.getChildren().get(i)).setValue(newValue);
    }

    private double log2(int num) {
        return Math.log(num) / Math.log(2);
    }

    private double findmax() {
        double max = -1;
        for (String value: values) {
            if (value.length() > max) {
                max = value.length();
            }
        }
        return max;
    }

    private double calcWidth() {
        double padding = 10;
        double charWidth = 8;
        return 2 * padding + findmax() * charWidth;
    }

    private double calcHeight() {
        double padding = 10;
        double charHeight = 10;
        return 2 * padding + charHeight;
    }

    private void createHeap() {
        StackPane stackPane = new StackPane();
        double bottomHorizontalGap = 5;
        double verticalGap = 10;

        int heapHeight = (int) log2(n);
        double nodeHeight = calcHeight();
        double nodeWidth = calcWidth();

        double totalWidth = Math.pow(2, heapHeight) * (nodeWidth + bottomHorizontalGap) - bottomHorizontalGap;

        double x, y;

        double[][] positions = new double[n][2];

        for (int h = 0; h <= heapHeight; h++) {
            double startGap = totalWidth / Math.pow(2, h + 1) - nodeWidth / 2;
            double middleGap = 0;
            if (h == 0) {
                Box box = new Box(type, values[0]);
                box.setLayoutX(startGap);
                box.setLayoutY(0);
                nodes.getChildren().add(box);
                positions[0][0] = startGap;
                positions[0][1] = 0;
            } else {
                middleGap = (totalWidth - 2 * startGap - Math.pow(2, h) * nodeWidth) / (Math.pow(2, h) - 1);
                int start = (int) (Math.pow(2, h) - 1);
                int end = (int) (Math.pow(2, h + 1) - 2);
                y = h * (nodeHeight + verticalGap);
                for (int i = start; i <= end && i < n; i++) {
                    Box box = new Box(type, values[i]);
                    if (i == start) {
                        x = startGap;
                    } else {
                        x = startGap + (i - start) * (middleGap + nodeWidth);
                    }
                    box.setLayoutX(x);
                    box.setLayoutY(y);
                    nodes.getChildren().add(box);
                    positions[i][0] = x;
                    positions[i][1] = y;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            positions[i][0] += nodeWidth / 2;
            positions[i][1] += nodeHeight / 2;
        }

        for (int i = 0; i < n/2; i++) {
            int left = 2 * i + 1;
            int right = left + 1;
            if (left < n) {
                Line line1 = new Line(
                        positions[i][0], positions[i][1],
                        positions[left][0], positions[left][1]
                );
                line1.setStroke(Color.WHITE);
                lines.getChildren().add(line1);
            }
            if (right < n) {
                Line line2 = new Line(
                        positions[i][0], positions[i][1],
                        positions[right][0], positions[right][1]
                );
                line2.setStroke(Color.WHITE);
                lines.getChildren().add(line2);
            }
        }
        stackPane.getChildren().add(lines);
        stackPane.getChildren().add(nodes);
        this.getChildren().add(stackPane);
    }

    public void toArray() {
        Pane parent = ((Pane) this.getParent());
        parent.getChildren().remove(this);
        parent.getChildren().add(new ListData(type, name, values, messageBox));
    }

    public String toString() {
        return this.type + " " + this.name;
    }

}
