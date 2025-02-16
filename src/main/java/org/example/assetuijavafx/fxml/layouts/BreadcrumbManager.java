package org.example.assetuijavafx.fxml.layouts;

import javafx.scene.control.TreeItem;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import org.example.assetuijavafx.fxml.utils.PageSwitchEvent;
import org.example.assetuijavafx.model.NavigationState;

import java.util.ArrayList;

public class BreadcrumbManager {
    private final TreeItem<String> ellipsisItem = new TreeItem<>("..."); // Non-clickable item
    private double lastChangedWidth = 0;
    BreadCrumbBar<String> breadCrumbBar;
    private final Pane parentContainer;
    protected final ArrayList<NavigationState<?>> collapsedStack =  new ArrayList<>();
    public ArrayList<NavigationState<?>> navigationStack = new ArrayList<>();

    public void addNavItem(final NavigationState<?> navItem) {
        navigationStack.add(navItem);
    }

    public NavigationState<?> removeLastAndGetSecondLast() {
        navigationStack.removeLast();
        return navigationStack.getLast();
    }

    public BreadcrumbManager(BreadCrumbBar<String> breadCrumbBar, Pane parentContainer) {
        this.breadCrumbBar = breadCrumbBar;
        this.parentContainer = parentContainer;

        this.breadCrumbBar.setOnCrumbAction(event -> {
            int depth = getCrumbDepth(event.getSelectedCrumb());
            int rawNavigationDepth = navigationStack.size();
            // Handle items after elipse
            if (depth > 0 && !collapsedStack.isEmpty()) {
                depth += collapsedStack.size() - 1; // To exclude ellipse depth
            }
            int rawSelectionDepth = depth + 1;
            // Done here because wewill get last item length using navstack length and depth calculated after elipse handlinh
            // Removed current item to last item
            // Because once the event is sccessful, itll get added back anyway
            event.getSelectedCrumb().getChildren().clear();
            NavigationState<?> state = navigationStack.get(depth);
            navigationStack.subList(depth, navigationStack.size()).clear();

            this.parentContainer.fireEvent(new PageSwitchEvent(state));
            expandIfRequired(rawSelectionDepth, rawNavigationDepth);
        });

        // Disables breadcrumb when collapsed
        this.breadCrumbBar.setCrumbFactory(crumb -> {
            BreadCrumbBar.BreadCrumbButton breadCrumbButton = new BreadCrumbBar.BreadCrumbButton(crumb.getValue());
            breadCrumbButton.setDisable(crumb.getValue().equals("..."));
            return breadCrumbButton;
        });
        this.breadCrumbBar.widthProperty().addListener((obs, oldVal, newVal) -> adjustBreadcrumbs(newVal.doubleValue()));
        parentContainer.widthProperty().addListener((obs, oldVal, newVal) -> expandItems(newVal.doubleValue()));
    }


    private void adjustBreadcrumbs(double breadcrumbWidth) {
        double containerWidth = this.parentContainer.getWidth();
        boolean isOverflowing = breadcrumbWidth + 42 > containerWidth;
        if (isOverflowing) {
            collapseItems();
        }
    }

    protected int getCrumbDepth(TreeItem<String> item) {
        int depth = 0;
        while (item.getParent() != null) {
            depth++;
            item = item.getParent();
        }
        return depth;
    }

    private void collapseItems() {
        if (getCrumbDepth(this.breadCrumbBar.getSelectedCrumb()) <3) return;
        TreeItem<String> last = this.breadCrumbBar.getSelectedCrumb();
        TreeItem<String> first = this.breadCrumbBar.getSelectedCrumb();
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
        System.out.println("Collapsing " + collapsedStack.size() + " items");
        // Append 3rd item to ellipse
        TreeItem<String> third = second.getChildren().getFirst();
        ellipsisItem.getChildren().addFirst(third);
        this.breadCrumbBar.setSelectedCrumb(third);
        first.getChildren().clear();
        first.getChildren().add(ellipsisItem);
        this.breadCrumbBar.setSelectedCrumb(first); // Tem workaround for UI to update
        this.breadCrumbBar.setSelectedCrumb(last);
        lastChangedWidth = parentContainer.getWidth();
    }

    private void expandIfRequired(int clickedDepth, int totalNavigationDepth) {
        if (collapsedStack.isEmpty()) return;
        System.out.println("Cliked depth: " + clickedDepth);
        System.out.println("Last item depth: " + totalNavigationDepth);
        int numOfItemsToExpand = totalNavigationDepth - clickedDepth;
        TreeItem<String> last = this.breadCrumbBar.getSelectedCrumb();
        TreeItem<String> first = this.breadCrumbBar.getSelectedCrumb();
        TreeItem<String> secondItem = ellipsisItem;
        while (first.getParent() != null) {
            first = first.getParent();
        }
        if (numOfItemsToExpand >= collapsedStack.size()) {
            System.out.println("Remove Eclipse");
            secondItem = ellipsisItem.getParent();
            secondItem.getChildren().add(ellipsisItem.getChildren().getFirst());
            secondItem.getChildren().remove(ellipsisItem);
        }
        System.out.println(collapsedStack.size());
        // Working state
        System.out.println("Expand " + numOfItemsToExpand + " items with ellipse");
        int finalItems = Math.min(numOfItemsToExpand, collapsedStack.size());
        for (int i = 0; i < finalItems; i++) {
            insertNewItemBefore(secondItem.getChildren().getFirst(), collapsedStack.removeLast().getTitle());
        }

        this.breadCrumbBar.setSelectedCrumb(null);
//        getBreadcrumbBar().setSelectedCrumb(first); // Tem workaround for UI to update
        this.breadCrumbBar.setSelectedCrumb(last);
    }


    private void expandItems(double newWidth) {
        if (Math.abs(newWidth - lastChangedWidth) < 100) return;
        if (collapsedStack.isEmpty()) return;
        // Expand 1 item and
        TreeItem<String> last = this.breadCrumbBar.getSelectedCrumb();
        TreeItem<String> first = this.breadCrumbBar.getSelectedCrumb();
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
        this.breadCrumbBar.setSelectedCrumb(first); // Tem workaround for UI to update
        this.breadCrumbBar.setSelectedCrumb(last);
        // uodate lastChangedWidth
        lastChangedWidth = newWidth;
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

}
