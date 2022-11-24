package com.example.lb1_javafx.fxController;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;

import static com.example.lb1_javafx.utils.SceneSwitcher.switchScene;

public class SearchWindow {

    public TextField minVal;
    public TextField maxVal;
    public DatePicker dateVal;
    public ChoiceBox enumVal;
    public Button searchButton;
    public Button backButton;

    public void searchAction(ActionEvent actionEvent) {

    }

    public void openMain(ActionEvent actionEvent) throws IOException {
        switchScene("com/example/lb1_javafx/view-truck.fxml", backButton);
    }
}
