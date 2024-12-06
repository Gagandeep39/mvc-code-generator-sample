package org.example.assetuijavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.assetuijavafx.models.AssetType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        insertData();
        launch();
    }

    public static void insertData() {
        List<AssetType> assetList = new ArrayList<>();

        // Add 10 dummy data objects
        assetList.add(new AssetType("Laptop", 5, "laptop.jpg"));
        assetList.add(new AssetType("Desktop", 7, "desktop.jpg"));
        assetList.add(new AssetType("Printer", 10, "printer.jpg"));
        assetList.add(new AssetType("Scanner", 8, "scanner.jpg"));
        assetList.add(new AssetType("Projector", 6, "projector.jpg"));
        assetList.add(new AssetType("Router", 4, "router.jpg"));
        assetList.add(new AssetType("Switch", 10, "switch.jpg"));
        assetList.add(new AssetType("Monitor", 6, "monitor.jpg"));
        assetList.add(new AssetType("Server", 12, "server.jpg"));
        assetList.add(new AssetType("UPS", 5, "ups.jpg"));

        assetList.forEach(AssetType::addAssetType);

        System.out.println(assetList.size());
    }
}