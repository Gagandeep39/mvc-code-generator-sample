package org.example.assetuijavafx.fxml.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.example.assetuijavafx.fxml.utils.PageSwitchEvent;
import org.example.assetuijavafx.model.NavigationState;
import org.example.assetuijavafx.model.TOSpecificAsset;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SpecificAssetDisplayController extends BaseDisplayController implements Initializable {
    @FXML
    public VBox parentContainer, childContainer;
    protected String multiplicity = "*";
    @FXML
    private Button buttonAdd;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.childContainer = childContainer;
        super.activeFxml = "SpecificAssetTable.fxml";
        super.loadFXML();
    }

    public void onAddSpecificAsset(ActionEvent event) {
        parentContainer.fireEvent(new PageSwitchEvent(new NavigationState<>("Add SpecificAsset", "ADD", "SpecificAssetForm.fxml")));
    }

    public <T> void setData(String multiplicity, T data) {
        this.multiplicity = multiplicity;
        buttonAdd.setVisible(false);
        buttonAdd.setManaged(false);
        if (this.multiplicity.equals("*")) {
            super.activeFxml = "SpecificAssetTable.fxml";
            SpecificAssetTableController controller = super.loadFXML().getController();
            controller.setData((List<TOSpecificAsset>) data);
        } else {
            super.activeFxml = "SpecificAssetTableOne.fxml";
            SpecificAssetTableOneController controller = super.loadFXML().getController();
            controller.setData((TOSpecificAsset) data);
        }
    }

}
