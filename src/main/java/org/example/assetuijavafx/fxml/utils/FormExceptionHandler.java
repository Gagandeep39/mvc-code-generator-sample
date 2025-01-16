package org.example.assetuijavafx.fxml.utils;

import javafx.scene.control.Alert.*;

import static org.example.assetuijavafx.fxml.layouts.AlertWindow.showAlert;

public class FormExceptionHandler {

    public static void handleException(Exception e) {
        String message = switch (e) {
            case NumberFormatException ne -> "Invalid input number";
            case InvalidInputException ie -> ie.getMessage();
            default -> "An unexpected error occurred: " + e.getMessage();
        };
        showAlert(message, AlertType.ERROR);
    }

}
