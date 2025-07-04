package com.example.gui1.OrganizerGUI;

import Projectt.Organizer;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

public class OrganizerDashboard {

    private final Organizer organizer;

    public OrganizerDashboard(Stage primaryStage,Organizer organizer) {
        this.organizer = organizer;
    }

    public void show(Stage primaryStage) {
        Label title = new Label("🎩 Organizer Dashboard 🎩");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button viewProfileBtn = new Button("View Profile");
        Button createEventBtn = new Button("Create Event");
        Button showRoomsBtn = new Button("Show Available Rooms");
        Button showEventsBtn = new Button("Show Events Organized");
        Button showAttendeesBtn = new Button("Show Paid Attendees");
        Button deleteEventBtn = new Button("Delete Event");

        viewProfileBtn.setOnAction(e -> new ProfileView(organizer).show(primaryStage));
        createEventBtn.setOnAction(e -> new EventCreationView(organizer).show(primaryStage));
        showRoomsBtn.setOnAction(e -> new AvailableRoomsView(organizer).show(primaryStage));
        showEventsBtn.setOnAction(e -> new OrganizedEventsView(organizer).show(primaryStage));
        showAttendeesBtn.setOnAction(e -> new PaidAttendeesView(organizer).show(primaryStage));
        deleteEventBtn.setOnAction(e -> new EventDeletionView(organizer).show(primaryStage));
        viewProfileBtn.getStyleClass().add("button");

        VBox root = new VBox(15, title, viewProfileBtn, createEventBtn, showRoomsBtn,
                showEventsBtn, showAttendeesBtn, deleteEventBtn);
        root.setStyle("-fx-padding: 30; -fx-alignment: center;");

        Scene scene = new Scene(root, 400, 500);
        URL cssUrl = getClass().getResource("/com/example/gui1/buttonStyle.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("CSS file not found!");
        }
        String cssPath = getClass().getResource("/com/example/gui1/buttonStyle.css").toExternalForm();
        scene.getStylesheets().add(cssPath);
        primaryStage.setTitle("Organizer Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}