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
    private final int THRESHOLD_WIDTH = 50;

    /**
     * Helper Methods
     * @param navItem Add item to the navStack
     */
    public void addNavItem(final NavigationState<?> navItem) {
        navigationStack.add(navItem);
    }

    /**
     * Remove last item and returns Second last item
     * @return navigationState
     */
    public NavigationState<?> removeLastAndGetSecondLast() {
        navigationStack.removeLast();
        return navigationStack.getLast();
    }


    /**
     * Force rerenders the Breadcrumb UI
     */
    public void forceUpdateUi(TreeItem<String> last) {
        this.breadCrumbBar.setSelectedCrumb(null); // Tem workaround for UI to update
        this.breadCrumbBar.setSelectedCrumb(last);
    }


    /**
     * Finds the first item in the Tree Structure of breadcrumb
     */
    public TreeItem<String> getRootTreeItem(TreeItem<String> node) {
        TreeItem<String> temp = node;
        while (temp.getParent() != null) {
            temp = temp.getParent();
        }
        return temp;
    }

    /**
     * Calculate depth of the selected breadcrumb
     */
    protected int getCrumbDepth(TreeItem<String> item) {
        int depth = 0;
        while (item.getParent() != null) {
            depth++;
            item = item.getParent();
        }
        return depth;
    }

    /**
     * Initialize Breadcrumbar
     * @param breadCrumbBar Breadcrumbar
     * @param parentContainer Container containing breadcrumb
     */
    public BreadcrumbManager(BreadCrumbBar<String> breadCrumbBar, Pane parentContainer) {
        this.breadCrumbBar = breadCrumbBar;
        this.parentContainer = parentContainer;

        this.breadCrumbBar.setOnCrumbAction(event -> {
            int depth = getCrumbDepth(event.getSelectedCrumb());
            if (depth > 0 && !collapsedStack.isEmpty()) {
                depth += collapsedStack.size() - 1; // To exclude ellipse depth
            }
            ///  1. Calculate Last item and Selected Item depth
            int rawNavigationDepth = navigationStack.size();
            int rawSelectionDepth = depth + 1; // Handle the new item that will be added as part of this event trigger

            /// 2. Update navigation stack and remove items after selected item
            event.getSelectedCrumb().getChildren().clear();
            NavigationState<?> state = navigationStack.get(depth);
            navigationStack.subList(depth, navigationStack.size()).clear();

            /// 3. Launch the selected Page
            this.parentContainer.fireEvent(new PageSwitchEvent(state));

            /// 4. Expand breadcrumb if required
            expandIfRequired(rawSelectionDepth, rawNavigationDepth);
        });

        // Handle Ellipse breadcrum styling
        this.breadCrumbBar.setCrumbFactory(crumb -> {
            BreadCrumbBar.BreadCrumbButton breadCrumbButton = new BreadCrumbBar.BreadCrumbButton(crumb.getValue());
            breadCrumbButton.setDisable(crumb.getValue().equals("..."));
            return breadCrumbButton;
        });
        this.breadCrumbBar.widthProperty()
                .addListener((obs, oldVal, newVal) -> collapseItems(newVal.doubleValue()));
        this.parentContainer
                .widthProperty().addListener((obs, oldVal, newVal) -> expandItems(newVal.doubleValue()));
    }

    private void collapseItems(double breadcrumbWidth) {
        double containerWidth = this.parentContainer.getWidth();
        boolean isOverflowing = breadcrumbWidth + THRESHOLD_WIDTH > containerWidth;
        if (!isOverflowing) return;
        if (getCrumbDepth(this.breadCrumbBar.getSelectedCrumb()) <3) return;
        TreeItem<String> last = this.breadCrumbBar.getSelectedCrumb();
        TreeItem<String> first = getRootTreeItem(last) ;

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
        first.getChildren().clear();
        first.getChildren().add(ellipsisItem);
        this.forceUpdateUi(last);
        lastChangedWidth = parentContainer.getWidth();
    }


    private void expandIfRequired(int clickedDepth, int totalNavigationDepth) {
        if (collapsedStack.isEmpty()) return;
        System.out.println("Clicked depth: " + clickedDepth);
        System.out.println("Last item depth: " + totalNavigationDepth);
        int numOfItemsToExpand = totalNavigationDepth - clickedDepth;
        TreeItem<String> last = this.breadCrumbBar.getSelectedCrumb();
        TreeItem<String> secondItem = ellipsisItem;
        if (numOfItemsToExpand >= collapsedStack.size()) {
            System.out.println("Remove Eclipse");
            secondItem = ellipsisItem.getParent();
            secondItem.getChildren().add(ellipsisItem.getChildren().getFirst());
            secondItem.getChildren().remove(ellipsisItem);
        }
        System.out.println(collapsedStack.size());
        System.out.println("Expand " + numOfItemsToExpand + " items with ellipse");
        int finalItems = Math.min(numOfItemsToExpand, collapsedStack.size());
        for (int i = 0; i < finalItems; i++) {
            insertNewItemBefore(secondItem.getChildren().getFirst(), collapsedStack.removeLast().getTitle());
        }
        forceUpdateUi(last);
    }


    private void expandItems(double newWidth) {
        if (Math.abs(newWidth - lastChangedWidth) < THRESHOLD_WIDTH) return;
        if (collapsedStack.isEmpty()) return;
        // Expand 1 item and
        TreeItem<String> last = this.breadCrumbBar.getSelectedCrumb();
        TreeItem<String> first = getRootTreeItem(last) ;

        if (collapsedStack.size() == 1) {
            TreeItem<String> itemAfterEllipse = ellipsisItem.getChildren().getFirst();
            TreeItem<String> collapsedItem = new TreeItem<>(collapsedStack.removeFirst().getTitle());
            collapsedItem.getChildren().add(itemAfterEllipse);
            first.getChildren().remove(ellipsisItem);
            first.getChildren().add(collapsedItem);
        } else {
            insertNewItemBefore(ellipsisItem.getChildren().getFirst(), collapsedStack.removeLast().getTitle());
        }
        forceUpdateUi(last);
        // update lastChangedWidth
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
