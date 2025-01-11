package org.example.assetuijavafx.fxml.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.assetuijavafx.models.MaintenanceTicket;
import org.example.assetuijavafx.models.MaintenanceTicket.*;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class MaintenanceTicketFormController implements Initializable {

	@FXML
	private TextField inputFieldId;
	@FXML
    private DatePicker inputFieldRaisedOnDate;
	@FXML
	private TextField inputFieldDescription;
//	@FXML
//	private TextField inputFieldTimeToResolve;
	@FXML
	private ChoiceBox<TimeEstimate> choiceBoxTimeToResolve;
//	@FXML
//	private TextField inputFieldPriority;
	@FXML
	private ChoiceBox<PriorityLevel> choiceBoxPriorityLevel;

    @FXML
    private Button buttonCancel;

    private MaintenanceTicket currentMaintenanceTicket;


	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		choiceBoxTimeToResolve.getItems().addAll(TimeEstimate.values());
		choiceBoxPriorityLevel.getItems().addAll(PriorityLevel.values());
	}

    public void setData(MaintenanceTicket maintenanceTicket) {
        this.currentMaintenanceTicket = maintenanceTicket;

    	inputFieldId.setText(String.valueOf(maintenanceTicket.getId()));
    	inputFieldRaisedOnDate.setValue(maintenanceTicket.getRaisedOnDate().toLocalDate());
    	inputFieldDescription.setText(maintenanceTicket.getDescription());
    	choiceBoxTimeToResolve.setValue(maintenanceTicket.getTimeToResolve());
    	choiceBoxPriorityLevel.setValue(maintenanceTicket.getPriority());
        System.out.println("Preloaded MaintenanceTicket data");
    }

    @FXML
    private void onSave(ActionEvent event) {
		int id = Integer.parseInt(inputFieldId.getText());
		Date raisedOnDate = Date.valueOf(inputFieldRaisedOnDate.getValue());
		String description = inputFieldDescription.getText();
		TimeEstimate timeToResolve = choiceBoxTimeToResolve.getValue();
		PriorityLevel priority = choiceBoxPriorityLevel.getValue();
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
