package org.example.assetuijavafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.assetuijavafx.models.AssetType;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private Button addAssetType;

    @FXML
    private TableColumn<AssetType, String> delete;

    @FXML
    private TableColumn<AssetType, String> expectedLifeSpan;

    @FXML
    private TableColumn<AssetType, String> image;

    @FXML
    private TableColumn<AssetType, String> name;

    @FXML
    private TableView<AssetType> table;

    @FXML
    private TableColumn<AssetType, String> update;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<AssetType> list = FXCollections.observableArrayList(AssetType.getAssettypesByName().values());
        System.out.println(list.size());
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        expectedLifeSpan.setCellValueFactory(new PropertyValueFactory<>("expectedLifeSpan"));
        image.setCellValueFactory(new PropertyValueFactory<>("image"));
        // Insert update button
        update.setCellFactory(col -> new TableCell<AssetType, String>() {

            private final Button button = new Button("Update");

            {
                button.setOnAction(e -> {
                    AssetType asset = getTableView().getItems().get(getIndex());
                });
            }

            @Override
            protected void updateItem(String s, boolean b) {
                super.updateItem(s, b);

                if (b) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });
        table.setItems(list);
    }
}