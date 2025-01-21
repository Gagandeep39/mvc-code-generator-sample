package org.example.assetuijavafx.fxml.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.FlowPane;
import org.controlsfx.control.BreadCrumbBar;
import org.example.assetuijavafx.fxml.utils.PageSwitchEvent;
import org.example.assetuijavafx.model.TOAssetType;

import java.net.URL;
import java.util.ResourceBundle;

import static org.example.assetuijavafx.AssetPlusApplication.PACKAGE_ID;

public class AssetTypeController implements Initializable {

    private final TreeItem<String> rootItem = new TreeItem<>("AssetType");
    private final TreeItem<String> addItem = new TreeItem<>("Add AssetType");
    @FXML
    public BreadCrumbBar<String> breadCrumbBar;
    @FXML
    public FlowPane rootPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rootItem.getChildren().add(addItem);
        initializeBreadcrumbNavigation();
        rootPane.addEventHandler(PageSwitchEvent.PAGE_SWITCH, this::changePage);
        rootPane.fireEvent(new PageSwitchEvent<>("DISPLAY"));
    }

    public void initializeBreadcrumbNavigation() {
        breadCrumbBar.setOnCrumbAction(event -> {
            switch (event.getSelectedCrumb().getValue()) {
                case "Add AssetType":
                    rootPane.fireEvent(new PageSwitchEvent<>("ADD"));
                    break;
                case "AssetType":
                    rootPane.fireEvent(new PageSwitchEvent<>("DISPLAY"));
                    break;
            }
        });
    }

    public void changePage(PageSwitchEvent<TOAssetType> event) {
        if (rootPane.getChildren().size() > 1) {
            rootPane.getChildren().removeLast();
        }
        try {
            FXMLLoader loader;
            Parent child;
            switch (event.getPage()) {
                case "DISPLAY":
                    breadCrumbBar.setSelectedCrumb(rootItem);
                    loader = new FXMLLoader(getClass().getResource(PACKAGE_ID.concat("AssetTypeTable.fxml")));
                    child = loader.load();
                    rootPane.getChildren().add(child);
                    break;
                case "ADD":
                    breadCrumbBar.setSelectedCrumb(addItem);
                    loader = new FXMLLoader(getClass().getResource(PACKAGE_ID.concat("AssetTypeForm.fxml")));
                    child = loader.load();
                    rootPane.getChildren().add(child);
                    break;
                case "UPDATE":
                    breadCrumbBar.setSelectedCrumb(addItem);
                    loader = new FXMLLoader(getClass().getResource(PACKAGE_ID.concat("AssetTypeForm.fxml")));
                    child = loader.load();
                    rootPane.getChildren().add(child);
                    AssetTypeFormController controller = loader.getController();
                    controller.setData(event.getData());
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
