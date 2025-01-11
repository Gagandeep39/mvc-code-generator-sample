package org.example.assetuijavafx.fxml.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.assetuijavafx.fxml.layouts.ButtonCell;
import org.example.assetuijavafx.models.AssetType;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static org.example.assetuijavafx.AssetPlusApplication.PACKAGE_ID;
import static org.example.assetuijavafx.models.AssetType.getAssettypesByName;

public class AssetTypeTableController implements Initializable {

    @FXML
    private TableColumn<AssetType, String> columnExpectedLifeSpan;

    @FXML
    private TableColumn<AssetType, String> columnImage;

    @FXML
    private TableColumn<AssetType, String> columnName;

    @FXML
    private TableColumn<AssetType, Button> columnDelete;

    @FXML
    private TableColumn<AssetType, Button> columnUpdate;

    @FXML
    private TableView<AssetType> table;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTable();
    }

    public void initializeTable() {
        ObservableList<AssetType> list = FXCollections.observableArrayList(getAssettypesByName().values());
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnExpectedLifeSpan.setCellValueFactory(new PropertyValueFactory<>("expectedLifeSpan"));
        columnImage.setCellValueFactory(new PropertyValueFactory<>("image"));
        columnUpdate.setCellFactory(column -> new ButtonCell<>("Update", this::updateAssetType));
        columnDelete.setCellFactory(column -> new ButtonCell<>("Delete", this::showDialogDeleteAssetType));
        table.setItems(list);
    }

    private void updateAssetType(AssetType assetType) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PACKAGE_ID + "AssetTypeForm.fxml"));
            Parent parent = fxmlLoader.load();
            AssetTypeFormController controller = fxmlLoader.getController();
            controller.setData(assetType);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(parent));
            stage.showAndWait();
            initializeTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showDialogDeleteAssetType(AssetType assetType) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PACKAGE_ID + "DeleteDialog.fxml"));
            Parent parent = fxmlLoader.load();
            // Get controller
            DeleteDialogController<AssetType> controller = fxmlLoader.getController();
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

    private void deleteAssetType(AssetType assetType) {
        System.out.println("Deleted AssetType: " + assetType);
        AssetType.delete(assetType);
    }

}
