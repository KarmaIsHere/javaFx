package com.example.lb1_javafx.fxController.preview;

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
import java.util.ResourceBundle;

import static com.example.lb1_javafx.utils.SceneSwitcher.switchScene;

public class ViewUser implements Initializable {

    @FXML
    public TableColumn<ClassUser, Integer> id;
    @FXML
    public TableColumn<ClassUser, UserAccountType> account_type;
    @FXML
    public TableColumn<ClassUser, String> first_name;
    @FXML
    public TableColumn<ClassUser, String> last_name;
    @FXML
    public TableColumn<ClassUser, UserStatus> status;
    @FXML
    public TableColumn<ClassUser, String> email;
    @FXML
    public TableColumn<ClassUser, String> login;
    @FXML
    public TableColumn<ClassUser, String> password;
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
    public Text phoneNumber;
    public TextField passwordField;
    public TextField lastNameField;
    public TextField firstNameField;
    public TextField phoneNumberField;
    public Button deleteButton;
    public TextField salaryField;
    public ChoiceBox statusChoice;
    public ChoiceBox accountTypeChoice;
    public Text Status;
    public TextField editLoginField;

    ObservableList<String> statusChoices = FXCollections.observableArrayList("FREE", "WORKING", "ABSENT");

    ObservableList<String> accountTypeChoices = FXCollections.observableArrayList("USER", "MANAGER", "DRIVER", "ADMIN");


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillTable();

        statusChoice.setItems(statusChoices);
        statusChoice.setValue("-----");

        accountTypeChoice.setItems(accountTypeChoices);
        accountTypeChoice.setValue("-----");
    }

    public void openCreate(ActionEvent actionEvent) throws IOException {
        switchScene("create-window.fxml", createButton);
    }

    public void openSearch(ActionEvent actionEvent) throws IOException {
        switchScene("search-window.fxml", createButton);
    }

    public void fillTable() {
        id.setCellValueFactory(new PropertyValueFactory<ClassUser, Integer>("id"));
        account_type.setCellValueFactory(new PropertyValueFactory<ClassUser, UserAccountType>("account_type"));
        first_name.setCellValueFactory(new PropertyValueFactory<ClassUser, String>("first_name"));
        last_name.setCellValueFactory(new PropertyValueFactory<ClassUser, String>("last_name"));
        status.setCellValueFactory(new PropertyValueFactory<ClassUser, UserStatus>("status"));
        email.setCellValueFactory(new PropertyValueFactory<ClassUser, String>("email"));
        login.setCellValueFactory(new PropertyValueFactory<ClassUser, String>("login"));
        password.setCellValueFactory(new PropertyValueFactory<ClassUser, String>("password"));
        phone_number.setCellValueFactory(new PropertyValueFactory<ClassUser, String>("phone_number"));
        salary.setCellValueFactory(new PropertyValueFactory<ClassUser, String>("salary"));
        tableUser.getItems().setAll(ClassUser.getArray());
    }


    public void createEntry(ActionEvent actionEvent) {
        JSONObject json = new JSONObject();

        String getLogin = CallEndpoints.Get("http://localhost:8080/api/user/users?login=" + loginField.getText());

        if (getLogin.length() == 2) {
            json.put("firstName", firstNameField.getText());
            json.put("lastName", lastNameField.getText());
            json.put("email", emailField.getText());
            json.put("login", loginField.getText());
            json.put("password", passwordField.getText());
            json.put("phoneNumber", phoneNumberField.getText());
            CallEndpoints.Post("http://localhost:8080/api/user/registration", json.toString());
            System.out.println(json);
            fillTable();
        } else {
            FxUtils fxUtils = new FxUtils();
            fxUtils.alertErrorMsg(Alert.AlertType.ERROR, "Error", "Boohoo",
                    "Login already exists.");
        }
    }

    public void searchTable(ActionEvent actionEvent) {

    }

    public void submitEdit(ActionEvent actionEvent) {
        Validation validate = new Validation();

        JSONObject json = new JSONObject();

        String getLogin = CallEndpoints.Get("http://localhost:8080/api/user/users?login=" + editLoginField.getText());

        if (getLogin.length() != 2) {
            String url = "http://localhost:8080/api/user/update";
            System.out.println(loginField.getText());
            if (loginField.getText() != null) {
                url = url.concat("?newLogin=");
                url = url.concat((loginField.getText()));
            }
            if (emailField.getText() != null) {
                url.concat("?newEmail=");
                url.concat((emailField.getText()));
            }
            if (passwordField.getText() != null) {
                url.concat("?newPassword=");
                url.concat((passwordField.getText()));
            }
            if (firstNameField.getText() != null) {
                url.concat("?newFirstName=");
                url.concat((firstNameField.getText()));
            }
            if (lastNameField.getText() != null) {
                url.concat("?newLastName=");
                url.concat((lastNameField.getText()));
            }
            if (phoneNumberField.getText() != null) {
                url.concat("?newPhoneNumber=");
                url.concat((phoneNumberField.getText()));
            }
            if (salaryField.getText() != null) {
                url.concat("?newSalary=");
                url.concat((salaryField.getText()));
            }
            if (statusChoice.getValue() != null) {
                url.concat("?newStatus=");
                url.concat((statusChoice.getValue().toString()));
            }

            if (accountTypeChoice.getValue() != null) {
                url.concat("?newAccountType=");
                url.concat((accountTypeChoice.getValue()).toString());
            }
            System.out.println(url);
            CallEndpoints.Put(url);
            fillTable();
        } else {
            FxUtils fxUtils = new FxUtils();
            fxUtils.alertErrorMsg(Alert.AlertType.ERROR, "Error", "Boohoo",
                    "Login already exists.");
        }
    }

    public void deleteEntry(ActionEvent actionEvent) {
        Validation validate = new Validation();

        JSONObject json = new JSONObject();

        String getLogin = CallEndpoints.Get("http://localhost:8080/api/user/users?id=" + idField.getText());
        if (getLogin.length() != 2) {
            CallEndpoints.DELETE("http://localhost:8080/api/user/delete?id=" + idField.getText());
            fillTable();
        } else {
            FxUtils fxUtils = new FxUtils();
            fxUtils.alertErrorMsg(Alert.AlertType.ERROR, "Error", "Boohoo",
                    "No such user exists with that id.");
        }
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        switchScene("main-window.fxml", backButton);
    }
}
