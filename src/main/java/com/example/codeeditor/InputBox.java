package com.example.codeeditor;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Objects;

public class InputBox extends HBox {
    Text title = new Text();
    TextField textField = new TextField();
    Region spacer = new Region();
    Button closeButton = new Button();
    FontIcon closeIcon = new FontIcon(FontAwesomeSolid.TIMES);
    Button enterButton = new Button("Enter");
    FontIcon upIcon = new FontIcon(FontAwesomeSolid.ANGLE_UP);
    FontIcon downIcon = new FontIcon(FontAwesomeSolid.ANGLE_DOWN);
    Button upButton = new Button();
    Button downButton = new Button();

    TextField newTextField = new TextField();
    Button replaceButton = new Button("Replace All");
    CodeEditor codeEditor;

    public InputBox(String title, CodeEditor codeEditor) {
        this.title.setText(title);
        this.codeEditor = codeEditor;
        HBox.setHgrow(spacer, Priority.ALWAYS);
        closeIcon.setIconSize(20);
        closeIcon.setIconColor(Color.WHITE);
        closeButton.setGraphic(closeIcon);
        upButton.setGraphic(upIcon);
        downButton.setGraphic(downIcon);
        closeButton.setOnAction(event -> handleClose());

        Platform.runLater(() -> textField.requestFocus());

        if (title.equals("Goto")) {
            enterButton.setOnAction(event -> {
                String text = textField.getText();
                int lineNumber = Integer.parseInt(text);
                handleClose();
                codeEditor.handleGoToLine(lineNumber);
            });
            this.setOnKeyPressed(event -> {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    enterButton.fire();
                } else if (event.getCode().equals(KeyCode.ESCAPE)) {
                    closeButton.fire();
                }
            });
            this.getChildren().addAll(this.title, textField, enterButton, spacer, closeButton);
        } else if (title.equals("Find")) {
            enterButton.setText("Find");
            this.getChildren().addAll(textField, enterButton, spacer, closeButton);
            textField.textProperty().addListener((obs, oldText, newText) -> {
                if (!newText.isEmpty()) {
                    codeEditor.handleFind(newText);
                }
            });
            this.setOnKeyPressed(event -> {
                if (event.getCode().equals(KeyCode.ESCAPE)) {
                    closeButton.fire();
                } else if (event.getCode().equals(KeyCode.ENTER)) {
                    enterButton.fire();
                }
            });
            enterButton.setOnAction(event -> {
                String text = textField.getText();
                codeEditor.handleFind(text);
                if (!this.getChildren().contains(upButton)) {
                    this.getChildren().add(1, upButton);
                    this.getChildren().add(2, downButton);
                }
                upButton.setOnAction(event1 -> codeEditor.handleFindPrev(text));
                downButton.setOnAction(event1 -> codeEditor.handleFindNext(text));
            });
        } else if (title.equals("Replace")) {
            enterButton.setText("Find");
            this.getChildren().addAll(textField, enterButton, newTextField, replaceButton, spacer, closeButton);
            textField.textProperty().addListener((obs, oldText, newText) -> {
                if (!newText.isEmpty()) {
                    codeEditor.handleFind(newText);
                }
            });
            this.setOnKeyPressed(event -> {
                if (event.getCode().equals(KeyCode.ESCAPE)) {
                    closeButton.fire();
                } else if (event.getCode().equals(KeyCode.ENTER)) {
                    enterButton.fire();
                }
            });
            enterButton.setOnAction(event -> {
                String text = textField.getText();
                codeEditor.handleFind(text);
                if (!this.getChildren().contains(upButton)) {
                    this.getChildren().add(1, upButton);
                    this.getChildren().add(2, downButton);
                }
                upButton.setOnAction(event1 -> codeEditor.handleFindPrev(text));
                downButton.setOnAction(event1 -> codeEditor.handleFindNext(text));
            });
            replaceButton.setOnAction(event -> {
                codeEditor.handleReplace(textField.getText(), newTextField.getText());
            });
        }

        this.title.getStyleClass().add("text");
        this.textField.getStyleClass().add("text-field");
        this.getStyleClass().add("input-box");
        this.enterButton.getStyleClass().add("button");
        this.closeButton.getStyleClass().add("button");
        this.upIcon.getStyleClass().add("font-icon");
        this.downIcon.getStyleClass().add("font-icon");
        this.upButton.getStyleClass().add("button");
        this.downButton.getStyleClass().add("button");
        this.closeIcon.getStyleClass().add("font-icon");
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("code_editor.css")).toExternalForm());

    }

    private void handleClose() {
        ((CodeEditor) this.getParent()).getChildren().remove(this);
    }

    public InputBox(String title) {
        this.title = new Text(title);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        closeIcon.setIconSize(20);
        closeIcon.setIconColor(Color.WHITE);
        closeButton.setGraphic(closeIcon);

        this.getChildren().addAll(this.title, textField, enterButton, spacer, closeButton);

        closeButton.setOnAction(event -> {
            ((CodeEditor) this.getParent()).getChildren().remove(this);
        });

        this.title.getStyleClass().add("text");
        this.textField.getStyleClass().add("text-field");
        this.getStyleClass().add("input-box");
        this.enterButton.getStyleClass().add("button");
        this.closeButton.getStyleClass().add("button");
        this.closeIcon.getStyleClass().add("font-icon");
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("code_editor.css")).toExternalForm());
    }


//    public InputBox(String title, CodeEditor codeEditor) {
//        this.title = new Text(title);
//        upButton.setGraphic(upIcon);
//        downButton.setGraphic(downIcon);
//        HBox.setHgrow(spacer, Priority.ALWAYS);
//        closeIcon.setIconSize(20);
//        closeIcon.setIconColor(Color.WHITE);
//        closeButton.setGraphic(closeIcon);
//        this.getChildren().addAll(this.title, textField, enterButton, spacer, closeButton);
//        if (title.equals("Find")) {
//            this.getChildren().removeFirst();
//            enterButton.setText("Find");
//        }
//        if (title.equals("Replace")) {
//            this.getChildren().removeFirst();
//            enterButton.setText("Find");
//            newTextField = new TextField();
//            this.getChildren().add(2, newTextField);
//            this.getChildren().add(3, replaceButton);
//        }
//
//        closeButton.setOnAction(event -> {
//            ((CodeEditor) this.getParent()).getChildren().remove(this);
//        });
//
//        Platform.runLater(() -> textField.requestFocus());
//        this.setOnKeyPressed(event -> {
//            if (event.getCode().equals(KeyCode.ENTER)) {
//                System.out.println("pressed enter");
//                enterButton.fire();
//            } else if (event.getCode().equals(KeyCode.DOWN)) {
//                System.out.println("pressed down");
//                if (title.equals("Find")) {
//                    downButton.fire();
//                }
//            } else if (event.getCode().equals(KeyCode.UP)) {
//                System.out.println("pressed up");
//                if (title.equals("Find")) {
//                    upButton.fire();
//                }
//            }
//        });
//
//        upButton.setOnAction(event -> codeEditor.handleFindPrev(textField.getText()));
//        downButton.setOnAction(event -> codeEditor.handleFindNext(textField.getText()));
//        final boolean[] flag = {false};
//        enterButton.setOnAction(event -> {
//            String text = textField.getText();
//            if (title.equals("Goto")) {
//                int value = Integer.parseInt(text);
//                System.out.println("going to " + value);
//                ((CodeEditor) this.getParent()).getChildren().remove(this);
//                codeEditor.handleGoToLine(value);
//            } else if (title.equals("Find") || title.equals("Replace")) {
//                System.out.println("finding " + text);
//                System.out.println("Size: " + this.getChildren().size());
//                int size = this.getChildren().size();
//                if (!flag[0]) {
//                    this.getChildren().add(2, upButton);
//                    this.getChildren().add(3, downButton);
//                    flag[0] = true;
//                }
//                codeEditor.handleFind(text);
//            }
//            this.requestFocus();
//        });
//
//        replaceButton.setOnAction(event -> {
//            String text = textField.getText();
//            String newText = newTextField.getText();
//            codeEditor.handleFind(text);
//            codeEditor.handleReplace(text, newText);
//        });
//
//        this.title.getStyleClass().add("text");
//        this.textField.getStyleClass().add("text-field");
//        this.getStyleClass().add("input-box");
//        this.enterButton.getStyleClass().add("button");
//        this.closeButton.getStyleClass().add("button");
//        this.closeIcon.getStyleClass().add("font-icon");
//        this.upButton.getStyleClass().add("button");
//        this.downButton.getStyleClass().add("button");
//        this.upIcon.getStyleClass().add("font-icon");
//        this.downIcon.getStyleClass().add("font-icon");
//        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("code_editor.css")).toExternalForm());
//    }

}
