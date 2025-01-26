package org.example.assetuijavafx.fxml.controllers;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.controlsfx.control.BreadCrumbBar;
import org.example.assetuijavafx.fxml.utils.PageSwitchEvent;
import org.example.assetuijavafx.model.TOSpecificAsset;

import java.net.URL;
import java.util.*;

import static org.example.assetuijavafx.application.AssetPlusApplication.PACKAGE_ID;

public class SpecificAssetController implements Initializable {

    private final TreeItem<String> rootItem = new TreeItem<>("SpecificAsset");
    private final TreeItem<String> addItem = new TreeItem<>("Add SpecificAsset");
    private final TreeItem<String> updateItem = new TreeItem<>("Update SpecificAsset");
    private final Map<String, String> pageToFxmlMap = Map.of("DISPLAY", "SpecificAssetTable.fxml" , "ADD", "SpecificAssetForm.fxml", "UPDATE", "SpecificAssetForm.fxml");

    @FXML
    public BreadCrumbBar<String> breadCrumbBar;
    @FXML
    public VBox parentContainer, childContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rootItem.getChildren().add(addItem);
        rootItem.getChildren().add(updateItem);
        initializeBreadcrumbNavigation();
        parentContainer.addEventHandler(PageSwitchEvent.PAGE_SWITCH, this::changePage);
        parentContainer.fireEvent(new PageSwitchEvent<>("DISPLAY"));
    }

    public void initializeBreadcrumbNavigation() {
        breadCrumbBar.setOnCrumbAction(event -> {
            if (event.getSelectedCrumb().getValue().equals("SpecificAsset")) {
                parentContainer.fireEvent(new PageSwitchEvent<>("DISPLAY"));
            }
        });
    }

    public <T> void changePage(PageSwitchEvent<T> event) {
        if (!childContainer.getChildren().isEmpty()) {
            childContainer.getChildren().clear();
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PACKAGE_ID.concat(pageToFxmlMap.get(event.getPage()))));
            Parent child = loader.load();
            childContainer.getChildren().add(child);
            switch (event.getPage()) {
                case "DISPLAY":
                    breadCrumbBar.setSelectedCrumb(rootItem);
                    break;
                case "ADD":
                    breadCrumbBar.setSelectedCrumb(addItem);
                    break;
                case "UPDATE":
                    breadCrumbBar.setSelectedCrumb(updateItem);
                    SpecificAssetFormController controller = loader.getController();
                    controller.setData((TOSpecificAsset) event.getData());
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
