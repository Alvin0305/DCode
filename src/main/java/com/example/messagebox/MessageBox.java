package com.example.messagebox;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Objects;

public class MessageBox extends StackPane {

    TextArea textArea = new TextArea();
    ScrollPane scrollPane = new ScrollPane(textArea);
    Button delete = new Button();
    Button copy = new Button();
    Button cut = new Button();
    VBox buttons = new VBox();
    FontIcon deleteIcon = new FontIcon("fas-trash");
    FontIcon copyIcon = new FontIcon("fas-copy");
    FontIcon cutIcon = new FontIcon("fas-cut");

    public MessageBox() {

        final Color DELETE_ICON_COLOR = Color.RED;
        final Color COPY_ICON_COLOR = Color.LIGHTGREEN;
        final Color CUT_ICON_COLOR = Color.ORANGE;
        final int ICON_SIZE = 16;
        final int BUTTON_WIDTH = 30;
        final double MIN_HEIGHT = 300;

        deleteIcon.setIconSize(ICON_SIZE);
        deleteIcon.setIconColor(DELETE_ICON_COLOR);
        delete.setGraphic(deleteIcon);

        copyIcon.setIconSize(ICON_SIZE);
        copyIcon.setIconColor(COPY_ICON_COLOR);
        copy.setGraphic(copyIcon);

        cutIcon.setIconSize(ICON_SIZE);
        cutIcon.setIconColor(CUT_ICON_COLOR);
        cut.setGraphic(cutIcon);

        textArea.setEditable(false);
        textArea.setWrapText(true);

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setMaxHeight(Double.MAX_VALUE);
        scrollPane.setMaxWidth(Double.MAX_VALUE);

        delete.setMaxWidth(Double.MAX_VALUE);
        copy.setMaxWidth(Double.MAX_VALUE);
        cut.setMaxWidth(Double.MAX_VALUE);

        delete.setOnAction(event -> onDelete());
        copy.setOnAction(event -> onCopy());
        cut.setOnAction(event -> onCut());

        buttons.setMaxWidth(BUTTON_WIDTH);
        buttons.getChildren().addAll(delete, copy, cut);

        this.setMinHeight(MIN_HEIGHT);
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/messagebox/message_box.css")).toExternalForm());
        this.setAlignment(Pos.TOP_RIGHT);
        this.getChildren().addAll(scrollPane, buttons);
        this.getStyleClass().add("message-box");
    }

    public void addText(String text) {
        if (text.endsWith("\n")) {
            textArea.appendText(text);
        } else {
            textArea.appendText(text + "\n");
        }
    }

    private void onDelete() {
        textArea.clear();
    }

    private void onCopy() {
        String text = textArea.getText();
        if (text != null && !text.isEmpty()) {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(text);
            clipboard.setContent(content);
        }
    }

    private void onCut() {
        onCopy();
        onDelete();
    }

    public void setDarkTheme() {
        System.out.println("Dark Theme set");
    }

    public void setLightTheme() {
        System.out.println("Light Theme set");
    }
}