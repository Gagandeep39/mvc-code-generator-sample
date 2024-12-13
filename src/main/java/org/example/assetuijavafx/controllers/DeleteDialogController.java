package org.example.assetuijavafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class DeleteDialogController<T> {

    Consumer<T> confirmAction;

    @FXML
    Button buttonCancel;

    public void setAction(Consumer<T> confirmAction) {
        this.confirmAction = confirmAction;
    }

    @FXML
    private void delete(ActionEvent event) {
        confirmAction.accept(null);
        cancel(event);
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();
    }
}
