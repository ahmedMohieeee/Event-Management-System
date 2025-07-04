package com.example.gui1.AttendeeGUI;

import Projectt.Attendee;
import Projectt.Events;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;


public class ContinueRegistrationController {

    @FXML
    Label balanceLabel;
    @FXML
    Label ticketPrice;
    private Attendee attendee;

    public void display(Events selectedEvent, Attendee attendee){
        this.attendee=attendee;
        ticketPrice.setText("Ticket price: "+ selectedEvent.getTicketPrice());
        balanceLabel.setText("Your balance: "+ attendee.getBalance());
    }

    public void switchBack1(ActionEvent event) throws IOException {
      /* Parent root = FXMLLoader.load(getClass().getResource("/org/example/myjavafxgui/EventRegister.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();*/

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gui1/EventRegister.fxml"));
        Parent root = loader.load();
        EventsRegistrationController controller = loader.getController();
        controller.setAttendee(attendee);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void confirm(ActionEvent event) throws IOException {

        /*if((EventsRegistrationController.getSelectedEvent().getAvailability())==false){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No places are available");
            alert.setHeaderText("The event is sold out!");
            alert.setContentText("We are very sorry :(. Please select another event.");
            alert.showAndWait();
        }else{
            if(attendee.getBalance()<EventsRegistrationController.getSelectedEvent().getTicketPrice()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Cannot confirm registration");
                alert.setHeaderText("No enough balance!");
                alert.setContentText("Please select another event.");
                alert.showAndWait();
            }else{
                attendee.setBalance(attendee.getBalance()-EventsRegistrationController.getSelectedEvent().getTicketPrice());
                EventsRegistrationController.getSelectedEvent().addAttendee(attendee);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registration confirmed!");
                alert.setHeaderText(null);
                alert.setContentText("See you at the event. We hope you enjoy the event :)");
                alert.showAndWait();
                Attendee.updateObservableList();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gui1/EventRegister.fxml"));
                Parent root = loader.load();
                EventsRegistrationController controller = loader.getController();
                controller.setAttendee(attendee);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }*/

        Events selectedEvent = EventsRegistrationController.getSelectedEvent();
        if (!selectedEvent.getAvailability()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No places are available");
            alert.setHeaderText("The event is sold out!");
            alert.setContentText("We are very sorry :(. Please select another event.");
            alert.showAndWait();
        } else if (attendee.getBalance() < selectedEvent.getTicketPrice()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Cannot confirm registration");
            alert.setHeaderText("No enough balance!");
            alert.setContentText("Please select another event.");
            alert.showAndWait();
        } else {
            attendee.setBalance(attendee.getBalance() - selectedEvent.getTicketPrice());
            selectedEvent.addAttendee(attendee);


            if (!Attendee.registeredEvents.contains(selectedEvent)) {
                Attendee.registeredEvents.add(selectedEvent);
                Attendee.updateObservableList();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registration confirmed!");
            alert.setHeaderText(null);
            alert.setContentText("See you at the event. We hope you enjoy the event :)");
            alert.showAndWait();
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gui1/EventRegister.fxml"));
        Parent root = loader.load();
        EventsRegistrationController controller = loader.getController();
        controller.setAttendee(attendee);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

