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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    private TableView<AssetType> table;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        intializeTable();
    }

    @FXML
    private void addAssetType(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PACKAGE_ID + "AddAssetType.fxml"));
        Parent parent = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(parent));
        stage.showAndWait();
        intializeTable();
    }

    private void intializeTable() {
        ObservableList<AssetType> list = FXCollections.observableArrayList(getAssettypesByName().values());
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        expectedLifeSpan.setCellValueFactory(new PropertyValueFactory<>("expectedLifeSpan"));
        image.setCellValueFactory(new PropertyValueFactory<>("image"));
        table.setItems(list);
    }
}
