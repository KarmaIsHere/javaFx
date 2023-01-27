package com.example.lb1_javafx.fxController.manager;

import com.example.lb1_javafx.CallEndpoints;
import com.example.lb1_javafx.model.ClassTruck;
import com.example.lb1_javafx.model.TruckStatus;
import com.example.lb1_javafx.model.user.ClassUser;
import com.example.lb1_javafx.model.user.UserAccountType;
import com.example.lb1_javafx.utils.FxUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.lb1_javafx.utils.SceneSwitcher.switchScene;


public class CreateTrip implements Initializable {
    public DatePicker deadlineField;
    public ChoiceBox driverChoice;
    public ChoiceBox truckChoice;
    public Button nextButton;
    public Button backButton;
    public Button refreshButton;

    FxUtils fxUtils = new FxUtils();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    ObservableList<String> driverChoices = FXCollections.observableArrayList();
    ObservableList<String> truckChoices = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillChoiceBoxes();
    }
    public void openStopPointCreation(ActionEvent actionEvent) throws IOException {
        JSONObject json = new JSONObject();

        if (    deadlineField.getValue() == null ||
                driverChoice.getValue() == "" ||
                truckChoice.getValue() == "" )
        {
            fxUtils.alertErrorMsg(Alert.AlertType.ERROR, "Error", "Empty fields",
                    "Cant create an entry with empty fields");
            return;
        }
        if(deadlineField.getValue().isBefore(LocalDate.now()))
        {
            fxUtils.alertErrorMsg(Alert.AlertType.ERROR, "Error", "Date in the past",
                    "Deadline field can't be created to the past");
            return;
        }
        json.put("user", getIdFromChoice(driverChoice.getValue().toString()));
        json.put("manager", 3);
        json.put("truck", getIdFromChoice(truckChoice.getValue().toString()));
        json.put("deadline", deadlineField.getValue().format(formatter));

        CallEndpoints.Post("http://localhost:8080/api/truck/create", json.toString());
        System.out.println(json);

        switchScene("newTripCreationSequence/createStopPointsForTrip.fxml", backButton);
    }

    public void backToMain(ActionEvent actionEvent) throws IOException {
        switchScene("main-window.fxml", backButton);
    }

    private Long getIdFromChoice(String input){
        Pattern pattern = Pattern.compile("\\((\\d+)\\)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            Long id = Long.parseLong(matcher.group(1));
            return id;
        }
        return null;
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
                    .append(obj.getId())
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
        driverChoice.setValue("");

        truckChoice.setItems(truckChoices);
        truckChoice.setValue("");
    }
}
