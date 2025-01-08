package com.example.topmenubar;

import javax.tools.*;
import java.io.*;
import java.lang.reflect.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class CodeRunner {
    public static Map<String, Object> execute(File codeFile) throws Exception {
        String code = new String(java.nio.file.Files.readAllBytes(codeFile.toPath()));
        String className = codeFile.getName().replace(".java", "");

        // Compile the code
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> compilationUnits =
                fileManager.getJavaFileObjectsFromFiles(List.of(codeFile));
        boolean compilationSuccess = compiler.getTask(null, fileManager, null, null, null, compilationUnits).call();
        fileManager.close();

        if (!compilationSuccess) {
            throw new RuntimeException("Compilation failed");
        }

        // Load the compiled class
        File compiledDir = codeFile.getParentFile();
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{compiledDir.toURI().toURL()});
        Class<?> clazz = Class.forName(className, true, classLoader);

        // Execute the class's main method
        Method mainMethod = clazz.getDeclaredMethod("main", String[].class);
        mainMethod.invoke(null, (Object) new String[]{});

        // Capture variables (this needs instrumentation or modification of the user code)
        // Example: Assume user code stores variables in a static Map<String, Object>
        try {
            Field variablesField = clazz.getDeclaredField("name");
            Map<String, Object> variables = (Map<String, Object>) variablesField.get(null);
            return variables;
        } catch (Exception e) {
            System.out.println("error");
//            throw new ClassNotFoundException("field not found");
            return new HashMap<>();
        }
    }
}
