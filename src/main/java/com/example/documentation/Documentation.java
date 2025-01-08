package com.example.documentation;

import com.example.about.About;
import com.example.shortcutswindow.ShortcutsWindow;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Objects;

public class Documentation extends VBox {

    String content = """
               1. Introduction
                    Welcome to DCode,
                    a code editor that can be used to manipulate and run Java files, with output visualisation.
                    This documentation will guide you through the features and functionality of the app.
                   \s
               2. Components
                    Project Navigator
                        For navigating, opening and manipulating files
                    Code Editor
                        For coding
                    Objects Pane
                        For visualising the Code
                    Terminal
                        For running the Code
                    Message Box
                        For showing messages related to the Visualisation
                    \s
               3. Features
                    Project Navigator allows creation, moving, copying, pasting, opening and renaming of files and folders
                    Code Editor allow the user to code with automatic syntax highlighting, keyword highlighting, error highlighting,
                    keyword suggestions, and context based suggestions
                    Objects Pane can be used to view the objects created in the code.
                    Content in the Message Box can be copied, moved or deleted
                    Side Bar contains option to collapse the Project Navigator, Search, Get Help and to open Settings
                    User can change the Theme of the Code Editor, Auto Save Interval, Version of JDK... using the Settings
                    User can also set templates and boiler plates using Set Abbreviations tab in Settings or Menu Bar
                    \s
                    JDK Version Used: 21.0.5
                    Javac Version Used: 21.0.5
                    Copyright 2025-2050
                    Created by: Alvin A S
               \s""";

    public Documentation() {
        Heading introductionHeading = new Heading("1. Introduction");
        Text introduction = new Text("""
                    Welcome to DCode,
                    a code editor that can be used to manipulate and run Java files, with output visualisation.
                    This documentation will guide you through the features and functionality of the app.
                """);

        Heading componentsHeading = new Heading("2. Components");
        Text components = new Text("""
                    Project Navigator
                        For navigating, opening and manipulating files
                    Code Editor
                        For coding
                    Objects Pane
                        For visualising the Code
                    Terminal
                        For running the Code
                    Message Box
                        For showing messages related to the Visualisation
                """
        );

        Heading featuresHeading = new Heading("Features");
        Text features = new Text("""
                    Project Navigator allows creation, moving, copying, pasting, opening and renaming of files and folders
                    Code Editor allow the user to code with automatic syntax highlighting, keyword highlighting, error highlighting,
                    keyword suggestions, and context based suggestions
                    Objects Pane can be used to view the objects created in the code.
                    Content in the Message Box can be copied, moved or deleted
                    Side Bar contains option to collapse the Project Navigator, Search, Get Help and to open Settings
                    User can change the Theme of the Code Editor, Auto Save Interval, Version of JDK... using the Settings
                    User can also set templates and boiler plates using Set Abbreviations tab in Settings or Menu Bar
                """
        );

        Button keyBoardShortcutsButton = new Button("View Shortcuts");
        keyBoardShortcutsButton.setOnAction(event -> {
            Stage stage = new Stage();
            Scene scene = new Scene(new ShortcutsWindow());
            stage.setScene(scene);
            stage.setWidth(550);
            stage.setHeight(540);
            stage.setResizable(false);
            stage.show();
        });

        Text javaVersionDetails = new Text("    JDK Version Used: 21.0.5");
        Text javacVersionDetails = new Text("    Javac Version Used: 21.0.5");
        Text copyRight = new Text("    Copyright 2025-2050");
        Text createdBy = new Text("    Created by: Alvin A S");

        Button showAbout = new Button("View About");
        showAbout.setOnAction(event -> {
            Stage stage = new Stage();
            Scene about = new Scene(new About());
            stage.setTitle("About DCode");
            stage.setResizable(false);
            stage.setHeight(330);
            stage.setWidth(600);
            stage.setScene(about);
            stage.show();
        });

        ScrollPane scrollPane = new ScrollPane();
        VBox contents = new VBox(
                20, introductionHeading, introduction, componentsHeading, components, featuresHeading, features, keyBoardShortcutsButton,
                javaVersionDetails, javacVersionDetails, copyRight, createdBy, showAbout
        );

        scrollPane.setContent(contents);

        Button copyAndClose = new Button("Copy And Close");
        copyAndClose.setOnAction(event -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.putString(content);
            clipboard.setContent(clipboardContent);
            close();
        });

        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> close());

        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        HBox buttons = new HBox(20, spacer1, copyAndClose, closeButton);


        Region spacer2 = new Region();
        spacer2.setMinHeight(20);

        this.getChildren().addAll(scrollPane, spacer2, buttons);

        keyBoardShortcutsButton.getStyleClass().add("button");
        showAbout.getStyleClass().add("button");
        copyAndClose.getStyleClass().add("button");
        closeButton.getStyleClass().add("button");

        introduction.getStyleClass().add("text");
        components.getStyleClass().add("text");
        features.getStyleClass().add("text");
        javaVersionDetails.getStyleClass().add("text");
        javacVersionDetails.getStyleClass().add("text");
        copyRight.getStyleClass().add("text");
        createdBy.getStyleClass().add("text");

        scrollPane.getStyleClass().add("style-class");
        this.getStyleClass().add("documentation");

        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/documentation/documentation.css")).toExternalForm());
    }

    private void close() {
        ((Stage) this.getScene().getWindow()).close();
    }
}
