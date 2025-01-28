package org.example.assetuijavafx.fxml.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.example.assetuijavafx.fxml.utils.PageSwitchEvent;
import org.example.assetuijavafx.model.NavigationState;
import org.example.assetuijavafx.model.TOAssetType;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AssetTypeDisplayController extends BaseDisplayController implements Initializable {

    @FXML
    public VBox parentContainer, childContainer;
    protected String multiplicity = "*";
    @FXML
    private Button buttonAdd;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.activeFxml = "AssetTypeTable.fxml";
        super.childContainer = childContainer;
        super.loadFXML();
    }

    public void onAddAssetType(ActionEvent event) {
        parentContainer.fireEvent(new PageSwitchEvent(new NavigationState<>("Add AssetType", "ADD", "AssetTypeForm.fxml")));
    }

    public <T> void setData(String multiplicity, T data) {
        this.multiplicity = multiplicity;
        buttonAdd.setVisible(false);
        buttonAdd.setManaged(false);
        if (this.multiplicity.equals("*")) {
            super.activeFxml = "AssetTypeTable.fxml";
            AssetTypeTableController controller = super.loadFXML().getController();
            controller.setData((List<TOAssetType>) data);
        } else {
            super.activeFxml = "AssetTypeTableOne.fxml";
            AssetTypeTableOneController controller = super.loadFXML().getController();
            controller.setData((TOAssetType) data);
        }
    }

}

