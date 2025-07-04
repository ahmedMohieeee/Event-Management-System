package com.example.gui1.OrganizerGUI;
import Projectt.Organizer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;

public class ProfileView {
    private final Organizer organizer;

    public ProfileView(Organizer organizer) {
        this.organizer = organizer;
    }

    public void show(Stage primaryStage) {
        VBox eventsAll = new VBox(10);
        Label title = new Label("\uD83D\uDC64 Organizer Profile");
        title.setStyle("-fx-font-size:20px; -fx-font-weight: bold;");

        Label name = new Label("Name: " + organizer.getUsername());
        Label balance=new Label("Balance: "+ organizer.getBalance());
        Label num = new Label("Number of events organized: " + organizer.getNumberOfEvents());
        Label eventsOrganized = new Label("Organized events: ");

        List<String> events = organizer.showEvents();
        if (events.isEmpty()) {
            Label noEvents = new Label("There is no events organized");
            noEvents.setStyle("-fx-font-style:italic");
            eventsAll.getChildren().add(noEvents);
        } else {
            ListView<String> eventListView = new ListView<>();
            eventListView.getItems().addAll(events);
            eventsAll.getChildren().add(eventListView);
        }

        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> new Logout().logout(primaryStage));
        Button backBtn = new Button("Back to dashboard");
        backBtn.setOnAction(e -> new OrganizerDashboard(primaryStage, organizer).show(primaryStage));
        Button editPasswordBtn = new Button("Edit Password");
        editPasswordBtn.setOnAction(e -> new PasswordEditView(organizer).show(primaryStage));

        HBox root1 = new HBox(title);
        root1.setStyle("-fx-padding:20;-fx-alignment:center");

        HBox root4 = new HBox(editPasswordBtn);
        root4.setStyle("-fx-padding:20;-fx-alignment:left");

        HBox root5 = new HBox(backBtn);
        root5.setStyle("-fx-padding:20;-fx-alignment:left");
        root5.setAlignment(Pos.BOTTOM_LEFT);

        VBox centerContent = new VBox(15, name, balance, num, eventsOrganized, eventsAll, logoutBtn);
        centerContent.setStyle("-fx-padding:20;-fx-alignment:left");

        BorderPane rootLayout = new BorderPane();
        rootLayout.setTop(root1);
        rootLayout.setRight(root4);
        rootLayout.setBottom(root5);
        rootLayout.setCenter(centerContent);

        Scene scene = new Scene(rootLayout, 400, 500);
        URL cssUrl = getClass().getResource("/com/example/gui1/buttonStyle.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("CSS file not found!");
        }
        primaryStage.setTitle("Organizer Profile");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}