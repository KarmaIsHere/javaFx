package com.example.lb1_javafx.fxController.preview;

import com.example.lb1_javafx.model.ClassTruck;
import com.example.lb1_javafx.model.order.ClassDestination;
import com.example.lb1_javafx.model.order.ClassTrip;
import com.example.lb1_javafx.model.user.ClassUser;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Timestamp;

import static com.example.lb1_javafx.utils.SceneSwitcher.switchScene;

public class ViewDestination {
    public Button createButton;
    public Button searchButton;
    public Button editButton;
    public Button backButton;
    public Button refresh;
    public TableColumn <ClassDestination, Integer> id;
    public TableColumn <ClassDestination, String> country;
    public TableColumn <ClassDestination, String> city;
    public TableColumn <ClassDestination, String> streetAddress;
    public TableView <ClassDestination> tableUser;


    public void createEntry(ActionEvent actionEvent) {
    }

    public void searchTable(ActionEvent actionEvent) {
    }

    public void fillTable(){
        id.setCellValueFactory(new PropertyValueFactory<ClassDestination, Integer>("id"));
        country.setCellValueFactory(new PropertyValueFactory<ClassDestination, String>("country"));
        city.setCellValueFactory(new PropertyValueFactory<ClassDestination, String>("city"));
        streetAddress.setCellValueFactory(new PropertyValueFactory<ClassDestination, String>("streetAddress"));

    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        switchScene("main-admin-window.fxml", createButton);
    }

    public void openEdit(ActionEvent actionEvent) {
    }
}
