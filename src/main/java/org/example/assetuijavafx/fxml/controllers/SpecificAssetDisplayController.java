package org.example.assetuijavafx.fxml.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.example.assetuijavafx.fxml.utils.PageSwitchEvent;
import org.example.assetuijavafx.model.NavigationState;
import org.example.assetuijavafx.model.TOAssetType;
import org.example.assetuijavafx.model.TOSpecificAsset;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static org.example.assetuijavafx.application.AssetPlusApplication.PACKAGE_ID;

public class SpecificAssetDisplayController implements Initializable {
    @FXML
    public VBox parentContainer, childContainer;

    @FXML
    private Button buttonAdd;

    protected String multiplicity = "*";
    protected TOSpecificAsset specificAsset = null;
    protected List<TOSpecificAsset> specificAssetList = null;
    private String activeFxml = "SpecificAssetTable.fxml";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadFXML();
    }

    public void onAddSpecificAsset(ActionEvent event) {
        parentContainer.fireEvent(new PageSwitchEvent(
                new NavigationState<>("Add SpecificAsset", "ADD", "SpecificAssetForm.fxml")
        ));
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
        System.out.println("Set data - specific asset");
        this.multiplicity = multiplicity;
        buttonAdd.setVisible(false);
        buttonAdd.setManaged(false);
        if (this.multiplicity.equals("*")) {
            this.activeFxml = "SpecificAssetTable.fxml";
            this.specificAssetList = (List<TOSpecificAsset>) data;
            this.specificAsset = null;
            SpecificAssetTableController controller = loadFXML().getController();
            controller.setData(specificAssetList);
        } else {
            this.activeFxml = "SpecificAssetTableOne.fxml";
            this.specificAsset = (TOSpecificAsset) data;
            this.specificAssetList = null;
            SpecificAssetTableOneController controller = loadFXML().getController();
            controller.setData(specificAsset);
        }
    }

}
