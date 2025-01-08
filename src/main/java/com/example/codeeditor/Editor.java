package com.example.codeeditor;

import com.example.GlobalVariables;
import com.example.boilerplateswindow.BoilerPlates;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import javafx.event.EventHandler;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.javaparser.*;
import org.fxmisc.richtext.model.TwoDimensional;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

public class Editor extends CodeArea {

    private static final String[] ACCESS_MODIFIERS = {
            "private", "protected", "public"
    };

    private static final String[] CONTROL_FLOW_KEYWORDS = {
            "if", "else", "switch", "case", "default", "for", "while", "do", "break", "continue", "return", "try", "catch", "finally", "throw", "throws"
    };

    private static final String[] DATA_TYPES = {
            "int", "float", "double", "boolean", "char", "byte", "short", "long", "void"
    };

    private static final String[] OTHER_KEYWORDS = {
            "abstract", "assert", "class", "enum", "extends", "final", "implements", "import", "instanceof", "interface", "native", "new", "package", "static", "strictfp", "super", "synchronized", "this", "transient", "volatile", "goto", "const"
    };

    // Define patterns for each category
    private static final String ACCESS_MODIFIER_PATTERN = "\\b(" + String.join("|", ACCESS_MODIFIERS) + ")\\b";
    private static final String CONTROL_FLOW_PATTERN = "\\b(" + String.join("|", CONTROL_FLOW_KEYWORDS) + ")\\b";
    private static final String DATA_TYPE_PATTERN = "\\b(" + String.join("|", DATA_TYPES) + ")\\b";
    private static final String OTHER_KEYWORD_PATTERN = "\\b(" + String.join("|", OTHER_KEYWORDS) + ")\\b";

    private static final String PAREN_PATTERN = "[()]";
    private static final String BRACE_PATTERN = "[{}]";
    private static final String BRACKET_PATTERN = "[\\[\\]]";
    private static final String SEMICOLON_PATTERN = ";";
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\""; // Matches double-quoted strings
    private static final String CHAR_PATTERN = "'([^'\\\\]|\\\\.)'"; // Matches single-quoted characters
    private static final String NUMBER_PATTERN = "\\b\\d+(\\.\\d+)?\\b"; // Matches integers and floats
    private static final String COMMENT_PATTERN = "//[^\n]*|/\\*(.|\\R)*?\\*/"; // Matches single-line and multi-line comments

    private static final Pattern PATTERN = Pattern.compile(
            "(?<ACCESSMODIFIER>" + ACCESS_MODIFIER_PATTERN + ")"
                    + "|(?<CONTROLFLOW>" + CONTROL_FLOW_PATTERN + ")"
                    + "|(?<DATATYPE>" + DATA_TYPE_PATTERN + ")"
                    + "|(?<OTHERKEYWORD>" + OTHER_KEYWORD_PATTERN + ")"
                    + "|(?<PAREN>" + PAREN_PATTERN + ")"
                    + "|(?<BRACE>" + BRACE_PATTERN + ")"
                    + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
                    + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
                    + "|(?<STRING>" + STRING_PATTERN + ")"
                    + "|(?<CHAR>" + CHAR_PATTERN + ")"
                    + "|(?<NUMBER>" + NUMBER_PATTERN + ")"
                    + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
    );

    private final ContextMenu suggestionsMenu = new ContextMenu();
    private ContextMenu contextBasedSuggestionsMenu = new ContextMenu();
    private Trie keywordsTrie;
    private Trie stringTrie;
    private Trie integerTrie;
    private Trie floatTrie;

    private boolean isTextBeingModified = false;
    private void setupErrorHighlighting() {
        this.textProperty().addListener((obs, oldText, newText) -> {
            highlightErrors(newText);
        });
    }

    private void highlightErrors(String code) {
        List<Diagnostic<? extends JavaFileObject>> errors = ErrorHighlighter.getCompilationErrors(code);
        ErrorHighlighter.highlightErrors(this, errors);
    }

    public Editor(String text) throws IOException {
        this();
        this.insertText(0, text);
    }

    BoilerPlates boilerPlates = new BoilerPlates();

    public void setFontSize() {
        String small = Objects.requireNonNull(getClass().getResource("/com/example/codeeditor/font_small.css")).toExternalForm();
        String medium = Objects.requireNonNull(getClass().getResource("/com/example/codeeditor/font_medium.css")).toExternalForm();
        String large = Objects.requireNonNull(getClass().getResource("/com/example/codeeditor/font_large.css")).toExternalForm();
        this.getStylesheets().removeAll(small, medium, large);
        if (GlobalVariables.getFontSize().equals("Small")) {
            this.getStylesheets().add(small);
        } else if (GlobalVariables.getFontSize().equals("Medium")) {
            this.getStylesheets().add(medium);
        } else {
            this.getStylesheets().add(large);
        }
    }

    public void setFontTheme() {
        String default_ = Objects.requireNonNull(getClass().getResource("/com/example/codeeditor/default.css")).toExternalForm();
        String monokai = Objects.requireNonNull(getClass().getResource("/com/example/codeeditor/monokai.css")).toExternalForm();
        String solarized_dark = Objects.requireNonNull(getClass().getResource("/com/example/codeeditor/solarized_dark.css")).toExternalForm();
        String solarized_light = Objects.requireNonNull(getClass().getResource("/com/example/codeeditor/solarized_light.css")).toExternalForm();
        String fontTheme = GlobalVariables.getFontTheme();
        System.out.println("font theme: " + fontTheme);

        this.getStylesheets().removeAll(default_, monokai, solarized_dark, solarized_light);

        if (fontTheme.equals("Default")) {
            System.out.println("adding default");
            this.getStylesheets().add(default_);
        } else if (fontTheme.equals("Monokai")) {
            System.out.println("adding monokai");
            this.getStylesheets().add(monokai);
        } else if (fontTheme.equals("Solarized_Dark")) {
            System.out.println("adding dark");
            this.getStylesheets().add(solarized_dark);
        } else if (fontTheme.equals("Solarized_Light")) {
            System.out.println("adding light");
            this.getStylesheets().add(solarized_light);
        }
    }

    public Editor() throws IOException {
        this.getStyleClass().add("code-editor");
        this.setParagraphGraphicFactory(LineNumberFactory.get(this));

        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/codeeditor/code_editor.css")).toExternalForm());
        if (GlobalVariables.getFontSize().equals("Small")) {
            this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/codeeditor/font_small.css")).toExternalForm());
        } else if (GlobalVariables.getFontSize().equals("Medium")) {
            this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/codeeditor/font_medium.css")).toExternalForm());
        } else {
            this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/codeeditor/font_large.css")).toExternalForm());
        }

        String default_ = Objects.requireNonNull(getClass().getResource("/com/example/codeeditor/default.css")).toExternalForm();
        String monokai = Objects.requireNonNull(getClass().getResource("/com/example/codeeditor/monokai.css")).toExternalForm();
        String solarized_dark = Objects.requireNonNull(getClass().getResource("/com/example/codeeditor/solarized_dark.css")).toExternalForm();
        String solarized_light = Objects.requireNonNull(getClass().getResource("/com/example/codeeditor/solarized_light.css")).toExternalForm();
        String fontTheme = GlobalVariables.getFontTheme();
        System.out.println("init font theme: " + fontTheme);

        if (fontTheme.equals("Default")) {
            System.out.println("init default");
            this.getStylesheets().add(default_);
        } else if (fontTheme.equals("Monokai")) {
            System.out.println("init monokai");
            this.getStylesheets().add(monokai);
        } else if (fontTheme.equals("Solarized_Dark")) {
            System.out.println("init dark");
            this.getStylesheets().add(solarized_dark);
        } else if (fontTheme.equals("Solarized_Light")) {
            System.out.println("init light");
            this.getStylesheets().add(solarized_light);
        }

        this.textProperty().addListener((observable, oldText, newText) -> {
            this.setStyleSpans(0, computeHighlighting(newText));
        });

        init_tries();
        setupErrorHighlighting();

        if (GlobalVariables.getAutoBracketClosing()) {
            this.addEventFilter(KeyEvent.KEY_TYPED, this::handleKeyTyped);
        }

        this.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPress);

        this.textProperty().addListener((observable, oldText, newText) -> {
            int caretPosition = this.getCaretPosition();
            String currentWord = getCurrentWord(caretPosition);
            if (!currentWord.isEmpty()) {
                showSuggestions();
            } else {
                suggestionsMenu.hide();
            }
        });

        this.caretPositionProperty().addListener((observable, oldPos, newPos) -> {
            colorBrackets(newPos);
        });

        this.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (suggestionsMenu.isShowing() && !suggestionsMenu.getItems().isEmpty()) {
                    MenuItem firstItem = suggestionsMenu.getItems().getFirst();
                    firstItem.fire();
                    event.consume();
                    System.out.println("this");
                }
            }
        });

        this.textProperty().addListener((observable, oldText, newText) -> {
            if (newText.length() < oldText.length()) {
                int caret = this.getCaretPosition();

                char deletedChar = oldText.charAt(caret);
                System.out.println("deleted: " + deletedChar);

                if (isBracket(deletedChar)) {
                    int direction = findDirection(deletedChar);
                    char match = getMatchingBracket(deletedChar);

                    if (direction == 1 && caret < this.getLength() && this.getText(caret, caret + 1).charAt(0) == match) {
                        this.deleteText(caret, caret + 1);
                    } else if (direction == -1 && caret > 0 && this.getText(caret - 1, caret).charAt(0) == match) {

                    }
                }
            }
        });

        this.focusedProperty().addListener((obs, wasFocused, isFocused) -> {
            if (!isFocused) {
                suggestionsMenu.hide();
                contextBasedSuggestionsMenu.hide();
            }
        });

        this.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.TAB) {
                event.consume();
                System.out.println("TAB");
                System.out.println(this.getSelection().getLength());
                if (this.getSelection().getLength() > 0) {
                    int start = this.getSelection().getStart();
                    int end = this.getSelection().getEnd();

                    System.out.println("s: " + start + "e: " + end);

                    int startLine = this.offsetToPosition(start, org.fxmisc.richtext.model.TwoDimensional.Bias.Backward).getMajor();
                    int endLine = this.offsetToPosition(end, org.fxmisc.richtext.model.TwoDimensional.Bias.Forward).getMajor();
                    System.out.println("start: " + startLine + "end: " + endLine);

                    if (event.isShiftDown()) {
                        System.out.println("unindenting");
                        for (int i = startLine; i <= endLine; i++) {
                            String lineText = this.getParagraph(i).getText();
                            if (lineText.startsWith("\t")) {
                                this.replaceText(this.getAbsolutePosition(i, 0), this.getAbsolutePosition(i, 1), "");
                            } else if (lineText.startsWith("    ")) {
                                this.replaceText(this.getAbsolutePosition(i, 0), this.getAbsolutePosition(i, 4), "");
                            }
                        }
                    } else {
                        System.out.println("indenting");
                        for (int line = startLine; line <= endLine; line++) {
                            int lineStart = this.position(line, 0).toOffset();
                            this.insertText(lineStart, "\t"); // Add a tab at the start of the line
                        }
                    }
                } else {
                    if (event.isShiftDown()) {
                        int para = this.getCurrentParagraph();
                        String text = this.getText(para);
                        if (text.startsWith("\t")) {
                            this.replaceText(this.getAbsolutePosition(para, 0), this.getAbsolutePosition(para, 1), "");
                        } else if (text.startsWith("    ")) {
                            this.replaceText(this.getAbsolutePosition(para, 0), this.getAbsolutePosition(para, 4), "");
                        }
                    } else {
                        this.replaceSelection("\t");
                    }
                }
                event.consume();
            }
        });

        this.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.SLASH && event.isControlDown() && event.isShiftDown()) {
                event.consume();
                IndexRange selection = this.getSelection();
                String text = this.getText();

                // Calculate the start of the first line in the selection
                int start = text.lastIndexOf('\n', selection.getStart() - 1);
                if (start == -1) start = 0; // If there's no newline before, start at the beginning of the document
                else start += 1; // Move to the start of the line

                // Calculate the end of the last line in the selection
                int end = text.indexOf('\n', selection.getEnd());
                if (end == -1) end = text.length(); // If there's no newline after, go to the end of the document

                // Get the selected text including complete lines
                String selectedText = text.substring(start, end);

                // Check if the selected text is already commented
                boolean isCommented = selectedText.trim().startsWith("/*") && selectedText.trim().endsWith("*/");

                if (isCommented) {
                    // Remove the existing comments
                    String uncommentedText = selectedText
                            .replaceFirst("^\\s*/\\*+", "") // Remove opening comment marker
                            .replaceFirst("\\*+/\\s*$", ""); // Remove closing comment marker
                    this.replaceText(start, end, uncommentedText);
                } else {
                    // Add comments if not already commented
                    this.insertText(end, " */");
                    this.insertText(start, "/* ");
                }
            } else if (event.getCode() == KeyCode.SLASH && event.isControlDown()) {
                event.consume();
                IndexRange selection = this.getSelection();
                int start = selection.getStart();
                int end = selection.getEnd();

                int startLine = this.offsetToPosition(start, TwoDimensional.Bias.Backward).getMajor();
                int endLine = this.offsetToPosition(end, TwoDimensional.Bias.Backward).getMajor();

                StringBuilder newText = new StringBuilder();
                for (int i = startLine; i <= endLine; i++) {
                    int lineStart = this.position(i, 0).toOffset();
                    int lineEnd = this.getParagraphLength(i) + lineStart;
                    String lineText = this.getText(lineStart, lineEnd);

                    if (lineText.trim().startsWith("//")) {
                        System.out.println("uncommenting");
                        newText.append(lineText.replaceFirst("//", ""));
                    } else {
                        System.out.println("commenting");
                        newText.append("//").append(lineText);
                    }
                    newText.append("\n");
                }
                newText.deleteCharAt(newText.length() - 1);
                this.replaceText(
                        this.position(startLine, 0).toOffset(),
                        this.position(endLine, this.getParagraphLength(endLine)).toOffset(),
                        newText.toString()
                );
            }
        });

    }

    public void handleComment() {
        IndexRange selection = this.getSelection();
        int start = selection.getStart();
        int end = selection.getEnd();

        int startLine = this.offsetToPosition(start, TwoDimensional.Bias.Backward).getMajor();
        int endLine = this.offsetToPosition(end, TwoDimensional.Bias.Backward).getMajor();

        StringBuilder newText = new StringBuilder();
        for (int i = startLine; i <= endLine; i++) {
            int lineStart = this.position(i, 0).toOffset();
            int lineEnd = this.getParagraphLength(i) + lineStart;
            String lineText = this.getText(lineStart, lineEnd);

            if (lineText.trim().startsWith("//")) {
                System.out.println("uncommenting");
                newText.append(lineText.replaceFirst("//", ""));
            } else {
                System.out.println("commenting");
                newText.append("//").append(lineText);
            }
            newText.append("\n");
        }
        newText.deleteCharAt(newText.length() - 1);
        this.replaceText(
                this.position(startLine, 0).toOffset(),
                this.position(endLine, this.getParagraphLength(endLine)).toOffset(),
                newText.toString()
        );
    }

    private boolean isClosingBracket(char ch) {
        return ch == ')' || ch == '}' || ch == ']';
    }

    private Set<Integer> matchingBracketPositions = new HashSet<>();

    private void colorBrackets(int newPos) {
        Set<Integer> newMatchingPositions = new HashSet<>();

        // Check character before the caret
        if (newPos > 0) {
            char prevChar = this.getText(newPos - 1, newPos).charAt(0);
            if (isBracket(prevChar)) {
                int matchPos = findMatchingBracket(newPos - 1, prevChar);
                if (matchPos != -1) {
                    newMatchingPositions.add(newPos - 1);
                    newMatchingPositions.add(matchPos);
                }
            }
        }

        // Check character after the caret
        if (newPos < this.getLength()) {
            char nextChar = this.getText(newPos, newPos + 1).charAt(0);
            if (isBracket(nextChar)) {
                int matchPos = findMatchingBracket(newPos, nextChar);
                if (matchPos != -1) {
                    newMatchingPositions.add(newPos);
                    newMatchingPositions.add(matchPos);
                }
            }
        }

        // Update the matchingBracketPositions set
        if (!matchingBracketPositions.equals(newMatchingPositions)) {
            matchingBracketPositions = newMatchingPositions;
            // Trigger re-computation of syntax highlighting
            this.setStyleSpans(0, computeHighlighting(this.getText()));
        }
    }


    private char getMatchingBracket(char bracket) {
        return switch (bracket) {
            case '(' -> ')';
            case ')' -> '(';
            case '[' -> ']';
            case ']' -> '[';
            case '{' -> '}';
            case '}' -> '{';
            case '"' -> '"';
            case '\'' -> '\'';
            default -> '\0';
        };
    }

    private int findDirection(char bracket) {
        return "([{\"'".indexOf(bracket) != -1 ? 1 : -1;
    }

    private int findMatchingBracket(int position, char bracket) {
        char match = getMatchingBracket(bracket);

        int direction = findDirection(bracket);
        int depth = 0;

        for (int i = position; i >= 0 && i < this.getLength(); i += direction) {
            char currentChar = this.getText(i, i + 1).charAt(0);

            if (currentChar == bracket) {
                depth++;
            } else if (currentChar == match) {
                depth--;
                if (depth == 0) {
                    return i;
                }
            }
        }
        return -1;

    }

    private void showSuggestions() {
        int caretPosition = this.getCaretPosition();
        String currentWord = getCurrentWord(caretPosition);
        if (currentWord.isEmpty()) {
            suggestionsMenu.hide();
            return;
        }

        List<String> suggestions = keywordsTrie.suggestions(currentWord);
        if (suggestions.isEmpty()) {
            suggestionsMenu.hide();
            return;
        }

        suggestionsMenu.getItems().clear();
        for (String suggestion : suggestions) {
            MenuItem item = new MenuItem(suggestion);
            item.setOnAction(event -> insertSuggestion(caretPosition, currentWord, suggestion));
            suggestionsMenu.getItems().add(item);
        }

        double x = this.caretBoundsProperty().getValue().orElseThrow(() -> new IllegalStateException("No caret position")).getMaxX();
        double y = this.caretBoundsProperty().getValue().orElseThrow(() -> new IllegalStateException("No caret position")).getMaxY();

        suggestionsMenu.show(this, x, y);
    }

    private String getCurrentWord(int caretPosition) {
        int start = caretPosition - 1;
        while (start >= 0 && Character.isLetter(this.getText().charAt(start))) {
            start--;
        }
        start++;
        return this.getText().substring(start, caretPosition);
    }

    private void insertSuggestion(int caretPosition, String currentWord, String suggestion) {
        int start = caretPosition - currentWord.length();
        this.replaceText(start, caretPosition, suggestion);
        suggestionsMenu.hide();
    }
    private final EventHandler<KeyEvent> bracketHandle = this::handleKeyTyped;

    private void handleKeyTyped(KeyEvent event) {
        String character = event.getCharacter();
        if (GlobalVariables.getAutoBracketClosing()) {
            switch (character) {
                case "(" -> insertAutoClosingBracket("(", ")");
                case "[" -> insertAutoClosingBracket("[", "]");
                case "{" -> insertAutoClosingBracket("{", "}");
                case "\"" -> insertAutoClosingBracket("\"", "\"");
                case "'" -> insertAutoClosingBracket("'", "'");
                default -> {
                    return;
                }
            }
            event.consume();
        }
    }

    private void handleKeyPress(KeyEvent event) {
        int caretPosition = this.getCaretPosition();
        int currentParagraph = this.getCurrentParagraph();
        String currentLine = this.getParagraph(currentParagraph).getText();
        if (event.getText().equals(".")) {
            System.out.println("typed .");
            handleDotPressed();
        } else if (event.getCode() == KeyCode.ENTER) {
            event.consume();

            int caret = getCaretPosition();
            String lastWord = getCurrentWord(caret);
            System.out.println("last word" + lastWord);
            try {
                BoilerPlates bp = new BoilerPlates();
                String out = bp.get(lastWord);
                if (out != null && !out.isEmpty() && GlobalVariables.getAutoCompletion()) {
                    System.out.println("replacing text" + out);
                    int start = caret - lastWord.length();
                    replaceText(start, caret, out);
                    return;
                }
            } catch (IOException e) {
                System.out.println("Error");
            }

            System.out.println("para: " + currentParagraph);
            System.out.println("line: " + currentLine);
            String trimmed = currentLine.trim();
            String indentation = getCurrentIndentation(currentLine);
            if (GlobalVariables.getAutoIndentation()) {
                if (trimmed.isEmpty()) {
                    this.insertText(caretPosition, "\n" + indentation);
                    return;
                }
                if (caretPosition > 0) {
                    String lastChar = this.getText(caretPosition - 1, caretPosition);
                    System.out.println("last char: " + lastChar);
                    if (Objects.equals(lastChar, "{")) {
                        System.out.println("indentation needed");
                        this.insertText(caretPosition, "\n" + indentation + "\t\n" + indentation);
                        this.moveTo(caretPosition + indentation.length() + 2);
                    } else {
                        this.insertText(caretPosition, "\n" + indentation);
                    }
                } else {
                    this.insertText(caretPosition, "\n" + indentation);
                    return;
                }
            } else {
                this.insertText(caretPosition, "\n");
            }
        }
    }

    private void handleDotPressed() {
        System.out.println("inside handleDotPressed");
        String currentCode = this.getText();
        int caretPosition = this.getCaretPosition();
        showContextSuggestions(currentCode, caretPosition);
    }

    private void showContextSuggestions(String code, int caretPosition) {
        try {
            System.out.println("trying to show context suggestion");

            int lineStart = code.lastIndexOf('\n', caretPosition - 1);
            int lineEnd = code.indexOf('\n', caretPosition);

            // If caret is at the end of a line, lineEnd will be -1
            if (lineEnd == -1) {
                lineEnd = code.length();
            }

            // Remove the line that contains the caret
            String codeWithoutCurrentLine = code.substring(0, lineStart + 1) + code.substring(lineEnd);

            JavaParser parser = new JavaParser();
            ParseResult<CompilationUnit> result = parser.parse(codeWithoutCurrentLine);

            if (result.isSuccessful() && result.getResult().isPresent()) {
                System.out.println("Parsing successful");
                CompilationUnit cu = result.getResult().get();
                VariableTypeResolver resolver = new VariableTypeResolver(caretPosition);
                cu.accept(resolver, null);
                String variableType = resolver.getResolvedType();
                System.out.println(getCurrentWord(caretPosition));
                String name = getCurrentWord(caretPosition);
                System.out.println("name: " + name);
                System.out.println(resolver.getSymbolTable());
                String type = resolver.getSymbolTable().get(name);
                if (type != null) {
                    fetchAndDisplaySuggestions(type);
                }
                if (variableType != null) {
                    System.out.println("Resolved type: " + variableType);
                    fetchAndDisplaySuggestions(variableType);
                }
            } else {
                System.out.println("Parsing failed. Code might be incomplete.");
            }
        } catch (Exception e) {
            System.out.println("failed to show context menu");
//            e.printStackTrace();
        }
    }

    private void fetchAndDisplaySuggestions(String variableType) {
        suggestionsMenu.getItems().clear();

        try {
            System.out.println("Trying to fetch and display suggestions");
            Class<?> clazz = Class.forName("java.lang." + variableType);
            Method[] methods = clazz.getDeclaredMethods();
            if (variableType.equals("String")) {
                System.out.println("got String");

            }
            System.out.println("Methods: " + Arrays.toString(methods));

            // Create a VBox to hold menu items
            VBox menuBox = new VBox();
            menuBox.setSpacing(0); // No space between items to maintain clean look
            menuBox.getStyleClass().add("menu-box");

            Popup popup = new Popup();
            for (Method method : methods) {
                Label item = new Label(method.getName() + "()");
                item.getStyleClass().add("menu-item");
                item.setMaxWidth(Double.MAX_VALUE); // Make items flex to full width
                item.setOnMouseClicked(e -> {
                    insertMethod(method.getName() + "()");
                    popup.hide();
                });
                menuBox.getChildren().add(item);
            }

            // Limit the height of the menu box to show only 6 items at a time
            ScrollPane scrollPane = new ScrollPane(menuBox);
            scrollPane.setPrefHeight(6 * 30); // Adjust item height if needed
            scrollPane.setPrefWidth(200); // Adjust width if needed
            scrollPane.setFitToWidth(true);
            scrollPane.getStyleClass().add("menu-scroll-pane");

            // Use a Popup to display the scrollable menu
            popup.getContent().add(scrollPane);
            popup.setAutoHide(true);

            if (!this.getCaretBounds().isPresent()) {
                System.out.println("Caret bounds are not present.");
                return;
            }

            Bounds caretBounds = this.getCaretBounds().get();
            System.out.println("Caret bounds: X=" + caretBounds.getMinX() + ", Y=" + caretBounds.getMinY());

            Platform.runLater(() ->
                    popup.show(this, caretBounds.getMinX(), caretBounds.getMinY() + 20)
            );
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found: " + variableType);
//            e.printStackTrace();
        }
    }

    private void insertMethod(String method) {
        this.replaceText(this.getCaretPosition(), this.getCaretPosition(), method);
    }

    private String getCurrentIndentation(String line) {
        StringBuilder indentation = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == ' ' || c == '\t') {
                indentation.append(c);
            } else {
                break;
            }
        }
        return indentation.toString();
    }

    private void insertAutoClosingBracket(String open, String close) {
        int caretPosition = this.getCaretPosition();
        this.insertText(caretPosition, open + close);
        this.moveTo(caretPosition + 1);
    }

    private boolean isBracket(char c) {
        return "()[]{}\"'".indexOf(c) != -1;
    }

    private boolean isOpeningBracket(char c) {
        return "([{\"'".indexOf(c) != -1;
    }

    private char getClosingBracket(char open) {
        return switch (open) {
            case '(' -> ')';
            case '[' -> ']';
            case '{' -> '}';
            case '"' -> '"';
            case '\'' -> '\'';
            default -> '\0';
        };
    }

    private char getOpeningBracket(char close) {
        return switch (close) {
            case ')' -> '(';
            case ']' -> '[';
            case '}' -> '{';
            case '"' -> '"';
            case '\'' -> '\'';
            default -> '\0';
        };
    }

    private StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();

        while (matcher.find()) {
            String styleClass;
            if (matcher.group("ACCESSMODIFIER") != null) {
                styleClass = "access-modifier";
            } else if (matcher.group("CONTROLFLOW") != null) {
                styleClass = "control-flow";
            } else if (matcher.group("DATATYPE") != null) {
                styleClass = "data-type";
            } else if (matcher.group("OTHERKEYWORD") != null) {
                styleClass = "other-keyword";
            } else if (matcher.group("PAREN") != null) {
                styleClass = "parenthesis";
            } else if (matcher.group("BRACE") != null) {
                styleClass = "brace";
            } else if (matcher.group("BRACKET") != null) {
                styleClass = "bracket";
            } else if (matcher.group("SEMICOLON") != null) {
                styleClass = "semicolon";
            } else if (matcher.group("STRING") != null) {
                styleClass = "string";
            } else if (matcher.group("COMMENT") != null) {
                styleClass = "comment";
            } else if (matcher.group("CHAR") != null) {
                styleClass = "char";
            } else if (matcher.group("NUMBER") != null) {
                styleClass = "number";
            } else {
                styleClass = "default";
            }

            spansBuilder.add(Collections.singleton("default"), matcher.start() - lastEnd);
            boolean isMatchingBracket = false;
            for (int i = matcher.start(); i < matcher.end(); i++) {
                if (matchingBracketPositions.contains(i)) {
                    isMatchingBracket = true;
                    break;
                }
            }

            if (isMatchingBracket) {
                spansBuilder.add(new HashSet<>(Arrays.asList(styleClass, "matching-bracket")), matcher.end() - matcher.start());
            } else {
                spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            }

            lastEnd = matcher.end();
        }

        spansBuilder.add(Collections.singleton("default"), text.length() - lastEnd);
        return spansBuilder.create();
    }

    private void init_tries() {
        String[] keywords = JavaKeyWords.getKeywords();
        keywordsTrie = new Trie(keywords);

        List<String> stringFunctions = new ArrayList<>();
        Class<String> stringClass = String.class;
        Method[] stringMethods = stringClass.getMethods();
        for (Method method : stringMethods) {
            stringFunctions.add(method.getName());
        }
        stringTrie = new Trie(stringFunctions.toArray(new String[0]));

        List<String> floatFunctions = new ArrayList<>();
        Class<Float> floatClass = Float.class;
        Method[] floatMethods = floatClass.getMethods();
        for (Method method : floatMethods) {
            stringFunctions.add(method.getName());
        }
        floatTrie = new Trie(floatFunctions.toArray(new String[0]));

        List<String> integerFunctions = new ArrayList<>();
        Class<Integer> integerClass = Integer.class;
        Method[] integerMethods = integerClass.getMethods();
        for (Method method : integerMethods) {
            integerFunctions.add(method.getName());
        }
        integerTrie = new Trie(integerFunctions.toArray(new String[0]));

    }
}
