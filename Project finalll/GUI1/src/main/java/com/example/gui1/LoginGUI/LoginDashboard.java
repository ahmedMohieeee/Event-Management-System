package com.example.gui1.LoginGUI;
import com.example.gui1.AdminGui.AdminDashboard;
import com.example.gui1.AttendeeGUI.HelloController;
import com.example.gui1.OrganizerGUI.OrganizerDashboard;
import com.example.gui1.RegisterGUI.RegisterDashboard;
import Projectt.Admin;
import Projectt.Attendee;
import Projectt.Database;
import Projectt.Organizer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginDashboard {


    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Label errorLabel;
    @FXML private Hyperlink registerLink;
          private Stage primaryStage;

    public  void setStage(Stage stage){
        this.primaryStage=stage;
    }
 @FXML public void handleLogin(ActionEvent event) throws IOException {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        boolean found =  false;

        if (username.isBlank() || password.isBlank()){
            return;
        }

        for (Admin admin : Database.admins) {
            if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                found = true;
               AdminDashboard adminDashboard= new AdminDashboard(primaryStage, admin);
                adminDashboard.show();
                break;
            }
        }

        if (!found) {
            for (Organizer organizer : Database.organizers) {
                if (organizer.getUsername().equals(username) && organizer.getPassword().equals(password)) {
                    found = true;
                   OrganizerDashboard organizerDashboard = new OrganizerDashboard(primaryStage, organizer);
                   organizerDashboard.show(primaryStage);
                    break;
                }
            }
        }

     if (!found) {
         for (Attendee attendee : Database.attendees) {
             if (attendee.getUsername().equals(username) && attendee.getPassword().equals(password)) {
                 FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gui1/hello-view.fxml"));
                 Parent root = loader.load();
                 HelloController helloController = loader.getController();
                 helloController.init(attendee);
                 Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                 helloController.displayName(username);

                 currentStage.setScene(new Scene(root));
                 currentStage.show();
                 return;
             }
         }
     }

     if(!found) {
         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setTitle("Invalid username or password");
         alert.setHeaderText("Please provide a valid username and password");
         alert.setContentText("");
         alert.showAndWait();
     }

     }
       @FXML public void handleRegister(){
             RegisterDashboard registerDashboard = new RegisterDashboard();
             registerDashboard.RegisterDashboard(primaryStage);
             registerDashboard.show();
    }
      ;
}

