package com.example.codeeditor;

import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class VariableTypeResolver extends VoidVisitorAdapter<Void> {
    private Map<String, String> symbolTable = new HashMap<>();
    private final int caretPosition;
    private String resolvedType;

    public VariableTypeResolver(int caretPosition) {
        this.caretPosition = caretPosition;
    }

    public String getResolvedType() {
        return resolvedType;
    }

    public Map<String, String> getSymbolTable() {
        return symbolTable;
    }

    @Override
    public void visit(VariableDeclarator variable, Void arg) {
        super.visit(variable, arg);

        String variableName = variable.getNameAsString();
        String variableType = variable.getTypeAsString();
        System.out.println("Variable declared: Name = " + variableName + ", Type = " + variableType);

        symbolTable.put(variableName, variableType);
        System.out.println("Symbol Table Updated: " + symbolTable);
    }

    @Override
    public void visit(MethodCallExpr methodCall, Void arg) {
        super.visit(methodCall, arg);

        if (methodCall.getBegin().isPresent() && methodCall.getEnd().isPresent()) {
            int startLine = methodCall.getBegin().get().line;
            int startColumn = methodCall.getBegin().get().column;
            int endLine = methodCall.getEnd().get().line;
            int endColumn = methodCall.getEnd().get().column;

            System.out.println("MethodCall detected: Start = (" + startLine + ", " + startColumn
                    + "), End = (" + endLine + ", " + endColumn + ")");

            if (isCaretInRange(startLine, startColumn, endLine, endColumn)) {
                methodCall.getScope().ifPresent(scope -> {
                    String variableName = scope.toString();
                    System.out.println("Scope detected: " + variableName);

                    resolvedType = symbolTable.get(variableName); // Lookup type in the symbol table
                    System.out.println("Resolved Type: " + resolvedType);

                    if (resolvedType == null) {
                        System.out.println("Warning: Type not found in Symbol Table for variable " + variableName);
                    }
                });
            } else {
                System.out.println("Caret is not in range of the MethodCall.");
            }
        } else {
            System.out.println("MethodCall position is not available.");
        }
    }

    private boolean isCaretInRange(int startLine, int startColumn, int endLine, int endColumn) {
        // Add logic to map caretPosition to (line, column) and check range
        // For now, always return true to bypass actual check
        System.out.println("Checking if caret is in range: Start = (" + startLine + ", " + startColumn
                + "), End = (" + endLine + ", " + endColumn + ")");
        return true; // Replace this with the actual range-check logic
    }

    private void fetchMethodForType(String variableType) {
        try {
            System.out.println("Fetching methods for type: " + variableType);
            Class<?> clazz = Class.forName("java.lang." + variableType);
            Method[] methods = clazz.getMethods();
            System.out.println("Methods for " + variableType + ":");
            for (Method method : methods) {
                System.out.println(" - " + method);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Type not found: " + variableType);
        }
    }
}
