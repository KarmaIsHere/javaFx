package com.example.lb1_javafx.utils;

import com.example.lb1_javafx.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {

    public static void switchScene (String window, Button createButton) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(window));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) createButton.getScene().getWindow();
        stage.setTitle(window);
        stage.setScene(scene);
        stage.show();
    }
}
