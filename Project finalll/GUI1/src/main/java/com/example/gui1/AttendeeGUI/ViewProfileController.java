package com.example.gui1.AttendeeGUI;
import Projectt.Attendee;

import Projectt.Category;
import com.example.gui1.LoginGUI.LoginDashboard;
import com.example.gui1.LoginGUI.LoginLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewProfileController {

    @FXML
    Label UsernameViewP;
    @FXML
    Label PasswordViewP;
    @FXML
    Label DateOfBirthViewP;
    @FXML
    Label GenderViewP;
    @FXML
    Label AddressViewP;
    @FXML
    Label InterestsViewP;
    @FXML
    Label BalanceViewP;
    @FXML
    Button LogoutButton;
    @FXML
    private AnchorPane ViewProfilePane;
    public Attendee attendee;


    public void setAttendee(Attendee attendee) {
        this.attendee = attendee;
        displayNameViewP(attendee);
    }

    public void displayNameViewP(Attendee attendee){
        UsernameViewP.setText("Username: "+ attendee.getUsername());
        PasswordViewP.setText("Password: "+ attendee.getPassword());
        DateOfBirthViewP.setText("Date of birth: "+ attendee.getDateOfBirth());
        GenderViewP.setText("Gender: "+ attendee.getGender());
        AddressViewP.setText("Address: "+ attendee.getAddress());
        BalanceViewP.setText("Balance: "+ attendee.getBalance());

        if (attendee.getSelectedInterests() != null) {
            StringBuilder sb = new StringBuilder("Interests: \n");
            for (Category selectedInterests : attendee.getSelectedInterests()) {
                sb.append(selectedInterests).append("\n");
            }
            if (sb.length() > 3) {
                sb.setLength(sb.length() - 2);
            }
            InterestsViewP.setText(sb.toString());
        } else {
            InterestsViewP.setText("Interests: None selected");
        }
        /*
        StringBuilder sb = new StringBuilder("Interests: ");
        for (Category selectedInterests: attendee.getSelectedInterests() ) {
            sb.append(selectedInterests).append(", ");
        }
        if (sb.length() > 3) {
            sb.setLength(sb.length() - 2);
        }
        InterestsViewP.setText(sb.toString());*/
    }

    public void switchBack(ActionEvent event) throws IOException {
      /*  Parent root = FXMLLoader.load(getClass().getResource("/org/example/myjavafxgui/hello-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();*/

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gui1/hello-view.fxml"));
        Parent root = loader.load();
        HelloController controller = loader.getController();
        controller.init(attendee);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

   /* public void logout(ActionEvent event){

        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You are about to logout");
        alert.setContentText("Are you sure you want to logout?");

        if(alert.showAndWait().get()== ButtonType.OK){
            Stage stage=(Stage)ViewProfilePane.getScene().getWindow();
            System.out.println("You successfully logged out");
            stage.close();
        }
    }*/

    /*public void logout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(LoginLoader.class.getResource("/com/example/gui1/LoginFx.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }*/

    public void logout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gui1/LoginFx.fxml"));
        Parent root = loader.load();

        LoginDashboard loginController = loader.getController();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        loginController.setStage(stage);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}


