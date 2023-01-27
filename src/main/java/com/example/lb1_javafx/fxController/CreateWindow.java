package com.example.lb1_javafx.fxController;


import com.example.lb1_javafx.CallEndpoints;
import com.example.lb1_javafx.utils.FxUtils;
import com.example.lb1_javafx.utils.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.json.JSONObject;

import java.io.IOException;

import static com.example.lb1_javafx.utils.SceneSwitcher.switchScene;

public class CreateWindow {

    public Button createButton;
    public Button backButton;
    ObservableList<String> availableChoices = FXCollections.observableArrayList("COMPLETED", "CANCELLED", "ACTIVE", "");
    public TextField title;
    public DatePicker releaseDate;
    public TextField count;
    public TextField price;
    public ChoiceBox gameStatus;

    @FXML
    private void initialize(){
        gameStatus.setItems(availableChoices);
        gameStatus.setValue("");
    }

    public void validate(ActionEvent actionEvent) throws IOException {

        Validation validate = new Validation();

        JSONObject json = new JSONObject();

        if (validate.isInteger(count.getText()) && validate.isDouble(price.getText())) {
            json.put("title", title.getText());
            json.put("releaseDate", releaseDate.getValue());
            json.put("count", count.getText());
            json.put("price", price.getText());
            json.put("gameStatus", gameStatus.getValue());
            CallEndpoints.Post("http://localhost:8080/api/game/create", json.toString());
            System.out.println(json.toString());

            switchScene("create-window.fxml", createButton);
        }
        else {
            FxUtils fxUtils = new FxUtils();
            fxUtils.alertErrorMsg(Alert.AlertType.ERROR, "Error", "Boohoo",
                    "Fill everything in correctly");
        }

    }

    public void openMain(ActionEvent actionEvent) throws IOException {
        switchScene("com/example/lb1_javafx/view-truck.fxml", backButton);
    }
}
