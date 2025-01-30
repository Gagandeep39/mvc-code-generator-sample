package org.example.assetuijavafx.fxml.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.example.assetuijavafx.controllers.AssetPlusController;
import org.example.assetuijavafx.fxml.utils.PageSwitchEvent;
import org.example.assetuijavafx.model.NavigationState;
import org.example.assetuijavafx.model.TOAssetType;
import org.example.assetuijavafx.model.TOSpecificAsset;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AssetTypeDisplayOneController implements Initializable {

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

    @FXML
    private Button buttonSpecificAssets;

    private TOAssetType assetType;

    public void populateData() {
        textName.setText(assetType.getName());
        textExpectedLifeSpan.setText(String.valueOf(assetType.getExpectedLifeSpan()));
        textVisible.setText(String.valueOf(assetType.getVisible()));
        textImage.setText(assetType.getImage());
    }

    @FXML
    public void redirectToSpecificAssets(ActionEvent event) {
        NavigationState<List<TOSpecificAsset>> state = new NavigationState<>("SpecificAssets", "REDIRECT_DISPLAY", "SpecificAssetDisplay.fxml");
        state.setMultiplicity("*");
        List<TOSpecificAsset> specificAssetList = assetType.getAssetNumberForSpecificAssets().stream().map(AssetPlusController::getSpecificAsset).toList();
        state.setData(specificAssetList);
        buttonSpecificAssets.fireEvent(new PageSwitchEvent(state));
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
        NavigationState<TOAssetType> state = new NavigationState("Update AssetType", "UPDATE", "AssetTypeForm.fxml");
        state.setData(assetType);
        buttonUpdate.fireEvent(new PageSwitchEvent(state));
    }


}
