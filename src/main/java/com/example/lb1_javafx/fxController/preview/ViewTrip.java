package com.example.lb1_javafx.fxController.preview;

import com.example.lb1_javafx.model.ClassTruck;
import com.example.lb1_javafx.model.TruckStatus;
import com.example.lb1_javafx.model.order.ClassTrip;
import com.example.lb1_javafx.model.user.ClassUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Timestamp;

import static com.example.lb1_javafx.utils.SceneSwitcher.switchScene;

public class ViewTrip {
    public Button createButton;
    public Button searchButton;
    public Button editButton;
    public Button backButton;
    public Button refresh;
    @FXML
    public TableColumn <ClassTrip, Integer> id;
    @FXML
    public TableColumn <ClassTrip, Timestamp> start;
    @FXML
    public TableColumn <ClassTrip, Timestamp> end;
    @FXML
    public TableColumn <ClassTrip, ClassUser> driver;
    @FXML
    public TableColumn <ClassTrip, ClassTruck> truck;
    @FXML
    public TableView <ClassTrip> tableUser;

    public void fillTable(){
        id.setCellValueFactory(new PropertyValueFactory<ClassTrip, Integer>("id"));
        start.setCellValueFactory(new PropertyValueFactory<ClassTrip, Timestamp>("start"));
        end.setCellValueFactory(new PropertyValueFactory<ClassTrip, Timestamp>("end"));
        driver.setCellValueFactory(new PropertyValueFactory<ClassTrip, ClassUser>("driver"));
        truck.setCellValueFactory(new PropertyValueFactory<ClassTrip, ClassTruck>("truck"));
    }

    public void createEntry(ActionEvent actionEvent) {
    }

    public void searchTable(ActionEvent actionEvent) {
    }

    public void openEdit(ActionEvent actionEvent) {
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        switchScene("main-admin-window.fxml", createButton);
    }

    public void submitEdit(ActionEvent actionEvent) {
    }

    public void deleteEntry(ActionEvent actionEvent) {
    }
}
