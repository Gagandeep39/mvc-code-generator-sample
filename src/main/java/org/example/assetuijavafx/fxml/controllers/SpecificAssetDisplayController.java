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
    private VBox parentContainer, childContainer;
    @FXML
    private Button buttonAdd;

    @Override
    public VBox getChildContainer() {
        return childContainer;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.loadFXML("SpecificAssetTable.fxml");
    }

    public void onAddSpecificAsset(ActionEvent event) {
        parentContainer.fireEvent(new PageSwitchEvent(new NavigationState<>("Add SpecificAsset", "ADD", "SpecificAssetForm.fxml")));
    }

    public <T> void setData(String multiplicity, T data) {
        buttonAdd.setVisible(false);
        buttonAdd.setManaged(false);
        if (multiplicity.equals("*")) {
            SpecificAssetTableController controller = super.loadFXML("SpecificAssetTable.fxml").getController();
            controller.setData((List<TOSpecificAsset>) data);
        } else {
            SpecificAssetTableOneController controller = super.loadFXML("SpecificAssetTableOne.fxml").getController();
            controller.setData((TOSpecificAsset) data);
        }
    }

}
