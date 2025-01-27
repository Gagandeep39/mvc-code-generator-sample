package org.example.assetuijavafx.fxml.controllers;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.controlsfx.control.BreadCrumbBar;
import org.example.assetuijavafx.fxml.utils.PageHelper;
import org.example.assetuijavafx.fxml.utils.PageSwitchEvent;
import org.example.assetuijavafx.model.NavigationState;
import org.example.assetuijavafx.model.TOSpecificAsset;

import java.net.URL;
import java.util.*;

import static org.example.assetuijavafx.application.AssetPlusApplication.PACKAGE_ID;

public class SpecificAssetController implements Initializable {

    @FXML
    public BreadCrumbBar<String> breadCrumbBar;

    TreeItem<String> root = new TreeItem<>("SpecificAsset");
    @FXML
    public VBox parentContainer, childContainer;

    private final ArrayList<NavigationState<?>> navigationStack = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeBreadcrumbNavigation();
        parentContainer.addEventHandler(PageSwitchEvent.PAGE_SWITCH, this::changePage);
        parentContainer.fireEvent(new PageSwitchEvent(
                new NavigationState<>("SpecificAsset",
                "DISPLAY",
                "SpecificAssetDisplay.fxml")));
    }

    public void initializeBreadcrumbNavigation() {
        breadCrumbBar.setOnCrumbAction(event -> {
            int depth = getCrumbDepth(event.getSelectedCrumb());
            navigationStack.subList(depth+1, navigationStack.size()).clear();
            parentContainer.fireEvent(new PageSwitchEvent(navigationStack.get(depth)));
        });
    }

    private int getCrumbDepth(TreeItem<String> item) {
        int depth = 0;
        while (item.getParent() != null) {
            depth++;
            item = item.getParent();
        }
        return depth;
    }

    public void changePage(PageSwitchEvent event) {
        if (!childContainer.getChildren().isEmpty()) {
            childContainer.getChildren().clear();
        }
        NavigationState<?> navigationState = event.getNavigationState();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PACKAGE_ID.concat(navigationState.getPageName())));
            Parent child = loader.load();
            childContainer.getChildren().add(child);

            if (!navigationStack.isEmpty() && navigationStack.getLast().getFeature().contains("REDIRECT") && navigationState.getFeature().equals("UPDATE"))
                navigationState.setFeature("REDIRECT_UPDATE");

            navigationStack.add(navigationState);


            switch (navigationState.getFeature()) {
                case "DISPLAY":
                    breadCrumbBar.setSelectedCrumb(root);
                    break;
                case "ADD":
                    TreeItem<String> addItem = new TreeItem<>(navigationState.getTitle());
                    root.getChildren().add(addItem);
                    breadCrumbBar.setSelectedCrumb(addItem);
                    break;
                case "UPDATE":
                    TreeItem<String> updateItem = new TreeItem<>(navigationState.getTitle());
                    root.getChildren().add(updateItem);
                    breadCrumbBar.setSelectedCrumb(updateItem);
                    SpecificAssetFormController controller = loader.getController();
                    controller.setData((TOSpecificAsset) navigationState.getData());
                    break;
                case "REDIRECT_DISPLAY":
                    TreeItem<String> redirectItem = new TreeItem<>(navigationState.getTitle());
                    breadCrumbBar.getSelectedCrumb().getChildren().add(redirectItem);
                    breadCrumbBar.setSelectedCrumb(redirectItem);
                    Object redirectController = loader.getController();
                    redirectController.getClass()
                            .getMethod("setData", String.class, Object.class)
                            .invoke(redirectController, navigationState.getMultiplicity(), navigationState.getData());
                    break;
                case "REDIRECT_UPDATE":
                    TreeItem<String> redirectUpdateItem = new TreeItem<>(navigationState.getTitle());
                    breadCrumbBar.getSelectedCrumb().getChildren().add(redirectUpdateItem);
                    breadCrumbBar.setSelectedCrumb(redirectUpdateItem);
                    Object redirectUpdateController = loader.getController();
                    Class<?> parameterType = navigationState.getData().getClass();
                    redirectUpdateController.getClass()
                            .getMethod("setData", parameterType)
                            .invoke(redirectUpdateController, navigationState.getData());
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
