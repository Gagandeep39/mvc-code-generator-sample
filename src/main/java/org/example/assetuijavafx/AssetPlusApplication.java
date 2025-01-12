package org.example.assetuijavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.assetuijavafx.models.AssetPlus;
import org.example.assetuijavafx.models.AssetType;
import org.example.assetuijavafx.models.SpecificAsset;
import org.example.assetuijavafx.persistence.AssetPlusPersistence;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class AssetPlusApplication extends Application {

    private static AssetPlus assetPlus;
    public static String PACKAGE_ID = "/org/example/assetuijavafx/";


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
//        initData();
        launch();
    }

//    // Below code is just for testing ad will not be a part of automation
//    public static void initData() {
//        // new HashMap is used tp create new Hashmap because Map.Of is immutable
//        AssetType.setAssettypesByName(new HashMap<>(Map.of(
//                "Laptop", new AssetType("Laptop", 5, "laptop.jpg"),
//                "Desktop", new AssetType("Desktop", 7, "desktop.jpg"),
//                "Printer", new AssetType("Printer", 10, "printer.jpg"),
//                "Scanner", new AssetType("Scanner", 8, "scanner.jpg"),
//                "Projector", new AssetType("Projector", 6, "projector.jpg"),
//                "Router", new AssetType("Router", 4, "router.jpg"),
//                "Switch", new AssetType("Switch", 10, "switch.jpg"),
//                "Monitor", new AssetType("Monitor", 6, "monitor.jpg"),
//                "Server", new AssetType("Server", 12, "server.jpg"),
//                "UPS", new AssetType("UPS", 5, "ups.jpg")
//        )));
//
//        SpecificAsset.setSpecificassetsByAssetNumber(new HashMap<>(Map.of(
//                1001, new SpecificAsset(1001, new Date(System.currentTimeMillis()), 1001, 1),
//                1002, new SpecificAsset(1002, new Date(System.currentTimeMillis()), 1002, 1),
//                1003, new SpecificAsset(1003, new Date(System.currentTimeMillis()), 2001, 2),
//                1004, new SpecificAsset(1004, new Date(System.currentTimeMillis()), 2003, 2),
//                1005, new SpecificAsset(1005, new Date(System.currentTimeMillis()), 2004, 2),
//                1006, new SpecificAsset(1006, new Date(System.currentTimeMillis()), 3001, 3),
//                1007, new SpecificAsset(1007, new Date(System.currentTimeMillis()), 3002, 3),
//                1008, new SpecificAsset(1008, new Date(System.currentTimeMillis()), 3003, 3),
//                1009, new SpecificAsset(1009, new Date(System.currentTimeMillis()), 5001, 5),
//                1010, new SpecificAsset(1010, new Date(System.currentTimeMillis()), 4001, 4)
//        )));
//    }
}