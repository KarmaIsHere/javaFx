package com.example.lb1_javafx.fxController.preview;

import com.example.lb1_javafx.model.ClassTruck;
import com.example.lb1_javafx.model.order.ClassDestination;
import com.example.lb1_javafx.model.order.ClassTrip;
import com.example.lb1_javafx.model.user.ClassUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;

import static com.example.lb1_javafx.utils.SceneSwitcher.switchScene;

public class ViewDestination implements Initializable {
    public Button createButton;
    public Button searchButton;
    public Button editButton;
    public Button backButton;
    public Button refresh;
    @FXML
    public TableColumn <ClassDestination, Integer> id;
    @FXML
    public TableColumn <ClassDestination, String> country;
    @FXML
    public TableColumn <ClassDestination, String> city;
    @FXML
    public TableColumn <ClassDestination, String> streetAddress;
    @FXML
    public TableView <ClassDestination> tableDestination;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillTable();
    }
    public void createEntry(ActionEvent actionEvent) {
    }

    public void searchTable(ActionEvent actionEvent) {
    }

    public void fillTable(){
        id.setCellValueFactory(new PropertyValueFactory<ClassDestination, Integer>("id"));
        country.setCellValueFactory(new PropertyValueFactory<ClassDestination, String>("country"));
        city.setCellValueFactory(new PropertyValueFactory<ClassDestination, String>("city"));
        streetAddress.setCellValueFactory(new PropertyValueFactory<ClassDestination, String>("streetAddress"));
        tableDestination.getItems().setAll(ClassDestination.getArray());

    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        switchScene("main-window.fxml", createButton);
    }

    public void openEdit(ActionEvent actionEvent) {
    }

    public void submitEdit(ActionEvent actionEvent) {
    }

    public void deleteEntry(ActionEvent actionEvent) {
    }
}
