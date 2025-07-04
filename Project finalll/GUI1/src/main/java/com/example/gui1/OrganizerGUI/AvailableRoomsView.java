package com.example.gui1.OrganizerGUI;

import Projectt.Organizer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class AvailableRoomsView {

    private final Organizer organizer;

    public AvailableRoomsView(Organizer organizer) {
        this.organizer = organizer;
    }

    public void show(Stage primaryStage) {
        BorderPane brpane = new BorderPane();
        VBox center = new VBox(7);
        HBox Htitle = new HBox();
        HBox hbox1 = new HBox();
        HBox hbox2 = new HBox();

        Label title = new Label("🚪 Available Venues 🚪");
        title.setStyle("-fx-padding:20;-fx-font-size:20px; -fx-font-weight:bold");
        title.setPadding(new Insets(20));
        Htitle.getChildren().add(title);
        Htitle.setAlignment(Pos.CENTER);
        brpane.setTop(Htitle);

        Label enter = new Label("Date and time e.g, 2025-07-28 15:00 ");
        TextField date = new TextField();
        hbox1.getChildren().addAll(enter, date);

        Button showBtn = new Button("Show");
        Button backBtn = new Button("Back to dashboard");
        hbox2.setPadding(new Insets(20));
        hbox2.setSpacing(10);
        hbox2.setAlignment(Pos.CENTER_LEFT);
        hbox2.getChildren().addAll(backBtn, showBtn);
        brpane.setBottom(hbox2);

        ListView<String> availableRoomsView = new ListView<>();
        availableRoomsView.setMaxHeight(250);
        availableRoomsView.setMaxWidth(250);
        availableRoomsView.setVisible(false);

        center.setPadding(new Insets(20));
        center.setAlignment(Pos.CENTER);
        center.getChildren().addAll(hbox1, availableRoomsView);
        brpane.setCenter(center);

        backBtn.setOnAction(e -> new OrganizerDashboard(primaryStage, organizer).show(primaryStage));

        showBtn.setOnAction(e -> {
            String inputDate = date.getText();
            LocalDateTime dateTime;

            try {
                dateTime = LocalDateTime.parse(inputDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            } catch (DateTimeParseException ex) {
                showAlert("Invalid date format. Please use yyyy-MM-dd HH:mm.");
                return;
            }

            List<String> availableRoomsList = organizer.showAvailableRooms(dateTime);
            availableRoomsView.getItems().clear();

            if (availableRoomsList.isEmpty()) {
                availableRoomsView.getItems().add("No rooms available.");
            } else {
                availableRoomsView.getItems().addAll(availableRoomsList);
            }

            availableRoomsView.setVisible(true);
        });

        Scene scene = new Scene(brpane, 400, 500);
        URL cssUrl = getClass().getResource("/com/example/gui1/buttonStyle.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("CSS file not found!");
        }
        primaryStage.setTitle("Available Rooms");
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