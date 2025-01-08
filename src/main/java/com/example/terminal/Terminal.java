package com.example.terminal;

import com.example.GlobalVariables;
import javafx.scene.control.IndexRange;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Terminal extends ScrollPane {

    private String currentDir;
    private final String username = System.getProperty("user.name");
    private final TextArea terminal = new TextArea();
    private int historyIndex = -1;
    private final ArrayList<String> commandHistory = new ArrayList<>();
    private String HOSTNAME;

    public Terminal() {
        this.HOSTNAME = GlobalVariables.getHostName();
        currentDir = System.getProperty("user.home");

        terminal.setEditable(true);
        terminal.setMinHeight(200);
        terminal.setPrefHeight(400);
        this.setContent(terminal);
        this.setFitToHeight(true);
        this.setFitToWidth(true);

        this.getStyleClass().add("terminal");
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/terminal/terminal.css")).toExternalForm());

        addPrompt();

        AtomicInteger len = new AtomicInteger(getPrompt().length() + 1);

        terminal.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            int endIndex = terminal.getText().lastIndexOf(getPrompt()) + getPrompt().length();
            int caret = terminal.getCaretPosition();
            if (0 <= caret && caret < endIndex) {
                event.consume();
            }
            IndexRange indexRange = terminal.getSelection();
            if (indexRange.getStart() < endIndex) {
                event.consume();
            }
        });

        terminal.textProperty().addListener((obs, oldText, newText) -> {
            if (oldText.length() > newText.length() && terminal.getCaretPosition() < len.get()) {
                terminal.setText(oldText);
            }
        });

        terminal.caretPositionProperty().addListener((obs, oldValue, newValue) -> {
            int promptIndex = terminal.getText().lastIndexOf(getPrompt()) + getPrompt().length();
            System.out.println(promptIndex);
            if (newValue.doubleValue() < promptIndex) {
                terminal.positionCaret(oldValue.intValue());
            }
            if (newValue.doubleValue() < getPrompt().length()) {
                terminal.positionCaret(oldValue.intValue());
            }
        });

        terminal.setOnKeyPressed(event -> {
            System.out.println(len.get());
            switch (event.getCode()) {
                case ENTER -> {
                    handleCommandExecution();
                    event.consume();
                    len.set(terminal.getCaretPosition() + 1);
                    historyIndex = commandHistory.size();
                }
                case UP -> {
                    handleUpArrow();
                    event.consume();
                }
                case DOWN -> {
                    handleDownArrow();
                    event.consume();
                }
                case LEFT -> {
                    handleLeftArrow();
                    event.consume();
                }
            }
        });
    }

    public void execute(String command) {
        terminal.appendText(command);
        handleCommandExecution();
    }

    private void handleCommandExecution() {
        String text = terminal.getText();
        int promptIndex = text.lastIndexOf(getPrompt());
        String command = text.substring(promptIndex + getPrompt().length()).trim();

        if (!command.isEmpty()) {
            terminal.appendText("\n"); // Append newline after the command
            commandHistory.add(command);
            historyIndex = commandHistory.size();

            try {
                // Execute command
                ProcessBuilder pb = new ProcessBuilder("bash", "-c", command + " ; pwd"); // Append pwd to get current directory
                pb.directory(new java.io.File(currentDir)); // Set working directory
                pb.redirectErrorStream(true); // Redirect error stream to output stream
                Process process = pb.start();

                // Capture output
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                StringBuilder output = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
                process.waitFor();

                // Extract the last line (updated current directory)
                String[] lines = output.toString().split("\n");
                if (lines.length > 0) {
                    String lastLine = lines[lines.length - 1].trim();
                    java.io.File newDir = new java.io.File(lastLine);
                    if (newDir.exists() && newDir.isDirectory()) {
                        currentDir = newDir.getAbsolutePath();
                    }
                }

                // Append command output (excluding the last line, which is pwd)
                for (int i = 0; i < lines.length - 1; i++) {
                    terminal.appendText(lines[i] + "\n");
                }

            } catch (Exception e) {
                terminal.appendText("Error executing command: " + e.getMessage() + "\n");
            }
        }

        addPrompt(); // Add a new prompt after executing the command
    }

    private void handleUpArrow() {
        if (historyIndex > 0) {
            historyIndex--;
            String command = commandHistory.get(historyIndex);
            setTextAtCaret(command);
        } else {
            terminal.positionCaret(terminal.getLength());
        }
    }

    private void handleDownArrow() {
        if (historyIndex < commandHistory.size()) {
            historyIndex++;
            String command = (historyIndex < commandHistory.size()) ? commandHistory.get(historyIndex) : "";
            setTextAtCaret(command);
        } else {
            setTextAtCaret("");
        }
    }

    private void handleLeftArrow() {
        int promptIndex = terminal.getText().lastIndexOf(getPrompt()) + getPrompt().length();
        if (promptIndex > terminal.getCaretPosition()) {
            terminal.positionCaret(terminal.getCaretPosition() + 1);
        }
    }

    private void setTextAtCaret(String command) {
        String text = terminal.getText();
        int promptIndex = text.lastIndexOf(getPrompt()) + getPrompt().length();
        System.out.println(promptIndex);
        if (command.isEmpty()) {
            terminal.replaceText(promptIndex, text.length(), "");
        } else if (command.length() < text.length() - promptIndex) {
            terminal.replaceText(promptIndex, promptIndex + command.length(), command);
            terminal.replaceText(promptIndex + command.length(), text.length(), "");
        } else {
            terminal.replaceText(promptIndex, text.length(), command);
        }

        // Position the caret at the end
        terminal.positionCaret(terminal.getLength());
    }


    private void addPrompt() {
        terminal.appendText(getPrompt());
        terminal.positionCaret(terminal.getText().length()); // Move caret to the end
    }

    private String getPrompt() {
        return "\n┌──(" + username + "㉿" + HOSTNAME + ")-[" + currentDir + "]\n└─$ ";
    }

    public void setDarkTheme() {
        System.out.println("Dark Theme set");
    }

    public void setLightTheme() {
        System.out.println("Light Theme set");
    }
}