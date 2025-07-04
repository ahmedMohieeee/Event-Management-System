package com.example.gui1.AdminGui;

import Projectt.Admin;
import Projectt.Database;
import Projectt.Events;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.time.format.DateTimeFormatter;

public class EventManagement {
    private Stage primaryStage;
    private boolean addShow, deleteShow, showAllShow;
    private VBox inputBox = new VBox(10);
    private TextField eventName = new TextField();
    private PasswordField password = new PasswordField();
    private TextField date = new TextField();
    private Admin admin = null;

    public EventManagement(Stage primaryStage, Admin admin) {
        this.primaryStage = primaryStage;
        this.admin = admin;
    }

    public void show() {
        VBox orgVbox = new VBox(20);
        orgVbox.setAlignment(Pos.TOP_CENTER);
        orgVbox.setPadding(new Insets(25));
        orgVbox.setStyle("-fx-background-color: linear-gradient(to bottom right, #1A1A2E, #4A148C);");
        primaryStage.setTitle("Events");

        Label title = new Label("Events Management");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white; -fx-font-family: Arial;");

        Button showAllButton = buttonStyle("Show All");
        Button deleteEventButton = buttonStyle("Delete Event");
        Button backButton = buttonStyle("Back to Dashboard");

        showAllButton.setOnAction(e -> showEvents());
        deleteEventButton.setOnAction(e -> deleteEvent());
        backButton.setOnAction(e -> new AdminDashboard(primaryStage, admin).show());

        textStyle(eventName);
        textStyle(date);

        inputBox.setAlignment(Pos.CENTER);
        inputBox.setPadding(new Insets(15));
        inputBox.setVisible(false);
        inputBox.setManaged(false);

        orgVbox.getChildren().addAll(title, showAllButton, deleteEventButton, inputBox, backButton);

        Scene scene = new Scene(orgVbox, 400, 500);
        URL cssUrl = getClass().getResource("/com/example/gui1/buttonStyle.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("CSS file not found!");
        }
        primaryStage.setScene(scene);
    }

    private Button buttonStyle(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("button");
        return button;
    }

    private void deleteEvent() {
        if (deleteShow) {
            inputBox.getChildren().clear();
            inputBox.setVisible(false);
            inputBox.setManaged(false);
            deleteShow = false;
            return;
        }
        if (addShow) {
            inputBox.getChildren().clear();
            addShow = false;
        }
        if (showAllShow) {
            inputBox.getChildren().clear();
            showAllShow = false;
        }

        inputBox.getChildren().clear();

        Label title = new Label("Delete Event");
        title.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-font-family: Arial;");

        Label eventNameLabel = new Label("Event Name:");
        eventNameLabel.setStyle("-fx-text-fill: white; -fx-font-family: Arial;");

        Button submitButton = buttonStyle("Delete");
        submitButton.setOnAction(e -> {

            inputBox.getChildren().removeIf(node -> node instanceof Label && "alert".equals(node.getId()));
            String nameInput = eventName.getText();
            if (nameInput.isBlank())
                return;
            if(admin.deleteEvent(nameInput)){
                Label success = new Label("Event removed successfully!");
                success.setStyle("-fx-text-fill: lightgreen; -fx-font-family: Arial;");
                success.setId("alert"); // for later removal
                inputBox.getChildren().add(success);
            }
            else {
                Label invalid = new Label("Incorrect Event name!");
                invalid.setStyle("-fx-text-fill: red; -fx-font-family: Arial;");
                invalid.setId("alert");
                inputBox.getChildren().add(invalid);
            }
            eventName.clear();

        });

        inputBox.getChildren().addAll(title, eventNameLabel, eventName, submitButton);
        inputBox.setVisible(true);
        inputBox.setManaged(true);
        deleteShow = true;
    }

    private void showEvents() {
        if (showAllShow) {
            inputBox.getChildren().clear();
            inputBox.setVisible(false);
            inputBox.setManaged(false);
            showAllShow = false;
            return;
        }
        if (addShow) {
            inputBox.getChildren().clear();
            addShow = false;
        }
        if (deleteShow) {
            inputBox.getChildren().clear();
            deleteShow = false;
        }

        inputBox.getChildren().clear();

        Label title = new Label("Events Available:");
        title.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-font-family: Arial;");
        inputBox.getChildren().add(title);

        for (String[] cat : admin.showCategories()) {
            boolean flag = false;
            Label catLabel = new Label(cat[0]);
            catLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: white; -fx-font-family: Arial;");
            inputBox.getChildren().add(catLabel);

            for (String event : admin.showEvents()) {
                if (admin.eventMatchesCategory(event).equalsIgnoreCase(cat[0])) {
                    flag = true;
                    String formattedDate = admin.getEventObject(event).getDateTime().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    Label eventLabel = new Label("- " + event + "\t" + formattedDate);
                    eventLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: white; -fx-font-family: Arial;");
                    inputBox.getChildren().add(eventLabel);
                }
            }
            if (!flag){
                Label noEvent = new Label("No events available");
                noEvent.setStyle("-fx-font-size: 14px; -fx-text-fill: yellow; -fx-font-family: Arial;");
                inputBox.getChildren().add(noEvent);
            }
        }

        inputBox.setVisible(true);
        inputBox.setManaged(true);
        showAllShow = true;
    }


    private void textStyle(TextField textField) {
        textField.setStyle("-fx-background-color: rgba(255,255,255,0.2); " +
                "-fx-text-fill: white; -fx-font-family: Arial; " +
                "-fx-prompt-text-fill: #aaa; -fx-background-radius: 5;");
        textField.setMaxWidth(250);
    }
}
