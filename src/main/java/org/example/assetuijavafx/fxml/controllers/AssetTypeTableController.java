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

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.*;

import static org.example.assetuijavafx.application.AssetPlusApplication.PACKAGE_ID;

public class AssetTypeTableController implements Initializable  {


    @FXML
    public VBox parentContainer;
	@FXML
	private TableColumn<TOAssetType, String> columnName;
	@FXML
	private TableColumn<TOAssetType, Integer> columnExpectedLifeSpan;
	@FXML
	private TableColumn<TOAssetType, Boolean> columnVisible;
	@FXML
	private TableColumn<TOAssetType, String> columnImage;
	
	
	@FXML
	private TableColumn<TOAssetType, Button> columnDelete;

	@FXML
	private TableColumn<TOAssetType, Button> columnUpdate;

	@FXML
	private TableView<TOAssetType> table;

    public void initializeTable() {
		List<TOAssetType> toList = AssetPlusController.getAssetTypesOfAssetPlus();
        if (toList == null) {
            toList = Collections.emptyList();
        }
        ObservableList<TOAssetType> list = FXCollections.observableArrayList(toList);

		columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		columnExpectedLifeSpan.setCellValueFactory(new PropertyValueFactory<>("expectedLifeSpan"));
		columnVisible.setCellValueFactory(new PropertyValueFactory<>("visible"));
		columnImage.setCellValueFactory(new PropertyValueFactory<>("image"));

        columnUpdate.setCellFactory(column -> new ButtonCell<>("Update", this::updateAssetType));
        columnDelete.setCellFactory(column -> new ButtonCell<>("Delete", this::showDialogDeleteAssetType));
        table.setItems(list);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
		initializeTable();
    }

    private void updateAssetType(TOAssetType assetType) {
		parentContainer.fireEvent(new PageSwitchEvent<>("UPDATE", assetType));
    }

    public void onAddAssetType(ActionEvent event) {
        parentContainer.fireEvent(new PageSwitchEvent<>("ADD"));
    }

    private void showDialogDeleteAssetType(TOAssetType assetType) {
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
            initializeTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteAssetType(TOAssetType assetType) {
        AssetPlusController.removeAssetType(assetType.getName());
        System.out.println("Deleted AssetType Successfully");
    }

}
