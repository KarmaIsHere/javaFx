package com.example.lb1_javafx.fxController.preview;

import com.example.lb1_javafx.CallEndpoints;
import com.example.lb1_javafx.model.order.ClassDestination;
import com.example.lb1_javafx.utils.FxUtils;
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

import static com.example.lb1_javafx.utils.SceneSwitcher.switchScene;

public class ViewDestination implements Initializable {
    public Button createButton;
    public Button searchButton;
    public Button editButton;
    public Button backButton;
    public Button refresh;
    @FXML
    public TableColumn <ClassDestination, Long> id;
    @FXML
    public TableColumn <ClassDestination, String> country;
    @FXML
    public TableColumn <ClassDestination, String> city;
    @FXML
    public TableColumn <ClassDestination, String> streetAddress;
    @FXML
    public TableView <ClassDestination> tableDestination;
    public TextField countryField;
    public TextField cityField;
    public TextField streetAddressField;
    public TextField editIdField;
    public TextField idField;
    public Button deleteButton;

    FxUtils fxUtils = new FxUtils();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillAllTable();
    }

    private void fillSearchTable(String body) {
        fillTable();
        tableDestination.getItems().setAll(ClassDestination.getArray(body));
    }

    private void fillAllTable() {
        fillTable();
        tableDestination.getItems().setAll(ClassDestination.getArray());
    }

    public void fillTable(){
        id.setCellValueFactory(new PropertyValueFactory<ClassDestination, Long>("id"));
        country.setCellValueFactory(new PropertyValueFactory<ClassDestination, String>("country"));
        city.setCellValueFactory(new PropertyValueFactory<ClassDestination, String>("city"));
        streetAddress.setCellValueFactory(new PropertyValueFactory<ClassDestination, String>("streetAddress"));
        tableDestination.getItems().setAll(ClassDestination.getArray());
    }
    public void createEntry(ActionEvent actionEvent) {
        JSONObject json = new JSONObject();

        if (    FxUtils.isFieldEmpty(countryField) ||
                FxUtils.isFieldEmpty(cityField) ||
                FxUtils.isFieldEmpty(streetAddressField) )
        {
            fxUtils.alertErrorMsg(Alert.AlertType.ERROR, "Error", "Empty fields",
                    "Cant create a destination entry with empty fields");
            return;
        }
        if (!isDestinationValid(countryField.getText(), cityField.getText(), streetAddressField.getText()))
        {
            fxUtils.alertErrorMsg(Alert.AlertType.ERROR, "Error", "Empty fields",
                    "This destination already exists");
            return;
        }
        json.put("country", countryField.getText());
        json.put("city", cityField.getText());
        json.put("streetAddress", streetAddressField.getText());
        CallEndpoints.Post("http://localhost:8080/api/destination/create", json.toString());
        System.out.println(json);
        fillAllTable();
    }

    public void searchTable(ActionEvent actionEvent) {
        String url = getUrl("http://localhost:8080/api/destination/destinations");
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

    public void openEdit(ActionEvent actionEvent) {
    }

    public void submitEdit(ActionEvent actionEvent) {
    }

    public void deleteEntry(ActionEvent actionEvent) {
        String getId = CallEndpoints.Get("http://localhost:8080/api/destination/destinations?id=" + idField.getText());
        if (getId.length() != 2) {
            String statusCode = CallEndpoints.DELETE("http://localhost:8080/api/destination/delete?id=" + idField.getText());
            fillAllTable();
        } else {
            FxUtils fxUtils = new FxUtils();
            FxUtils.alertErrorMsg(Alert.AlertType.ERROR, "Error", "Boohoo",
                    "No such destination exists with that id.");
        }
    }

    private String getUrl(String url)
    {
        String originalUrl = url;
        if (!FxUtils.isFieldEmpty(countryField)) {
            url = addSeparator(url, originalUrl);
            url = url + "country=";
            url = url + (countryField.getText());
        }
        if (!FxUtils.isFieldEmpty(cityField)) {
            url = addSeparator(url, originalUrl);
            url = url + "city=";
            url = url + (cityField.getText());
        }
        if (!FxUtils.isFieldEmpty(streetAddressField)) {
            url = addSeparator(url, originalUrl);
            url = url + "streetAddress=";
            url = url + (streetAddressField.getText());
        }

        return url;
    }
    private String addSeparator(String url, String originalUrl)
    {
        if (url == originalUrl) return url + "?";
        return url + "&";
    }

    private boolean isDestinationValid( String newCountry, String newCity, String newStreetAddress)
    {
        List<ClassDestination> destinations = new ArrayList<ClassDestination>();
        destinations = ClassDestination.getArray();
        for (ClassDestination obj : destinations)
        {
            if (    Objects.equals(obj.getCountry(), newCountry) &&
                    Objects.equals(obj.getCity(), newCity) &&
                    Objects.equals(obj.getStreetAddress(), newStreetAddress)) return false;
        }
        return true;
    }
}
