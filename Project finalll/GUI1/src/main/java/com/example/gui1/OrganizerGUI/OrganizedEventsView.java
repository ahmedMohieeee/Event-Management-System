package com.example.gui1.OrganizerGUI;

import Projectt.Organizer;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.net.URL;
import java.util.List;

public class OrganizedEventsView {

    private final Organizer organizer;

    public OrganizedEventsView(Organizer organizer) {
        this.organizer = organizer;
    }

    public void show(Stage primaryStage) {
        BorderPane brpane = new BorderPane();

        Label title = new Label("🎯 My Organized Events");
        title.setStyle("-fx-font-size:20px; -fx-font-weight:bold");
        title.setPadding(new Insets(20));
        HBox Htitle = new HBox(title);
        Htitle.setAlignment(Pos.CENTER);
        brpane.setTop(Htitle);

        Button backBtn = new Button("Back to dashboard");
        HBox hback = new HBox(backBtn);
        hback.setPadding(new Insets(10));
        brpane.setBottom(hback);

        backBtn.setOnAction(e -> new OrganizerDashboard(primaryStage, organizer).show(primaryStage));

        List<String> events = organizer.showEvents();
        ListView<String> eventsView = new ListView<>();
        eventsView.setMaxWidth(250);
        eventsView.setMaxHeight(250);

        if (events.isEmpty()) {
            showAlert("This organizer has no events organized");
        } else {
            eventsView.getItems().addAll(events);
            brpane.setCenter(eventsView);
        }

        Scene scene = new Scene(brpane, 400, 500);
        URL cssUrl = getClass().getResource("/com/example/gui1/buttonStyle.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("CSS file not found!");
        }
        primaryStage.setTitle("Show Events");
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