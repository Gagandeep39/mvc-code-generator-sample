package org.example.assetuijavafx.fxml.controllers;

import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import org.example.assetuijavafx.controllers.AssetPlusController;
import org.example.assetuijavafx.model.AssetType;
import org.example.assetuijavafx.model.TOAssetType;
 // Handles enum values and other imports
import org.example.assetuijavafx.model.AssetType.*;
import org.example.assetuijavafx.fxml.layouts.ButtonCell;
import org.example.assetuijavafx.fxml.utils.PageSwitchEvent;
import org.example.assetuijavafx.model.TOSpecificAsset;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.*;

import static org.example.assetuijavafx.application.AssetPlusApplication.PACKAGE_ID;

public class AssetTypeTableController implements Initializable  {

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
        columnSpecificAssets.setCellFactory(column -> new ButtonCell<>("Details", this::redirectToAssetType));
        columnUpdate.setCellFactory(column -> new ButtonCell<>("Update", this::updateAssetType));
        columnDelete.setCellFactory(column -> new ButtonCell<>("Delete", this::showDialogDeleteAssetType));
        table.setItems(list);
    }

    public void redirectToAssetType(TOAssetType assetType) {
        parentContainer.fireEvent(new PageSwitchEvent<>("REDIRECT", assetType.getSpecificAssets()));
    }

    protected void updateAssetType(TOAssetType assetType) {
        parentContainer.fireEvent(new PageSwitchEvent<>("UPDATE", assetType));
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
