package org.example.assetuijavafx.fxml.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import org.example.assetuijavafx.controllers.AssetPlusController;
import org.example.assetuijavafx.fxml.utils.*;
import org.example.assetuijavafx.model.TOSpecificAsset;
 // Handles enum values and other imports
import org.example.assetuijavafx.model.SpecificAsset.*;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class SpecificAssetFormController implements Initializable {

	@FXML
	private TextField inputFieldAssetNumber;
	@FXML
	private TextField inputFieldFloorNumber;
	@FXML
	private TextField inputFieldRoomNumber;
	@FXML
	private DatePicker inputFieldPurchaseDate;

    @FXML
    private Button buttonCancel;

    @FXML
    private Text textError;

    private TOSpecificAsset currentSpecificAsset;

	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		
	}

    public void setData(TOSpecificAsset specificAsset) {
        this.currentSpecificAsset = specificAsset;

    	inputFieldAssetNumber.setText(String.valueOf(specificAsset.getAssetNumber()));
    	inputFieldFloorNumber.setText(String.valueOf(specificAsset.getFloorNumber()));
    	inputFieldRoomNumber.setText(String.valueOf(specificAsset.getRoomNumber()));
    	inputFieldPurchaseDate.setValue(specificAsset.getPurchaseDate().toLocalDate());

		// Mark Key field as Disabled
		inputFieldAssetNumber.setDisable(true);

        System.out.println("Preloaded SpecificAsset data");
    }

    @FXML
    private void onSave(ActionEvent event) {
		try {
			int assetNumber = Integer.parseInt(inputFieldAssetNumber.getText());
			int floorNumber = Integer.parseInt(inputFieldFloorNumber.getText());
			int roomNumber = Integer.parseInt(inputFieldRoomNumber.getText());
			Date purchaseDate = Date.valueOf(inputFieldPurchaseDate.getValue());
			String savedStatus;
			if (currentSpecificAsset != null) {
        	    savedStatus = AssetPlusController.updateSpecificAsset(
					assetNumber, assetNumber, floorNumber, roomNumber, purchaseDate
				);
			} else {
        	    savedStatus = AssetPlusController.addSpecificAsset(
					assetNumber, floorNumber, roomNumber, purchaseDate
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
