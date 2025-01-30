package org.example.assetuijavafx.fxml.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import org.example.assetuijavafx.fxml.utils.PageSwitchEvent;
import org.example.assetuijavafx.model.NavigationState;

import java.io.IOException;
import java.util.ArrayList;

import static org.example.assetuijavafx.application.AssetPlusApplication.PACKAGE_ID;

public abstract class BaseController {

    protected abstract BreadCrumbBar<String> getBreadcrumbBar();
    protected abstract Pane getParentContainer();
    protected abstract Pane getChildContainer();
    protected TreeItem<String> root = new TreeItem<>();
    protected final ArrayList<NavigationState<?>> navigationStack = new ArrayList<>();


    public void initializeBreadcrumbNavigation(String rootPage) {
        this.root = new TreeItem<>(rootPage);
        getBreadcrumbBar().setOnCrumbAction(event -> {
            int depth = getCrumbDepth(event.getSelectedCrumb());
            navigationStack.subList(depth+1, navigationStack.size()).clear();
            getParentContainer().fireEvent(new PageSwitchEvent(navigationStack.get(depth)));
        });
    }

    protected int getCrumbDepth(TreeItem<String> item) {
        int depth = 0;
        while (item.getParent() != null) {
            depth++;
            item = item.getParent();
        }
        return depth;
    }

    public FXMLLoader loadPage(NavigationState<?> navigationState) throws IOException {
        if (!getChildContainer().getChildren().isEmpty()) {
            getChildContainer().getChildren().clear();
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource(PACKAGE_ID.concat(navigationState.getPageName())));
        Parent child = loader.load();
        getChildContainer().getChildren().add(child);
        navigationStack.add(navigationState);
        return loader;
    }

    protected void handleDisplay() {
        getBreadcrumbBar().setSelectedCrumb(root);
    }

    protected void handleAdd(NavigationState<?> state) {
        TreeItem<String> addItem = new TreeItem<>(state.getTitle());
        root.getChildren().add(addItem);
        getBreadcrumbBar().setSelectedCrumb(addItem);
    }


    protected void handleRedirectDisplay(NavigationState<?> state, FXMLLoader loader) throws Exception {
        TreeItem<String> redirectItem = new TreeItem<>(state.getTitle());
        getBreadcrumbBar().getSelectedCrumb().getChildren().add(redirectItem);
        getBreadcrumbBar().setSelectedCrumb(redirectItem);
        Object redirectController = loader.getController();
        redirectController.getClass()
                .getMethod("setData", String.class, Object.class)
                .invoke(redirectController, state.getMultiplicity(), state.getData());

    }

    protected void handleUpdate(NavigationState<?> state, FXMLLoader loader) throws Exception {
        TreeItem<String> redirectUpdateItem = new TreeItem<>(state.getTitle());
        getBreadcrumbBar().getSelectedCrumb().getChildren().add(redirectUpdateItem);
        getBreadcrumbBar().setSelectedCrumb(redirectUpdateItem);
        Object redirectUpdateController = loader.getController();
        Class<?> parameterType = state.getData().getClass();
        redirectUpdateController.getClass()
                .getMethod("setData", parameterType)
                .invoke(redirectUpdateController, state.getData());
    }

    protected void handleBack() {
        // Switch to second last crumb
        TreeItem<String> current = getBreadcrumbBar().getSelectedCrumb();
        getBreadcrumbBar().setSelectedCrumb(current.getParent());
        // Remove last item from nav stack
        navigationStack.removeLast();
        // Trigger event to set last item as active
        int index = navigationStack.size() - 1;
        getParentContainer().fireEvent(new PageSwitchEvent(navigationStack.get(index)));
    }

}
