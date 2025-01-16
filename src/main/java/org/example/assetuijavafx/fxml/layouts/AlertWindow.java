package org.example.assetuijavafx.fxml.layouts;

import javafx.scene.control.*;

public class AlertWindow {

    public static void showSuccessAlert(String message) {
        showAlert(message, Alert.AlertType.INFORMATION);
    }

    public static void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        String title = switch (type) {
            case Alert.AlertType.INFORMATION -> "Information";
            case Alert.AlertType.WARNING -> "Warning";
            case Alert.AlertType.ERROR -> "Error";
            case Alert.AlertType.CONFIRMATION -> "Confirmation";
            default -> "";
        };
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
