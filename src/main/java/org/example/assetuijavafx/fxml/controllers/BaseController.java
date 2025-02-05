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
import java.util.List;

import static org.example.assetuijavafx.application.AssetPlusApplication.PACKAGE_ID;

public abstract class BaseController {

    protected abstract BreadCrumbBar<String> getBreadcrumbBar();
    protected abstract Pane getParentContainer();
    protected abstract Pane getChildContainer();
    protected TreeItem<String> root = new TreeItem<>();
    protected final ArrayList<NavigationState<?>> navigationStack = new ArrayList<>();
    protected final ArrayList<NavigationState<?>> collapsedStack =  new ArrayList<>();
    private final TreeItem<String> ellipsisItem = new TreeItem<>("..."); // Non-clickable item
    private double lastChangedWidth = 0;

    public void initializeBreadcrumbNavigation(String rootPage) {
        this.root = new TreeItem<>(rootPage);
        getBreadcrumbBar().setOnCrumbAction(event -> {
            int depth = getCrumbDepth(event.getSelectedCrumb());
            // Handle items after elipse
            if (depth > 0 && !collapsedStack.isEmpty()) {
                depth += collapsedStack.size() - 1; // To exclude ellipse depth
            }
            // Removed current item to last item
            // Because once the event is sccessful, itll get added back anyway
            event.getSelectedCrumb().getChildren().clear();
            NavigationState<?> state = navigationStack.get(depth);
            navigationStack.subList(depth, navigationStack.size()).clear();

            getParentContainer().fireEvent(new PageSwitchEvent(state));
        });

        // Disables breadcrumb when collapsed
        getBreadcrumbBar().setCrumbFactory(crumb -> {
            BreadCrumbBar.BreadCrumbButton breadCrumbButton = new BreadCrumbBar.BreadCrumbButton(crumb.getValue());
            breadCrumbButton.setDisable(crumb.getValue().equals("..."));
            return breadCrumbButton;
        });
        getBreadcrumbBar().widthProperty().addListener((obs, oldVal, newVal) -> adjustBreadcrumbs(newVal.doubleValue()));
        getParentContainer().widthProperty().addListener((obs, oldVal, newVal) -> expandItems(newVal.doubleValue()));
    }

    private void insertNewItemBefore(TreeItem<String> selectedItem, String newItemValue) {
        TreeItem<String> parent = selectedItem.getParent();
        if (parent != null) {
            int index = parent.getChildren().indexOf(selectedItem);
            parent.getChildren().remove(selectedItem); // Remove the selected item from its parent

            TreeItem<String> newItem = new TreeItem<>(newItemValue);
            parent.getChildren().add(index, newItem); // Insert new item at the same position
            newItem.getChildren().add(selectedItem); // Add the original item as a child of the new item
        }
    }

    private void expandItems(double newWidth) {
        if (Math.abs(newWidth - lastChangedWidth) < 100) return;
        if (collapsedStack.isEmpty()) return;
        // Expand 1 item and
        TreeItem<String> last = getBreadcrumbBar().getSelectedCrumb();
        TreeItem<String> first = getBreadcrumbBar().getSelectedCrumb();
        while (first.getParent() != null) {
            first = first.getParent();
        }
        if (collapsedStack.size() == 1) {
            TreeItem<String> itemAfterEllipse = ellipsisItem.getChildren().getFirst();
            TreeItem<String> collapsedItem = new TreeItem<>(collapsedStack.removeFirst().getTitle());
            collapsedItem.getChildren().add(itemAfterEllipse);
            first.getChildren().remove(ellipsisItem);
            first.getChildren().add(collapsedItem);
        } else {
            insertNewItemBefore(ellipsisItem.getChildren().getFirst(), collapsedStack.removeLast().getTitle());
        }
        getBreadcrumbBar().setSelectedCrumb(first); // Tem workaround for UI to update
        getBreadcrumbBar().setSelectedCrumb(last);
        // uodate lastChangedWidth
        lastChangedWidth = newWidth;
    }

    private void adjustBreadcrumbs(double breadcrumbWidth) {
        double containerWidth = getParentContainer().getWidth();
        boolean isOverflowing = breadcrumbWidth + 42 > containerWidth;

        if (isOverflowing) {
            collapseItems();
        }
    }

    private void collapseItems() {
        if (getCrumbDepth(getBreadcrumbBar().getSelectedCrumb()) <3) return;
        TreeItem<String> last = getBreadcrumbBar().getSelectedCrumb();
        TreeItem<String> first = getBreadcrumbBar().getSelectedCrumb();
        while (first.getParent() != null) {
            first = first.getParent();
        }

        TreeItem<String> second = first.getChildren().getFirst();
        // If ellipse already there then remove ellipse and update the second item to non ellipse second item
        if (second.equals(ellipsisItem)) {
            first.getChildren().remove(second);
            second = second.getChildren().getFirst();
        }
        // Remove second item
        collapsedStack.add(navigationStack.get(1 + collapsedStack.size()));
        // Append 3rd item to ellipse
        TreeItem<String> third = second.getChildren().getFirst();
        ellipsisItem.getChildren().clear();
        ellipsisItem.getChildren().add(third);
        // Remove second item from first item
        first.getChildren().remove(second);
        // Append ellipse to first item
        first.getChildren().add(ellipsisItem);
        getBreadcrumbBar().setSelectedCrumb(first); // Tem workaround for UI to update
        getBreadcrumbBar().setSelectedCrumb(last);
        lastChangedWidth = getParentContainer().getWidth();
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
