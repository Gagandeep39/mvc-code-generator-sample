package org.example.assetuijavafx.fxml.controllers;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.controlsfx.control.BreadCrumbBar;
import org.example.assetuijavafx.fxml.utils.PageHelper;
import org.example.assetuijavafx.fxml.utils.PageSwitchEvent;
import org.example.assetuijavafx.model.NavigationState;
import org.example.assetuijavafx.model.PageType;
import org.example.assetuijavafx.model.TOSpecificAsset;

import java.net.URL;
import java.util.*;

import static org.example.assetuijavafx.application.AssetPlusApplication.PACKAGE_ID;

public class SpecificAssetController extends BaseController implements Initializable {

    @FXML
    public BreadCrumbBar<String> breadCrumbBar;
  @FXML
    public VBox parentContainer, childContainer;

    @Override
    protected BreadCrumbBar<String> getBreadcrumbBar() {
        return breadCrumbBar;
    }

    @Override
    protected Pane getParentContainer() {
        return parentContainer;
    }

    @Override
    protected Pane getChildContainer() {
        return childContainer;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initializeBreadcrumbNavigation("SpecificAsset");
        parentContainer.addEventHandler(PageSwitchEvent.PAGE_SWITCH, this::changePage);
        parentContainer.fireEvent(new PageSwitchEvent(
                new NavigationState<>("SpecificAsset",
                        PageType.DISPLAY,
                "SpecificAssetDisplay.fxml")));
    }


    public void changePage(PageSwitchEvent event) {
        try {
            NavigationState<?> navigationState = event.getNavigationState();
            if (navigationState.getPageType().equals(PageType.BACK)) {
                super.handleBack();
                return;
            }
            FXMLLoader loader = super.loadPage(navigationState);
            switch (navigationState.getPageType()) {
                case PageType.DISPLAY:
                    super.handleDisplay();
                    break;
                case PageType.ADD:
                    super.handleAdd(navigationState);
                    break;
                case PageType.UPDATE:
                    super.handleUpdate(navigationState, loader);
                    break;
                case PageType.REDIRECT_DISPLAY:
                    super.handleRedirectDisplay(navigationState, loader);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
