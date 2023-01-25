package com.example.lb1_javafx.fxController.manager;

import com.example.lb1_javafx.CallEndpoints;
import com.example.lb1_javafx.model.user.UserAccountType;
import com.example.lb1_javafx.model.user.UserStatus;
import com.example.lb1_javafx.model.user.ClassUser;
import com.example.lb1_javafx.utils.FxUtils;
import com.example.lb1_javafx.utils.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.example.lb1_javafx.utils.SceneSwitcher.switchScene;

public class ViewDriver implements Initializable {

    @FXML
    public TableColumn<ClassUser, Integer> id;
    @FXML
    public TableColumn<ClassUser, String> first_name;
    @FXML
    public TableColumn<ClassUser, String> last_name;
    @FXML
    public TableColumn<ClassUser, UserStatus> user_status;
    @FXML
    public TableColumn<ClassUser, String> email;
    @FXML
    public TableColumn<ClassUser, String> login;
    @FXML
    public TableColumn<ClassUser, String> phone_number;
    @FXML
    public TableColumn<ClassUser, String> salary;
    @FXML
    public TableView<ClassUser> tableUser;
    public Button createButton;
    public Button searchButton;
    public TextField textField;
    public Button refresh;
    public Button editButton;
    public TextField idField;
    public TextField loginField;
    public TextField emailField;
    public TextField accountTypeField;
    public TextField statusField;
    public Button backButton;
    public TextField passwordField;
    public TextField lastNameField;
    public TextField firstNameField;
    public TextField phoneNumberField;
    public Button deleteButton;
    public TextField salaryField;
    public ChoiceBox statusChoice;
    public ChoiceBox accountTypeChoice;
    public TextField editLoginField;

    FxUtils fxUtils = new FxUtils();

    ObservableList<String> statusChoices = FXCollections.observableArrayList("FREE", "WORKING", "ABSENT");

    ObservableList<String> accountTypeChoices = FXCollections.observableArrayList("USER", "MANAGER", "DRIVER", "ADMIN");


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillAllTable();

        statusChoice.setItems(statusChoices);
        statusChoice.setValue("-----");

        accountTypeChoice.setItems(accountTypeChoices);
        accountTypeChoice.setValue("-----");
    }

    public void fillTable() {
        id.setCellValueFactory(new PropertyValueFactory<ClassUser, Integer>("id"));
        first_name.setCellValueFactory(new PropertyValueFactory<ClassUser, String>("first_name"));
        last_name.setCellValueFactory(new PropertyValueFactory<ClassUser, String>("last_name"));
        user_status.setCellValueFactory(new PropertyValueFactory<ClassUser, UserStatus>("status"));
        email.setCellValueFactory(new PropertyValueFactory<ClassUser, String>("email"));
        login.setCellValueFactory(new PropertyValueFactory<ClassUser, String>("login"));
        phone_number.setCellValueFactory(new PropertyValueFactory<ClassUser, String>("phone_number"));
        salary.setCellValueFactory(new PropertyValueFactory<ClassUser, String>("salary"));
    }

    public void fillSearchTable(String body) {
        fillTable();
        tableUser.getItems().setAll(ClassUser.getArray(body));
    }

    public void fillAllTable() {
        fillTable();
        tableUser.getItems().setAll(ClassUser.getArray());
    }


    public void createEntry(ActionEvent actionEvent) {
        JSONObject json = new JSONObject();

        String getLogin = CallEndpoints.Get("http://localhost:8080/api/user/users?login=" + loginField.getText());

        if (    FxUtils.isFieldEmpty(firstNameField) ||
                FxUtils.isFieldEmpty(lastNameField) ||
                FxUtils.isFieldEmpty(emailField) ||
                FxUtils.isFieldEmpty(loginField) ||
                FxUtils.isFieldEmpty(passwordField) ||
                FxUtils.isFieldEmpty(phoneNumberField))
        {
            fxUtils.alertErrorMsg(Alert.AlertType.ERROR, "Error", "Empty fields",
                    "Cant create an entry with empty fields");
            return;
        }
        if (getLogin.length() == 2) {
            json.put("firstName", firstNameField.getText());
            json.put("lastName", lastNameField.getText());
            json.put("email", emailField.getText());
            json.put("login", loginField.getText());
            json.put("password", passwordField.getText());
            json.put("phoneNumber", phoneNumberField.getText());
            CallEndpoints.Post("http://localhost:8080/api/user/registration", json.toString());
            System.out.println(json);
            fillAllTable();
        } else {
            fxUtils.alertErrorMsg(Alert.AlertType.ERROR, "Error", "Boohoo",
                    "Login already exists.");
        }
    }



    public void searchTable(ActionEvent actionEvent) {
        String url = getUrl("http://localhost:8080/api/user/users", false);
        System.out.println(url);
        String response = CallEndpoints.Get(url);

//        if(response.charAt(0) == '{'){
//            FxUtils fxUtils = new FxUtils();
//            fxUtils.alertErrorMsg(Alert.AlertType.ERROR, "Welp", "Boohoo",
//                    "Nothing was found");
//        }
        /*else*/ fillSearchTable(response);
    }

    public void submitEdit(ActionEvent actionEvent) throws IOException {
        String getLogin = CallEndpoints.Get("http://localhost:8080/api/user/users?login=" + editLoginField.getText());

        if (getLogin.length() != 2)
        {
            String url = getUrl("http://localhost:8080/api/user/update", true);
            System.out.println(url);
            String statusCode = CallEndpoints.Put(url);
            fillAllTable();
        } else {
            FxUtils fxUtils = new FxUtils();
            fxUtils.alertErrorMsg(Alert.AlertType.ERROR, "Error", "Boohoo",
                    "Such login does not exist.");
        }
    }

    public void deleteEntry(ActionEvent actionEvent) throws IOException {
        String getLogin = CallEndpoints.Get("http://localhost:8080/api/user/users?id=" + idField.getText());
        if (getLogin.length() != 2) {
            String statusCode = CallEndpoints.DELETE("http://localhost:8080/api/user/delete?id=" + idField.getText());
            fillTable();
        } else {
            FxUtils fxUtils = new FxUtils();
            fxUtils.alertErrorMsg(Alert.AlertType.ERROR, "Error", "Boohoo",
                    "No such user exists with that id.");
        }
    }
    public void goBack(ActionEvent actionEvent) throws IOException {
        switchScene("login-window.fxml", backButton);
    }

    private String getUrl(String url, boolean isPut)
    {
        String originalUrl = url;
        if (!FxUtils.isFieldEmpty(editLoginField)) {
            url = addSeparator(url, originalUrl);
            url = url + "login=";
            url = url + (editLoginField.getText());
        }
        if (!FxUtils.isFieldEmpty(loginField)) {
            url = addSeparator(url, originalUrl);
            url = url + "Login=";
            url = url + (loginField.getText());
        }
        if (!FxUtils.isFieldEmpty(emailField)) {
            url = addSeparator(url, originalUrl);
            url = url + "Email=";
            url = url + (emailField.getText());
        }
        if (!FxUtils.isFieldEmpty(passwordField)) {
            url = addSeparator(url, originalUrl);
            url = url + "Password=";
            url = url + (passwordField.getText());
        }
        if (!FxUtils.isFieldEmpty(firstNameField)) {
            url = addSeparator(url, originalUrl);
            url = url + "FirstName=";
            url = url + (firstNameField.getText());
        }
        if (!FxUtils.isFieldEmpty(lastNameField)) {
            url = addSeparator(url, originalUrl);
            url = url + "LastName=";
            url = url + (lastNameField.getText());
        }
        if (!FxUtils.isFieldEmpty(phoneNumberField)) {
            url = addSeparator(url, originalUrl);
            url = url + "PhoneNumber=";
            url = url + (phoneNumberField.getText());
        }
        if (!FxUtils.isFieldEmpty(salaryField)) {
            url = addSeparator(url, originalUrl);
            url = url + "Salary=";
            url = url + (salaryField.getText());
        }
        if (statusChoice.getValue() != null && statusChoice.getValue() != "-----") {
            url = addSeparator(url, originalUrl);
            url = url + "Status=";
            url = url + (statusChoice.getValue().toString());
        }
        if (accountTypeChoice.getValue() != null && statusChoice.getValue() != "-----") {
            url = addSeparator(url, originalUrl);
            url = url + "AccountType=";
            url = url + (accountTypeChoice.getValue()).toString();
        }
        return url;
    }
    private String addSeparator(String url, String originalUrl)
    {
        if (url == originalUrl) url = url + "?";
        else url = url + "&";
        return url;
    }

}
