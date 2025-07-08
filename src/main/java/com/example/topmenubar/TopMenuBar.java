package com.example.topmenubar;

import com.example.Main;
import com.example.about.About;
import com.example.boilerplateswindow.BoilerPlatesWindow;
import com.example.codeeditor.CodeEditor;
import com.example.codeeditor.InputBox;
import com.example.documentation.Documentation;
import com.example.messagebox.MessageBox;
import com.example.objectspane.Datatype;
import com.example.objectspane.ObjectsPane;
import com.example.projectnavigator.ProjectNavigator;
import com.example.settings.Settings;
import com.example.shortcutswindow.ShortcutsWindow;
import com.example.terminal.Terminal;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TopMenuBar extends HBox {

    MenuBar menuBar = new MenuBar();
    Menu fileMenu = new Menu("File");
    Menu editMenu = new Menu("Edit");
    Menu viewMenu = new Menu("View");
    Menu codeMenu = new Menu("Code");
    Menu runMenu = new Menu("Run");
    Menu toolsMenu = new Menu("Tools");
    Menu windowMenu = new Menu("Window");
    Menu helpMenu = new Menu("Help");

    MenuItem newFileMenu = new MenuItem("New File");
    MenuItem newFolderMenu = new MenuItem("New Folder");
    MenuItem openMenuItem = new MenuItem("Open");
    MenuItem saveMenuItem = new MenuItem("Save");
    MenuItem saveAsMenuItem = new MenuItem("Save As");
    MenuItem exitMenuItem = new MenuItem("Exit");

    MenuItem undoMenuItem = new MenuItem("Undo");
    MenuItem redoMenuItem = new MenuItem("Redo");
    MenuItem cutMenuItem = new MenuItem("Cut");
    MenuItem copyMenuItem = new MenuItem("Copy");
    MenuItem pasteMenuItem = new MenuItem("Paste");
    MenuItem findMenuItem = new MenuItem("Find");
    MenuItem replaceMenuItem = new MenuItem("Replace");
    MenuItem selectAllMenuItem = new MenuItem("Select All");

    MenuItem toggleLineNumbersMenuItem = new MenuItem("Toggle Line Numbers");
    MenuItem showObjectsPaneMenuItem = new MenuItem("Show Objects Pane");
    MenuItem hideObjectsPaneMenuItem = new MenuItem("Hide Objects Pane");
    MenuItem showMessageBoxMenuItem = new MenuItem("Show MessageBox");
    MenuItem hideMessageBoxMenuItem = new MenuItem("Hide MessageBox");
    MenuItem showProjectNavigatorMenuItem = new MenuItem("Show Project Navigator");
    MenuItem hideProjectNavigatorMenuItem = new MenuItem("Hide Project Navigator");

    MenuItem commentMenuItem = new MenuItem("Comment/Uncomment");
    MenuItem gotoLineMenuItem = new MenuItem("Go To Line");
    MenuItem setAbbreviationsItem = new MenuItem("Set Abbreviations");

    MenuItem buildMenuItem = new MenuItem("Build");
    MenuItem runMenuItem = new MenuItem("Run");
    MenuItem buildAllMenuItem = new MenuItem("Build All");
    MenuItem stopMenuItem = new MenuItem("Stop");
    FontIcon runIcon = new FontIcon(FontAwesomeSolid.PLAY);
    FontIcon stopIcon = new FontIcon(FontAwesomeSolid.STOP);
    FontIcon buildIcon = new FontIcon(FontAwesomeSolid.COG);
    Button runButton = new Button();
    Button stopButton = new Button();
    Button buildButton = new Button();

    MenuItem preferencesMenuItem = new MenuItem("Preferences");

    MenuItem newWindowMenuItem = new MenuItem("New Window");
    MenuItem fullScreenMenuItem = new MenuItem("Full Screen");

    MenuItem documentationMenuItem = new MenuItem("Documentation");
    MenuItem keyboardShortcutsMenuItem = new MenuItem("Keyboard Shortcuts");
    MenuItem aboutMenuItem = new MenuItem("About");

    CodeEditor codeEditor;
    Terminal terminal;
    ObjectsPane objectsPane;
    Region spacer = new Region();
    ProjectNavigator projectNavigator;
    SplitPane app;

    Stage stage;
    Main main;

    public TopMenuBar(CodeEditor codeEditor, Terminal terminal, ObjectsPane objectsPane, SplitPane app, ProjectNavigator projectNavigator, Stage stage, Main main) {
        HBox.setHgrow(spacer, Priority.ALWAYS);
        this.codeEditor = codeEditor;
        this.objectsPane = objectsPane;
        this.terminal = terminal;
        this.app = app;
        this.projectNavigator = projectNavigator;
        this.stage = stage;
        this.main = main;

        runIcon.setIconColor(Color.GREEN);
        stopIcon.setIconColor(Color.RED);
        buildIcon.setIconColor(Color.YELLOW);
        buildIcon.setIconSize(16);

        runButton.setGraphic(runIcon);
        stopButton.setGraphic(stopIcon);
        buildButton.setGraphic(buildIcon);

        stopButton.getStyleClass().add("run-button");
        runButton.getStyleClass().add("run-button");
        buildButton.getStyleClass().add("run-button");

        runButton.setOnAction(event -> handleRun());
        stopButton.setOnAction(event -> handleStop());
        buildButton.setOnAction(event -> handleBuild());

        spacer.getStyleClass().add("region");

        // file menu
        saveMenuItem.setOnAction(event -> handleSave());
        saveMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        exitMenuItem.setOnAction(event -> handleExit());
        exitMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
        newFileMenu.setOnAction(event -> handleNewFile());
        newFileMenu.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        newFolderMenu.setOnAction(event -> handleNewFolder());
        newFolderMenu.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+N"));
        openMenuItem.setOnAction(event -> projectNavigator.handleOpenFolder());
        openMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        saveAsMenuItem.setOnAction(event -> {
            try {
                projectNavigator.handleSaveAs();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        saveAsMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));

        // edit menu
        undoMenuItem.setOnAction(event -> codeEditor.handleUndo());
        undoMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Z"));
        redoMenuItem.setOnAction(event -> codeEditor.handleRedo());
        redoMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Y"));
        copyMenuItem.setOnAction(event -> codeEditor.handleCopy());
        copyMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+C"));
        cutMenuItem.setOnAction(event -> codeEditor.handleCut());
        cutMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
        pasteMenuItem.setOnAction(event -> codeEditor.handlePaste());
        pasteMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+V"));
        selectAllMenuItem.setOnAction(event -> codeEditor.handleSelectAll());
        selectAllMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+A"));
        findMenuItem.setOnAction(event -> handleFind());
        findMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+F"));
        replaceMenuItem.setOnAction(event -> handleReplace());
        replaceMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+H"));

        // view menu
        toggleLineNumbersMenuItem.setOnAction(event -> codeEditor.handleToggleLineNumber());
        hideProjectNavigatorMenuItem.setOnAction(event -> handleHideProjectNavigator());
        showProjectNavigatorMenuItem.setOnAction(event -> handleShowProjectNavigator());
        hideObjectsPaneMenuItem.setOnAction(event -> handleHideObjectsPane());
        showObjectsPaneMenuItem.setOnAction(event -> handleShowObjectsPane());
        hideMessageBoxMenuItem.setOnAction(event -> handleHideMessageBox());
        showMessageBoxMenuItem.setOnAction(event -> handleShowMessageBox());

        // code menu
        commentMenuItem.setOnAction(event -> codeEditor.handleComment());
        commentMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+/"));
        gotoLineMenuItem.setOnAction(event -> handleGoto());
        gotoLineMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+G"));
        setAbbreviationsItem.setOnAction(event -> {
            try {
                handleGenerateCode();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // run menu
        buildMenuItem.setOnAction(event -> handleBuild());
        buildMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+B"));
        runMenuItem.setOnAction(event -> handleRun());
        runMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+R"));
        buildAllMenuItem.setOnAction(event -> handleBuildAll());
        buildAllMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+B"));

        // tools menu
        preferencesMenuItem.setOnAction(event -> handlePreferences());

        // window menu
        fullScreenMenuItem.setOnAction(event -> handleFullScreen());
        newWindowMenuItem.setOnAction(event -> {
            try {
                handleNewWindow();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // help menu
        documentationMenuItem.setOnAction(event -> handleDocumentation());
        keyboardShortcutsMenuItem.setOnAction(event -> handleKeyBoardShortcuts());
        aboutMenuItem.setOnAction(event -> handleAbout());

        fileMenu.getItems().addAll(newFileMenu, newFolderMenu, openMenuItem, saveMenuItem, saveAsMenuItem,  exitMenuItem);
        editMenu.getItems().addAll(undoMenuItem, redoMenuItem, cutMenuItem, copyMenuItem, pasteMenuItem, findMenuItem, replaceMenuItem, selectAllMenuItem);
        viewMenu.getItems().addAll(toggleLineNumbersMenuItem,
                hideObjectsPaneMenuItem, hideMessageBoxMenuItem, hideProjectNavigatorMenuItem
        );
        codeMenu.getItems().addAll(commentMenuItem, gotoLineMenuItem, setAbbreviationsItem);
        runMenu.getItems().addAll(buildMenuItem, buildAllMenuItem, runMenuItem, stopMenuItem);
        toolsMenu.getItems().addAll(preferencesMenuItem);
        windowMenu.getItems().addAll(newWindowMenuItem, fullScreenMenuItem);
        helpMenu.getItems().addAll(documentationMenuItem, keyboardShortcutsMenuItem, aboutMenuItem);

        menuBar.getMenus().addAll(fileMenu, editMenu, viewMenu, codeMenu, runMenu, toolsMenu, windowMenu, helpMenu);

        this.getChildren().addAll(menuBar, spacer, buildButton, runButton, stopButton);
        for (Menu menu: menuBar.getMenus()) {
            menu.getStyleClass().add("menu");
            menu.setId("debug-menu");
            for (MenuItem menuItem: menu.getItems()) {
                menuItem.getStyleClass().add("menu-item");
                menuItem.setId("debug-menu-item");
            }
        }
        menuBar.getStyleClass().add("menu-bar");
        menuBar.setId("debug-menu-bar");
        this.getStyleClass().add("top-menu-bar");
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/topmenubar/top_menu_bar.css")).toExternalForm());
    }

    private void handleDocumentation() {
        Scene settingsScreen = new Scene(new Documentation());
        Stage stage2 = new Stage();
        stage2.setTitle("Documentation");
        stage2.setScene(settingsScreen);
        stage2.setWidth(1000);
        stage2.setHeight(550);
        stage2.setResizable(false);
        stage2.show();
    }

    private void handlePreferences() {
        Scene settingsScreen = new Scene(new Settings(projectNavigator, codeEditor, objectsPane, terminal, messageBox, this));
        Stage stage2 = new Stage();
        stage2.setTitle("Settings");
        stage2.setScene(settingsScreen);
        stage2.setWidth(1000);
        stage2.setHeight(550);
        stage2.setResizable(false);
        stage2.show();
    }

    private void handleAbout() {
        Stage stage1 = new Stage();
        Scene about = new Scene(new About());
        stage1.setTitle("About DCode");
        stage1.setResizable(false);
        stage1.setHeight(330);
        stage1.setWidth(600);
        stage1.setScene(about);
        stage1.show();
    }

    private void handleGenerateCode() throws IOException {
        Stage stage1 = new Stage();
        Scene scene = new Scene(new BoilerPlatesWindow());
        stage1.setTitle("Set Abbreviation");
        stage1.setScene(scene);
        stage1.setHeight(700);
        stage1.setWidth(1000);
        stage1.setResizable(false);
        stage1.show();
    }

    private void handleKeyBoardShortcuts() {
        Stage stage1 = new Stage();
        Scene shortcutsWindow = new Scene(new ShortcutsWindow());
        stage1.setTitle("Shortcuts");
        stage1.setScene(shortcutsWindow);
        stage1.setWidth(550);
        stage1.setHeight(540);
        stage1.setResizable(false);
        stage1.show();
    }

    private void handleNewFile() {
        projectNavigator.handleCreateFile();
    }

    private void handleNewFolder() {
        projectNavigator.handleCreateFolder();
    }

    private void handleReplace() {
        if (codeEditor.getChildren().size() != 3) {
            codeEditor.getChildren().add(1, new InputBox("Replace", codeEditor));
        } else {
            codeEditor.getChildren().remove(1);
            codeEditor.getChildren().add(1, new InputBox("Replace", codeEditor));
        }
    }

    public void handleFind() {
        if (codeEditor.getChildren().size() != 3) {
            codeEditor.getChildren().add(1, new InputBox("Find", codeEditor));
        } else {
            codeEditor.getChildren().remove(1);
            codeEditor.getChildren().add(1, new InputBox("Find", codeEditor));
        }
    }

    private void showInputBox(String title) {
        codeEditor.getChildren().add(1, new InputBox(title));
    }

    private void handleGoto() {
        if (codeEditor.getChildren().size() != 3) {
            codeEditor.getChildren().add(1, new InputBox("Goto", codeEditor));
        } else {
            codeEditor.getChildren().remove(1);
            codeEditor.getChildren().add(1, new InputBox("Goto", codeEditor));
        }
    }

    private void handleExit() {
        System.out.println("exiting");
        stage.close();
    }

    private void handleFullScreen() {
        stage.setMaximized(true);
    }

    private void handleNewWindow() throws IOException {
        Stage newStage = new Stage();
        main.setupWindow(newStage);
    }

    private void handleSave() {
        try {
            codeEditor.saveFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    SplitPane codeEditorAndObjectsPane;
    private void handleHideObjectsPane() {
        SplitPane rightPart = (SplitPane) app.getItems().getLast();
        codeEditorAndObjectsPane = (SplitPane) rightPart.getItems().getFirst();
        codeEditorAndObjectsPane.getItems().removeLast();
        viewMenu.getItems().remove(1);
        viewMenu.getItems().add(1, showObjectsPaneMenuItem);
    }

    private void handleShowObjectsPane() {
        codeEditorAndObjectsPane.getItems().addLast(objectsPane);
        viewMenu.getItems().remove(1);
        viewMenu.getItems().add(1, hideObjectsPaneMenuItem);
    }
    SplitPane terminalAndMessageBox;
    MessageBox messageBox;
    private void handleHideMessageBox() {
        SplitPane rightPart = (SplitPane) app.getItems().getLast();
        terminalAndMessageBox = (SplitPane) rightPart.getItems().getLast();
        messageBox = (MessageBox) terminalAndMessageBox.getItems().getLast();
        terminalAndMessageBox.getItems().removeLast();
        viewMenu.getItems().remove(2);
        viewMenu.getItems().add(2, showMessageBoxMenuItem);
    }

    private void handleShowMessageBox() {
        terminalAndMessageBox.getItems().add(messageBox);
        viewMenu.getItems().remove(2);
        viewMenu.getItems().add(2, hideMessageBoxMenuItem);
    }

//    ProjectNavigator projectNavigator;
    private void handleHideProjectNavigator() {
        projectNavigator = (ProjectNavigator) app.getItems().getFirst();
        app.getItems().removeFirst();
        viewMenu.getItems().remove(3);
        viewMenu.getItems().add(3, showProjectNavigatorMenuItem);
    }

    private void handleShowProjectNavigator() {
        app.getItems().addFirst(projectNavigator);
        viewMenu.getItems().remove(3);
        viewMenu.getItems().add(3, hideProjectNavigatorMenuItem);
        app.setDividerPositions(0.2);
    }

    private void handleStop() {
        System.out.println("Stopping the compilation");
    }

    private void handleBuild() {
        System.out.println("building the file");
        File parent = codeEditor.getCurrentFile().getParentFile();
        String fileName = codeEditor.getCurrentFileName();
        System.out.println(parent.toString());
        System.out.println(fileName);
        String name = fileName.substring(0, fileName.indexOf("."));
        System.out.println(name);
        terminal.execute("cd " + parent.toString() + " && javac --release 21 " + fileName);
    }

    private void handleBuildAll() {
        System.out.println("running all files");
        File parent = codeEditor.getCurrentFile().getParentFile();
        String fileName = codeEditor.getCurrentFileName();
        System.out.println(parent.toString());
        System.out.println(fileName);
        terminal.execute("cd " + parent.toString() + " && javac --release 17 *.java");
    }

    private void handleDraw() {
        try {
            File codeFile = codeEditor.getCurrentFile();
            Map<String, Object> variables = CodeExecutor.execute(codeFile);
            objectsPane.clear();
            variables.forEach((varName, value) -> {
                if (value instanceof Integer) {
                    System.out.println("Integer: " + varName + " : " + value);
                    objectsPane.add(Datatype.INTEGER, varName, value.toString());
                } else if (value instanceof Character) {
                    System.out.println("Character: " + varName + " : " + value);
                    objectsPane.add(Datatype.CHARACTER, varName, value.toString());
                } else if (value instanceof Float) {
                    System.out.println("Float: " + varName + " : " + value);
                    objectsPane.add(Datatype.FLOAT, varName, value.toString());
                } else if (value instanceof Boolean) {
                    System.out.println("Boolean: " + varName + " : " + value);
                    objectsPane.add(Datatype.BOOLEAN, varName, value.toString());
                } else if (value instanceof String) {
                    System.out.println("String: " + varName + " : " + value);
                    objectsPane.add(Datatype.STRING, varName, value.toString());
                } else if (value instanceof Object[][]) {
                    System.out.println("Object[][]: " + varName + " : " + value);
                    List<List<String>> values = new ArrayList<>();

                    for (Object[] objects: (Object[][]) value) {
                        List<String> row = new ArrayList<>();
                        for (Object object: (Object[]) objects) {
                            row.add(object.toString());
                        }
                        values.add(row);
                    }

                    String[][] stringArray = new String[values.size()][];
                    for (int i = 0; i < values.size(); i++) {
                        List<String> row = values.get(i);
                        stringArray[i] = row.toArray(new String[0]);
                    }

                    switch (value) {
                        case Integer[][] ignored -> {
                            objectsPane.add2DList(Datatype.INTEGER, varName, stringArray);
                        }
                        case Float[][] ignored -> {
                            objectsPane.add2DList(Datatype.FLOAT, varName, stringArray);
                        }
                        case String[][] ignored -> {
                            objectsPane.add2DList(Datatype.STRING, varName, stringArray);
                        }
                        case Character[][] ignored -> {
                            objectsPane.add2DList(Datatype.CHARACTER, varName, stringArray);
                        }
                        case Boolean[][] ignored -> {
                            objectsPane.add2DList(Datatype.BOOLEAN, varName, stringArray);
                        }
                        default -> {

                        }
                    }

                } else if (value instanceof Object[]) {
                    System.out.println("Object[]: " + varName + " : " + value);
                    List<String> values = new ArrayList<>();
                    for (Object object: (Object[]) value) {
                        values.add(object.toString());
                    }
                    switch (value) {
                        case Integer[] ignored ->
                                objectsPane.addList(Datatype.INTEGER, varName, values.toArray(new String[0]));
                        case String[] ignored ->
                                objectsPane.addList(Datatype.STRING, varName, values.toArray(new String[0]));
                        case Boolean[] ignored ->
                                objectsPane.addList(Datatype.BOOLEAN, varName, values.toArray(new String[0]));
                        case Float[] ignored ->
                                objectsPane.addList(Datatype.FLOAT, varName, values.toArray(new String[0]));
                        case Character[] ignored ->
                                objectsPane.addList(Datatype.CHARACTER, varName, values.toArray(new String[0]));
                        default -> {
                        }
                    }
                }
            });
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private void handleRun() {
        System.out.println("running the file");
        File parent = codeEditor.getCurrentFile().getParentFile();
        String fileName = codeEditor.getCurrentFileName();
        System.out.println(parent.toString());
        System.out.println(fileName);
        String name = fileName.substring(0, fileName.indexOf("."));
        System.out.println(name);
        terminal.execute("cd " + parent.toString() + " && javac --release 17 " + fileName + " && java " + name);
        handleDraw();
    }

    public void setDarkTheme() {
        System.out.println("Dark Theme set");
    }

    public void setLightTheme() {
        System.out.println("Light Theme set");
    }

}