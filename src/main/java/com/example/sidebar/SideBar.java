package com.example.sidebar;

import com.example.codeeditor.CodeEditor;
import com.example.codeeditor.InputBox;
import com.example.helpwindow.HelpWindow;
import com.example.messagebox.MessageBox;
import com.example.objectspane.ObjectsPane;
import com.example.projectnavigator.ProjectNavigator;
import com.example.settings.Settings;
import com.example.terminal.Terminal;
import com.example.topmenubar.TopMenuBar;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Objects;

public class SideBar extends VBox {
    FontIcon searchIcon = new FontIcon(FontAwesomeSolid.SEARCH);
    FontIcon fileIcon = new FontIcon(FontAwesomeSolid.FILE);
    FontIcon helpIcon = new FontIcon(FontAwesomeSolid.QUESTION);
    FontIcon settingsIcon = new FontIcon(FontAwesomeSolid.COG);
    Button searchButton = new Button();
    Button fileButton = new Button();
    Button helpButton = new Button();
    Button settingsButton = new Button();
    Region spacer = new Region();
    CodeEditor codeEditor;

    public SideBar(ProjectNavigator projectNavigator, CodeEditor codeEditor, ObjectsPane objectsPane, Terminal terminal,
                   MessageBox messageBox, TopMenuBar topMenuBar
    ) {
        this.codeEditor = codeEditor;
        searchIcon.setIconSize(20);
        fileIcon.setIconSize(20);
        helpIcon.setIconSize(20);
        settingsIcon.setIconSize(20);
        searchButton.setGraphic(searchIcon);
        fileButton.setGraphic(fileIcon);
        helpButton.setGraphic(helpIcon);
        settingsButton.setGraphic(settingsIcon);
        VBox.setVgrow(spacer, Priority.ALWAYS);

        this.getChildren().addAll(fileButton, searchButton, helpButton, spacer, settingsButton);

        this.getStyleClass().add("side-bar");
        this.searchIcon.getStyleClass().add("font-icon");
        this.fileIcon.getStyleClass().add("font-icon");
        this.helpIcon.getStyleClass().add("font-icon");
        this.settingsIcon.getStyleClass().add("font-icon");
        this.spacer.getStyleClass().add("region");

        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/sidebar/sidebar.css")).toExternalForm());

        searchButton.setOnAction(event -> {
            topMenuBar.handleFind();
        });

        fileButton.setOnAction(event -> {
            SplitPane parent = (SplitPane) ((HBox) this.getParent()).getChildren().getLast();
            if (parent.getItems().contains(projectNavigator)) {
                parent.getItems().removeFirst();
            } else {
                parent.getItems().addFirst(projectNavigator);
            }
        });

        helpButton.setOnAction(event -> {
            Scene helpScreen = new Scene(new HelpWindow());
            Stage stage1 = new Stage();
            stage1.setTitle("Help Window");
            stage1.setScene(helpScreen);
            stage1.show();
        });

        settingsButton.setOnAction(event -> {
            Scene settingsScreen = new Scene(new Settings(projectNavigator, codeEditor, objectsPane, terminal, messageBox, topMenuBar));
            Stage stage2 = new Stage();
            stage2.setTitle("Settings");
            stage2.setScene(settingsScreen);
            stage2.setWidth(1000);
            stage2.setHeight(550);
            stage2.setResizable(false);
            stage2.show();
        });
    }

    public void setDarkTheme() {
        System.out.println("Dark Theme set");
    }

    public void setLightTheme() {
        System.out.println("Light Theme set");
    }
}
