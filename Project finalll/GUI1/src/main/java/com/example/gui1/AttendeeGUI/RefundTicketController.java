package com.example.gui1.AttendeeGUI;

import Projectt.Attendee;
import Projectt.Events;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class RefundTicketController {

    @FXML
    Label selectedEvent;
    @FXML
    ListView<Events> EventsToRefund;
    @FXML
    Button confirmRefund;
    private Attendee attendee;
    private static Events selectedEventRefund;


    public RefundTicketController(){
    }

    public static void setSelectedEventRefund(Events event) {
        selectedEventRefund = event;
    }

    public void setAttendee(Attendee attendee) {
        this.attendee = attendee;
    }


    public void initialize(){

        EventsToRefund.setItems(Attendee.observableRegisteredEvents);
        EventsToRefund.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void confirmRefund(ActionEvent event) throws IOException {
      /*  Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Refund ticket");
        alert.setHeaderText("You are about to refund your ticket!");
        alert.setContentText("Are you sure you want to refund your ticket?");

        if ((alert.showAndWait().get() == ButtonType.OK)&&(selectedEventRefund!=null)) {
            setSelectedEventRefund(EventsToRefund.getSelectionModel().getSelectedItem());
            attendee.setBalance(attendee.getBalance()+selectedEventRefund.getTicketPrice());
            selectedEventRefund.removeAttendee(attendee);
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Refund confirmed!");
            alert1.setHeaderText(null);
            alert1.setContentText("we are sad that you wouldn't be able to attend :(.");
            alert1.showAndWait();
            Attendee.updateObservableList();
        }*/

        if(EventsToRefund.getSelectionModel().getSelectedItem()==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Cannot refund");
            alert.setHeaderText("You did not select an event to refund!");
            alert.setContentText("Please select an event to refund.");
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Refund ticket");
            alert.setHeaderText("You are about to refund your ticket!");
            alert.setContentText("Are you sure you want to refund your ticket?");

            if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                selectedEventRefund = EventsToRefund.getSelectionModel().getSelectedItem();
                if (selectedEventRefund != null) {
                    attendee.setBalance(attendee.getBalance() + selectedEventRefund.getTicketPrice());
                    selectedEventRefund.removeAttendee(attendee);
                    Attendee.registeredEvents.remove(selectedEventRefund);
                    Attendee.updateObservableList();

                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Refund confirmed!");
                    alert1.setHeaderText(null);
                    alert1.setContentText("We're sad you won't be able to attend :(");
                    alert1.showAndWait();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gui1/hello-view.fxml"));
                    Parent root = loader.load();
                    HelloController controller = loader.getController();
                    controller.init(attendee);
                    controller.setAttendee(attendee);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            }
        }
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
}
