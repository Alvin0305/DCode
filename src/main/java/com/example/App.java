package com.example;

import com.example.codeeditor.CodeEditor;
import com.example.helpwindow.HelpWindow;
import com.example.helpwindow.LeftBar;
import com.example.sidebar.SideBar;
import com.example.messagebox.MessageBox;
import com.example.objectspane.ObjectsPane;
import com.example.projectnavigator.ProjectNavigator;
import com.example.terminal.Terminal;
import com.example.topmenubar.TopMenuBar;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class App extends BorderPane {
    MessageBox messageBox = new MessageBox();
    CodeEditor editor = new CodeEditor();
    ObjectsPane objectsPane = new ObjectsPane(messageBox);
    ProjectNavigator projectNavigator = new ProjectNavigator(editor, null);
    Terminal terminal = new Terminal();
    SplitPane editorAndObjectsPane = new SplitPane(editor, objectsPane);
    SplitPane terminalAndMessageBox = new SplitPane(terminal, messageBox);
    SplitPane rightPart = new SplitPane(editorAndObjectsPane, terminalAndMessageBox);
    SplitPane app = new SplitPane(projectNavigator, rightPart);
    TopMenuBar topMenuBar;

    Main main;

    App(Stage stage, Main main) throws IOException {
        File openedFolderFolder = new File("opened_folder.txt");
        if (!openedFolderFolder.exists()) {
            openedFolderFolder = new File("classes/com/example/opened_folder.txt");
        }
        String content = Files.readString(openedFolderFolder.toPath());
        System.out.println("path: " + content);
        if (!content.isEmpty()) {
            File openedFolder = new File(content);
            projectNavigator = new ProjectNavigator(editor, openedFolder);
            app = new SplitPane(projectNavigator, rightPart);
        }
        this.main = main;
        topMenuBar = new TopMenuBar(editor, terminal, objectsPane, app, projectNavigator, stage, main);
        editorAndObjectsPane.setOrientation(Orientation.HORIZONTAL);
        terminalAndMessageBox.setOrientation(Orientation.HORIZONTAL);
        rightPart.setOrientation(Orientation.VERTICAL);
        app.setOrientation(Orientation.HORIZONTAL);

        editorAndObjectsPane.getStyleClass().add("split-pane");
        terminalAndMessageBox.getStyleClass().add("split-pane");
        rightPart.getStyleClass().add("split-pane");
        app.getStyleClass().add("split-pane");

        editorAndObjectsPane.setDividerPositions(0.7);
        terminalAndMessageBox.setDividerPositions(0.6);
        rightPart.setDividerPositions(0.7);
        app.setDividerPositions(0.2);
        SideBar sideBar = new SideBar(projectNavigator, editor, objectsPane, terminal, messageBox, topMenuBar);
        HBox mountedApp = new HBox(sideBar, app);
        HBox.setHgrow(app, Priority.ALWAYS);

        HelpWindow helpWindow = new HelpWindow();

        this.setTop(topMenuBar);
        this.setCenter(mountedApp);
        mountedApp.getStyleClass().add("split-pane");
        this.getStyleClass().add("pane");
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/app.css")).toExternalForm());
    }

    public void setDarkTheme() {
        System.out.println("Dark Theme set");
    }

    public void setLightTheme() {
        System.out.println("Light Theme set");
    }

}
