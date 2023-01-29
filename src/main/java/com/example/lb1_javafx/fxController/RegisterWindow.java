package com.example.lb1_javafx.fxController;

import com.example.lb1_javafx.CallEndpoints;
import com.example.lb1_javafx.utils.FxUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.lb1_javafx.utils.SceneSwitcher.switchScene;

public class RegisterWindow implements Initializable {
    public TextField loginField;
    public TextField pswField;
    public Button loginButton;
    public Button registerButton;
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField emailField;
    public TextField numberField;
    public Button backButton;
    public ChoiceBox roleField;

    FxUtils fxUtils = new FxUtils();
    ObservableList<String> accountTypeChoices = FXCollections.observableArrayList("USER", "MANAGER", "DRIVER", "ADMIN", "");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        roleField.setItems(accountTypeChoices);
        roleField.setValue("");
    }

    public void validateRegistration(ActionEvent actionEvent) throws IOException {
        JSONObject json = new JSONObject();

        String getLogin = CallEndpoints.Get("http://localhost:8080/api/user/users?login=" + loginField.getText());

        if (    FxUtils.isFieldEmpty(firstNameField) ||
                FxUtils.isFieldEmpty(lastNameField) ||
                FxUtils.isFieldEmpty(loginField) ||
                FxUtils.isFieldEmpty(pswField) ||
                FxUtils.isFieldEmpty(emailField) ||
                FxUtils.isFieldEmpty(numberField))
        {
            fxUtils.alertErrorMsg(Alert.AlertType.ERROR, "Error", "Empty fields",
                    "Cant create an entry. No empty fields allowed.");
            return;
        }
        if (getLogin.length() == 2) {
            json.put("firstName", firstNameField.getText());
            json.put("lastName", lastNameField.getText());
            json.put("login", loginField.getText());
            json.put("password", pswField.getText());
            json.put("email", emailField.getText());
            json.put("phoneNumber", numberField.getText());
            CallEndpoints.Post("http://localhost:8080/api/user/registration", json.toString());
            System.out.println(json);
            switchScene("login-window.fxml", backButton);
        } else {
            fxUtils.alertErrorMsg(Alert.AlertType.ERROR, "Error", "Boohoo",
                    "Login already exists.");
        }
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        switchScene("login-window.fxml", backButton);
    }
}
