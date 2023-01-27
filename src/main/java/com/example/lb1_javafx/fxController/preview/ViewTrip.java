package com.example.lb1_javafx.fxController.preview;

import com.example.lb1_javafx.CallEndpoints;
import com.example.lb1_javafx.model.ClassTruck;
import com.example.lb1_javafx.model.TruckStatus;
import com.example.lb1_javafx.model.order.ClassTrip;
import com.example.lb1_javafx.model.order.TripStatus;
import com.example.lb1_javafx.model.user.ClassUser;
import com.example.lb1_javafx.utils.FxUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;

import java.time.LocalDate;
import java.util.ResourceBundle;

import static com.example.lb1_javafx.utils.SceneSwitcher.switchScene;

public class ViewTrip implements Initializable {
    public Button createButton;
    public Button searchButton;
    public Button editButton;
    public Button backButton;
    public Button refresh;
    @FXML
    public TableColumn <ClassTrip, Long> id;

    @FXML
    public TableColumn <ClassTrip, TripStatus> status;
    @FXML
    public TableColumn <ClassTrip, LocalDate> start;
    @FXML
    public TableColumn <ClassTrip, LocalDate> end;
    @FXML
    public TableColumn <ClassTrip, Long> userId;
    @FXML
    public TableColumn <ClassTrip, Long> managerId;
    @FXML
    public TableColumn <ClassTrip, Long> truckId;
    @FXML
    public TableColumn <ClassTrip, LocalDate> deadline;
    @FXML
    public TableView <ClassTrip> tableTrip;
    public TextField editIdField;
    public TextField truckField;
    public TextField userField;
    public TextField startField;
    public ChoiceBox statusChoice;
    public TextField idField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //fillTable();
    }
    public void fillTable(){
        id.setCellValueFactory(new PropertyValueFactory<ClassTrip, Long>("id"));
        start.setCellValueFactory(new PropertyValueFactory<ClassTrip, LocalDate>("start"));
        end.setCellValueFactory(new PropertyValueFactory<ClassTrip, LocalDate>("end"));
        userId.setCellValueFactory(new PropertyValueFactory<ClassTrip, Long>("userId"));
        truckId.setCellValueFactory(new PropertyValueFactory<ClassTrip, Long>("truckId"));
        managerId.setCellValueFactory(new PropertyValueFactory<ClassTrip, Long>("managerId"));
        deadline.setCellValueFactory(new PropertyValueFactory<ClassTrip, LocalDate>("deadline"));

        tableTrip.getItems().setAll(ClassTrip.getArray());
    }

    public void createEntry(ActionEvent actionEvent) {

    }

    public void searchTable(ActionEvent actionEvent) {
    }

    public void openEdit(ActionEvent actionEvent) {
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        switchScene("main-window.fxml", createButton);
    }

//    public void submitEdit(ActionEvent actionEvent) {
//        String getId = CallEndpoints.Get("http://localhost:8080/api/trip/trips?id=" + editIdField.getText());
//
//        if (getLogin.length() != 2)
//        {
//            String url = getUrl("http://localhost:8080/api/user/update");
//            System.out.println(url);
//            String statusCode = CallEndpoints.Put(url);
//            fillAllTable();
//        } else {
//            FxUtils fxUtils = new FxUtils();
//            fxUtils.alertErrorMsg(Alert.AlertType.ERROR, "Error", "Boohoo",
//                    "Such login does not exist.");
//        }
//    }

    public void deleteEntry(ActionEvent actionEvent) {
    }
}
