package org.example.assetuijavafx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ValidationLabelController {

    @FXML
    Label labelError;

    public void showValidationError(String error) {
        labelError.setText(error);
        labelError.setVisible(true);
    }

    public void hideValidationError() {
        labelError.setVisible(false);
    }
}
