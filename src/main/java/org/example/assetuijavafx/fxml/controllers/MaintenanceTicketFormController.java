package org.example.assetuijavafx.fxml.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import org.example.assetuijavafx.controllers.AssetPlusController;
import org.example.assetuijavafx.fxml.utils.*;
import org.example.assetuijavafx.model.TOMaintenanceTicket;
 // Handles enum values and other imports
import org.example.assetuijavafx.model.MaintenanceTicket.*;

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
	@FXML
	private ChoiceBox<TimeEstimate> choiceBoxTimeToResolve;
	@FXML
	private ChoiceBox<PriorityLevel> choiceBoxPriority;

    @FXML
    private Button buttonCancel;

    @FXML
    private Text textError;

    private TOMaintenanceTicket currentMaintenanceTicket;

	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		
		choiceBoxTimeToResolve.getItems().addAll(TimeEstimate.values());
		choiceBoxPriority.getItems().addAll(PriorityLevel.values());
	}

    public void setData(TOMaintenanceTicket maintenanceTicket) {
        this.currentMaintenanceTicket = maintenanceTicket;

    	inputFieldId.setText(String.valueOf(maintenanceTicket.getId()));
    	inputFieldRaisedOnDate.setValue(maintenanceTicket.getRaisedOnDate().toLocalDate());
    	inputFieldDescription.setText(maintenanceTicket.getDescription());
		choiceBoxTimeToResolve.setValue(TimeEstimate.valueOf(maintenanceTicket.getTimeToResolve()));
		choiceBoxPriority.setValue(PriorityLevel.valueOf(maintenanceTicket.getPriority()));

		// Mark Key field as Disabled
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
			PriorityLevel priority = choiceBoxPriority.getValue();
			String savedStatus;
			if (currentMaintenanceTicket != null) {
        	    savedStatus = AssetPlusController.updateMaintenanceTicket(
					id, id, raisedOnDate, description, timeToResolve.toString(), priority.toString()
				);
			} else {
        	    savedStatus = AssetPlusController.addMaintenanceTicket(
					id, raisedOnDate, description, timeToResolve.toString(), priority.toString()
				);
			}

			// Validate Controller response
			if (!savedStatus.isEmpty()) throw new InvalidInputException(savedStatus);
			else {
                textError.setText("Successfully saved item. Redirecting back to table...");
                FormHelper.triggerAfterDelay(() -> buttonCancel.fireEvent(new PageSwitchEvent<>("DISPLAY")), 2);
			}

		} catch (RuntimeException e) {
            textError.setText(FormHelper.formatTextMessage(e));
		}
    }

    public void onCancel(ActionEvent actionEvent) {
        buttonCancel.fireEvent(new PageSwitchEvent<>("DISPLAY"));
    }

}
