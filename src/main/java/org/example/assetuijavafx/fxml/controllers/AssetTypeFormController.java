package org.example.assetuijavafx.fxml.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import org.example.assetuijavafx.controllers.AssetPlusController;
import org.example.assetuijavafx.fxml.utils.*;
import org.example.assetuijavafx.model.NavigationState;
import org.example.assetuijavafx.model.TOAssetType;
 // Handles enum values and other imports
import org.example.assetuijavafx.model.AssetType.*;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class AssetTypeFormController implements Initializable {

	@FXML
	private TextField inputFieldName;
	@FXML
	private TextField inputFieldExpectedLifeSpan;
	@FXML
	private CheckBox checkBoxVisible;
	@FXML
	private TextField inputFieldImage;

    @FXML
    private Button buttonCancel;

    @FXML
    private Text textError;

    private TOAssetType currentAssetType;

	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		
	}

    public void setData(TOAssetType assetType) {
        this.currentAssetType = assetType;

    	inputFieldName.setText(assetType.getName());
    	inputFieldExpectedLifeSpan.setText(String.valueOf(assetType.getExpectedLifeSpan()));
    	inputFieldImage.setText(assetType.getImage());
		checkBoxVisible.setSelected(assetType.getVisible());

		// Mark Key field as Disabled
		inputFieldName.setDisable(true);

        System.out.println("Preloaded AssetType data");
    }

    @FXML
    private void onSave(ActionEvent event) {
		try {
			String name = inputFieldName.getText();
			int expectedLifeSpan = Integer.parseInt(inputFieldExpectedLifeSpan.getText());
			boolean visible = checkBoxVisible.isSelected();
			String image = inputFieldImage.getText();
			String savedStatus;
			if (currentAssetType != null) {
        	    savedStatus = AssetPlusController.updateAssetType(
					name, name, expectedLifeSpan, image, visible
				);
			} else {
        	    savedStatus = AssetPlusController.addAssetType(
					name, expectedLifeSpan, image, visible
				);
			}

			// Validate Controller response
			if (!savedStatus.isEmpty()) throw new InvalidInputException(savedStatus);
			else {
                textError.setText("Successfully saved item. Redirecting back to table...");
                FormHelper.triggerAfterDelay(() -> buttonCancel.fireEvent(new PageSwitchEvent(
						new NavigationState<>("AssetType", "DISPLAY", "AssetTypeDisplay.fxml")
				)), 2);
			}

		} catch (RuntimeException e) {
            textError.setText(FormHelper.formatTextMessage(e));
		}
    }

    public void onCancel(ActionEvent actionEvent) {
        buttonCancel.fireEvent(new PageSwitchEvent(
				new NavigationState<>("AssetType", "DISPLAY", "AssetTypeDisplay.fxml")
		));
    }

}
