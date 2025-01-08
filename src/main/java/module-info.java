module com.example.messagebox {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.desktop;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.fontawesome5;
    requires org.kordamp.ikonli.javafx;
    requires com.github.javaparser.core;
    requires org.fxmisc.richtext;
    requires java.compiler;
    requires java.sql;
//    requires fontawesomefx;

    opens com.example to javafx.fxml;
    opens com.example.boilerplateswindow to javafx.base;
    exports com.example;
}