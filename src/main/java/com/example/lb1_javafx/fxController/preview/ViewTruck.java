package com.example.lb1_javafx.fxController.preview;

import com.example.lb1_javafx.model.ClassTruck;
import com.example.lb1_javafx.model.TruckStatus;
import com.example.lb1_javafx.model.user.ClassUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.lb1_javafx.utils.SceneSwitcher.switchScene;

public class ViewTruck {
    public Button createButton;
    public Button searchButton;
    @FXML
    public TableView <ClassTruck> tableUser;
    @FXML
    public TableColumn<ClassTruck, Integer > id;
    @FXML
    public TableColumn<ClassTruck, String > brand;
    @FXML
    public TableColumn<ClassTruck, String > year;
    @FXML
    public TableColumn<ClassTruck, String > height;
    @FXML
    public TableColumn<ClassTruck, String > weight;
    @FXML
    public TableColumn<ClassTruck, TruckStatus> status;
    public Button refresh;
    public Button backButton;
    public Button editButton;

    public void fillTable(){
        id.setCellValueFactory(new PropertyValueFactory<ClassTruck, Integer>("id"));
        brand.setCellValueFactory(new PropertyValueFactory<ClassTruck, String>("brand"));
        year.setCellValueFactory(new PropertyValueFactory<ClassTruck, String>("year"));
        height.setCellValueFactory(new PropertyValueFactory<ClassTruck, String>("height"));
        weight.setCellValueFactory(new PropertyValueFactory<ClassTruck, String>("weight"));
        status.setCellValueFactory(new PropertyValueFactory<ClassTruck, TruckStatus>("status"));
    }

    public void createEntry(ActionEvent actionEvent) {
    }

    public void searchTable(ActionEvent actionEvent) {
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        switchScene("main-admin-window.fxml", createButton);
    }

    public void openEdit(ActionEvent actionEvent) {
    }


    public void submitEdit(ActionEvent actionEvent) {
    }

    public void deleteEntry(ActionEvent actionEvent) {
    }
}
