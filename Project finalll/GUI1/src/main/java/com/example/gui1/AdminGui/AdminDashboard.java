package com.example.gui1.AdminGui;
import Projectt.Admin;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.net.URL;

public class AdminDashboard {
    private Stage primaryStage;
    private Admin admin = null;

    public AdminDashboard(Stage stage, Admin admin) {
        this.primaryStage = stage;
        this.admin = admin;
    }

    public void show() {
        VBox adminVBox = new VBox(10);
        adminVBox.setPadding(new javafx.geometry.Insets(30, 10, 10, 10));
        adminVBox.setStyle("-fx-alignment: top-center;");
        Label welcomeLabel = new Label("Welcome back!");
        welcomeLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: white; -fx-font-family: Arial;");


        Button viewProfileButton = buttonStyle("View Profile");
        Button organizersButton = buttonStyle("Organizers");
        Button attendeesButton = buttonStyle("Attendees");
        Button roomsButton = buttonStyle("Rooms");
        Button eventsButton = buttonStyle("Events");
        Button categoriesButton = buttonStyle("Categories");

        viewProfileButton.setOnAction(e -> viewProfile());
        organizersButton.setOnAction(e -> viewOrganizers());
        attendeesButton.setOnAction(e -> viewAttendees());
        roomsButton.setOnAction(e -> viewRooms());
        eventsButton.setOnAction(e -> viewEvents());
        categoriesButton.setOnAction(e -> viewCategories());

        adminVBox.getChildren().addAll(welcomeLabel, viewProfileButton, organizersButton, attendeesButton, roomsButton, eventsButton, categoriesButton);

        Scene adminScene = new Scene(adminVBox, 400, 420);
        URL cssUrl = getClass().getResource("/com/example/gui1/buttonStyle.css");
        if (cssUrl != null) {
            adminScene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("CSS file not found!");
        }
        primaryStage.setTitle("Admin Dashboard");
        primaryStage.setScene(adminScene);
        primaryStage.show();
    }

    private Button buttonStyle(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("button");
        return button;
    }

    private void viewProfile() {
        new adminProfile(primaryStage, admin).show();
    }

    private void viewOrganizers() {
        new OrganizerManagement(primaryStage,admin).show();
    }

    private void viewAttendees() {
        new AttendeeManagement(primaryStage,admin).show();
    }

    private void viewRooms() {
        new RoomManagement(primaryStage,admin).show();
    }

    private void viewEvents() {
        new com.example.gui1.AdminGui.EventManagement(primaryStage,admin).show();
    }

    private void viewCategories() {
        new CategoryManagement(primaryStage,admin).show();
    }
}

