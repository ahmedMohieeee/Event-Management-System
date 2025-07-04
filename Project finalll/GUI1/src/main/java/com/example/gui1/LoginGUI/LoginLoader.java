package com.example.gui1.LoginGUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginLoader {

    public static void showLoginDashboard(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(LoginLoader.class.getResource("/com/example/gui1/LoginFx.fxml"));
            Parent root = loader.load();

            LoginDashboard controller = loader.getController();
            controller.setStage(stage);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();

        } catch (IOException e) {
            System.err.println("Failed to load LoginFx.fxml. Check the path and FXML file.");
            e.printStackTrace();
        }
    }
}
