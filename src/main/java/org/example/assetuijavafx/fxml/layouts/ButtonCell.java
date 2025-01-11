package org.example.assetuijavafx.fxml.layouts;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

import java.util.function.Consumer;

public class ButtonCell<S> extends TableCell<S, Button> {

    private final Button button;
    private final Consumer<S> action;

    public ButtonCell(String buttonText,  Consumer<S> action) {
        this.button = new Button(buttonText);
        this.action = action;
    }

    @Override
    protected void updateItem(Button item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            button.setOnAction(e -> {
                S data  = getTableRow().getItem();
                if(data != null)
                    action.accept(data);
            });
            setGraphic(button);
        }

    }
}