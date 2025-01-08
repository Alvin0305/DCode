package com.example.topmenubar;

import javax.tools.*;
import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.util.*;

public class CodeExecutor {

    public static Map<String, Object> execute(File file) throws Exception {
        String fileName = file.getName();
        if (!fileName.endsWith(".java")) {
            throw new IllegalArgumentException("Provided file is not a Java source file.");
        }
        String className = fileName.substring(0, fileName.lastIndexOf('.'));

        // Read source code
        String sourceCode = new String(java.nio.file.Files.readAllBytes(file.toPath()));

        // In-memory JavaFileObject
        JavaFileObject javaFile = new InMemoryJavaFile(className, sourceCode);

        // Set up compiler
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        StandardJavaFileManager standardFileManager = compiler.getStandardFileManager(diagnostics, null, null);
        InMemoryFileManager inMemoryFileManager = new InMemoryFileManager(standardFileManager);

        // Compile
        JavaCompiler.CompilationTask task = compiler.getTask(null, inMemoryFileManager, diagnostics, null, null, List.of(javaFile));
        boolean success = task.call();

        if (!success) {
            StringBuilder errorMessage = new StringBuilder("Compilation failed:\n");
            for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                errorMessage.append(diagnostic.getMessage(null)).append("\n");
            }
            throw new RuntimeException(errorMessage.toString());
        }

        // Load compiled class
        ClassLoader classLoader = inMemoryFileManager.getClassLoader(null);
        Class<?> compiledClass = classLoader.loadClass(className);

        // Create instance and run 'run' method
        Object instance = compiledClass.getDeclaredConstructor().newInstance();
        Method runMethod;
        try {
            runMethod = compiledClass.getMethod("run");
            runMethod.invoke(instance);
        } catch (NoSuchMethodException e) {
            // No 'run' method, it's fine; continue to fetch variables.
        }

        // Extract variable values
        Map<String, Object> variables = new HashMap<>();
        for (Field field : compiledClass.getDeclaredFields()) {
            field.setAccessible(true);
            variables.put(field.getName(), field.get(instance));
        }

        return variables;
    }
}

// Helper classes for in-memory compilation and storage

