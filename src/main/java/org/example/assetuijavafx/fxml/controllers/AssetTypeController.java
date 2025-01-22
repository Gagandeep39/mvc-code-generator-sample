package org.example.assetuijavafx.fxml.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.VBox;
import org.controlsfx.control.BreadCrumbBar;
import org.example.assetuijavafx.fxml.utils.PageSwitchEvent;
import org.example.assetuijavafx.model.TOAssetType;

import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.ResourceBundle;

import static org.example.assetuijavafx.AssetPlusApplication.PACKAGE_ID;

public class AssetTypeController implements Initializable {

    private final TreeItem<String> rootItem = new TreeItem<>("AssetType");
    private final TreeItem<String> addItem = new TreeItem<>("Add AssetType");
    private final TreeItem<String> updateItem = new TreeItem<>("Update AssetType");
    private final Map<String, String> pageToFxmlMap = Map.of(
            "DISPLAY", "AssetTypeTable.fxml",
            "ADD", "AssetTypeForm.fxml",
            "UPDATE", "AssetTypeForm.fxml"
    );

    @FXML
    public BreadCrumbBar<String> breadCrumbBar;
    @FXML
    public VBox parentContainer, childContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rootItem.getChildren().addAll(Arrays.asList(addItem, updateItem));
        initializeBreadcrumbNavigation();
        parentContainer.addEventHandler(PageSwitchEvent.PAGE_SWITCH, this::changePage);
        parentContainer.fireEvent(new PageSwitchEvent<>("DISPLAY"));
    }

    public void initializeBreadcrumbNavigation() {
        breadCrumbBar.setOnCrumbAction(event -> {
            if (event.getSelectedCrumb().getValue().equals("AssetType")) {
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
                    AssetTypeFormController controller = loader.getController();
                    controller.setData((TOAssetType) event.getData());
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
