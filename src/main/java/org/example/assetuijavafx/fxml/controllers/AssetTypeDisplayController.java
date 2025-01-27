package org.example.assetuijavafx.fxml.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.assetuijavafx.controllers.AssetPlusController;
import org.example.assetuijavafx.fxml.utils.PageSwitchEvent;
import org.example.assetuijavafx.model.AssetType;
import org.example.assetuijavafx.model.TOAssetType;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static org.example.assetuijavafx.application.AssetPlusApplication.PACKAGE_ID;

public class AssetTypeDisplayController implements Initializable {

    @FXML
    public VBox parentContainer, childContainer;

    @FXML
    private Button buttonAdd;

    protected String multiplicity = "*";
    protected TOAssetType assetType = null;
    protected List<TOAssetType> assetTypeList = null;
    private String activeFxml = "AssetTypeTable.fxml";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadFXML();
    }

    public void onAddAssetType(ActionEvent event) {
        parentContainer.fireEvent(new PageSwitchEvent<>("ADD"));
    }

    public FXMLLoader loadFXML() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(PACKAGE_ID.concat(activeFxml)));
        try {
            Parent child = loader.load();
            childContainer.getChildren().clear();
            childContainer.getChildren().add(child);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return loader;
    }

    public <T> void setData(String multiplicity, T data) {
        this.multiplicity = multiplicity;
        buttonAdd.setVisible(false);
        buttonAdd.setManaged(false);
        if (this.multiplicity.equals("*")) {
            this.activeFxml = "AssetTypeTable.fxml";
            this.assetTypeList = (List<TOAssetType>) data;
            this.assetType = null;
            AssetTypeTableController controller = loadFXML().getController();
            controller.setData(assetTypeList);
        } else {
            this.activeFxml = "AssetTypeTableOne.fxml";
            this.assetType = (TOAssetType) data;
            this.assetTypeList = null;
            AssetTypeTableOneController controller = loadFXML().getController();
            controller.setData(assetType);
        }
    }

}
