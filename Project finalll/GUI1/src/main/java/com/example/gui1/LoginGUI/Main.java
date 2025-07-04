package com.example.gui1.LoginGUI;

import Projectt.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Database.addData();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gui1/LoginFx.fxml"));
        Parent root = loader.load();

        LoginDashboard controller = loader.getController();
        controller.setStage(primaryStage);


        primaryStage.setTitle("Event Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            logout(primaryStage);
        });
    }

    public void logout(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You are about to exit");
        alert.setContentText("Are you sure you want to exit?");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            System.out.println("You successfully exited");
            stage.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
