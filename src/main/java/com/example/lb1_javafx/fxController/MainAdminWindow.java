package com.example.lb1_javafx.fxController;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.io.IOException;

import static com.example.lb1_javafx.utils.SceneSwitcher.switchScene;

public class MainAdminWindow {
    public Button openForumButton;
    public Button openDestinationButton;
    public Button openTruckButton;
    public Button openCommentButton;
    public Button openTripButton;
    public Button openUserButton;
    public Button logOutButton;
    public Button openShipmentsButton;
    public Button openStopPointsButton;

    public void openUser(ActionEvent actionEvent) throws IOException {
        switchScene("view-user.fxml", openUserButton);
    }

    public void openTrip(ActionEvent actionEvent) throws IOException{
        switchScene("view-trip.fxml", openUserButton);
    }

    public void openComment(ActionEvent actionEvent) throws IOException{
        switchScene("view-comment.fxml", openUserButton);
    }

    public void openTruck(ActionEvent actionEvent) throws IOException{
        switchScene("view-truck.fxml", openUserButton);
    }

    public void openDestination(ActionEvent actionEvent) throws IOException{
        switchScene("view-destination.fxml", openUserButton);
    }

    public void openForum(ActionEvent actionEvent) throws IOException{
        switchScene("view-forum.fxml", openUserButton);
    }

    public void openShipments(ActionEvent actionEvent) throws IOException {
        switchScene("view-shipment.fxml", openUserButton);
    }

    public void openStopPoints(ActionEvent actionEvent) throws IOException {
        switchScene("view-stop-point.fxml", openUserButton);
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        switchScene("login-window.fxml", openUserButton);
    }
}