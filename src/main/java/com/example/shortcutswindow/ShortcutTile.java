package com.example.shortcutswindow;

import javafx.scene.control.Label;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Objects;


public class ShortcutTile extends HBox {
    private final String symbol;
    private final KeyCombination keyCombination;
    private final String label;

    public ShortcutTile(String symbol, KeyCombination keyCombination, String label) {
        this.symbol = symbol;
        this.keyCombination = keyCombination;
        this.label = label;

        Label symbolBox = new Label(symbol);
        Label combinationBox = new Label(keyCombination.getDisplayText());
        Label labelBox = new Label(label);
        VBox combinationAndLabel = new VBox(combinationBox, labelBox);
        this.getChildren().addAll(symbolBox, combinationAndLabel);
        symbolBox.getStyleClass().add("symbol-box");
        combinationBox.getStyleClass().add("combination-box");
        labelBox.getStyleClass().add("label-box");
        this.getStyleClass().add("shortcut-tile");
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/shortcutswindow/shortcuts_window.css")).toExternalForm());
    }

    public String getLabel() {
        return label;
    }

    public String getSymbol() {
        return symbol;
    }

    public KeyCombination getKeyCombination() {
        return keyCombination;
    }
}
