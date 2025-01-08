package com.example.helpwindow;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Code extends VBox {
    Button language;

    Button javaButton = new Button("Java");
    Button cppButton = new Button("C++");
    Button pythonButton = new Button("Python");
    HBox languages = new HBox(javaButton, cppButton, pythonButton);
    String javaCode;
    String cppCode;
    String pythonCode;

    Button copy = new Button("copy");
    StackPane stackPane = new StackPane();
    TextFlow content = new TextFlow();
    String text;

    String[] javaKeyWords = {
            "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "continue", "default", "do", "double", "else", "enum",
            "extends", "final", "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native", "new", "null",
            "package", "private", "protected", "public", "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this", "throw", "throws",
            "transient", "try", "void", "volatile", "while"
    };
    HashSet<String> javaKeyWordsSet = new HashSet<>(Set.of(javaKeyWords));

    String[] PythonKeyWords = {
            "False", "None", "True", "and", "as", "assert", "async", "await", "break", "class", "continue", "def", "del", "elif", "else", "except", "finally",
            "for", "from", "global", "if", "import", "in", "is", "lambda", "nonlocal", "not", "or", "pass", "raise", "return", "try", "while", "with", "yield"
    };
    HashSet<String> PythonKeyWordsSet = new HashSet<>(Set.of(PythonKeyWords));

    String[] CPPKeyWords = {
            "alignas", "alignof", "and", "and_eq", "asm", "atomic_cancel", "atomic_commit", "atomic_noexcept", "auto", "bitand", "bitor", "bool", "break",
            "case", "catch", "char", "char8_t", "char16_t", "char32_t", "class", "compl", "concept", "const", "consteval", "constexpr", "constinit", "const_cast",
            "continue", "co_await", "co_return", "co_yield", "decltype", "default", "delete", "do", "double", "dynamic_cast", "else", "enum", "explicit", "export",
            "extern", "false", "float", "for", "friend", "goto", "if", "inline", "int", "long", "mutable", "namespace", "new", "noexcept", "not", "not_eq", "nullptr",
            "operator", "or", "or_eq", "private", "protected", "public", "register", "reinterpret_cast", "requires", "return", "short", "signed", "sizeof", "static",
            "static_assert", "static_cast", "struct", "switch", "synchronized", "template", "this", "thread_local", "throw", "true", "try", "typedef", "typeid",
            "typename", "union", "unsigned", "using", "virtual", "void", "volatile", "wchar_t", "while", "xor", "xor_eq"
    };
    HashSet<String> CPPKeyWordsSet = new HashSet<>(Set.of(CPPKeyWords));

    public Code(String lang, String cont) {
        this.copy.setOnAction(event -> handleCopy());
        this.copy.getStyleClass().add("copy-button");
        this.language = new Button(lang);
        this.text = cont;
        this.content.getChildren().addAll(createContent());
        this.language.getStyleClass().add("lang-label");
        this.content.getStyleClass().add("code");
        this.content.setPrefWidth(800);
        this.content.setMaxWidth(800);
        this.stackPane.getChildren().addAll(this.content, this.copy);
        this.getChildren().addAll(this.language, this.stackPane);
        this.stackPane.setAlignment(Pos.TOP_LEFT);
        stackPane.setMaxWidth(content.getMaxWidth());
        this.copy.setTranslateX(-10);
        this.copy.setTranslateY(10);
        StackPane.setAlignment(copy, Pos.TOP_RIGHT);
        VBox.setVgrow(this.content, Priority.ALWAYS);
    }

    public Code(String javaCode, String cppCpde, String pythonCode) {
        this.javaButton.setOnAction(event -> handleJavaButton());
        this.cppButton.setOnAction(event -> handleCPPButton());
        this.pythonButton.setOnAction(event -> handlePythonCode());
        this.javaButton.getStyleClass().add("lang-label");
        this.cppButton.getStyleClass().add("lang-label");
        this.pythonButton.getStyleClass().add("lang-label");
        this.language = new Button("Java");
        this.javaCode = javaCode;
        this.cppCode = cppCpde;
        this.pythonCode = pythonCode;
        this.copy.setOnAction(event -> handleCopy());
        this.copy.getStyleClass().add("copy-button");
        this.text = javaCode;
        this.content.getChildren().addAll(createContent());
        this.content.getStyleClass().add("code");
        this.content.setPrefWidth(800);
        this.content.setMaxWidth(800);
        this.stackPane.getChildren().addAll(this.content, this.copy);
        this.getChildren().addAll(this.languages, this.stackPane);
        this.stackPane.setAlignment(Pos.TOP_LEFT);
        stackPane.setMaxWidth(content.getMaxWidth());
        this.copy.setTranslateX(-10);
        this.copy.setTranslateY(10);
        StackPane.setAlignment(copy, Pos.TOP_RIGHT);
        VBox.setVgrow(this.content, Priority.ALWAYS);
    }

    private Text[] createContent() {
        Pattern pattern = Pattern.compile(
                // Match string literals, comments, identifiers, numbers, operators, and whitespace, including the colon symbol
                "(\"[^\"]*\")|(/\\*[^*]*\\*/|//[^\n]*)|#.*|[a-zA-Z_][a-zA-Z0-9_]*|\\d+|[\\[\\](){};,.<>!=+\\-*/&|^%~:]+|\\s+"
        );
        Matcher matcher = pattern.matcher(this.text);

        // Determine the language's keyword set based on the selected language
        HashSet<String> keywordSet = switch (language.getText()) {
            case "Java" -> javaKeyWordsSet;
            case "Python" -> PythonKeyWordsSet;
            case "CPP" -> CPPKeyWordsSet;
            default -> new HashSet<>();
        };

        return matcher.results().map(result -> {
            String token = result.group();
            Text text = new Text(token);

            if (token.startsWith("\"") && token.endsWith("\"") || (language.getText().equals("Python") && token.startsWith("'") && token.endsWith("'"))) {
                text.getStyleClass().add("string");
            } else if ((token.startsWith("//") || token.startsWith("/*") || token.startsWith("*/")) && !language.getText().equals("Python")) {
                text.getStyleClass().add("comment");
            } else if (token.startsWith("#") && language.getText().equals("Python")) {
                text.getStyleClass().add("comment");
            } else if (keywordSet.contains(token)) {
                text.getStyleClass().add("keyword");
            } else if (!token.trim().isEmpty()) {
                text.getStyleClass().add("normal");
            }

            return text;
        }).toArray(Text[]::new);
    }



    private void handleJavaButton() {
        content.getChildren().clear();
        text = javaCode;
        language.setText("Java");
        content.getChildren().addAll(createContent());
    }

    private void handleCPPButton() {
        content.getChildren().clear();
        text = cppCode;
        language.setText("CPP");
        content.getChildren().addAll(createContent());
    }

    private void handlePythonCode() {
        content.getChildren().clear();
        text = pythonCode;
        language.setText("Python");
        content.getChildren().addAll(createContent());
    }

    private void handleCopy() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(text);
        clipboard.setContent(clipboardContent);
    }
}
