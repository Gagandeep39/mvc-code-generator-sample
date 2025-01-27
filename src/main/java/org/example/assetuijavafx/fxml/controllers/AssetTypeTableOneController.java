package org.example.assetuijavafx.fxml.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.example.assetuijavafx.fxml.utils.PageSwitchEvent;
import org.example.assetuijavafx.model.TOAssetType;

import java.net.URL;
import java.util.ResourceBundle;

public class AssetTypeTableOneController  implements Initializable {

    @FXML
    private Text textName;

    @FXML
    private Text textExpectedLifeSpan;

    @FXML
    private Text textVisible;

    @FXML
    private Text textImage;

    @FXML
    private Button buttonUpdate;

    private TOAssetType assetType;

    public void populateData() {
        textName.setText(assetType.getName());
        textExpectedLifeSpan.setText(String.valueOf(assetType.getExpectedLifeSpan()));
        textVisible.setText(String.valueOf(assetType.getVisible()));
        textImage.setText(assetType.getImage());
    }

    public void setData(TOAssetType assetType) {
        this.assetType = assetType;
        populateData();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void updateAssetType(ActionEvent event) {
        buttonUpdate.fireEvent(new PageSwitchEvent<>("UPDATE", assetType));
    }


}
