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
import org.example.assetuijavafx.model.SpecificAsset;
import org.example.assetuijavafx.model.TOAssetType;
import org.example.assetuijavafx.model.TOSpecificAsset;
 // Handles enum values and other imports
import org.example.assetuijavafx.model.SpecificAsset.*;
import org.example.assetuijavafx.fxml.layouts.ButtonCell;
import org.example.assetuijavafx.fxml.utils.PageSwitchEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.*;

import static org.example.assetuijavafx.application.AssetPlusApplication.PACKAGE_ID;

public class SpecificAssetTableController implements Initializable  {


    @FXML
    public VBox parentContainer;
	@FXML
	private TableColumn<TOSpecificAsset, Integer> columnAssetNumber;
	@FXML
	private TableColumn<TOSpecificAsset, Integer> columnFloorNumber;
	@FXML
	private TableColumn<TOSpecificAsset, Integer> columnRoomNumber;
	@FXML
	private TableColumn<TOSpecificAsset, Date> columnPurchaseDate;

    @FXML
    private TableColumn<TOSpecificAsset, Button> columnDetailsAssetType;
	
	@FXML
	private TableColumn<TOSpecificAsset, Button> columnDelete;

	@FXML
	private TableColumn<TOSpecificAsset, Button> columnUpdate;

	@FXML
	private TableView<TOSpecificAsset> table;

    private List<TOSpecificAsset> specificAssetList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        specificAssetList = AssetPlusController.getSpecificAssetsOfAssetPlus();
        if (specificAssetList == null) {
            specificAssetList = Collections.emptyList();
        }
        populateData();
    }

    public void setData(List<TOSpecificAsset> specificAssetList) {
        this.specificAssetList = specificAssetList;
        populateData();
    }


    public void populateData() {
        ObservableList<TOSpecificAsset> list = FXCollections.observableArrayList(specificAssetList);

		columnAssetNumber.setCellValueFactory(new PropertyValueFactory<>("assetNumber"));
		columnFloorNumber.setCellValueFactory(new PropertyValueFactory<>("floorNumber"));
		columnRoomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
		columnPurchaseDate.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));
        // 1..1 mapping
        columnDetailsAssetType.setCellFactory(column ->  new ButtonCell<>("Details", this::redirectToAssetType));

        columnUpdate.setCellFactory(column -> new ButtonCell<>("Update", this::updateSpecificAsset));
        columnDelete.setCellFactory(column -> new ButtonCell<>("Delete", this::showDialogDeleteSpecificAsset));
        table.setItems(list);
    }


    public void redirectToAssetType(TOSpecificAsset specificAsset) {
        parentContainer.fireEvent(new PageSwitchEvent<>("REDIRECT", specificAsset.getAssetType()));
    }

    private void updateSpecificAsset(TOSpecificAsset specificAsset) {
		parentContainer.fireEvent(new PageSwitchEvent<>("UPDATE", specificAsset));
    }

    private void showDialogDeleteSpecificAsset(TOSpecificAsset specificAsset) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PACKAGE_ID + "DeleteDialog.fxml"));
            Parent parent = fxmlLoader.load();
            // Get controller
            DeleteDialogController<TOSpecificAsset> controller = fxmlLoader.getController();
            controller.setAction(a -> deleteSpecificAsset(specificAsset));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(parent));
            stage.showAndWait();
            populateData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteSpecificAsset(TOSpecificAsset specificAsset) {
        AssetPlusController.removeSpecificAsset(specificAsset.getAssetNumber());
        System.out.println("Deleted SpecificAsset Successfully");
    }

}
