package org.example.assetuijavafx.fxml.controllers;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.controlsfx.control.BreadCrumbBar;
import org.example.assetuijavafx.fxml.utils.PageSwitchEvent;
import org.example.assetuijavafx.model.TOMaintenanceTicket;

import java.net.URL;
import java.util.*;

import static org.example.assetuijavafx.application.AssetPlusApplication.PACKAGE_ID;

public class MaintenanceTicketController implements Initializable {

    private final TreeItem<String> rootItem = new TreeItem<>("MaintenanceTicket");
    private final TreeItem<String> addItem = new TreeItem<>("Add MaintenanceTicket");
    private final TreeItem<String> updateItem = new TreeItem<>("Update MaintenanceTicket");
    private final Map<String, String> pageToFxmlMap = Map.of("DISPLAY", "MaintenanceTicketTable.fxml" , "ADD", "MaintenanceTicketForm.fxml", "UPDATE", "MaintenanceTicketForm.fxml");

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
            if (event.getSelectedCrumb().getValue().equals("MaintenanceTicket")) {
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
                    MaintenanceTicketFormController controller = loader.getController();
                    controller.setData((TOMaintenanceTicket) event.getData());
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
