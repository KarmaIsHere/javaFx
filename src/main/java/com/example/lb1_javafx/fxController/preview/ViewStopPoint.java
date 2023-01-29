package com.example.lb1_javafx.fxController.preview;

import com.example.lb1_javafx.model.order.ClassStopPoint;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static com.example.lb1_javafx.utils.SceneSwitcher.switchScene;

public class ViewStopPoint implements Initializable {
    public Button editButton;
    public Button backButton;
    public Button refresh;
    public Button createButton;
    public Button searchButton;
    @FXML
    public TableColumn <ClassStopPoint, Long> id;
    @FXML
    public TableColumn <ClassStopPoint, LocalDate> stopDate;
    @FXML
    public TableColumn <ClassStopPoint, Long> shipment;
    @FXML
    public TableColumn <ClassStopPoint, Long> trip;
    @FXML
    public TableView  <ClassStopPoint> tableStopPoint;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillTable();
    }
    public void createEntry(ActionEvent actionEvent) {
    }

    public void searchTable(ActionEvent actionEvent) {
    }

    public void fillTable(){
        id.setCellValueFactory(new PropertyValueFactory<ClassStopPoint, Long>("id"));
        stopDate.setCellValueFactory(new PropertyValueFactory<ClassStopPoint, LocalDate>("stopDate"));
        shipment.setCellValueFactory(new PropertyValueFactory<ClassStopPoint, Long>("shipment_id"));
        trip.setCellValueFactory(new PropertyValueFactory<ClassStopPoint, Long>("trip_id"));
        tableStopPoint.getItems().setAll(ClassStopPoint.getArray());

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
