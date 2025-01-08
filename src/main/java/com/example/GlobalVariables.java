package com.example;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class GlobalVariables {
    public static Properties properties = new Properties();

    static {
        try (FileInputStream in = new FileInputStream("src/main/resources/com/example/files/config.properties")) {
            properties.load(in);
        } catch (IOException e) {
            System.out.println("Load Error");
        }
    }

    private static void save() {
        try (FileOutputStream out = new FileOutputStream("src/main/resources/com/example/files/config.properties")) {
            properties.store(out, "Application Settings");
        } catch (IOException e) {
            System.out.println("Save Error");
        }
    }

    public GlobalVariables() {

    }
    public static boolean getAutoSave() {
        return Boolean.parseBoolean(properties.getProperty("AutoSave", "false"));
    }

    public static String getTheme() {
        return properties.getProperty("Theme", "dark");
    }

    public static boolean getAutoIndentation() {
        return Boolean.parseBoolean(properties.getProperty("AutoIndentation", "true"));
    }

    public static boolean getAutoBracketClosing() {
        return Boolean.parseBoolean(properties.getProperty("AutoBracketClosing", "true"));
    }

    public static boolean getAutoCompletion() {
        return Boolean.parseBoolean(properties.getProperty("AutoCompletion", "true"));
    }

    public static int getAutoSaveInterval() {
        return Integer.parseInt(properties.getProperty("AutoSaveInterval", "3"));
    }

    public static String getFontSize() {
        return properties.getProperty("FontSize", "medium");
    }

    public static String getLanguage() {
        return properties.getProperty("Language", "Eng");
    }

    public static boolean getRestoreLastSession() {
        return Boolean.parseBoolean(properties.getProperty("RestoreLastSession", "true"));
    }

    public static String getTabSize() {
        return properties.getProperty("TabSize", "\t");
    }

    public static String getFontTheme() {
        return properties.getProperty("FontTheme", "Default");
    }

    public static boolean getShowLineNumbers() {
        return Boolean.parseBoolean(properties.getProperty("ShowLineNumbers", "true"));
    }

    public static String getProgrammingLanguage() {
        return properties.getProperty("ProgrammingLanguage", "Java");
    }

    public static String getCompilerVersion() {
        return properties.getProperty("CompilerVersion", "21.0.5");
    }

    public static String getJavaSDKPath() {
        return properties.getProperty("JavaSDKPath", "/usr/bin/java");
    }

    public static String getBuildTool() {
        return properties.getProperty("BuildTool", "Maven");
    }

    public static String getHostName() {
        return properties.getProperty("HostName", "localhost");
    }

    public static void setAutoSave(boolean newValue) {
        properties.setProperty("AutoSave", String.valueOf(newValue));
        save();
    }

    public static void setTheme(String newValue) {
        properties.setProperty("Theme", newValue);
        save();
    }

    public static void setAutoIndentation(boolean newValue) {
        properties.setProperty("AutoIndentation", String.valueOf(newValue));
        save();
    }

    public static void setAutoBracketClosing(boolean newValue) {
        properties.setProperty("AutoBracketClosing", String.valueOf(newValue));
        save();
    }

    public static void setAutoCompletion(boolean newValue) {
        properties.setProperty("AutoCompletion", String.valueOf(newValue));
        save();
    }

    public static void setAutoSaveInterval(int newValue) {
        properties.setProperty("AutoSaveInterval", String.valueOf(newValue));
        save();
    }

    public static void setFontSize(String newValue) {
        properties.setProperty("FontSize", newValue);
        save();
    }

    public static void setLanguage(String newValue) {
        properties.setProperty("Language", newValue);
        save();
    }

    public static void setRestoreLastSession(boolean newValue) {
        properties.setProperty("RestoreLastSession", String.valueOf(newValue));
        save();
    }

    public static void setTabSize(String newValue) {
        properties.setProperty("TabSize", newValue);
        save();
    }

    public static void setFontTheme(String newValue) {
        properties.setProperty("FontTheme", newValue);
        save();
    }

    public static void setShowLineNumbers(boolean newValue) {
        properties.setProperty("ShowLineNumbers", String.valueOf(newValue));
        save();
    }

    public static void setProgrammingLanguage(String newValue) {
        properties.setProperty("ProgrammingLanguage", newValue);
        save();
    }

    public static void setCompilerVersion(String newValue) {
        properties.setProperty("CompilerVersion", newValue);
        save();
    }

    public static void setJavaSDKPath(String newValue) {
        properties.setProperty("JavaSDKPath", newValue);
        save();
    }

    public static void setBuildTool(String newValue) {
        properties.setProperty("BuildTool", newValue);
        save();
    }

    public static void setHostName(String newValue) {
        properties.setProperty("HostName", newValue);
        save();
    }
    
}
