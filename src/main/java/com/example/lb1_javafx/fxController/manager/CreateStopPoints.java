package com.example.lb1_javafx.fxController.manager;

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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
import static com.example.lb1_javafx.utils.TrimResponse.trimJSON;

public class CreateStopPoints implements Initializable {

    @FXML
    public TableColumn<ClassStopPointView, Long> shipment;
    @FXML
    public TableColumn<ClassStopPointView, String> description;
    @FXML
    public TableColumn<ClassStopPointView, String> weight;
    @FXML
    public TableColumn<ClassStopPointView, String> country;
    @FXML
    public TableColumn<ClassStopPointView, String> city;
    @FXML
    public TableColumn<ClassStopPointView, String> streetAddress;
    @FXML
    public TableView<ClassStopPointView> tableStopPoint;
    public TextField shipmentField;
    public Button createButton;

    public DatePicker deadlineField;
    public ChoiceBox driverChoice;
    public ChoiceBox truckChoice;
    public Button nextButton;
    public Button backButton;
    public Button refreshButton;
    public Button editButton;
    public Button deleteButton;
    public Button createTripButton;
//----------------------------------
    @FXML
    public TableView<ClassTrip> tableTrip;
    @FXML
    public TableColumn<ClassTrip, TripStatus> status;
    @FXML
    public TableColumn<ClassTrip, String> managerId;
    @FXML
    public TableColumn<ClassTrip, String> truckId;
    @FXML
    public TableColumn<ClassTrip, String> userId;
    @FXML
    public TableColumn<ClassTrip, LocalDate> deadline;
    @FXML
    public TableColumn<ClassTrip, Long> id;
    public Button deleteTripButton;
    public TextField tripField;

    public Long currentTripId;
    public ChoiceBox statusChoice;


    FxUtils fxUtils = new FxUtils();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    ObservableList<String> driverChoices = FXCollections.observableArrayList();
    ObservableList<String> truckChoices = FXCollections.observableArrayList();
    ObservableList<String> statusChoices = FXCollections.observableArrayList("NEW", "ACTIVE", "CLOSED", "");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillTripTable();
        fillChoiceBoxes();

        statusChoice.setItems(statusChoices);
        statusChoice.setValue("");

    }


    public void fillTripTable(){
        id.setCellValueFactory(new PropertyValueFactory<ClassTrip, Long>("id"));
        userId.setCellValueFactory(new PropertyValueFactory<ClassTrip, String>("userInfo"));
        status.setCellValueFactory(new PropertyValueFactory<ClassTrip, TripStatus>("status"));
        truckId.setCellValueFactory(new PropertyValueFactory<ClassTrip, String>("truckInfo"));
        managerId.setCellValueFactory(new PropertyValueFactory<ClassTrip, String>("managerInfo"));
        deadline.setCellValueFactory(new PropertyValueFactory<ClassTrip, LocalDate>("deadline"));
        tableTrip.getItems().setAll(ClassTrip.getArray());
    }

    public void fillStopPointTable(Long input){
        shipment.setCellValueFactory(new PropertyValueFactory<ClassStopPointView, Long>("shipment_id"));
        description.setCellValueFactory(new PropertyValueFactory<ClassStopPointView, String>("description"));
        weight.setCellValueFactory(new PropertyValueFactory<ClassStopPointView, String>("weight"));
        country.setCellValueFactory(new PropertyValueFactory<ClassStopPointView, String>("country"));
        city.setCellValueFactory(new PropertyValueFactory<ClassStopPointView, String>("city"));
        streetAddress.setCellValueFactory(new PropertyValueFactory<ClassStopPointView, String>("streetAddress"));

        tableStopPoint.getItems().setAll(ClassStopPointView.getArray(input));
    }

    public void createEntry(ActionEvent actionEvent) {
    }

    public void searchTable(ActionEvent actionEvent) {
    }

    public void submitEdit(ActionEvent actionEvent) {

        String getId = CallEndpoints.Get("http://localhost:8080/api/trip/trips?id=" + tripField.getText());

        if (getId.length() != 2)
        {
            String url = getUrl("http://localhost:8080/api/trip/update");
            System.out.println(url);
            String statusCode = CallEndpoints.Put(url);
            fillTripTable();
        } else {
            FxUtils fxUtils = new FxUtils();
            fxUtils.alertErrorMsg(Alert.AlertType.ERROR, "Error", "Boohoo",
                    "Such login does not exist.");
        }

    }

    private String getUrl(String url)
    {
        String originalUrl = url;
        if (driverChoice.getValue() != "") {
            url = addSeparator(url, originalUrl);
            url = url + "driver=";
            url = url + (driverChoice.getValue());
        }
        if (statusChoice.getValue() != "") {
            url = addSeparator(url, originalUrl);
            url = url + "status=";
            url = url + (statusChoice.getValue());
        }
        return url;
    }
    private String addSeparator(String url, String originalUrl)
    {
        if (url == originalUrl) url = url + "?";
        else url = url + "&";
        return url;
    }

    public void deleteEntry(ActionEvent actionEvent) {
    }

    public void goBack(ActionEvent actionEvent) {
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

        String temp = CallEndpoints.Post("http://localhost:8080/api/trip/create", json.toString());
        System.out.println(temp);
        System.out.println(json);
        fillTripTable();
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

    public void backToMain(ActionEvent actionEvent) throws IOException {
        switchScene("main-window.fxml", backButton);
    }



    public void refresh(ActionEvent actionEvent) {
        driverChoices.clear();
        truckChoices.clear();
        fillChoiceBoxes();
        fillTripTable();
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

    public void deleteTrip(ActionEvent actionEvent) {
        String getId = CallEndpoints.Get("http://localhost:8080/api/trip/trips?id=" + tripField.getText());
        if (getId.length() != 2) {
            String statusCode = CallEndpoints.DELETE("http://localhost:8080/api/trip/delete?id=" + tripField.getText());
            fillTripTable();
        } else {
            FxUtils fxUtils = new FxUtils();
            FxUtils.alertErrorMsg(Alert.AlertType.ERROR, "Error", "Boohoo",
                    "No such trip exists with that id.");
        }
    }

    public void searchTrip(ActionEvent actionEvent) {
    }
    public void fieldSelect(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            TablePosition pos = tableTrip.getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow();
            ClassTrip item = tableTrip.getItems().get(row);
            TableColumn<ClassTrip, ?> column0 = tableTrip.getColumns().get(0);
            Object column0Value = column0.getCellData(item);
            this.currentTripId = Long.valueOf(column0Value.toString());
            fillStopPointTable(Long.valueOf(column0Value.toString()));
        }
    }

    public void createShipmentEntry(ActionEvent actionEvent) {
        JSONObject json = new JSONObject();
        String response = CallEndpoints.Get("http://localhost:8080/api/stopPoint/stopPoints?shipment_id=" + shipmentField.getText());
        if (response.length() != 2)
        {
            FxUtils.alertErrorMsg(Alert.AlertType.ERROR, "Error", "Duplicate",
                    "This shipment has already been assigned elsewhere");
            return;
        }
        if (FxUtils.isFieldEmpty(shipmentField))
        {
            FxUtils.alertErrorMsg(Alert.AlertType.ERROR, "Error", "Empty fields",
                    "Cant create a stop point entry with empty fields");
            return;
        }

        json.put("shipment", shipmentField.getText());
        json.put("trip", currentTripId);
        CallEndpoints.Post("http://localhost:8080/api/stopPoint/create", json.toString());
        System.out.println(json);
        fillStopPointTable(currentTripId);
    }

    public void deleteShipmentEntry(ActionEvent actionEvent) {
        String response = CallEndpoints.Get("http://localhost:8080/api/stopPoint/stopPoints?shipment_id=" + shipmentField.getText());
        response = trimJSON(response);
        JSONObject jsonObject = new JSONObject(response);
        if (response.length() != 2) {
            String statusCode = CallEndpoints.DELETE("http://localhost:8080/api/stopPoint/delete?id=" + jsonObject.get("id").toString());
            fillStopPointTable(currentTripId);
        } else {
            FxUtils fxUtils = new FxUtils();
            FxUtils.alertErrorMsg(Alert.AlertType.ERROR, "Error", "Boohoo",
                    "No such shipment exists with that id.");
        }
    }
}
