package com.example.lb1_javafx.fxController.preview;

import com.example.lb1_javafx.model.ClassTruck;
import com.example.lb1_javafx.model.forum.ClassComment;
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

public class ViewComment {
    public Button createButton;
    public Button searchButton;
    public Button editButton;
    public Button backButton;
    public Button refresh;
    @FXML
    public TableColumn<ClassComment, Integer> id;
    @FXML
    public TableColumn <ClassComment, String> text;
    @FXML
    public TableColumn <ClassComment, Instant> date;
    @FXML
    public TableColumn <ClassComment, ClassForum> forum;
    @FXML
    public TableColumn <ClassComment, ClassUser> user;
    @FXML
    public TableView<ClassComment> tableUser;

    public void fillTable(){
        id.setCellValueFactory(new PropertyValueFactory<ClassComment, Integer>("id"));
        text.setCellValueFactory(new PropertyValueFactory<ClassComment, String>("text"));
        date.setCellValueFactory(new PropertyValueFactory<ClassComment, Instant>("date"));
        forum.setCellValueFactory(new PropertyValueFactory<ClassComment, ClassForum>("forum"));
        user.setCellValueFactory(new PropertyValueFactory<ClassComment, ClassUser>("user"));
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
}
