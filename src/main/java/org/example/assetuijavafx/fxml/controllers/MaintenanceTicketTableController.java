package org.example.assetuijavafx.fxml.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.assetuijavafx.fxml.layouts.ButtonCell;
import org.example.assetuijavafx.models.MaintenanceTicket;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import static org.example.assetuijavafx.AssetPlusApplication.PACKAGE_ID;
import static org.example.assetuijavafx.models.MaintenanceTicket.getMaintenanceticketsById;


public class MaintenanceTicketTableController implements Initializable  {

	@FXML
    private TableColumn<MaintenanceTicket, Integer> columnid;
	@FXML
    private TableColumn<MaintenanceTicket, Date> columnraisedOnDate;
	@FXML
    private TableColumn<MaintenanceTicket, String> columndescription;
	@FXML
    private TableColumn<MaintenanceTicket, TimeEstimate> columntimeToResolve;
	@FXML
    private TableColumn<MaintenanceTicket, PriorityLevel> columnpriority;
	
	
    @FXML
    private TableColumn<MaintenanceTicket, Button> columnDelete;

    @FXML
    private TableColumn<MaintenanceTicket, Button> columnUpdate;

    @FXML
    private TableView<MaintenanceTicket> table;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTable();
    }

    public void initializeTable() {
        ObservableList<MaintenanceTicket> list = FXCollections.observableArrayList(getMaintenanceticketsById().values());

		columnid.setCellValueFactory(new PropertyValueFactory<>("id"));
		columnraisedOnDate.setCellValueFactory(new PropertyValueFactory<>("raisedOnDate"));
		columndescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		columntimeToResolve.setCellValueFactory(new PropertyValueFactory<>("timeToResolve"));
		columnpriority.setCellValueFactory(new PropertyValueFactory<>("priority"));

        columnUpdate.setCellFactory(column -> new ButtonCell<>("Update", this::updateMaintenanceTicket));
        columnDelete.setCellFactory(column -> new ButtonCell<>("Delete", this::showDialogDeleteMaintenanceTicket));
        table.setItems(list);
    }


    private void updateMaintenanceTicket(MaintenanceTicket maintenanceTicket) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PACKAGE_ID + "MaintenanceTicketForm.fxml"));
            Parent parent = fxmlLoader.load();
            MaintenanceTicketFormController controller = fxmlLoader.getController();
            controller.setData(maintenanceTicket);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(parent));
            stage.showAndWait();
            initializeTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showDialogDeleteMaintenanceTicket(MaintenanceTicket maintenanceTicket) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PACKAGE_ID + "DeleteDialog.fxml"));
            Parent parent = fxmlLoader.load();
            // Get controller
            DeleteDialogController<MaintenanceTicket> controller = fxmlLoader.getController();
            controller.setAction(a -> deleteMaintenanceTicket(maintenanceTicket));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(parent));
            stage.showAndWait();
            initializeTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteMaintenanceTicket(MaintenanceTicket maintenanceTicket) {
        System.out.println("Deleted MaintenanceTicket Successfully");
        MaintenanceTicket.delete(maintenanceTicket);
    }

}
