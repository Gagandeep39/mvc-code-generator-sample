package org.example.assetuijavafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.assetuijavafx.models.AssetType;
import org.example.assetuijavafx.utilities.ValidationHelper;

public class AssetTypeFormController {

    @FXML
    private Button buttonCancel;

    @FXML
    private TextField inputFieldExpectedLifeSpan;

    @FXML
    private TextField inputFieldImage;

    @FXML
    private TextField inputFieldName;

    @FXML
    private ValidationLabelController validationLabelController;

    private AssetType currentAssetType;

    public void setData(AssetType type) {
        this.currentAssetType = type;
        inputFieldName.setText(type.getName());
        inputFieldImage.setText(type.getImage());
        inputFieldExpectedLifeSpan.setText(String.valueOf(type.getExpectedLifeSpan()));
    }

    @FXML
    private void onSave(ActionEvent event) {
        // Check for Validations
        String errorMessage = checkForErrors();
        if (!errorMessage.isEmpty()) {
            validationLabelController.showValidationError(errorMessage);
            return;
        }
        validationLabelController.hideValidationError();

        // Perform Actual mapping
        String name = inputFieldName.getText();
        String image = inputFieldImage.getText();
        int expectedLifeSpan = Integer.parseInt(inputFieldExpectedLifeSpan.getText());

        if (currentAssetType != null) {
            currentAssetType.setExpectedLifeSpan(expectedLifeSpan);
            currentAssetType.setImage(image);
            currentAssetType.setName(name);
        } else {
            AssetType.addAssetType(new AssetType(name, expectedLifeSpan, image));
        }
        onCancel(event);
    }

    @FXML
    private void onCancel(ActionEvent event) {
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();
    }

    public String checkForErrors() {
        String errorMessage = "";
        if (!ValidationHelper.isValidNumber(inputFieldExpectedLifeSpan.getText())) {
            errorMessage = "Please enter a valid number";
        }
        if (!ValidationHelper.isValidString(inputFieldName.getText())) {
            errorMessage = "Text Fields cannot be blank!";
        }
        if (!ValidationHelper.isValidString(inputFieldImage.getText())) {
            errorMessage = "Text Fields cannot be blank!";
        }
        return errorMessage;
    }
}
