package org.example.assetuijavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.assetuijavafx.models.AssetType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AssetPlusApplication extends Application {

    public static String PACKAGE_ID = "/org/example/assetuijavafx/";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PACKAGE_ID + "AssetPlus.fxml"));
        System.out.println(getClass().getResource(PACKAGE_ID + "AssetPlus.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("AssetPlus");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}