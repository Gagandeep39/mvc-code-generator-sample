package org.example.assetuijavafx.fxml.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.assetuijavafx.controllers.AssetPlusController;
import org.example.assetuijavafx.fxml.utils.*;
import org.example.assetuijavafx.model.MaintenanceTicket;
import org.example.assetuijavafx.model.MaintenanceTicket.*;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import static org.example.assetuijavafx.fxml.layouts.AlertWindow.*;

public class MaintenanceTicketFormController implements Initializable {

	AssetPlusController assetPlusController;

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
		inputFieldId.setDisable(true);
        System.out.println("Preloaded MaintenanceTicket data");
    }

    @FXML
    private void onSave(ActionEvent event) {
		try {
			int id = Integer.parseInt(inputFieldId.getText());
			Date raisedOnDate = Date.valueOf(inputFieldRaisedOnDate.getValue());
			String description = inputFieldDescription.getText();
			TimeEstimate timeToResolve = choiceBoxTimeToResolve.getValue();
			PriorityLevel priority = choiceBoxPriorityLevel.getValue();
			String savedStatus;
			if (currentMaintenanceTicket != null) {
				savedStatus = AssetPlusController.updateMaintenanceTicket(id, id, raisedOnDate, description, timeToResolve.toString(), priority.toString());
			} else {
				savedStatus = AssetPlusController.addMaintenanceTicket(id, raisedOnDate, description, timeToResolve.toString(), priority.toString());
			}
			if (!savedStatus.isEmpty()) throw new InvalidInputException(savedStatus);
			else showSuccessAlert("Successfully saved item");
		} catch (RuntimeException e) {
			FormExceptionHandler.handleException(e);
		}
        onCancel(event);
    }

    @FXML
    private void onCancel(ActionEvent event) {
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();
    }

}
