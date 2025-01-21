package org.example.assetuijavafx.fxml.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.example.assetuijavafx.controllers.AssetPlusController;
import org.example.assetuijavafx.fxml.utils.FormExceptionHandler;
import org.example.assetuijavafx.fxml.utils.FormHelper;
import org.example.assetuijavafx.fxml.utils.InvalidInputException;
import org.example.assetuijavafx.fxml.utils.PageSwitchEvent;
import org.example.assetuijavafx.model.TOAssetType;

import java.util.Timer;

import static org.example.assetuijavafx.fxml.layouts.AlertWindow.showSuccessAlert;

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

    @FXML
    private Text textError;

    private TOAssetType currentAssetType;

    public void setData(TOAssetType type) {
        this.currentAssetType = type;
        inputFieldName.setText(type.getName());
        inputFieldImage.setText(type.getImage());
        inputFieldExpectedLifeSpan.setText(String.valueOf(type.getExpectedLifeSpan()));
        checkboxVisible.setSelected(type.getVisible());
        System.out.println("Triggered onSave");
    }

    public void onCancel(ActionEvent actionEvent) {
        buttonCancel.fireEvent(new PageSwitchEvent<>("DISPLAY"));
    }

    @FXML
    private void onSave(ActionEvent event) {
        try {
            String name = inputFieldName.getText();
            int expectedLifeSpan = Integer.parseInt(inputFieldExpectedLifeSpan.getText());
            String image = inputFieldImage.getText();
            boolean visible = checkboxVisible.isSelected();
            String savedStatus;
            if (currentAssetType != null) {
                savedStatus = AssetPlusController.updateAssetType(
                        name, name, expectedLifeSpan, image
                );
            } else {
                savedStatus = AssetPlusController.addAssetType(
                        name, expectedLifeSpan, image, visible
                );
            }

            // Validate Controller response
            if (!savedStatus.isEmpty()) throw new InvalidInputException(savedStatus);
            else {
                textError.setText("Successfully saved item");
                FormHelper.triggerAfterDelay(() -> buttonCancel.fireEvent(new PageSwitchEvent<>("DISPLAY")), 2);
            }

        } catch (RuntimeException e) {
            textError.setText(FormHelper.formatTextMessage(e));
        }
    }


}
