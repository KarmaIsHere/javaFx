package com.example.lb1_javafx.fxController.preview;

import com.example.lb1_javafx.model.order.ClassTrip;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DriverMain implements Initializable {

    @FXML
    public TableColumn <ClassTrip, LocalDate> start;
    @FXML
    public TableColumn <ClassTrip, Long> userId;
    @FXML
    public TableColumn <ClassTrip, Long> truckId;
    @FXML
    public TableColumn <ClassTrip, LocalDate> deadline;
    @FXML
    public TableView <ClassTrip> tableTrip;
    public Button backButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillTable();
    }

    public void fillTable(){
        start.setCellValueFactory(new PropertyValueFactory<ClassTrip, LocalDate>("start"));
        userId.setCellValueFactory(new PropertyValueFactory<ClassTrip, Long>("userId"));
        truckId.setCellValueFactory(new PropertyValueFactory<ClassTrip, Long>("truckId"));
        deadline.setCellValueFactory(new PropertyValueFactory<ClassTrip, LocalDate>("deadline"));
        tableTrip.getItems().setAll(ClassTrip.getArray());
    }

    public void goBack(ActionEvent actionEvent) {
    }


}
