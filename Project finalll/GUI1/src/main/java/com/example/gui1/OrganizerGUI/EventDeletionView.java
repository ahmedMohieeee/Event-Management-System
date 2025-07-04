package com.example.gui1.OrganizerGUI;

import Projectt.Organizer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.URL;

public class EventDeletionView {

    private final Organizer organizer;

    public EventDeletionView(Organizer organizer) {
        this.organizer = organizer;
    }

    public void show(Stage primaryStage) {
        BorderPane br = new BorderPane();
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));

        Label titleLabel = new Label("🗑 Delete Event");
        HBox hbox=new HBox(titleLabel);
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(20));
        br.setTop(hbox);

        Label inputLabel = new Label("Event Name:");
        TextField eventNameField = new TextField();
        eventNameField.setMinWidth(250);
        vbox.getChildren().addAll(inputLabel, eventNameField);
        br.setLeft(vbox);

        Button deleteBtn = new Button("Delete");
        Button backBtn = new Button("Back to dashboard");
        HBox buttonBox = new HBox(10, backBtn, deleteBtn);
        buttonBox.setPadding(new Insets(20));
        br.setBottom(buttonBox);

        deleteBtn.setOnAction(e -> {
            String eventName = eventNameField.getText().trim();
            if (eventName.isEmpty()) {
                showAlert("❌ Please enter an event name.");
                return;
            }

            boolean deleted = organizer.deleteEventByName(eventName);
            if (deleted) {
                showAlert("✅ Event '" + eventName + "' deleted successfully.");
            } else {
                showAlert("❌ Event '" + eventName + "' not found.");
            }
        });

        backBtn.setOnAction(e -> new OrganizerDashboard(primaryStage, organizer).show(primaryStage));

        Scene scene = new Scene(br, 400, 500);
        URL cssUrl = getClass().getResource("/com/example/gui1/buttonStyle.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("CSS file not found!");
        }
        primaryStage.setTitle("Delete Event");
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