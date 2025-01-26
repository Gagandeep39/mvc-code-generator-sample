package org.example.assetuijavafx.fxml.controllers;

import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import org.example.assetuijavafx.controllers.AssetPlusController;
import org.example.assetuijavafx.model.MaintenanceTicket;
import org.example.assetuijavafx.model.TOMaintenanceTicket;
 // Handles enum values and other imports
import org.example.assetuijavafx.model.MaintenanceTicket.*;
import org.example.assetuijavafx.fxml.layouts.ButtonCell;
import org.example.assetuijavafx.fxml.utils.PageSwitchEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.*;

import static org.example.assetuijavafx.application.AssetPlusApplication.PACKAGE_ID;

public class MaintenanceTicketTableController implements Initializable  {


    @FXML
    public VBox parentContainer;
	@FXML
	private TableColumn<TOMaintenanceTicket, Integer> columnId;
	@FXML
	private TableColumn<TOMaintenanceTicket, Date> columnRaisedOnDate;
	@FXML
	private TableColumn<TOMaintenanceTicket, String> columnDescription;
	@FXML
	private TableColumn<TOMaintenanceTicket, TimeEstimate> columnTimeToResolve;
	@FXML
	private TableColumn<TOMaintenanceTicket, PriorityLevel> columnPriority;
	
	
	@FXML
	private TableColumn<TOMaintenanceTicket, Button> columnDelete;

	@FXML
	private TableColumn<TOMaintenanceTicket, Button> columnUpdate;

	@FXML
	private TableView<TOMaintenanceTicket> table;

    public void initializeTable() {
		List<TOMaintenanceTicket> toList = AssetPlusController.getMaintenanceTicketsOfAssetPlus();
        if (toList == null) {
            toList = Collections.emptyList();
        }
        ObservableList<TOMaintenanceTicket> list = FXCollections.observableArrayList(toList);

		columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		columnRaisedOnDate.setCellValueFactory(new PropertyValueFactory<>("raisedOnDate"));
		columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		columnTimeToResolve.setCellValueFactory(new PropertyValueFactory<>("timeToResolve"));
		columnPriority.setCellValueFactory(new PropertyValueFactory<>("priority"));

        columnUpdate.setCellFactory(column -> new ButtonCell<>("Update", this::updateMaintenanceTicket));
        columnDelete.setCellFactory(column -> new ButtonCell<>("Delete", this::showDialogDeleteMaintenanceTicket));
        table.setItems(list);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
		initializeTable();
    }

    private void updateMaintenanceTicket(TOMaintenanceTicket maintenanceTicket) {
		parentContainer.fireEvent(new PageSwitchEvent<>("UPDATE", maintenanceTicket));
    }

    public void onAddMaintenanceTicket(ActionEvent event) {
        parentContainer.fireEvent(new PageSwitchEvent<>("ADD"));
    }

    private void showDialogDeleteMaintenanceTicket(TOMaintenanceTicket maintenanceTicket) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PACKAGE_ID + "DeleteDialog.fxml"));
            Parent parent = fxmlLoader.load();
            // Get controller
            DeleteDialogController<TOMaintenanceTicket> controller = fxmlLoader.getController();
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

    private void deleteMaintenanceTicket(TOMaintenanceTicket maintenanceTicket) {
        AssetPlusController.removeMaintenanceTicket(maintenanceTicket.getId());
        System.out.println("Deleted MaintenanceTicket Successfully");
    }

}
