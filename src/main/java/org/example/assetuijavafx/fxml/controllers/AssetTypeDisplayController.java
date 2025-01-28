package org.example.assetuijavafx.fxml.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
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
    @FXML
    private Button buttonAdd;

    @Override
    protected Pane getChildContainer() {
        return childContainer;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.loadFXML("AssetTypeTable.fxml");
    }

    public void onAddAssetType(ActionEvent event) {
        parentContainer.fireEvent(new PageSwitchEvent(new NavigationState<>("Add AssetType", "ADD", "AssetTypeForm.fxml")));
    }

    public <T> void setData(String multiplicity, T data) {
        multiplicity = multiplicity;
        buttonAdd.setVisible(false);
        buttonAdd.setManaged(false);
        if (multiplicity.equals("*")) {
            AssetTypeTableController controller = super.loadFXML("AssetTypeTable.fxml").getController();
            controller.setData((List<TOAssetType>) data);
        } else {
            AssetTypeTableOneController controller = super.loadFXML("AssetTypeTableOne.fxml").getController();
            controller.setData((TOAssetType) data);
        }
    }
}

