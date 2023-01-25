package com.example.lb1_javafx.fxController.manager;

import com.example.lb1_javafx.model.ClassTruck;
import com.example.lb1_javafx.model.user.ClassUser;
import com.example.lb1_javafx.model.user.UserAccountType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.example.lb1_javafx.utils.SceneSwitcher.switchScene;


public class CreateTrip implements Initializable {
    public DatePicker deadlineField;
    public ChoiceBox driverChoice;
    public ChoiceBox truckChoice;
    public Button nextButton;
    public Button backButton;
    public Button refreshButton;

    ObservableList<String> driverChoices = FXCollections.observableArrayList();
    ObservableList<String> truckChoices = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillChoiceBoxes();
    }
    public void openStopPointCreation(ActionEvent actionEvent) {

    }

    public void backToMain(ActionEvent actionEvent) throws IOException {
        switchScene("main-window.fxml", backButton);
    }


    public void refresh(ActionEvent actionEvent) {
        driverChoices.clear();
        truckChoices.clear();
        fillChoiceBoxes();
    }

    private void fillChoiceBoxes()
    {
        String userSum;
        String truckSum;

        List<ClassUser> users = new ArrayList<ClassUser>();
        users = ClassUser.getArray();
        for (ClassUser obj : users)
        {
            if (obj.getAccount_type() != UserAccountType.DRIVER) continue;

            StringBuilder sb = new StringBuilder();
            userSum = sb.append(obj.getFirst_name())
                    .append(" ")
                    .append(obj.getLast_name())
                    .append(" (")
                    .append(obj.getLogin())
                    .append(")").toString();
            driverChoices.add(userSum);
        }

        List<ClassTruck> trucks = new ArrayList<ClassTruck>();
        trucks = ClassTruck.getArray();
        for (ClassTruck obj : trucks)
        {
            StringBuilder sb = new StringBuilder();
            truckSum = sb.append(obj.getBrand())
                    .append(" - ")
                    .append(obj.getYear())
                    .append(" (")
                    .append(obj.getId())
                    .append(")").toString().toString();
            truckChoices.add(truckSum);
        }

        driverChoice.setItems(driverChoices);
        driverChoice.setValue("-----");

        truckChoice.setItems(truckChoices);
        truckChoice.setValue("-----");
    }
}
