package com.example.projectnavigator;

import com.example.GlobalVariables;
import com.example.codeeditor.CodeEditor;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javafx.stage.FileChooser;
import org.kordamp.ikonli.javafx.FontIcon;

public class ProjectNavigator extends VBox {

    enum TYPE {
        COPY, CUT
    }

    Text title = new Text("EXPLORER");
    Button openFolderButton = new Button("Open A Folder");
    TreeView<String> fileTreeView = new TreeView<>();
    private File selectedFile;
    private File root;
    private File clipboard;
    private boolean cut = false;
    private boolean copy = false;
    TreeItem<String> cutItem;

    HBox topPart = new HBox();

    FontIcon openNewFolderIcon = new FontIcon("fas-folder-open");
    FontIcon createNewFolderIcon = new FontIcon("fas-folder-plus");
    FontIcon createNewFileIcon = new FontIcon("far-file-alt");
    FontIcon copyIcon = new FontIcon("far-copy");
    FontIcon cutIcon = new FontIcon("fas-cut");
    FontIcon deleteIcon = new FontIcon("far-trash-alt");
    FontIcon pasteIcon = new FontIcon("fas-paste");
    FontIcon refreshIcon = new FontIcon("fas-sync");

    Button openNewFolderButton = new Button();
    Button createNewFileButton = new Button();
    Button createNewFolderButton = new Button();
    Button copyButton = new Button();
    Button cutButton = new Button();
    Button deleteButton = new Button();
    Button pasteButton = new Button();
    Button refreshButton = new Button();

    Region spacer = new Region();

    HBox buttonGroup1 = new HBox(openNewFolderButton, refreshButton);
    HBox buttonGroup2 = new HBox(createNewFolderButton, createNewFileButton);
    HBox buttonGroup3 = new HBox(copyButton, cutButton, pasteButton);
    HBox buttonGroup4 = new HBox(deleteButton);

    HBox toolBar = new HBox(buttonGroup1, buttonGroup2, buttonGroup3, buttonGroup4);
    CodeEditor codeEditor;

    public ProjectNavigator(CodeEditor codeEditor, File file) {
        int IconSize = 16;
        this.codeEditor = codeEditor;

        toolBar.setSpacing(10);
        openFolderButton.setOnAction(event -> openFolderWizard());

        refreshIcon.setIconSize(IconSize);
        refreshIcon.setIconColor(Color.WHITE);
        refreshButton.setGraphic(refreshIcon);
        refreshButton.setTooltip(new Tooltip("Refresh"));
        refreshButton.getStyleClass().add("op-button");

        openNewFolderIcon.setIconSize(IconSize);
        openNewFolderIcon.setIconColor(Color.WHITE);
        openNewFolderButton.setGraphic(openNewFolderIcon);
        openNewFolderButton.setTooltip(new Tooltip("Open a new Folder"));
        openNewFolderButton.getStyleClass().add("op-button");

        createNewFileIcon.setIconSize(IconSize);
        createNewFileIcon.setIconColor(Color.WHITE);
        createNewFileButton.setGraphic(createNewFileIcon);
        createNewFileButton.setTooltip(new Tooltip("Create a new File"));
        createNewFileButton.getStyleClass().add("op-button");

        createNewFolderIcon.setIconSize(IconSize);
        createNewFolderIcon.setIconColor(Color.WHITE);
        createNewFolderButton.setGraphic(createNewFolderIcon);
        createNewFolderButton.setTooltip(new Tooltip("Create a new Folder"));
        createNewFolderButton.getStyleClass().add("op-button");

        copyIcon.setIconSize(IconSize);
        copyIcon.setIconColor(Color.WHITE);
        copyButton.setGraphic(copyIcon);
        copyButton.setTooltip(new Tooltip("Copy"));
        copyButton.getStyleClass().add("op-button");

        cutIcon.setIconSize(IconSize);
        cutIcon.setIconColor(Color.WHITE);
        cutButton.setGraphic(cutIcon);
        cutButton.setTooltip(new Tooltip("Cut"));
        cutButton.getStyleClass().add("op-button");

        deleteIcon.setIconSize(IconSize);
        deleteIcon.setIconColor(Color.WHITE);
        deleteButton.setGraphic(deleteIcon);
        deleteButton.setTooltip(new Tooltip("Delete"));
        deleteButton.getStyleClass().add("op-button");

        pasteIcon.setIconSize(IconSize);
        pasteIcon.setIconColor(Color.WHITE);
        pasteButton.setGraphic(pasteIcon);
        pasteButton.setTooltip(new Tooltip("Paste"));
        pasteButton.getStyleClass().add("op-button");

        VBox.setVgrow(fileTreeView, Priority.ALWAYS);
        HBox.setHgrow(topPart, Priority.ALWAYS);
        topPart.setSpacing(10);
        topPart.getChildren().addAll(title, spacer);

        fileTreeView.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                if (!reconstructFilePath(newValue).isEmpty()) {
                    selectedFile = new File(root.toString() + reconstructFilePath(newValue));
                } else {
                    selectedFile = new File(root.toString());
                }
                System.out.println(selectedFile);
            }
        });

        fileTreeView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                TreeItem<String> selectedItem = fileTreeView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    String filePath = reconstructFilePath(selectedItem);
                    if (!filePath.isEmpty()) {
                        selectedFile = new File(root.toString() + filePath);
                        System.out.println("Double-clicked: " + selectedFile);
                        System.out.println(selectedFile.toPath());
                        if (!Files.isDirectory(selectedFile.toPath())) {
                            System.out.println("is not directory");
                            try {
                                codeEditor.addFile(selectedFile);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
//                            codeEditor.highlightCurrentTab();
                        }
                    }
                }
            }
        });

        fileTreeView.setCellFactory(treeView -> createTreeCell());

        this.getChildren().addAll(topPart, openFolderButton, fileTreeView);

        if (file != null && file.isDirectory()) {
            this.getChildren().remove(openFolderButton);
            TreeItem<String> rootItem = createTreeItem(file);
            rootItem.setExpanded(true);
            root = file;
            System.out.println("rootItem: " + rootItem);
            System.out.println("selected: " + selectedFile);
            fileTreeView.setRoot(rootItem);
            if (!this.getChildren().contains(toolBar)) {
                this.getChildren().add(1, toolBar);
                setupButtonAction(rootItem);
            }
            openNewFolderButton.setOnAction(event -> openFolderWizard());
        }

        String dark = Objects.requireNonNull(getClass().getResource("/com/example/projectnavigator/project_navigator.css")).toExternalForm();
        this.getStylesheets().add(dark);
//        String light = Objects.requireNonNull(getClass().getResource("project_navigator_light.css")).toExternalForm();
//        if (GlobalVariables.getTheme().equals("Dark")) {
//              this.getStylesheets().add(dark);
//        } else {
//            this.getStylesheets().add(light);
//        }

        this.getStyleClass().add("project_navigator");
        title.getStyleClass().add("text");
        openFolderButton.getStyleClass().add("button");
        fileTreeView.getStyleClass().add("tree-view");

        setupKeyBoardShortcuts();
    }

    public File getSelectedFile() {
        return selectedFile;
    }

    public void handleOpenFolder() {
        openFolderWizard();
    }

    private void handleRefresh() {
        if (root == null || !root.exists()) {
            showAlert("Refresh Error", "No folder is currently opened to refresh.");
            return;
        }

        // Preserve the expanded state of the nodes
        TreeItem<String> currentRoot = fileTreeView.getRoot();
        Map<String, Boolean> expandedState = new HashMap<>();
        saveExpandedState(currentRoot, expandedState);

        // Clear and recreate the TreeView
        TreeItem<String> newRootItem = createTreeItem(root);
        fileTreeView.setRoot(newRootItem);

        // Restore the expanded state
        restoreExpandedState(newRootItem, expandedState);
    }

    private void saveExpandedState(TreeItem<String> treeItem, Map<String, Boolean> expandedState) {
        if (treeItem == null) return;

        expandedState.put(reconstructFilePath(treeItem), treeItem.isExpanded());
        for (TreeItem<String> child : treeItem.getChildren()) {
            saveExpandedState(child, expandedState);
        }
    }

    private void restoreExpandedState(TreeItem<String> treeItem, Map<String, Boolean> expandedState) {
        if (treeItem == null) return;

        String path = reconstructFilePath(treeItem);
        if (expandedState.containsKey(path)) {
            treeItem.setExpanded(expandedState.get(path));
        }

        for (TreeItem<String> child : treeItem.getChildren()) {
            restoreExpandedState(child, expandedState);
        }
    }


    private String reconstructFilePath(TreeItem<String> treeItem) {
        StringBuilder fullPath = new StringBuilder(treeItem.getValue());
        TreeItem<String> parent = treeItem.getParent();
        while (parent != null) {
            fullPath.insert(0, parent.getValue() + File.separator);
            parent = parent.getParent();
        }
        int startIndex = fullPath.indexOf("/");
        if (startIndex == -1) {
            return "";
        }
        return fullPath.substring(startIndex);
    }

    public void handleSaveAs() throws IOException {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showSaveDialog(this.getScene().getWindow());
        if (selectedFile != null) {
            System.out.println("trying to save As");
            Files.write(selectedFile.toPath(), codeEditor.getCode().getBytes());
        }
    }

    private void openFolderWizard() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedFolder = directoryChooser.showDialog(this.getScene().getWindow());
        if (selectedFolder != null) {
            this.getChildren().remove(openFolderButton);
            TreeItem<String> rootItem = createTreeItem(selectedFolder);
            root = selectedFolder;
            System.out.println("rootItem: " + rootItem);
            System.out.println("selected: " + selectedFolder);
            fileTreeView.setRoot(rootItem);

            if (!this.getChildren().contains(toolBar)) {
                this.getChildren().add(1, toolBar);
                setupButtonAction(rootItem);
            }
            File file = new File("src/main/resources/com/example/files/opened_folder.txt");
            if (!file.exists()) {
                file = new File("classes/com/example/opened_folder.txt");
            }
            try {
                Files.write(file.toPath(), root.getPath().getBytes());
            } catch (IOException e) {
                System.out.println("IO Exception");
            }
            openNewFolderButton.setOnAction(event -> openFolderWizard());
        }
    }

    private void setupButtonAction(TreeItem<String> rootItem) {
        deleteButton.setOnAction(event -> handleDelete());
        createNewFolderButton.setOnAction(event -> handleCreateFolder());
        createNewFileButton.setOnAction(event -> handleCreateFile());
        copyButton.setOnAction(event -> handleCopy());
        cutButton.setOnAction(event -> handleCut());
        pasteButton.setOnAction(event -> handlePaste());
        refreshButton.setOnAction(event -> handleRefresh());
    }

    private TreeItem<String> createTreeItem(File file) {
        TreeItem<String> item = new TreeItem<>(file.getName());

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File child: files) {
                    item.getChildren().add(createTreeItem(child));
                }
            }
        }
        return item;
    }

    private TreeCell<String> createTreeCell() {
        return new TreeCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setContextMenu(null);
                } else {
                    setText(item);

                    // Context menu for TreeItem
                    ContextMenu contextMenu = new ContextMenu();

                    MenuItem renameItem = new MenuItem("Rename");
                    renameItem.setOnAction(event -> handleRename(getTreeItem()));

                    MenuItem deleteItem = new MenuItem("Delete");
                    deleteItem.setOnAction(event -> handleDelete());

                    MenuItem copyItem = new MenuItem("Copy");
                    copyItem.setOnAction(event -> handleCopy());

                    MenuItem cutItem = new MenuItem("Cut");
                    cutItem.setOnAction(event -> handleCut());

                    MenuItem pasteItem = new MenuItem("Paste");
                    pasteItem.setOnAction(event -> handlePaste());

                    MenuItem newFolderItem = new MenuItem("New Folder");
                    newFolderItem.setOnAction(event -> createFolder());

                    MenuItem newFileItem = new MenuItem("New File");
                    newFileItem.setOnAction(event -> createFile());

                    contextMenu.getItems().addAll(renameItem, deleteItem, copyItem, cutItem, newFileItem, newFolderItem);

                    if (copy || cut) {
                        contextMenu.getItems().add(pasteItem);
                    }

                    setContextMenu(contextMenu);
                }
            }
        };
    }

    private void handleCopy() {
        clipboard = selectedFile;
        System.out.println("copy: " + clipboard);
        copy = true;
        cut = false;
        System.out.println("copy: " + copy);
    }

    private void handleCut() {
        clipboard = selectedFile;
        System.out.println("cut: " + clipboard);
        cut = true;
        copy = false;
        cutItem = fileTreeView.getSelectionModel().getSelectedItem();
    }

    private void handlePaste() {
        System.out.println("copy: " + copy);
        System.out.println("cut: " + cut);
        if (clipboard == null) {
            System.out.println("Nothing in clipboard");
        } else if (copy) {
            pasteFile(TYPE.COPY);
        } else if (cut) {
            pasteFile(TYPE.CUT);
        }
        cut = false;
        copy = false;
    }

    private void handleRename(TreeItem<String> treeItem) {
        File file = selectedFile;

        // Prompt for new name
        TextInputDialog dialog = new TextInputDialog(file.getName());
        dialog.setTitle("Rename");
        dialog.setHeaderText("Rename File/Folder");
        dialog.setContentText("New name:");

        dialog.showAndWait().ifPresent(newName -> {
            boolean renamed = renameFile(selectedFile, newName);
            if (renamed) {
                treeItem.setValue(newName);
            }
        });
        dialog.getDialogPane().getStyleClass().add("dialog-pane");
    }

    private void handleDelete() {
        File file = selectedFile;
        TreeItem<String> treeItem = fileTreeView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete File/Folder");
        alert.setContentText("Are you sure you want to delete " + file.getName() + "?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    deleteFiles(file);
                    treeItem.getParent().getChildren().remove(treeItem);
                } catch (IOException e) {
                    showAlert("Delete failed", "Deletion of the file/folder failed");
                }
            }
        });
        alert.getDialogPane().getStyleClass().add("dialog-pane");
    }

    public void handleCreateFolder() {
        createFolder();
    }

    private void createFolder() {
        TreeItem<String> treeItem = fileTreeView.getSelectionModel().getSelectedItem();
        if (selectedFile != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("New Folder");
            dialog.setHeaderText("Create a new folder");
            dialog.setContentText("Enter the name of the folder");
            dialog.showAndWait().ifPresent((folderName) -> {
                if (selectedFile.isDirectory()) {
                    Path mainFolder = Paths.get(selectedFile.toURI());
                    Path newFolder = mainFolder.resolve(folderName);
                    try {
                        Files.createDirectory(newFolder);
                        treeItem.getChildren().add(new TreeItem<>(folderName));
                    } catch (IOException e) {
                        showAlert("Folder creation Error", "Unable to create the Folder");
                    }
                } else {
                    Path mainFolder = Paths.get(selectedFile.getParentFile().toURI());
                    Path newFolder = mainFolder.resolve(folderName);
                    try {
                        Files.createDirectory(newFolder);
                        treeItem.getParent().getChildren().add(new TreeItem<>(folderName));
                    } catch (IOException e) {
                        showAlert("Folder creation Error", "Unable to create the Folder");
                    }
                }
            });
            dialog.getDialogPane().getStyleClass().add("dialog-pane");
        }
    }

    public void handleCreateFile() {
        createFile();
    }

    private void createFile() {
        TreeItem<String> treeItem = fileTreeView.getSelectionModel().getSelectedItem();
        if (selectedFile != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("New File");
            dialog.setHeaderText("Create a new file");
            dialog.setContentText("Enter the name of the file");
            dialog.showAndWait().ifPresent(fileName -> {
                if (selectedFile.isDirectory()) {
                    Path mainFolder = Paths.get(selectedFile.toURI());
                    Path newFile = mainFolder.resolve(fileName);
                    try {
                        Files.createFile(newFile);
                        treeItem.getChildren().add(new TreeItem<>(fileName));
                    } catch (IOException e) {
                        showAlert("File creation Error", "Unable to create the File");
                    }
                } else {
                    Path mainFolder = Paths.get(selectedFile.getParentFile().toURI());
                    Path newFile = mainFolder.resolve(fileName);
                    try {
                        Files.createFile(newFile);
                        treeItem.getParent().getChildren().add(new TreeItem<>(fileName));
                    } catch (IOException e) {
                        showAlert("File creation Error", "Unable to create the File");
                    }
                }

            });
            dialog.getDialogPane().getStyleClass().add("dialog-pane");
        }
    }

    private void pasteFile(TYPE type) {
        TreeItem<String> treeItem = fileTreeView.getSelectionModel().getSelectedItem();
        System.out.println("pasting file/folder");
        try {
            System.out.println("trying");
            System.out.println("paste: clipboard: " + clipboard);
            System.out.println("source: " + clipboard.toPath());
            System.out.println("dest: " + selectedFile.toPath());

            Path destPath;

            if (selectedFile.isDirectory()) {
                destPath = selectedFile.toPath().resolve(clipboard.getName());
                System.out.println("dest: " + destPath);

                if (Files.exists(destPath)) {
                    showAlert(type + " Error", "The file/folder already exists in the destination folder");
                    return;
                }

            } else {
                destPath = selectedFile.getParentFile().toPath().resolve(clipboard.getName());
                System.out.println("dest: " + destPath);

                if (Files.exists(destPath)) {
                    showAlert(type + " Error", "The file/folder already exists in the destination folder");
                    return;
                }
            }

            // Perform the copy or cut operation
            if (Files.isDirectory(clipboard.toPath())) {
                if (type.equals(TYPE.COPY)) {
                    copyFolder(clipboard.toPath(), destPath);
                } else if (type.equals(TYPE.CUT)) {
                    cutItem.getParent().getChildren().remove(cutItem);
                    Files.move(clipboard.toPath(), destPath);
                }
            } else {
                if (type.equals(TYPE.COPY)) {
                    Files.copy(clipboard.toPath(), destPath);
                } else if (type.equals(TYPE.CUT)) {
                    cutItem.getParent().getChildren().remove(cutItem);
                    Files.move(clipboard.toPath(), destPath);
                }
            }

            // Update the TreeView
            TreeItem<String> newTreeItem = createNewTreeItem(destPath.toFile());
            if (selectedFile.isDirectory()) {
                treeItem.getChildren().add(newTreeItem);
            } else {
                treeItem.getParent().getChildren().add(newTreeItem);
            }

        } catch (IOException e) {
            System.out.println("error");
            showAlert(type + " Error", "Unable to " + type + " the file/folder");
        }
    }

    private TreeItem<String> createNewTreeItem(File file) {
        TreeItem<String> treeItem = new TreeItem<>(file.getName());
        if (file.isDirectory()) {
            // Add dummy children to make expandable
            treeItem.getChildren().add(new TreeItem<>("Loading..."));
            treeItem.addEventHandler(TreeItem.branchExpandedEvent(), event -> loadDirectory(treeItem, file));
        }
        return treeItem;
    }

    private void loadDirectory(TreeItem<String> treeItem, File directory) {
        treeItem.getChildren().clear(); // Remove dummy children
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                treeItem.getChildren().add(createTreeItem(file));
            }
        }
    }

    private void copyFolder(Path source, Path destination) throws IOException {
        Files.walk(source).forEach(sourcePath -> {
            try {
                Path targetPath = destination.resolve(source.relativize(sourcePath));
                System.out.println("Copying " + sourcePath + " to " + targetPath);
                if (Files.isDirectory(sourcePath)) {
                    Files.createDirectories(targetPath);
                } else {
                    Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                throw new RuntimeException("Error while copying folder: " + sourcePath, e);
            }
        });
    }

    private void deleteFiles(File file) throws IOException {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File child: files) {
                    deleteFiles(child);
                }
            }
        }
        Files.delete(file.toPath());
    }

    private boolean renameFile(File oldFile, String newName) {
        File newFile = new File(oldFile.getParent() + File.separator + newName);
        if (newFile.exists()) {
            showAlert("Rename Failed", "The name already exists");
            return false;
        }
        boolean renamed = oldFile.renameTo(newFile);
        if (!renamed){
            showAlert("Rename Failed", "Renaming the File/Folder failed");
            return false;
        }
        return true;
    }

    private void setupKeyBoardShortcuts() {
        KeyCombination copyShortcut = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);
        KeyCombination cutShortcut = new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN);
        KeyCombination pasteShortcut = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN);
        KeyCombination renameShortcut = new KeyCodeCombination(KeyCode.F2);
        KeyCombination deleteShortcut = new KeyCodeCombination(KeyCode.DELETE);
        KeyCombination newFolderShortcut = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);
        KeyCombination newFileShortcut = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);

        fileTreeView.setOnKeyPressed(event -> {

            TreeItem<String> selectedTreeItem = fileTreeView.getSelectionModel().getSelectedItem();

            if (selectedFile == null) {
                return;
            }

            if (copyShortcut.match(event)) {
                handleCopy();
                event.consume();
            } else if (pasteShortcut.match(event)) {
                handlePaste();
                event.consume();
            } else if (cutShortcut.match(event)) {
                handleCut();
                event.consume();
            } else if (renameShortcut.match(event)) {
                handleRename(selectedTreeItem);
                event.consume();
            } else if (deleteShortcut.match(event)) {
                handleDelete();
                event.consume();
            } else if (newFolderShortcut.match(event)) {
                handleCreateFolder();
                event.consume();
            } else if (newFileShortcut.match(event)) {
                handleCreateFile();
                event.consume();
            }
        });
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setDarkTheme() {
        System.out.println("Dark Theme set");
    }

    public void setLightTheme() {
        System.out.println("Light Theme set");
    }

}
