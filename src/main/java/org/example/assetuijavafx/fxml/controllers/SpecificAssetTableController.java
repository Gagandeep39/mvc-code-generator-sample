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
	private TableColumn<TOSpecificAsset, Button> columnDelete;

	@FXML
	private TableColumn<TOSpecificAsset, Button> columnUpdate;

	@FXML
	private TableView<TOSpecificAsset> table;

    public void initializeTable() {
		List<TOSpecificAsset> toList = AssetPlusController.getSpecificAssetsOfAssetPlus();
        if (toList == null) {
            toList = Collections.emptyList();
        }
        ObservableList<TOSpecificAsset> list = FXCollections.observableArrayList(toList);

		columnAssetNumber.setCellValueFactory(new PropertyValueFactory<>("assetNumber"));
		columnFloorNumber.setCellValueFactory(new PropertyValueFactory<>("floorNumber"));
		columnRoomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
		columnPurchaseDate.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));

        columnUpdate.setCellFactory(column -> new ButtonCell<>("Update", this::updateSpecificAsset));
        columnDelete.setCellFactory(column -> new ButtonCell<>("Delete", this::showDialogDeleteSpecificAsset));
        table.setItems(list);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
		initializeTable();
    }

    private void updateSpecificAsset(TOSpecificAsset specificAsset) {
		parentContainer.fireEvent(new PageSwitchEvent<>("UPDATE", specificAsset));
    }

    public void onAddSpecificAsset(ActionEvent event) {
        parentContainer.fireEvent(new PageSwitchEvent<>("ADD"));
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
            initializeTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteSpecificAsset(TOSpecificAsset specificAsset) {
        AssetPlusController.removeSpecificAsset(specificAsset.getAssetNumber());
        System.out.println("Deleted SpecificAsset Successfully");
    }

}
