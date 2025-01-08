package com.example.codeeditor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TypeInspector<T> {
    private final Class<T> clazz;

    public TypeInspector(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void listMethods() {
        System.out.println("Methods of the class: " + clazz.getName());
        Method[] methods = clazz.getMethods();
        for (Method method: methods) {
            System.out.println(" - " + method.getName() + " : " + method.getReturnType().getName());
        }
    }

    public void listFields() {
        System.out.println("Fields in the class: " + clazz.getName());
        Field[] fields = clazz.getFields();
        for (Field field: fields) {
            System.out.println(" - " + field.getName() + " : " + field.getType().getName());
        }
    }

    public void inspect() {
        listMethods();
        listFields();
    }

    public List<String> getMethodNames() {
        List<String> methodsList = new ArrayList<>();
        Method[] methods = clazz.getMethods();
        for (Method method: methods) {
            methodsList.add(method.getName());
        }
        return methodsList;
    }

    public List<String> getFieldNames() {
        List<String> fieldsList = new ArrayList<>();
        Field[] fields = clazz.getFields();
        for (Field field: fields) {
            fieldsList.add(field.getName());
        }
        return fieldsList;
    }

    public List<Method> getMethods() {
        return List.of(clazz.getMethods());
    }

    public List<Field> getFields() {
        return List.of(clazz.getFields());
    }


}
