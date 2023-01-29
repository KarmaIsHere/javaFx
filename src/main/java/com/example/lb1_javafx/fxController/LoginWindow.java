package com.example.lb1_javafx.fxController;

import com.example.lb1_javafx.CallEndpoints;
import com.example.lb1_javafx.utils.FxUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

import static com.example.lb1_javafx.utils.SceneSwitcher.switchScene;

public class LoginWindow {
    public TextField loginField;
    public TextField pswField;
    public Button loginButton;
    public Button regButton;

    public void validate(ActionEvent actionEvent) throws IOException {

        String getLogin = CallEndpoints.Get("http://localhost:8080/api/user/users?login=" + loginField.getText() + "&password=" + pswField.getText());

        if (getLogin.length() != 2) {
            switchScene("main-window.fxml", loginButton);
        } else {
            FxUtils fxUtils = new FxUtils();
            fxUtils.alertErrorMsg(Alert.AlertType.ERROR, "Error", "Boohoo",
                    "Wrong login or password");
        }


    }

    public void goRegister(ActionEvent actionEvent) throws IOException {
        switchScene("register-window.fxml", loginButton);
    }
}
