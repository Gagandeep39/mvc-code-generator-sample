package org.example.assetuijavafx.fxml.controllers;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.controlsfx.control.BreadCrumbBar;
import org.example.assetuijavafx.fxml.utils.PageHelper;
import org.example.assetuijavafx.fxml.utils.PageSwitchEvent;
import org.example.assetuijavafx.model.NavigationState;
import org.example.assetuijavafx.model.TOAssetType;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static org.example.assetuijavafx.application.AssetPlusApplication.PACKAGE_ID;

public class AssetTypeController extends BaseController implements Initializable {

    @FXML
    public BreadCrumbBar<String> breadCrumbBar;

    @FXML
    public VBox parentContainer, childContainer;

    public AssetTypeController() {
    }

    @Override
    protected BreadCrumbBar<String> getBreadcrumbBar() {
        return breadCrumbBar;
    }

    @Override
    protected Pane getChildContainer() {
        return childContainer;
    }

    @Override
    protected Pane getParentContainer() {
        return parentContainer;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initializeBreadcrumbNavigation("AssetType");
        parentContainer.addEventHandler(PageSwitchEvent.PAGE_SWITCH, this::changePage);
        parentContainer.fireEvent(new PageSwitchEvent(
                new NavigationState<>("AssetType",
                        "DISPLAY",
                        "AssetTypeDisplay.fxml")));
    }



    public void changePage(PageSwitchEvent event) {
        try {
            NavigationState<?> navigationState = event.getNavigationState();
            FXMLLoader loader = super.loadPage(navigationState);

            switch (navigationState.getFeature()) {
                case "DISPLAY":
                    super.handleDisplay();
                    break;
                case "ADD":
                    super.handleAdd(navigationState);
                    break;
                case "UPDATE":
                    super.handleUpdate(navigationState, loader);
                    break;
                case "REDIRECT_DISPLAY":
                    super.handleRedirectDisplay(navigationState, loader);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
