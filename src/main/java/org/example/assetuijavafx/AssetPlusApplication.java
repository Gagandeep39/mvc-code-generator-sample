package org.example.assetuijavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.assetuijavafx.model.AssetPlus;
import org.example.assetuijavafx.persistence.AssetPlusPersistence;

import java.io.IOException;

public class AssetPlusApplication extends Application {

    private static AssetPlus assetPlus;
    public static final String PACKAGE_ID = "/org/example/assetuijavafx/";
    public static final String PACKAGE_NAME = AssetPlusApplication.class.getPackage().getName();

    public static AssetPlus getAssetPlus() {
        if (assetPlus == null) {
            assetPlus = AssetPlusPersistence.load();
        }
        return assetPlus;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AssetPlusApplication.class.getResource("AssetPlus.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("AssetPlus");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        getAssetPlus();
        launch();
    }

}