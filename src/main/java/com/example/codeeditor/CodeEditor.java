package com.example.codeeditor;

import com.example.GlobalVariables;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.IndexRange;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.TwoDimensional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class CodeEditor extends VBox {
    HBox files = new HBox(0);
    public Editor editor;
    private boolean numberLinePresent = true;
    File currentFile;
    int currentIndex = 0;
    String currentFileName;
    List<File> openedFileList = new ArrayList<>();

    public File getCurrentFile() {
        return currentFile;
    }

    public String getCurrentFileName() {
        return currentFileName;
    }

    public String getCode() {
        return editor.getText();
    }

    IdleAutoSaveService idleAutoSaveService;
    public IdleAutoSaveService getIdleAutoSaveService() {
        return idleAutoSaveService;
    }

    public CodeEditor(File... files) throws IOException {
        for (File file: files) {
            TabBar tabBar = new TabBar(file);
            openedFileList.add(file);
            this.files.getChildren().add(tabBar);
            setTabBarAction(tabBar);
        }
        File openedFiles = new File("src/main/resources/com/example/files/opened_files.txt");
        if (!openedFiles.exists()) {
            openedFiles = new File("classes/com/example/opened_files.txt");
        }
        List<String> fileNames = Files.readAllLines(openedFiles.toPath());
        for (String fileName: fileNames) {
            System.out.println(fileName);
            File f = new File(fileName);
            TabBar tabBar = new TabBar(f);
            openedFileList.add(f);
            this.files.getChildren().add(tabBar);
            setTabBarAction(tabBar);
        }
        setListeners();
        editor = new Editor(getText(openedFileList.getFirst()));
        currentFile = openedFileList.getFirst();
        currentFileName = currentFile.getName();
        this.getChildren().addAll(this.files, editor);
        VBox.setVgrow(editor, Priority.ALWAYS);
        highlightCurrentTab();
        if (GlobalVariables.getAutoSave()) {
            System.out.println("auto save initialized");
            idleAutoSaveService = new IdleAutoSaveService(this, currentFile);
            idleAutoSaveService.start();
        }
        this.getStyleClass().add("code-editor-vbox");
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/codeeditor/code_editor.css")).toExternalForm());
//        if (GlobalVariables.getTheme().equals("Dark")) {
//            this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("code_editor.css")).toExternalForm());
//        } else {
//            this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("code_editor_light.css")).toExternalForm());
//        }
    }

    public void setAutoSave() {
        idleAutoSaveService = new IdleAutoSaveService(this, currentFile);
        if (!idleAutoSaveService.isRunning()) {
            idleAutoSaveService.reset();
            idleAutoSaveService.start();
        }
    }

    public void resetAutoSave() {
        if (idleAutoSaveService.isRunning()) {
            System.out.println("cancelling");
            Platform.runLater(() -> idleAutoSaveService.cancel());
        }

    }

    public void setListeners() {
        this.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.S) {
                try {
                    saveFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                event.consume();
            }
            if (event.isControlDown() && event.isShiftDown() && event.getCode().equals(KeyCode.TAB)) {
                System.out.println("shift tabbed");
                try {
                    gotoPrevFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                event.consume();
            } else if (event.isControlDown() && event.getCode().equals(KeyCode.TAB)) {
                System.out.println("tabbed");
                try {
                    gotoNextFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                event.consume();
            }
        });
    }

    public void saveFile() throws IOException {
        System.out.println("saving file");
        System.out.println(currentFileName);
        String content = editor.getText();
        Files.write(currentFile.toPath(), content.getBytes());
    }

    public void highlightCurrentTab() {
        Region underLine = new Region();
        for (Node node: files.getChildren()) {
            TabBar tab = (TabBar) node;
            if (tab.getChildren().size() > 1) {
                tab.getChildren().removeLast();
            }
        }
        TabBar tabBar = (TabBar) files.getChildren().get(currentIndex);
        tabBar.getChildren().add(underLine);
        underLine.getStyleClass().add("underline-filename");
    }

    private void setTabBarAction(TabBar tabBar) {
        ((Button) ((HBox) tabBar.getChildren().getFirst()).getChildren().getFirst()).setOnAction(event -> {
            System.out.println("fileName: " + tabBar.fileName);
            this.getChildren().remove(editor);
            try {
                editor = new Editor(getText(tabBar.file));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.getChildren().add(editor);
            highlightCurrentTab();
            currentFile = tabBar.file;
            currentFileName = tabBar.fileName;
            currentIndex = files.getChildren().indexOf(tabBar);
            highlightCurrentTab();
            System.out.println(currentIndex);
            VBox.setVgrow(editor, Priority.ALWAYS);
        });
        ((Button) ((HBox) tabBar.getChildren().getFirst()).getChildren().getLast()).setOnAction(event -> {
            System.out.println("closing fileName: " + tabBar.fileName);
            if (this.files.getChildren().size() > 1) {
                this.files.getChildren().remove(tabBar);
                this.getChildren().remove(editor);
                openedFileList.remove(tabBar.file);
                File openedFiles = new File("src/main/resources/com/example/files/opened_files.txt");
                if (!openedFiles.exists()) {
                    openedFiles = new File("classes/com/example/opened_files.txt");
                }
                StringBuilder builder = new StringBuilder();
                List<String> read = new ArrayList<>();
                try {
                    read = Files.readAllLines(openedFiles.toPath());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                for (String line: read) {
                    if (!line.equals(tabBar.file.getPath())) {
                        builder.append(line).append("\n");
                    }
                }
                try {
                    Files.write(openedFiles.toPath(), builder.toString().getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    editor = new Editor(getText(openedFileList.getFirst()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(builder.toString());
                this.getChildren().add(editor);
                currentFile = openedFileList.getFirst();
                currentFileName = currentFile.getName();
                currentIndex = 0;
                highlightCurrentTab();
                System.out.println(currentIndex);
                VBox.setVgrow(editor, Priority.ALWAYS);
            }
        });
    }

    private void gotoNextFile() throws IOException {
        System.out.println("size: " + openedFileList.size());
        System.out.println(openedFileList);
        System.out.println("curr1 : " + currentIndex);
        currentIndex = (currentIndex + 1) % openedFileList.size();
        System.out.println("curr2 : " + currentIndex);
        changeFocus(openedFileList.get(currentIndex));
    }

    private void gotoPrevFile() throws IOException {
        System.out.println("size: " + openedFileList.size());
        System.out.println("curr1: " + currentIndex);
        if (currentIndex == 0) {
            currentIndex = openedFileList.size() - 1;
        } else {
            currentIndex--;
        }
        System.out.println("curr2: " + currentIndex);
        changeFocus(openedFileList.get(currentIndex));
    }

    private void changeFocus(File file) throws IOException {
        if (openedFileList.contains(file)) {
            currentFile = file;
            currentFileName = file.getName();
            this.getChildren().remove(editor);
            editor = new Editor(getText(file));
            this.getChildren().add(editor);
            int i = 0;
            for (Node tabBar: files.getChildren()) {
                if (((TabBar) tabBar).file.equals(file)) {
                    System.out.println(((TabBar) tabBar).file);
                    System.out.println(file);
                    currentIndex = i;
                    System.out.println("here: " + currentIndex);
                    break;
                }
                i++;
            }
            highlightCurrentTab();
            VBox.setVgrow(editor, Priority.ALWAYS);
        }
    }

    public void handleUndo() {
        editor.undo();
    }

    public void handleRedo() {
        editor.redo();
    }

    public void handleCut() {
        handleCopy();
        IndexRange indexRange = editor.getSelection();
        editor.replaceText(indexRange, "");
    }

    public void handleCopy() {
        IndexRange indexRange = editor.getSelection();
        int start = indexRange.getStart();
        int end = indexRange.getEnd();
        String copied = editor.getText(start, end);
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(copied);
        clipboard.setContent(clipboardContent);
    }

    public void addFile(File file) throws IOException {
        File openedFiles = new File("src/main/resources/com/example/files/opened_files.txt");
        if (!openedFiles.exists()) {
            openedFiles = new File("classes/com/example/opened_files.txt");
        }
        Files.write(
                openedFiles.toPath(),
                (file.getPath() + "\n").getBytes(),
                StandardOpenOption.APPEND
        );
        if (openedFileList.contains(file)) {
            changeFocus(file);
            return;
        }
        currentFile = file;
        currentFileName = file.getName();
        System.out.println("added file: " + file.getName());
        TabBar tabBar = new TabBar(file);
        setTabBarAction(tabBar);
        files.getChildren().add(tabBar);
        System.out.println(getText(file));
        this.getChildren().remove(editor);
        editor = new Editor(getText(file));
        openedFileList.add(file);
        currentIndex = files.getChildren().indexOf(tabBar);
        System.out.println(currentIndex);
        highlightCurrentTab();
        this.getChildren().add(editor);
        VBox.setVgrow(editor, Priority.ALWAYS);
    }

    public void handleGoToLine(int lineNumber) {
        lineNumber--;
        int size = (int) editor.getText().chars().filter(ch -> ch == '\n').count();
        if (lineNumber >= 0 && lineNumber < size) {
            editor.moveTo(lineNumber, 0);
            editor.requestFollowCaret();
            editor.showParagraphAtCenter(lineNumber);
            editor.requestFocus();
        } else {
            System.out.println("not in the range");
        }
    }
    private List<Integer> indices;
    private int currentFindIndex = 0;

    public void handleFind(String substring) {
        indices = KMPSearch(editor.getText(), substring);
        System.out.println(Arrays.toString(indices.toArray()));
        if (!indices.isEmpty()) {
            int index = indices.getFirst();
            editor.selectRange(index, index + substring.length());
            editor.requestFollowCaret();
            editor.showParagraphAtCenter(editor.offsetToPosition(index, TwoDimensional.Bias.Forward).getMajor());
        }
    }

    public void handleReplace(String substring, String newString) {
        for (int i = indices.size() - 1; i >= 0; i--) {
            editor.replaceText(indices.get(i), substring.length() + indices.get(i), newString);
            editor.moveTo(0);
        }
    }

    public void handleFindNext(String subString) {
        currentFindIndex++;
        System.out.println("next problem");
        if (indices.isEmpty()) {
            return;
        }
        if (currentFindIndex < indices.size()) {
            System.out.println("finding next");
            int index = indices.get(currentFindIndex);
            editor.selectRange(index, index + subString.length());
            editor.requestFollowCaret();
            editor.showParagraphAtCenter(editor.offsetToPosition(index, TwoDimensional.Bias.Forward).getMajor());
        } else {
            currentFindIndex--;
        }
    }

    public void handleFindPrev(String subString) {
        currentFindIndex--;
        if (indices.isEmpty()) {
            return;
        }
        if (currentFindIndex >= 0) {
            System.out.println("finding previous");
            int index = indices.get(currentFindIndex);
            editor.selectRange(index, index + subString.length());
            editor.requestFollowCaret();
            editor.showParagraphAtCenter(editor.offsetToPosition(index, TwoDimensional.Bias.Forward).getMajor());
        } else {
            currentFindIndex++;
        }
    }

    public void handlePaste() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        String data = clipboard.getString();
        System.out.println("pasting: " + data);
        editor.insertText(editor.getCaretPosition(), data);
    }

    public void handleSelectAll() {
        editor.selectAll();
    }

    public void handleComment() {
        editor.handleComment();
    }

    public void handleToggleLineNumber() {
        if (numberLinePresent) {
            editor.setParagraphGraphicFactory(null);
            numberLinePresent = false;
            GlobalVariables.setShowLineNumbers(false);
        } else {
            editor.setParagraphGraphicFactory(LineNumberFactory.get(editor));
            numberLinePresent = true;
            GlobalVariables.setShowLineNumbers(true);
        }
    }

    public void removeLineNumber() {
        editor.setParagraphGraphicFactory(null);
        numberLinePresent = false;
        GlobalVariables.setShowLineNumbers(false);
    }

    public void addLineNumber() {
        editor.setParagraphGraphicFactory(LineNumberFactory.get(editor));
        numberLinePresent = true;
        GlobalVariables.setShowLineNumbers(true);
    }

    private String getText(File file) {
        try (Scanner scanner = new Scanner(file)) {
            StringBuilder content = new StringBuilder();
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append("\n");
            }
            return content.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    private int[] buildPrefixTable(String pattern) {
        int[] lps = new int[pattern.length()];
        int length = 0; // Length of the previous longest prefix suffix
        int i = 1;

        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
                lps[i] = length;
                i++;
            } else {
                if (length != 0) {
                    length = lps[length - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }

    private List<Integer> KMPSearch(String text, String pattern) {
        List<Integer> indices = new ArrayList<>();
        int[] lps = buildPrefixTable(pattern);
        int i = 0; // Index for text
        int j = 0; // Index for pattern

        while (i < text.length()) {
            if (pattern.charAt(j) == text.charAt(i)) {
                i++;
                j++;
            }

            if (j == pattern.length()) {
                System.out.println("Pattern found at index " + (i - j));
                indices.add(i - j);
                j = lps[j - 1];
            } else if (i < text.length() && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        return indices;
    }

    public void setDarkTheme() {
        System.out.println("Dark Theme set");
    }

    public void setLightTheme() {
        System.out.println("Light Theme set");
    }
}
