package com.example.codeeditor;

import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import org.fxmisc.richtext.CodeArea;

import javax.tools.*;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorHighlighter {
    public static List<Diagnostic<? extends JavaFileObject>> getCompilationErrors(String code) {
        SimpleJavaFileObject javaFileObject = new SimpleJavaFileObject(
                URI.create("string:///Test.java"),
                JavaFileObject.Kind.SOURCE
        ) {
            @Override
            public CharSequence getCharContent(boolean ignoreEndingErrors) {
                return code;
            }
        };
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
        JavaCompiler.CompilationTask task = compiler.getTask(
                null, fileManager, diagnostics, null, null, Collections.singleton(javaFileObject)
        );
        task.call();

        return diagnostics.getDiagnostics();
    }

    public static void highlightErrors(CodeArea codeArea, List<Diagnostic<? extends JavaFileObject>> errors) {

        List<?> paragraphs = (List<?>) codeArea.getParagraphs();
        Map<Integer, String> errorMessages = new HashMap<>();

        for (int i = 0; i < paragraphs.size(); i++) {
            codeArea.clearParagraphStyle(i);
        }

        for (Diagnostic<? extends JavaFileObject> error: errors) {
            int line = (int) error.getLineNumber() - 1;
            int column = (int) error.getColumnNumber() - 1;
            String errorMessage = error.getMessage(null);
            System.out.println("error: " + errorMessage);
            System.out.println("line: " + line);
            System.out.println("column: " + error.getColumnNumber());

            System.out.println(line + "::" + column);

            if (line >= 0 && line < paragraphs.size()) {
                codeArea.setParagraphStyle(line, Collections.singleton("error-line"));
                errorMessages.put(line, errorMessage);
            }
        }
        Tooltip tooltip = new Tooltip();
        tooltip.setShowDelay(Duration.millis(50));
        tooltip.setHideDelay(Duration.millis(100));
        codeArea.setOnMouseMoved(event -> {
            int paragraphIndex = codeArea.getCurrentParagraph();

            if (errorMessages.containsKey(paragraphIndex)) {
                String message = errorMessages.get(paragraphIndex);
                tooltip.setText(message);
                if (!tooltip.isShowing()) {
                    Tooltip.install(codeArea, tooltip);
                }
            } else {
                tooltip.hide();
            }
        });
    }
}
