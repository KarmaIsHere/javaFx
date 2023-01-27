package com.example.lb1_javafx.fxController.preview;

import com.example.lb1_javafx.CallEndpoints;
import com.example.lb1_javafx.model.ClassTruck;
import com.example.lb1_javafx.model.TruckStatus;
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
import java.util.ResourceBundle;

import static com.example.lb1_javafx.utils.SceneSwitcher.switchScene;

public class ViewTruck implements Initializable {
    public Button createButton;
    public Button searchButton;
    @FXML
    public TableView <ClassTruck> tableTruck;
    @FXML
    public TableColumn<ClassTruck, Long > id;
    @FXML
    public TableColumn<ClassTruck, String > brand;
    @FXML
    public TableColumn<ClassTruck, String > year;
    @FXML
    public TableColumn<ClassTruck, String > height;
    @FXML
    public TableColumn<ClassTruck, String > weight;
    @FXML
    public TableColumn<ClassTruck, TruckStatus> status;
    public Button refresh;
    public Button backButton;
    public Button editButton;
    public ChoiceBox statusChoice;
    public TextField brandField;
    public TextField yearField;
    public TextField heightField;
    public TextField weightField;
    public TextField editIdField;
    public TextField idField;
    public Button deleteButton;
    public TextField IdField;

    FxUtils fxUtils = new FxUtils();
    ObservableList<String> statusChoices = FXCollections.observableArrayList("FREE", "WORKING", "BROKEN", "");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statusChoice.setItems(statusChoices);
        statusChoice.setValue("");
        fillTable();
    }

    private void fillSearchTable(String body) {
        fillTable();
        tableTruck.getItems().setAll(ClassTruck.getArray(body));
    }

    private void fillAllTable() {
        fillTable();
        tableTruck.getItems().setAll(ClassTruck.getArray());
    }

    private void fillTable(){
        id.setCellValueFactory(new PropertyValueFactory<ClassTruck, Long>("id"));
        brand.setCellValueFactory(new PropertyValueFactory<ClassTruck, String>("brand"));
        year.setCellValueFactory(new PropertyValueFactory<ClassTruck, String>("year"));
        height.setCellValueFactory(new PropertyValueFactory<ClassTruck, String>("height"));
        weight.setCellValueFactory(new PropertyValueFactory<ClassTruck, String>("weight"));
        status.setCellValueFactory(new PropertyValueFactory<ClassTruck, TruckStatus>("status"));
        tableTruck.getItems().setAll(ClassTruck.getArray());
    }

    public void createEntry(ActionEvent actionEvent) {
        JSONObject json = new JSONObject();

        if (    FxUtils.isFieldEmpty(brandField) ||
                FxUtils.isFieldEmpty(yearField) ||
                FxUtils.isFieldEmpty(heightField) ||
                FxUtils.isFieldEmpty(weightField))
        {
            fxUtils.alertErrorMsg(Alert.AlertType.ERROR, "Error", "Empty fields",
                    "Cant create an entry with empty fields");
            return;
        }
            json.put("brand", brandField.getText());
            json.put("year", yearField.getText());
            json.put("height", heightField.getText());
            json.put("weight", weightField.getText());
            json.put("status", TruckStatus.FREE);
            CallEndpoints.Post("http://localhost:8080/api/truck/create", json.toString());
            System.out.println(json);
            fillAllTable();

    }

    public void searchTable(ActionEvent actionEvent) {
        String url = getUrl("http://localhost:8080/api/truck/trucks");
        System.out.println(url);
        String response = CallEndpoints.Get(url);

        if(response.length() == 2){
            FxUtils fxUtils = new FxUtils();
            fxUtils.alertErrorMsg(Alert.AlertType.ERROR, "Welp", "Boohoo",
                    "Nothing was found");
        }
        else fillSearchTable(response);
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        switchScene("main-window.fxml", createButton);
    }

    public void submitEdit(ActionEvent actionEvent) {
        String getId = CallEndpoints.Get("http://localhost:8080/api/truck/trucks?id=" + idField.getText());

        if (getId.length() != 2)
        {
            String url = getUrl("http://localhost:8080/api/truck/update");
            System.out.println(url);
            String statusCode = CallEndpoints.Put(url);
            fillAllTable();
        } else {
            FxUtils fxUtils = new FxUtils();
            FxUtils.alertErrorMsg(Alert.AlertType.ERROR, "Error", "Boohoo",
                    "Such truck with that id does not exist.");
        }
    }

    public void deleteEntry(ActionEvent actionEvent) {
        String getId = CallEndpoints.Get("http://localhost:8080/api/truck/trucks?id=" + idField.getText());

        String tripCar = CallEndpoints.Get("http://localhost:8080/api/trip/trips?truckId=" + idField.getText());
        if (tripCar.length() != 2) {
            FxUtils fxUtils = new FxUtils();
            FxUtils.alertErrorMsg(Alert.AlertType.ERROR, "Error", "Boohoo",
                    "Trucks that are in Trips can't be deleted");
        }
        if (getId.length() != 2) {
            String statusCode = CallEndpoints.DELETE("http://localhost:8080/api/truck/delete?id=" + idField.getText());
            fillAllTable();
        } else {
            FxUtils fxUtils = new FxUtils();
            FxUtils.alertErrorMsg(Alert.AlertType.ERROR, "Error", "Boohoo",
                    "No such truck exists with that id.");
        }
    }

    private String getUrl(String url)
    {
        String originalUrl = url;
        if (!FxUtils.isFieldEmpty(editIdField)) {
            url = addSeparator(url, originalUrl);
            url = url + "id=";
            url = url + (editIdField.getText());
        }
        if (!FxUtils.isFieldEmpty(brandField)) {
            url = addSeparator(url, originalUrl);
            url = url + "brand=";
            url = url + (brandField.getText());
        }
        if (!FxUtils.isFieldEmpty(yearField)) {
            url = addSeparator(url, originalUrl);
            url = url + "year=";
            url = url + (yearField.getText());
        }
        if (!FxUtils.isFieldEmpty(heightField)) {
            url = addSeparator(url, originalUrl);
            url = url + "height=";
            url = url + (heightField.getText());
        }
        if (!FxUtils.isFieldEmpty(weightField)) {
            url = addSeparator(url, originalUrl);
            url = url + "weight=";
            url = url + (weightField.getText());
        }
        if (statusChoice.getValue() != null && statusChoice.getValue() != "") {
            url = addSeparator(url, originalUrl);
            url = url + "status=";
            url = url + (statusChoice.getValue().toString());
        }

        return url;
    }
    private String addSeparator(String url, String originalUrl)
    {
        if (url == originalUrl) return url + "?";
        return url + "&";
    }
}
