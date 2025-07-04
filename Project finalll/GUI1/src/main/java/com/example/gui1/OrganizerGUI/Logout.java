package com.example.gui1.OrganizerGUI;

import com.example.gui1.LoginGUI.LoginLoader;
import com.example.gui1.LoginGUI.LoginDashboard;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class Logout{

    public void logout(Stage primaryStage) {
        LoginLoader.showLoginDashboard(primaryStage);

//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Logout");
//        alert.setHeaderText("You are about to logout");
//        alert.setContentText("Are you sure you want to logout?");
//
//        alert.showAndWait().ifPresent(response -> {
//            if (response == ButtonType.OK) {
//                System.out.println("User logged out successfully");
//
//                primaryStage.close();
//
//                Stage loginStage = new Stage();
//                try {
//                    LoginLoader.showLoginDashboard(loginStage);
//                } catch (Exception e) {
//                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
//                    alert2.setContentText("An error occurred: " + e.getMessage());
//                    alert2.show();
//
//                }
//            }
//        });
//    }
    }}