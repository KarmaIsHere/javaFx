package com.example.lb1_javafx.utils;

import javafx.scene.control.Alert;

public class FxUtils {
    public static void alertErrorMsg(Alert.AlertType alertType, String title, String headerText, String alertMsg){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(alertMsg);
        alert.showAndWait();
    }

}
