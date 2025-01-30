package org.example.assetuijavafx.fxml.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.example.assetuijavafx.fxml.utils.PageSwitchEvent;
import org.example.assetuijavafx.model.NavigationState;
import org.example.assetuijavafx.model.PageType;
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
        super.loadFXML("AssetTypeDisplayMany.fxml");
    }

    public void onAddAssetType(ActionEvent event) {
        parentContainer.fireEvent(new PageSwitchEvent(new NavigationState<>("Add AssetType", PageType.ADD, "AssetTypeForm.fxml")));
    }

    public <T> void setData(String multiplicity, T data) {
        buttonAdd.setVisible(false);
        buttonAdd.setManaged(false);
        if (multiplicity.equals("*")) {
            AssetTypeDisplayManyController controller = super.loadFXML("AssetTypeDisplayMany.fxml").getController();
            controller.setData((List<TOAssetType>) data);
        } else {
            AssetTypeDisplayOneController controller = super.loadFXML("AssetTypeDisplayOne.fxml").getController();
            controller.setData((TOAssetType) data);
        }
    }
}

