package com.example.gui1.OrganizerGUI;

import Projectt.Organizer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;

public class PaidAttendeesView {

    private final Organizer organizer;

    public PaidAttendeesView(Organizer organizer) {
        this.organizer = organizer;
    }

    public void show(Stage primaryStage) {
        BorderPane brpane = new BorderPane();

        Label title = new Label("💰 Paid Attendees");
        HBox htitle = new HBox(title);
        title.setStyle("-fx-font-size:20px; -fx-font-weight:bold");
        htitle.setPadding(new Insets(20));
        htitle.setAlignment(Pos.CENTER);
        brpane.setTop(htitle);

        HBox hbox1 = new HBox(10);
        Label eventName = new Label("Enter the event name: ");
        TextField tfeventName = new TextField();
        hbox1.setPadding(new Insets(10));
        hbox1.getChildren().addAll(eventName, tfeventName);

        Button showAttendeesBtn = new Button("Show Attendees");
        Button backBtn = new Button("Back to dashboard");
        backBtn.setOnAction(e -> new OrganizerDashboard(primaryStage, organizer).show(primaryStage));

        HBox hbox2 = new HBox(10, backBtn, showAttendeesBtn);
        hbox2.setPadding(new Insets(10));
        hbox2.setSpacing(10);
        brpane.setBottom(hbox2);

        ListView<String> attendeesPaidView = new ListView<>();
        attendeesPaidView.setMaxHeight(250);
        attendeesPaidView.setMaxWidth(250);
        attendeesPaidView.setVisible(false);

        showAttendeesBtn.setOnAction(e -> {
            String input = tfeventName.getText();
            attendeesPaidView.getItems().clear();

            if (input.isEmpty()) {
                showAlert("Error, Event name cannot be empty");
                return;
            }

            List<String> attendeesPaid = organizer.showPaidAttendees(input);
            if (attendeesPaid == null) {
                showAlert("Event not found");
            } else if (attendeesPaid.isEmpty()) {
                attendeesPaidView.getItems().add("No attendees paid for this event");
            } else {
                attendeesPaidView.getItems().addAll(attendeesPaid);
            }
            attendeesPaidView.setVisible(true);
        });

        VBox center = new VBox(15, hbox1, attendeesPaidView);
        center.setAlignment(Pos.CENTER);
        brpane.setCenter(center);

        Scene scene = new Scene(brpane, 400, 500);
        URL cssUrl = getClass().getResource("/com/example/gui1/buttonStyle.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("CSS file not found!");
        }
        primaryStage.setTitle("Paid Attendees");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}