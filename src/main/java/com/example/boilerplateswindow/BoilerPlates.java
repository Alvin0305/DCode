package com.example.boilerplateswindow;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BoilerPlates {
    Map<String, String> map = new HashMap<>();
    File file = new File("src/main/resources/com/example/files/boiler_plates.txt");

    public BoilerPlates() throws IOException {
        if (!file.exists()) {
            file = new File("classes/com/example/boiler_plates.txt");
        }
        createMap();
    }

    public Map<String, String> getMap() {
        return map;
    }

    private List<String> getAll() throws IOException {
        if (!file.exists()) {
            System.out.println("no such file");
            file = new File("classes/com/example/boiler_plates.txt");
        }
        return Files.readAllLines(file.toPath());
    }


    private void createMap() throws IOException {
        List<String> lines = getAll();
        for (String line : lines) {
            if (line.contains(":")) {
                String key = line.substring(0, line.indexOf(":")).trim();
                String value = line.substring(line.indexOf(":") + 1).trim();
                map.put(key, value);
            }
        }
    }

    public void recreate(Map<String, String> map) throws IOException{
        Files.write(file.toPath(), "".getBytes());
        map.forEach((k, v) -> {
            try {
                Files.write(file.toPath(), (k + ":" + v + "\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public String get(String key) {
        return map.get(key);
    }

    public void add(String key, String value) {
        if (!map.containsKey(key)) {
            map.put(key, value);
            try {
                Files.write(file.toPath(), (key + ":" + value + "\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (IOException e) {
                System.out.println("Write Error");
            }
        }
    }

    public void remove(String code) throws IOException {
        map.remove(code);
        StringBuilder builder = new StringBuilder();
        map.forEach((key, value) -> {
            builder.append(key).append(":").append(value).append("\n");
        });
        Files.write(file.toPath(), builder.toString().getBytes());
    }

    public void edit(String key, String newValue) throws IOException {
        map.put(key, newValue);
        StringBuilder builder = new StringBuilder();
        map.forEach((k, v) -> {
            builder.append(k).append(":").append(v).append("\n");
        });
        Files.write(file.toPath(), builder.toString().getBytes());
    }

    public void edit(String key, String newKey, String value, String newValue) {

    }

    public void print() {
        map.forEach((key, value) -> {
            System.out.println(key + " : " + value);
        });
    }

}
