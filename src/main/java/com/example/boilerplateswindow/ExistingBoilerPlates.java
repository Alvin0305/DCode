package com.example.boilerplateswindow;

import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class ExistingBoilerPlates extends VBox {
    Map<String, String> map;
    ExistingBoilerPlates(Map<String, String> map) throws IOException {
        this.map = map;
        BoilerPlates boilerPlates = new BoilerPlates();

        map.forEach((k, v) -> {
            BoilerPlateTile boilerPlateTile = new BoilerPlateTile(k, v, map);
            this.getChildren().addAll(boilerPlateTile);
        });

        this.getStyleClass().add("existing-templates");
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/boilerplateswindow/boiler_plates_window.css")).toExternalForm());
    }

    public void add(BoilerPlateTile boilerPlateTile) {
        this.getChildren().add(boilerPlateTile);
        map.put(boilerPlateTile.getAbbreviation(), boilerPlateTile.getTemplate());
    }

    public void delete(BoilerPlateTile boilerPlateTile) {
        map.remove(boilerPlateTile.getAbbreviation());
        boilerPlateTile.delete();
    }

    public void edit(String key, String newValue) {
        this.getChildren().forEach(node -> {
            BoilerPlateTile boilerPlateTile = (BoilerPlateTile) node;
            if (boilerPlateTile.getAbbreviation().equals(key)) {
                boilerPlateTile.setTemplate(newValue);
            }
        });
    }

}
