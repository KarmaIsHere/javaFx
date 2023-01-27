package com.example.lb1_javafx.fxController.preview;

import com.example.lb1_javafx.CallEndpoints;
import com.example.lb1_javafx.model.ClassTruck;
import com.example.lb1_javafx.model.order.*;
import com.example.lb1_javafx.model.user.ClassUser;
import com.example.lb1_javafx.model.user.UserAccountType;
import com.example.lb1_javafx.utils.FxUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.lb1_javafx.utils.SceneSwitcher.switchScene;

public class ViewShipment implements Initializable {
    public Button createButton;
    public Button searchButton;
    public Button refresh;
    public Button backButton;
    public Button editButton;
    @FXML
    public TableColumn <ClassShipment, Long> id;
    @FXML
    public TableColumn <ClassShipment, ShipmentStatus> status;
    @FXML
    public TableColumn <ClassShipment, String> description;
    @FXML
    public TableColumn <ClassShipment, String> weight;
    @FXML
    public TableColumn <ClassShipment, String> destinationId;
    @FXML
    public TableView <ClassShipment> tableShipment;
    public TextField descriptionField;
    public TextField weightField;

    public TextField editIdField;
    public TextField idField;
    public Button deleteButton;
    public ChoiceBox destinationChoice;
    public Button refreshbutton;
    public ChoiceBox statusChoice;

    FxUtils fxUtils = new FxUtils();
    ObservableList<String> destinationChoices = FXCollections.observableArrayList();
    ObservableList<String> statusChoices = FXCollections.observableArrayList("NEW", "ASSIGNED", "DISPATCHED", "DELIVERED", "CLOSED");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillAllTable();
        fillChoiceBoxes();

        statusChoice.setItems(statusChoices);
        statusChoice.setValue("");
    }

    private void fillSearchTable(String body) {
        fillTable();
        tableShipment.getItems().setAll(ClassShipment.getArray(body));
    }

    private void fillAllTable() {
        fillTable();
        tableShipment.getItems().setAll(ClassShipment.getArray());
    }

    public void fillTable(){
        id.setCellValueFactory(new PropertyValueFactory<ClassShipment, Long>("id"));
        status.setCellValueFactory(new PropertyValueFactory<ClassShipment, ShipmentStatus>("status"));
        description.setCellValueFactory(new PropertyValueFactory<ClassShipment, String>("description"));
        weight.setCellValueFactory(new PropertyValueFactory<ClassShipment, String>("weight"));
        destinationId.setCellValueFactory(new PropertyValueFactory<ClassShipment, String>("destinationInfo"));
        tableShipment.getItems().setAll(ClassShipment.getArray());
    }
    public void createEntry(ActionEvent actionEvent) {
        JSONObject json = new JSONObject();

        if (    FxUtils.isFieldEmpty(descriptionField) ||
                FxUtils.isFieldEmpty(weightField) ||
                Objects.equals(destinationChoice.getValue().toString(), ""))
        {
            FxUtils.alertErrorMsg(Alert.AlertType.ERROR, "Error", "Empty fields",
                    "Cant create a destination entry with empty fields");
            return;
        }
        json.put("description", descriptionField.getText());
        json.put("weight", weightField.getText());
        json.put("destination", getIdFromChoice(destinationChoice.getValue().toString()));
        CallEndpoints.Post("http://localhost:8080/api/shipment/create", json.toString());
        System.out.println(json);
        fillAllTable();
    }

    public void searchTable(ActionEvent actionEvent) {
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

    private String getUrl(String url)
    {
        String originalUrl = url;
        if (!FxUtils.isFieldEmpty(descriptionField)) {
            url = addSeparator(url, originalUrl);
            url = url + "description=";
            url = url + (descriptionField.getText());
        }
        if (!FxUtils.isFieldEmpty(weightField)) {
            url = addSeparator(url, originalUrl);
            url = url + "weight=";
            url = url + (weightField.getText());
        }
        if (destinationChoice.getValue() != "") {
            url = addSeparator(url, originalUrl);
            url = url + "destination=";
            url = url + getIdFromChoice(destinationChoice.getValue().toString());
        }
        if (statusChoice.getValue() != "") {
            url = addSeparator(url, originalUrl);
            url = url + "status=";
            url = url + getIdFromChoice(statusChoice.getValue().toString());
        }

        return url;
    }
    private String addSeparator(String url, String originalUrl)
    {
        if (url == originalUrl) return url + "?";
        return url + "&";
    }

    private void fillChoiceBoxes()
    {
        String destinationSum = null;

        List<ClassDestination> users = new ArrayList<ClassDestination>();
        users = ClassDestination.getArray();
        for (ClassDestination obj : users)
        {
            StringBuilder sb = new StringBuilder();
            destinationSum = sb.append(obj.getCountry())
                    .append(", ")
                    .append(obj.getCity())
                    .append(", ")
                    .append(obj.getStreetAddress())
                    .append(" (")
                    .append(obj.getId())
                    .append(")").toString();
            destinationChoices.add(destinationSum);
        }
        destinationChoice.setItems(destinationChoices);
        destinationChoice.setValue("");
    }

    public void refreshDestinations(ActionEvent actionEvent) {
        destinationChoices.clear();
        fillChoiceBoxes();
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
}
