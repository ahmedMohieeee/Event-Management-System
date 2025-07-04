package com.example.gui1.AttendeeGUI;

import Projectt.Attendee;
import Projectt.Category;
import Projectt.Database;
import com.example.gui1.LoginGUI.LoginLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


public class HelloController {

    @FXML
    Button LogoutButton;
    @FXML
    Button submitNewUsernameButt;
    @FXML
    Label AttendeeName;
    @FXML
    Label UsernameViewP;
    @FXML
    TextField usernameTextField;
    @FXML
    TextField OldPass;
    @FXML
    TextField NewPass;
    @FXML
    TextField NewPassCheck;
    @FXML
    private Button SubmitButton;
    @FXML
    private AnchorPane scenePane;
    @FXML
    TextField usernameInput;
    @FXML
    TextField addressInput;
    @FXML
    Label labelChange;
    @FXML
    Button submitDate;
    @FXML
    DatePicker dateOfBirth;


    @FXML
    private RadioButton ViewYourProfile, EditPassword, EditUsername, EditDateOfBirth, AddInterests, EventRegister, RefundTicket;

    private Attendee attendee;
    private Stage primaryStage;

    public HelloController(){}

    public void init(Attendee attendee) {
//        this.primaryStage = primaryStage;
        this.attendee = attendee;
        displayName(attendee.getUsername());
    }

    public HelloController(Stage primaryStage,Attendee attendee){
        init(attendee);
    }

    public void displayName(String username) {
        AttendeeName.setText("Hello: " + username);
    }

    public void setAttendee(Attendee attendee) {
        this.attendee = attendee;
    }



    public void NewPassCheck(ActionEvent event) throws IOException {
        String pass = OldPass.getText();
        String pass1 = NewPass.getText();
        String pass2 = NewPassCheck.getText();
        String check = "";

        if ((pass.equals(check)) || (pass1.equals(check)) || (pass2.equals(check))) {
            if (pass.equals(check)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("You must enter your old password!");
                alert.setContentText("Enter your old password.");
                alert.showAndWait();
            } else if (((pass1.equals(check)) && (pass2.equals(check))) || ((pass1.equals(check)) || (pass2.equals(check)))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("You must enter your new password!");
                alert.setContentText("Enter your new password.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Mismatch");
                alert.setHeaderText("Passwords do not match!");
                alert.setContentText("Re-enter your new password..");
                alert.showAndWait();
            }
        } else if ((pass1.equals(pass2))&&(pass.equals(attendee.getPassword()))&&(pass.equals(pass2))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid");
            alert.setHeaderText("Your new password must not match your old password!");
            alert.setContentText("Re-enter your new password.");
            alert.showAndWait();
        }else if((pass1.equals(pass2))&&(pass.equals(attendee.getPassword()))&&!(pass.equals(pass2))){
            if((attendee.passwordCheck(pass2))==true) {
                attendee.setPassword(pass2);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Password Changed!");
                alert.setHeaderText(null);
                alert.setContentText("Your password has been successfully changed.");
                alert.showAndWait();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gui1/hello-view.fxml"));
                Parent root = loader.load();
                HelloController controller = loader.getController();
                controller.init(attendee);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Mismatch");
                alert.setHeaderText("Password does not match the required format!");
                alert.setContentText("Re-enter your new password. (The password must be 8 characters or more. \nThe password can not contain spaces.\nThe password must contain a digit, lower case, upper case, and a special character.)");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Mismatch");
            alert.setHeaderText("Passwords do not match!");
            alert.setContentText("Re-enter your password.");
            alert.showAndWait();
        }
    }

   /* public void logout(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You are about to logout");
        alert.setContentText("Are you sure you want to logout?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            Stage stage = (Stage) scenePane.getScene().getWindow();
            System.out.println("You successfully logged out");
            stage.close();
        }
    }*/

    public void switchToViewProfile(ActionEvent event) throws IOException {
       /* FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/myjavafxgui/ViewProfile.fxml"));
        Parent root = loader.load();
        ViewProfileController controller = loader.getController();
        controller.displayNameViewP(attendee);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();*/

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gui1/ViewProfile.fxml"));
        Parent root = loader.load();
        ViewProfileController controller = loader.getController();
        controller.setAttendee(attendee);
        displayName(attendee.getUsername());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();


    }

    public void switchToEditUsername(ActionEvent event) throws IOException {
      /*  Parent root = FXMLLoader.load(getClass().getResource("/org/example/myjavafxgui/EditUsername.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();*/

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gui1/EditUsername.fxml"));
        Parent root = loader.load();
        HelloController controller = loader.getController();
        controller.setAttendee(attendee);
        displayName(attendee.getUsername());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void switchToEditPassword(ActionEvent event) throws IOException {
       /*Parent root = FXMLLoader.load(getClass().getResource("/org/example/myjavafxgui/EditPassword.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();*/

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gui1/EditPassword.fxml"));
        Parent root = loader.load();
        HelloController controller = loader.getController();
        controller.setAttendee(attendee);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToEditDateOfBirth(ActionEvent event) throws IOException {
       /* Parent root = FXMLLoader.load(getClass().getResource("/org/example/myjavafxgui/EditDateOfBirth.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();*/

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gui1/EditDateOfBirth.fxml"));
        Parent root = loader.load();
        HelloController controller = loader.getController();
        controller.setAttendee(this.attendee);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }

    public void switchToAddInterests(ActionEvent event) throws IOException {
        Category.updateObservableList();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gui1/AddInterests.fxml"));
        Parent root = loader.load();
        AddInterestsController controller = loader.getController();
        controller.setAttendee(attendee);
        //Parent root = FXMLLoader.load(getClass().getResource("/org/example/myjavafxgui/AddInterests.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToEventRegister(ActionEvent event) throws IOException {
        //Parent root = FXMLLoader.load(getClass().getResource("/org/example/myjavafxgui/EventRegister.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gui1/EventRegister.fxml"));
        Parent root = loader.load();
        EventsRegistrationController controller = loader.getController();
        controller.setAttendee(attendee);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        // new EventsRegistrationController(attendee);



    }

    public void switchToRefundTicket(ActionEvent event) throws IOException {
        //Parent root = FXMLLoader.load(getClass().getResource("/org/example/myjavafxgui/RefundTicket.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gui1/RefundTicket.fxml"));
        Parent root = loader.load();
        RefundTicketController controller = loader.getController();
        controller.setAttendee(attendee);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        //new RefundTicketController(this.attendee);


    }

    public void switchBack(ActionEvent event) throws IOException {
       /* Parent root = FXMLLoader.load(getClass().getResource("/org/example/myjavafxgui/hello-view.fxml"));
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

    public void addUsername(ActionEvent event) throws IOException {
        String username = usernameInput.getText();
        boolean check = true;

        for (Attendee attendee : Database.attendees) {
            if (username.equalsIgnoreCase(attendee.getUsername())) {
                check = false;
                break;
            }
        }
        if (username.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("You must enter your new username!");
            alert.setContentText("Enter your new username.");
            alert.showAndWait();
        } else if (check == false) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("This username is unavailable!");
            alert.setContentText("Re-enter your new username.");
            alert.showAndWait();
        } else if (!attendee.usernameCheck(username)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Username is invalid!");
            alert.setContentText("Re-enter your new username.");
            alert.showAndWait();
        } else {
            attendee.setUsername(username);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Username Changed");
            alert.setHeaderText(null);
            alert.setContentText("Your username has been successfully changed.");
            alert.showAndWait();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gui1/hello-view.fxml"));
            Parent root = loader.load();
            HelloController controller = loader.getController();
            controller.init(attendee);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void edtAddress(ActionEvent event) throws IOException {
        String address = addressInput.getText();
        if (address.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("You must enter your new address!");
            alert.setContentText("Enter your new address.");
            alert.showAndWait();
        }else if(address.equals(attendee.getAddress())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("You must enter your new address!");
            alert.setContentText("Enter your new address.");
            alert.showAndWait();
        }else{
            attendee.setAddress(address);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Address Changed");
            alert.setHeaderText(null);
            alert.setContentText("Your address has been successfully changed.");
            alert.showAndWait();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gui1/hello-view.fxml"));
            Parent root = loader.load();
            HelloController controller = loader.getController();
            controller.init(attendee);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }


    public void newDateOfBirth(ActionEvent event) {
        LocalDate birth = dateOfBirth.getValue();
        String myFormattedDate = birth.format(DateTimeFormatter.ofPattern("MMM-dd-yyyy"));
        labelChange.setText(myFormattedDate);
    }

    public void submitDateButt(ActionEvent event) throws IOException {
        ChronoLocalDate date1 = LocalDate.of(2015, 1, 1);
        ChronoLocalDate date2 = LocalDate.of(1940, 1, 1);
        if(dateOfBirth.getValue()!=null) {
            LocalDate birth = dateOfBirth.getValue();
            if(birth.isAfter(date1)|| birth.isBefore(date2)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid");
                alert.setHeaderText("You must enter a valid date of birth!");
                alert.setContentText("Please enter a valid date of birth.");
                alert.showAndWait();
            }else{
            String myFormattedDate = birth.format(DateTimeFormatter.ofPattern("MMM-dd-yyyy"));
            attendee.setDateOfBirth(myFormattedDate);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Date of birth Changed!");
            alert.setHeaderText(null);
            alert.setContentText("Your date of birth has been successfully changed.");
            alert.showAndWait();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gui1/hello-view.fxml"));
            Parent root = loader.load();
            HelloController controller = loader.getController();
            controller.init(attendee);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();}
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You did not enter your date of birth!");
            alert.setContentText("Please enter your date of birth.");
            alert.showAndWait();
        }
    }
}



