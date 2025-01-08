package com.example.topmenubar;

import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

class InMemoryFileManager extends ForwardingJavaFileManager<JavaFileManager> {
    private final Map<String, ByteArrayOutputStream> classOutput = new HashMap<>();

    protected InMemoryFileManager(JavaFileManager fileManager) {
        super(fileManager);
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        classOutput.put(className, outputStream);
        return new SimpleJavaFileObject(URI.create("byte:///" + className), kind) {
            @Override
            public OutputStream openOutputStream() {
                return outputStream;
            }
        };
    }

    @Override
    public ClassLoader getClassLoader(Location location) {
        return new ClassLoader() {
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                ByteArrayOutputStream byteStream = classOutput.get(name);
                if (byteStream == null) {
                    throw new ClassNotFoundException(name);
                }
                byte[] byteCode = byteStream.toByteArray();
                return defineClass(name, byteCode, 0, byteCode.length);
            }
        };
    }
}


//class InMemoryFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {
//    private final Map<String, InMemoryClassFile> classFiles = new HashMap<>();
//
//    public InMemoryFileManager(StandardJavaFileManager fileManager) {
//        super(fileManager);
//    }
//
//    @Override
//    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) {
//        InMemoryClassFile classFile = new InMemoryClassFile(className);
//        classFiles.put(className, classFile);
//        return classFile;
//    }
//
//    @Override
//    public ClassLoader getClassLoader(Location location) {
//        return new InMemoryClassLoader(classFiles);
//    }
//}
