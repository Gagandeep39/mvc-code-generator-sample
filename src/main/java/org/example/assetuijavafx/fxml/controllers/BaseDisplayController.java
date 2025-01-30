package org.example.assetuijavafx.fxml.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

import static org.example.assetuijavafx.application.AssetPlusApplication.PACKAGE_ID;

public abstract class BaseDisplayController {

    protected abstract Pane getChildContainer();

    protected FXMLLoader loadFXML(String fxml) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(PACKAGE_ID.concat(fxml)));
        try {
            Parent child = loader.load();
            getChildContainer().getChildren().clear();
            getChildContainer().getChildren().add(child);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return loader;
    }
}
