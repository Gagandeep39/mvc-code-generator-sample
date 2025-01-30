package org.example.assetuijavafx.fxml.controllers;

import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import org.example.assetuijavafx.controllers.AssetPlusController;
import org.example.assetuijavafx.model.*;
// Handles enum values and other imports
import org.example.assetuijavafx.fxml.layouts.ButtonCell;
import org.example.assetuijavafx.fxml.utils.PageSwitchEvent;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static org.example.assetuijavafx.application.AssetPlusApplication.PACKAGE_ID;

public class AssetTypeDisplayManyController implements Initializable  {

    @FXML
    private VBox parentContainer;

	@FXML
	private TableColumn<TOAssetType, String> columnName;
	@FXML
	private TableColumn<TOAssetType, Integer> columnExpectedLifeSpan;
	@FXML
	private TableColumn<TOAssetType, Boolean> columnVisible;
	@FXML
	private TableColumn<TOAssetType, String> columnImage;

    @FXML
    private TableColumn<TOAssetType, Button> columnSpecificAssets;
	@FXML
	private TableColumn<TOAssetType, Button> columnDelete;

	@FXML
	private TableColumn<TOAssetType, Button> columnUpdate;

	@FXML
	private TableView<TOAssetType> table;

    public List<TOAssetType> assetTypeList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        assetTypeList = AssetPlusController.getAssetTypesOfAssetPlus();
        if (assetTypeList == null) {
            assetTypeList = Collections.emptyList();
        }
//        System.out.println(assetTypeList.stream().findFirst().get().getSpecificAssets());
        populateData();
    }

    public void setData(List<TOAssetType> assetTypeList) {
        this.assetTypeList = assetTypeList;
        populateData();
    }

    public void populateData() {
        ObservableList<TOAssetType> list = FXCollections.observableArrayList(assetTypeList);

        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnExpectedLifeSpan.setCellValueFactory(new PropertyValueFactory<>("expectedLifeSpan"));
        columnVisible.setCellValueFactory(new PropertyValueFactory<>("visible"));
        columnImage.setCellValueFactory(new PropertyValueFactory<>("image"));

        columnSpecificAssets.setCellFactory(column -> new ButtonCell<>("Details", this::redirectToSpecificAssets));
        columnUpdate.setCellFactory(column -> new ButtonCell<>("Update", this::updateAssetType));
        columnDelete.setCellFactory(column -> new ButtonCell<>("Delete", this::showDialogDeleteAssetType));
        table.setItems(list);
    }

    public void redirectToSpecificAssets(TOAssetType assetType) {
        NavigationState<List<TOSpecificAsset>> state = new NavigationState<>("SpecificAssets", PageType.REDIRECT_DISPLAY, "SpecificAssetDisplay.fxml");
        state.setMultiplicity("*");
        List<TOSpecificAsset> specificAssetList = assetType.getAssetNumberForSpecificAssets().stream().map(AssetPlusController::getSpecificAsset).toList();
        state.setData(specificAssetList);
        parentContainer.fireEvent(new PageSwitchEvent(state));
    }

    protected void updateAssetType(TOAssetType assetType) {
        NavigationState<TOAssetType> state = new NavigationState<>("Update AssetType", PageType.UPDATE, "AssetTypeForm.fxml");
        state.setData(assetType);
        parentContainer.fireEvent(new PageSwitchEvent(state));
    }

    protected void showDialogDeleteAssetType(TOAssetType assetType) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PACKAGE_ID + "DeleteDialog.fxml"));
            Parent parent = fxmlLoader.load();
            // Get controller
            DeleteDialogController<TOAssetType> controller = fxmlLoader.getController();
            controller.setAction(a -> deleteAssetType(assetType));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(parent));
            stage.showAndWait();
            populateData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteAssetType(TOAssetType assetType) {
        AssetPlusController.removeAssetType(assetType.getName());
        System.out.println("Deleted AssetType Successfully");
    }

}
