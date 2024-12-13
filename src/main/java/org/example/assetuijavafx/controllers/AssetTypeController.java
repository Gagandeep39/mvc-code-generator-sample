package org.example.assetuijavafx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import org.example.assetuijavafx.layouts.ButtonCell;
import org.example.assetuijavafx.models.AssetType;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static org.example.assetuijavafx.AssetPlusApplication.PACKAGE_ID;
import static org.example.assetuijavafx.models.AssetType.getAssettypesByName;

public class AssetTypeController implements Initializable {

    @FXML
    private TableColumn<AssetType, String> expectedLifeSpan;

    @FXML
    private TableColumn<AssetType, String> image;

    @FXML
    private TableColumn<AssetType, String> name;

    @FXML
    private TableColumn<AssetType, Button> delete;

    @FXML
    private TableColumn<AssetType, Button> update;

    @FXML
    private TableView<AssetType> table;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTable();
    }

    @FXML
    private void addAssetType(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PACKAGE_ID + "AssetTypeForm.fxml"));
        Parent parent = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(parent));
        stage.showAndWait();
        initializeTable();
    }

    @FXML
    private void updateAssetType(AssetType assetType) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PACKAGE_ID + "AssetTypeForm.fxml"));
        Parent parent = fxmlLoader.load();
        AssetTypeFormController controller = fxmlLoader.getController();
        controller.setData(assetType);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(parent));
        stage.showAndWait();
        initializeTable();
    }

    private void initializeTable() {
        ObservableList<AssetType> list = FXCollections.observableArrayList(getAssettypesByName().values());
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        expectedLifeSpan.setCellValueFactory(new PropertyValueFactory<>("expectedLifeSpan"));
        image.setCellValueFactory(new PropertyValueFactory<>("image"));
        update.setCellFactory(column -> new ButtonCell<>("Update", (assetType -> {
            try {
                updateAssetType(assetType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        })));
        delete.setCellFactory(column -> new ButtonCell<>("Delete", (assetType) -> {
            try {
                showDialogDeleteAssetType(assetType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
        table.setItems(list);
    }

    private void showDialogDeleteAssetType(AssetType assetType) throws IOException {
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
    }

    private void deleteAssetType(AssetType assetType) {
        System.out.println("Deleted AssetType: " + assetType);
        AssetType.delete(assetType);
    }
}
