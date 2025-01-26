module org.example.assetuijavafx {
    requires javafx.fxml;
    requires java.sql;
    requires xstream;
    requires com.google.common;
    requires org.controlsfx.controls;

    opens org.example.assetuijavafx.model to javafx.base, xstream;
    opens org.example.assetuijavafx.fxml.controllers to javafx.fxml;
    exports org.example.assetuijavafx.application;
}
