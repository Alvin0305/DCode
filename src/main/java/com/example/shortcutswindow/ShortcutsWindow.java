package com.example.shortcutswindow;

import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Objects;

public class ShortcutsWindow extends VBox {
    public ShortcutsWindow() {
        VBox box1 = new VBox(10);
        VBox box2 = new VBox(10);
        VBox box3 = new VBox(10);

        Text heading = new Text("SHORTCUTS");

        ShortcutTile copy = new ShortcutTile("C", KeyCombination.keyCombination("Ctrl+C"), "Copy File/Folder");
        ShortcutTile cut = new ShortcutTile("X", KeyCombination.keyCombination("Ctrl+X"), "Cut File/Folder");
        ShortcutTile newFile = new ShortcutTile("N", KeyCombination.keyCombination("Ctrl+N"), "Create New File");
        ShortcutTile newFolder = new ShortcutTile("N", KeyCombination.keyCombination("Ctrl+Shift+N"), "Create New Folder");
        ShortcutTile paste = new ShortcutTile("V", KeyCombination.keyCombination("Ctrl+V"), "Paste File/Folder");
        ShortcutTile open = new ShortcutTile("O", KeyCombination.keyCombination("Ctrl+O"), "Open Folder");
        ShortcutTile close = new ShortcutTile("Q", KeyCombination.keyCombination("Ctrl+Q"), "Close Window");
        ShortcutTile undo = new ShortcutTile("Z", KeyCombination.keyCombination("Ctrl+Z"), "Undo");
        ShortcutTile redo = new ShortcutTile("Y", KeyCombination.keyCombination("Ctrl+Y"), "Redo");
        ShortcutTile selectAll = new ShortcutTile("A", KeyCombination.keyCombination("Ctrl+A"), "Select All");
        ShortcutTile comment = new ShortcutTile("/", KeyCombination.keyCombination("Ctrl+/"), "Comment/Uncomment");
        ShortcutTile goto_ = new ShortcutTile("G", KeyCombination.keyCombination("Ctrl+G"), "Goto");
        ShortcutTile build =  new ShortcutTile("A", KeyCombination.keyCombination("Ctrl+A"), "Select All");
        ShortcutTile buildAll = new ShortcutTile("B", KeyCombination.keyCombination("Ctrl+Shift+B"), "Build All Code");
        ShortcutTile run = new ShortcutTile("R", KeyCombination.keyCombination("Ctrl+R"), "Run Code");
        ShortcutTile save = new ShortcutTile("S", KeyCombination.keyCombination("Ctrl+S"), "Save File");

        box1.getChildren().addAll(
                copy, cut, newFile, newFolder, paste, open
        );
        box2.getChildren().addAll(
                close, undo, redo, selectAll, comment
        );
        box3.getChildren().addAll(
                goto_, build, buildAll, run, save
        );

        HBox content = new HBox(20, box1, box2, box3);

        this.getChildren().addAll(heading, content);

        heading.getStyleClass().add("heading");
        this.getStyleClass().add("shortcuts-window");
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/shortcutswindow/shortcuts_window.css")).toExternalForm());
    }

    public void setDarkTheme() {
        System.out.println("Dark Theme set");
    }

    public void setLightTheme() {
        System.out.println("Light Theme set");
    }

}
