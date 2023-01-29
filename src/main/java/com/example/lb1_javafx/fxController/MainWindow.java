package com.example.lb1_javafx.fxController;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

import static com.example.lb1_javafx.utils.SceneSwitcher.switchScene;

public class MainWindow {
    public Button openForumButton;
    public Button openDestinationButton;
    public Button openTruckButton;
    public Button openCommentButton;
    public Button openTripButton;
    public Button openUserButton;
    public Button logOutButton;
    public Button openShipmentsButton;
    public Button openStopPointsButton;
    public Tab user;
    public Tab truck;
    public Tab comment;
    public Tab forum;
    public Tab trip;
    public Tab destination;
    public Tab shipment;
    public Tab stopPoint;
    public AnchorPane ViewUser;
    public AnchorPane ViewTruck;
    public AnchorPane ViewComment;
    public AnchorPane ViewForum;
    public AnchorPane ViewTrip;
    public AnchorPane ViewDestination;
    public AnchorPane ViewShipment;
    public AnchorPane ViewStopPoint;
    public AnchorPane DriverMain;
    public Tab manageTrips;
    public TabPane mainTabPane;
    public Tab driverMain;


    public void goBack(ActionEvent actionEvent) throws IOException {
        switchScene("login-window.fxml", openUserButton);
    }

}
