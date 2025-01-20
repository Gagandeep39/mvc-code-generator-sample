package org.example.assetuijavafx.fxml.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.assetuijavafx.controllers.AssetPlusController;
import org.example.assetuijavafx.model.TOAssetType;

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
    private CheckBox checkboxVisible;

    private TOAssetType currentAssetType;

    public void setData(TOAssetType type) {
        this.currentAssetType = type;
        inputFieldName.setText(type.getName());
        inputFieldImage.setText(type.getImage());
        inputFieldExpectedLifeSpan.setText(String.valueOf(type.getExpectedLifeSpan()));
        checkboxVisible.setSelected(type.getVisible());
        System.out.println("Triggered onSave");
    }

    @FXML
    private void onSave(ActionEvent event) {

        // Perform Actual mapping
        String name = inputFieldName.getText();
        String image = inputFieldImage.getText();
        int expectedLifeSpan = Integer.parseInt(inputFieldExpectedLifeSpan.getText());
        boolean visible = checkboxVisible.isSelected();

        if (currentAssetType != null) {
            AssetPlusController.updateAssetType(name, name, expectedLifeSpan, image);
        } else {
            AssetPlusController.addAssetType(name, expectedLifeSpan, image, visible);
        }
        onCancel(event);
    }

    @FXML
    private void onCancel(ActionEvent event) {
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();
    }
}
