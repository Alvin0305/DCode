package com.example.about;

import javafx.scene.control.Button;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLOutput;
import java.util.Objects;

public class About extends VBox {
    public About() {
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/com/example/images/hashtag.png")).toExternalForm(),
                100, 100, true, true, true);
        ImageView icon = new ImageView(image);
        icon.setSmooth(true);
        icon.setPreserveRatio(true);
//        icon.setFitHeight(100);
//        icon.setFitWidth(100);
        icon.setBlendMode(BlendMode.SRC_OVER);

        VBox contents = new VBox(10);
        HBox middlePart = new HBox(20);

        Text appName = new Text("DCode 2025.1 (Community Edition)");
        Text buildDetails = new Text("Built on 2025 January");
        Text javaVersionDetails = new Text("JDK Version Used: 21.0.5");
        Text javacVersionDetails = new Text("Javac Version Used: 21.0.5");
        Text ideUsed = new Text("IDE Used to Build: Intellij IDEA");
        Text copyRight = new Text("Copyright 2025-2050");
        Text createdBy = new Text("Created by: Alvin A S");
        Button copyAndClose = new Button("Copy And Close");
        Button close = new Button("Close");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox buttons = new HBox(20, spacer, copyAndClose, close);

        copyAndClose.setOnAction(event -> handleCopyAndClose());
        close.setOnAction(event -> handleClose());

        contents.getChildren().addAll(appName, buildDetails, javaVersionDetails, javacVersionDetails, ideUsed, copyRight, createdBy);

        middlePart.getChildren().addAll(icon, contents);
        this.getChildren().addAll(middlePart, buttons);
        appName.getStyleClass().add("app-name");
        buildDetails.getStyleClass().add("build-details");
        javaVersionDetails.getStyleClass().add("java-version");
        javacVersionDetails.getStyleClass().add("javac-version");
        ideUsed.getStyleClass().add("ide-used");
        copyRight.getStyleClass().add("copyright");
        createdBy.getStyleClass().add("created-by");
        middlePart.getStyleClass().add("middle-part");
        close.getStyleClass().add("button");
        copyAndClose.getStyleClass().add("button");
        this.getStyleClass().add("about");
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/about/about.css")).toExternalForm());
    }

    private void handleCopy() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString("""
                DCode 2025.1 (Community Edition)
                Built on 2025 January
                JDK Version Used: 21.0.5
                Javac Version Used: 21.0.5
                IDE Used to Build: Intellij IDEA
                Copyright 2025-2050
                Created by: Alvin A S
                """);
        clipboard.setContent(clipboardContent);
    }

    private void handleClose() {
        ((Stage) this.getScene().getWindow()).close();
    }

    private void handleCopyAndClose() {
        handleCopy();
        handleClose();
    }

    public void setDarkTheme() {
        System.out.println("Dark Theme set");
    }

    public void setLightTheme() {
        System.out.println("Light Theme set");
    }
}
