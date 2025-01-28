package org.example.assetuijavafx.fxml.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.io.IOException;

import static org.example.assetuijavafx.application.AssetPlusApplication.PACKAGE_ID;

public abstract class BaseDisplayController {
    protected VBox childContainer;
    protected String activeFxml;

    public FXMLLoader loadFXML() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(PACKAGE_ID.concat(activeFxml)));
        try {
            Parent child = loader.load();
            childContainer.getChildren().clear();
            childContainer.getChildren().add(child);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return loader;
    }
}
