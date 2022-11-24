package com.example.lb1_javafx.fxController.preview;

import com.example.lb1_javafx.model.ClassTruck;
import com.example.lb1_javafx.model.order.ClassShipment;
import com.example.lb1_javafx.model.order.ClassStopPoint;
import com.example.lb1_javafx.model.order.ClassTrip;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Timestamp;

import static com.example.lb1_javafx.utils.SceneSwitcher.switchScene;

public class ViewStopPoint {
    public Button editButton;
    public Button backButton;
    public Button refresh;
    public Button createButton;
    public Button searchButton;
    public TableColumn <ClassStopPoint, Integer> id;
    public TableColumn <ClassStopPoint, Integer> nr;
    public TableColumn <ClassStopPoint, Timestamp> stopDate;
    public TableColumn <ClassStopPoint, ClassShipment> shipment;
    public TableColumn <ClassStopPoint, ClassTrip> trip;
    public TableView  <ClassStopPoint> tableUser;

    public void createEntry(ActionEvent actionEvent) {
    }

    public void searchTable(ActionEvent actionEvent) {
    }

    public void fillTable(){
        id.setCellValueFactory(new PropertyValueFactory<ClassStopPoint, Integer>("id"));
        nr.setCellValueFactory(new PropertyValueFactory<ClassStopPoint, Integer>("nr"));
        stopDate.setCellValueFactory(new PropertyValueFactory<ClassStopPoint, Timestamp>("stopDate"));
        shipment.setCellValueFactory(new PropertyValueFactory<ClassStopPoint, ClassShipment>("shipment"));
        trip.setCellValueFactory(new PropertyValueFactory<ClassStopPoint, ClassTrip>("trip"));
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        switchScene("main-admin-window.fxml", createButton);
    }

    public void openEdit(ActionEvent actionEvent) {
    }
}
