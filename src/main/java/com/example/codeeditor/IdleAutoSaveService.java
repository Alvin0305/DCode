package com.example.codeeditor;

import com.example.GlobalVariables;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

public class IdleAutoSaveService extends ScheduledService<Void> {

    private final CodeEditor codeEditor;
    private File currentFile;

    public void changeFile(File newFile) {
        currentFile = newFile;
    }

    public IdleAutoSaveService(CodeEditor codeEditor, File currentFile) {
        this.codeEditor = codeEditor;
        this.currentFile = currentFile;

        setPeriod(Duration.seconds(GlobalVariables.getAutoSaveInterval()));
        setRestartOnFailure(true);

        codeEditor.editor.textProperty().addListener((obs, oldText, newText) -> {
            if (isRunning()) {
                cancel();
            }
            restart();
        });
    }

    public void setDuration(int newValue) {
        this.setPeriod(Duration.seconds(newValue));
    }
    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                autoSave();
                return null;
            }
        };
    }

    private void autoSave() throws IOException {
        codeEditor.saveFile();
    }
}
