module org.example.assetuijavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.assetuijavafx.models to javafx.base;
    opens org.example.assetuijavafx to javafx.fxml;
    exports org.example.assetuijavafx;
}