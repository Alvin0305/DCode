package com.example.topmenubar;

import javax.tools.SimpleJavaFileObject;
import java.net.URI;

class InMemoryJavaFile extends SimpleJavaFileObject {
    private final String code;

    protected InMemoryJavaFile(String className, String code) {
        super(URI.create("string:///" + className + Kind.SOURCE.extension), Kind.SOURCE);
        this.code = code;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return code;
    }
}

//class InMemoryJavaFile extends SimpleJavaFileObject {
//    private final String code;
//
//    public InMemoryJavaFile(String name, String code) {
//        super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
//        this.code = code;
//    }
//
//    @Override
//    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
//        return code;
//    }
//}
