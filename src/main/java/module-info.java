module org.example.assetuijavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens org.example.assetuijavafx.models to javafx.base;
    opens org.example.assetuijavafx to javafx.fxml;
    exports org.example.assetuijavafx;
    exports org.example.assetuijavafx.controllers;
    opens org.example.assetuijavafx.controllers to javafx.fxml;
}