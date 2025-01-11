package org.example.assetuijavafx.fxml.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import static org.example.assetuijavafx.AssetPlusApplication.PACKAGE_ID;

public class MaintenanceTicketController {

    @FXML
    private MaintenanceTicketTableController tableController;

    @FXML
    private void onAddMaintenanceTicket(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PACKAGE_ID + "MaintenanceTicketForm.fxml"));
        Parent parent = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(parent));
        stage.showAndWait();
        tableController.initializeTable();
    }


}
