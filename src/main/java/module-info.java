module org.example.assetuijavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires xstream;
    requires com.google.common;


    opens org.example.assetuijavafx.model to javafx.base, xstream;
    opens org.example.assetuijavafx to javafx.fxml;
    opens org.example.assetuijavafx.fxml.controllers to javafx.fxml;
    exports org.example.assetuijavafx;
    exports org.example.assetuijavafx.fxml.controllers;
    exports org.example.assetuijavafx.model;
}