package com.example.objectspane;

import com.example.messagebox.MessageBox;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

import java.util.Arrays;
import java.util.Objects;

public class Graph extends VBox {

    private Datatype type;
    private String name;
    private String[][] matrix;
    private int n;
    private MessageBox messageBox;
    private String message;
    private Pane content = new Pane();
    private Group nodesGroup = new Group();
    private Group edgesGroup = new Group();
    private Box[] nodes;
    private Line[][] edges;

    ContextMenu contextMenu = new ContextMenu();
    MenuItem toMatrixItem = new MenuItem("Convert to Matrix");
    MenuItem colorGraphItem = new MenuItem("Color the Graph");
    MenuItem dijkstraItem = new MenuItem("Do dijkstra");
    MenuItem floydWarshallItem = new MenuItem("Do Floyd Warshall");
    MenuItem findMSTItem = new MenuItem("Find MST");

    public Graph(Datatype type, String name, String[][] matrix, MessageBox messageBox) {
        this.type = type;
        this.name = name;
        this.matrix = matrix;
        this.n = matrix.length;
        this.messageBox = messageBox;
        createMessage();

        nodes = new Box[n];
        edges = new Line[n][n];

        setContent();

        this.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                messageBox.addText(message);
            }
        });

        TextBox nameBox = new TextBox(name, Font.font("Verdana", FontPosture.ITALIC, 12), Color.LIGHTGRAY);

        this.getChildren().addAll(content, nameBox);

        toMatrixItem.setOnAction(event -> toMatrix());
        colorGraphItem.setOnAction(event -> handleColorGraph());
        dijkstraItem.setOnAction(event -> handleDijkstra());
        floydWarshallItem.setOnAction(event -> handleFloydWarshall());
        findMSTItem.setOnAction(event -> handleFindMST());
        contextMenu.getItems().addAll(toMatrixItem, colorGraphItem, dijkstraItem, floydWarshallItem, findMSTItem);

        contextMenu.getStyleClass().add("context_menu");
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("objects_pane.css")).toExternalForm());

        this.setOnContextMenuRequested(event -> contextMenu.show(this, event.getScreenX(), event.getScreenY()));

        System.out.println(GraphFunctions.hasCycles(this));
        System.out.println(GraphFunctions.isBipartite(this));
    }

    private void createMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append("[\n");
        for (String[] strings: matrix) {
            builder.append("\t[ ");
            for (String string: strings) {
                builder.append(string).append(" ");
            }
            builder.append("]\n");
        }
        builder.append("]");
        message = "Matrix: " + builder.toString() + "\n" +
                "Properties: \n" +
                "Bipartite: " + GraphFunctions.isBipartite(this) + "\n" +
                "Has Cycles: " + GraphFunctions.hasCycles(this)
        ;
    }

    private void handleFindMST() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Graph");
        alert.setHeaderText("MST");
        String[][] MSTMatrix = GraphFunctions.findMSTMatrix(this);
        Graph MST = new Graph(type, name, MSTMatrix, messageBox);
        alert.getDialogPane().setContent(MST);
        alert.showAndWait();
    }

    private void handleFloydWarshall() {
        int[][] distances = GraphFunctions.floydWarshall(this);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Graph");
        alert.setHeaderText("Floyd Warshall");
        GridPane grid = new GridPane(10, 10);
        grid.setPadding(new Insets(20, 20, 20, 20));

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                String value = (distances[i][j] == Integer.MAX_VALUE) ? "INF" : String.valueOf(distances[i][j]);
                Label label = new Label(value);
                grid.add(label, i, j);
            }
        }
        alert.getDialogPane().setContent(grid);
        alert.showAndWait();
    }

    private void handleDijkstra() {
        TextInputDialog dialog = new TextInputDialog("Graph");
        dialog.setHeaderText("Enter the source vertex");
        dialog.showAndWait().ifPresent(input -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Dijkstra");
            Graph newGraph = new Graph(type, name, matrix, messageBox);
            GraphFunctions.doDijkstra(newGraph, Integer.parseInt(input));
            alert.getDialogPane().setContent(newGraph);
            alert.showAndWait();
        });
    }

    private void handleColorGraph() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Graph");
        alert.setHeaderText("Colored Graph");
        Graph coloredGraph = new Graph(type, name, matrix, messageBox);
        GraphFunctions.colorGraph(coloredGraph);
        alert.getDialogPane().setContent(coloredGraph);
        alert.showAndWait();
    }

    private void toMatrix() {
        Pane parent = ((Pane) this.getParent());
        parent.getChildren().remove(this);
        parent.getChildren().add(new MatrixData(type, name, matrix, messageBox));
    }

    private void setContent() {
        int degree = 360 / n;
        double centerX = 100;
        double centerY = 100;
        double radius = 100;

        for (int i = 0; i < n; i++) {
            double angle = Math.toRadians(i * degree);
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);
            nodes[i] = new Box(type, String.valueOf(i));
            nodes[i].setLayoutX(x);
            nodes[i].setLayoutY(y);
            nodesGroup.getChildren().add(nodes[i]);
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (!matrix[i][j].equals("0")) {
                    Line edge = new Line(
                        nodes[i].getLayoutX() + nodes[i].get_width() / 2,
                        nodes[i].getLayoutY() + nodes[i].get_height() / 2,
                        nodes[j].getLayoutX() + nodes[j].get_width() / 2,
                        nodes[j].getLayoutY() + nodes[j].get_height() / 2
                    );
                    edge.setStroke(Color.WHITE);
                    edges[i][j] = edge;
                    edges[j][i] = edge;
                    edgesGroup.getChildren().add(edge);
                }
            }
        }
        content.getChildren().addAll(edgesGroup, nodesGroup);

        for (int i = 0; i < n; i++) {
            setDrag(nodes[i], i);
        }
    }

    private void setDrag(Box node, int i) {
        final Delta delta = new Delta();

        node.setOnMousePressed(event -> {
            delta.x = event.getSceneX() - node.getLayoutX();
            delta.y = event.getSceneY() - node.getLayoutY();
        });

        node.setOnMouseDragged(event -> {
            double newX = event.getSceneX() - delta.x;
            double newY = event.getSceneY() - delta.y;
            node.setLayoutX(newX);
            node.setLayoutY(newY);

            for (int j = 0; j < n; j++) {
                if (edges[i][j] != null) {
                    Line edge = edges[i][j];
                    if (i < j) {
                        edge.setStartX(newX + nodes[i].get_width() / 2);
                        edge.setStartY(newY + nodes[i].get_height() / 2);
                    } else {
                        edge.setEndX(newX + nodes[i].get_width() / 2);
                        edge.setEndY(newY + nodes[i].get_height() / 2);
                    }
                }
            }
        });
    }

    public Datatype getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getN() {
        return n;
    }

    public String[][] getMatrix() {
        return matrix;
    }

    public Box[] getNodes() {
        return nodes;
    }

    @Override
    public String toString() {
        return this.type + " " + this.name;
    }
}
