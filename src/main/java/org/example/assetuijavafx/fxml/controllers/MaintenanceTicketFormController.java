package org.example.assetuijavafx.fxml.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.assetuijavafx.models.MaintenanceTicket;

import java.sql.Date;

public class MaintenanceTicketFormController {

	@FXML
	private TextField inputFieldId;
	@FXML
    private DatePicker inputFieldRaisedOnDate;
	@FXML
	private TextField inputFieldDescription;
	@FXML
	private TextField inputFieldTimeToResolve;
	@FXML
	private TextField inputFieldPriority;

    @FXML
    private Button buttonCancel;

    private MaintenanceTicket currentMaintenanceTicket;

    public void setData(MaintenanceTicket maintenanceTicket) {
        this.currentMaintenanceTicket = maintenanceTicket;

    	inputFieldId.setText(String.valueOf(maintenanceTicket.getId()));
    	inputFieldRaisedOnDate.setValue(maintenanceTicket.getRaisedOnDate().toLocalDate());
    	inputFieldDescription.setText(maintenanceTicket.getDescription());
    	inputFieldTimeToResolve.setText(maintenanceTicket.getTimeToResolve());
    	inputFieldPriority.setText(maintenanceTicket.getPriority());
        System.out.println("Preloaded MaintenanceTicket data");
    }

    @FXML
    private void onSave(ActionEvent event) {
		int id = Integer.parseInt(inputFieldId.getText());
		Date raisedOnDate = Date.valueOf(inputFieldRaisedOnDate.getValue());
		String description = inputFieldDescription.getText();
		TimeEstimate timeToResolve = inputFieldTimeToResolve.getText();
		PriorityLevel priority = inputFieldPriority.getText();
		if (currentMaintenanceTicket != null) {
			currentMaintenanceTicket.setId(id);
			currentMaintenanceTicket.setRaisedOnDate(raisedOnDate);
			currentMaintenanceTicket.setDescription(description);
			currentMaintenanceTicket.setTimeToResolve(timeToResolve);
			currentMaintenanceTicket.setPriority(priority);
		} else {
			MaintenanceTicket maintenanceTicket = new MaintenanceTicket();
			maintenanceTicket.setId(id);
			maintenanceTicket.setRaisedOnDate(raisedOnDate);
			maintenanceTicket.setDescription(description);
			maintenanceTicket.setTimeToResolve(timeToResolve);
			maintenanceTicket.setPriority(priority);
			MaintenanceTicket.addMaintenanceTicket(maintenanceTicket);
		}
        System.out.println("Successfully saved item");
        onCancel(event);
    }

    @FXML
    private void onCancel(ActionEvent event) {
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();
    }
}
