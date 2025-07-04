/*package com.example.gui1;
import Projectt.Database;
import Projectt.Events;
import Projectt.Organizer;
import com.sun.source.tree.LabeledStatementTree;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class OrganizerDashboardGUI extends Application {
    private Organizer organizer;


    private void showOrganizerDashboard(Stage primaryStage) {
        Label title = new Label("🎩 Organizer Dashboard 🎩");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button viewProfileBtn = new Button("View Profile");
        Button createEventBtn = new Button("Create Event");
        Button showRoomsBtn = new Button("Show Available Rooms");
        Button showEventsBtn = new Button("Show Events Organized");
        Button showAttendeesBtn = new Button("Show Paid Attendees");
        Button deleteEventBtn = new Button("Delete Event");
        Button logoutBtn = new Button("Logout");

        viewProfileBtn.setOnAction(e -> viewProfile(primaryStage));
        createEventBtn.setOnAction(e -> organizer.createEvents());
        showRoomsBtn.setOnAction(e -> organizer.showAvailableRooms());
        showEventsBtn.setOnAction(e -> organizer.showEvents());
        showAttendeesBtn.setOnAction(e -> organizer.showPaidAttendees());
        deleteEventBtn.setOnAction(e -> organizer.deleteEventByName());
        logoutBtn.setOnAction(e -> {
            System.out.println("Logged out!");
            primaryStage.close();
        });

        VBox root = new VBox(15, title, viewProfileBtn              , createEventBtn, showRoomsBtn, showEventsBtn, showAttendeesBtn, deleteEventBtn, logoutBtn);
        root.setStyle("-fx-padding: 30; -fx-alignment: center;");

        Scene scene = new Scene(root, 400, 500);
        primaryStage.setTitle("Organizer Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void viewProfile(Stage primaryStage) {
        VBox eventsAll=new VBox(10);
        Label title = new Label("\uD83D\uDC64 Organizer Profile");
        title.setStyle("-fx-font-size:20px; -fx-font-weight: bold;");
        Label name = new Label("Name: " + organizer.getUsername());
        Label num = new Label("Number of events organized: " + organizer.getNumberOfEvents());
        Label eventsOrganized = new Label("Organized events: ");
        List<String> events = organizer.showEvents();
        if (events.isEmpty()){
            Label noEvents=new Label("There is no events organized");
            noEvents.setStyle("-fx-font-style:italic");
            eventsAll.getChildren().add(noEvents);
        }
        else{
            ListView<String> eventListView = new ListView<>();
            eventListView.getItems().addAll(events);
            eventsAll.getChildren().add(eventListView);
        }

        Button backBtn= new Button(" Back to dashboard");
        backBtn.setOnAction(e-> showOrganizerDashboard(primaryStage));

        VBox root= new VBox(15, title, name,num,eventsOrganized,eventsAll,backBtn);
        root.setStyle("-fx-padding:20;-fx-alignment:center  ");
        Scene scene = new Scene(root, 400, 500);
        primaryStage.setTitle("Organizer Profile");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    @Override
    public void start(Stage primaryStage) {
        Database.addData();
        organizer = Database.organizers.get(3);
        showOrganizerDashboard(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}*/
