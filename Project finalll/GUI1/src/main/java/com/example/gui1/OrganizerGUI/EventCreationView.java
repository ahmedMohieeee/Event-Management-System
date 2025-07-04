package com.example.gui1.OrganizerGUI;

import Projectt.Events;
import Projectt.Organizer;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class EventCreationView {
    private final Organizer organizer;

    public EventCreationView(Organizer organizer) {
        this.organizer = organizer;
    }

    public void show(Stage primaryStage) {
        BorderPane brPane = new BorderPane();

        Label title = new Label("\uD83D\uDCC5 New Event Planner \uD83D\uDCC5");
        title.setStyle("-fx-padding:20;-fx-font-size:20px; -fx-font-weight:bold;");
        brPane.setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);

        GridPane grPane = new GridPane();
        brPane.setCenter(grPane);
        brPane.setPadding(new Insets(20));

        TextField tfname = new TextField();
        TextField tfticketprice = new TextField();
        TextField tfdate = new TextField();
        TextField tfroom = new TextField();

        Button nextBtn = new Button("Next");
        Button backBtn = new Button("Back to dashboard");

        HBox buttonBox = new HBox(10, backBtn, nextBtn);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        buttonBox.setPadding(new Insets(10));
        brPane.setBottom(buttonBox);

        grPane.setHgap(2);
        grPane.setVgap(4);
        grPane.add(new Label("Name: "), 0, 0);
        grPane.add(tfname, 1, 0);
        grPane.add(new Label("Ticket Price: "), 0, 1);
        grPane.add(tfticketprice, 1, 1);
        grPane.add(new Label("Date and time e.g, 2025-07-28 15:00"), 0, 2);
        grPane.add(tfdate, 1, 2);
        grPane.add(new Label("Room name: "), 0, 3);
        grPane.add(tfroom, 1, 3);

        tfname.setAlignment(Pos.BOTTOM_RIGHT);
        tfticketprice.setAlignment(Pos.BOTTOM_RIGHT);
        tfdate.setAlignment(Pos.BOTTOM_RIGHT);
        tfroom.setAlignment(Pos.BOTTOM_RIGHT);

        backBtn.setOnAction(e -> new OrganizerDashboard(primaryStage, organizer).show(primaryStage));

        nextBtn.setOnAction(e -> {
            String name = tfname.getText().trim();
            String price = tfticketprice.getText().trim();
            String date = tfdate.getText().trim();
            String room = tfroom.getText().trim();

            if (name.isEmpty() || price.isEmpty() || date.isEmpty() || room.isEmpty()) {
                showAlert("Please fill in all fields");
                return;
            }

            double ticketPrice;
            try {
                ticketPrice = Double.parseDouble(price);
            } catch (NumberFormatException ex) {
                showAlert("Invalid ticket price. Please enter a valid number");
                return;
            }

            if (ticketPrice<=0) {
                showAlert("Ticket price can not be negative");
                return;
            }

            LocalDateTime dateText;
            try {
                dateText = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            } catch (DateTimeParseException ex) {
                showAlert("Invalid date/format. Please use yyyy-MM-dd HH:mm");
                return;
            }

            if (dateText.isBefore(LocalDateTime.now())) {
                showAlert("The event date/time cannot be in the past.");
                return;
            }

            Events event = organizer.createEvent(name, ticketPrice, dateText, room);
            if (event == null) {
                showAlert("Failed to create event. Check room availability or event limit.");
            } else {
                new CategorySelectionView(organizer, event).show(primaryStage);
            }
        });

        Scene scene = new Scene(brPane, 400, 500);
        URL cssUrl = getClass().getResource("/com/example/gui1/buttonStyle.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("CSS file not found!");
        }
        primaryStage.setTitle("Create Events");
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