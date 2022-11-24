package com.example.lb1_javafx.fxController.preview;

import com.example.lb1_javafx.model.order.ClassDestination;
import com.example.lb1_javafx.model.order.ClassShipment;
import com.example.lb1_javafx.model.order.ClassStopPoint;
import com.example.lb1_javafx.model.order.ClassTrip;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Timestamp;

import static com.example.lb1_javafx.utils.SceneSwitcher.switchScene;

public class ViewShipment {
    public Button createButton;
    public Button searchButton;
    public Button refresh;
    public Button backButton;
    public Button editButton;
    @FXML
    public TableColumn <ClassShipment, Integer> id;
    @FXML
    public TableColumn <ClassShipment, String> description;
    @FXML
    public TableColumn <ClassShipment, String> weight;
    @FXML
    public TableColumn <ClassShipment, ClassDestination> destination;
    @FXML
    public TableView <ClassShipment> tableUser;

    public void createEntry(ActionEvent actionEvent) {
    }

    public void searchTable(ActionEvent actionEvent) {
    }

    public void fillTable(){
        id.setCellValueFactory(new PropertyValueFactory<ClassShipment, Integer>("id"));
        description.setCellValueFactory(new PropertyValueFactory<ClassShipment, String>("description"));
        weight.setCellValueFactory(new PropertyValueFactory<ClassShipment, String>("weight"));
        destination.setCellValueFactory(new PropertyValueFactory<ClassShipment, ClassDestination>("destination"));
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
