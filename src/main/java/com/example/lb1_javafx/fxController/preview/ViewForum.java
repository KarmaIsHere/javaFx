package com.example.lb1_javafx.fxController.preview;

import com.example.lb1_javafx.model.ClassTruck;
import com.example.lb1_javafx.model.forum.ClassForum;
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
import java.time.Instant;

import static com.example.lb1_javafx.utils.SceneSwitcher.switchScene;

public class ViewForum {
    public Button createButton;
    public Button searchButton;
    public Button refresh;
    public Button backButton;
    public Button editButton;
    @FXML
    public TableColumn <ClassForum, Integer> id;
    @FXML
    public TableColumn <ClassForum, String> title;
    @FXML
    public TableColumn <ClassForum, String> description;
    @FXML
    public TableColumn <ClassForum, String> category;
    @FXML
    public TableColumn <ClassForum, Integer> creator;
    @FXML
    public TableColumn <ClassForum, Instant> date;
    @FXML
    public TableView<ClassForum> tableUser;

    public void createEntry(ActionEvent actionEvent) {
    }

    public void searchTable(ActionEvent actionEvent) {
    }

    public void fillTable(){
        id.setCellValueFactory(new PropertyValueFactory<ClassForum, Integer>("id"));
        title.setCellValueFactory(new PropertyValueFactory<ClassForum, String>("title"));
        description.setCellValueFactory(new PropertyValueFactory<ClassForum, String>("description"));
        category.setCellValueFactory(new PropertyValueFactory<ClassForum, String>("category"));
        creator.setCellValueFactory(new PropertyValueFactory<ClassForum, Integer>("creator"));
        date.setCellValueFactory(new PropertyValueFactory<ClassForum, Instant>("date"));
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
