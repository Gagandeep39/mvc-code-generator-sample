package org.example.assetuijavafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.assetuijavafx.models.AssetType;

public class AssetTypeFormController {

    @FXML
    private Button buttonCancel;

    @FXML
    private TextField inputFieldExpectedLifeSpan;

    @FXML
    private TextField inputFieldImage;

    @FXML
    private TextField inputFieldName;

    private AssetType currentAssetType;

    public void setData(AssetType type) {
        this.currentAssetType = type;
        inputFieldName.setText(type.getName());
        inputFieldImage.setText(type.getImage());
        inputFieldExpectedLifeSpan.setText(String.valueOf(type.getExpectedLifeSpan()));
        System.out.println("Triggered onSave");
    }

    @FXML
    private void onSave(ActionEvent event) {

        // Perform Actual mapping
        String name = inputFieldName.getText();
        String image = inputFieldImage.getText();
        int expectedLifeSpan = Integer.parseInt(inputFieldExpectedLifeSpan.getText());

        if (currentAssetType != null) {
            currentAssetType.setExpectedLifeSpan(expectedLifeSpan);
            currentAssetType.setImage(image);
            currentAssetType.setName(name);
        } else {
            AssetType tempAssetType = new AssetType();
            tempAssetType.setName(name);
            tempAssetType.setImage(image);
            tempAssetType.setExpectedLifeSpan(expectedLifeSpan);
            AssetType.addAssetType(tempAssetType);
        }
        onCancel(event);
    }

    @FXML
    private void onCancel(ActionEvent event) {
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();
    }
}
