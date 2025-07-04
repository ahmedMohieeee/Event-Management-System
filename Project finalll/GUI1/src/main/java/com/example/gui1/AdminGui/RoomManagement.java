package com.example.gui1.AdminGui;
import Projectt.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.InputMismatchException;

public class RoomManagement {
    private Stage primaryStage;
    private boolean addShow,deleteShow;
    private VBox inputBox = new VBox(10);
    private TextField name = new TextField();
    private TextField maxCapacity = new TextField();
    private Admin admin = null;

    public RoomManagement(Stage primaryStage, Admin admin) {
        this.primaryStage = primaryStage;
        this.admin = admin;
    }

    public void show() {
        VBox orgVbox = new VBox(20);
        orgVbox.setAlignment(Pos.TOP_CENTER);
        orgVbox.setPadding(new Insets(25));
        orgVbox.setStyle("-fx-background-color: linear-gradient(to bottom right, #1A1A2E, #4A148C);");
        Label title = new Label("Room Management");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white; -fx-font-family: Arial;");

        Button showAllbutton = buttonStyle("Show All");
        Button addRoom = buttonStyle("Add Room");
        Button deleteRoom = buttonStyle("Delete Room");
        Button backbutton = buttonStyle("Back to Dashboard");
        showAllbutton.setOnAction(e-> showRoom());
        addRoom.setOnAction(e -> addRoom());
        deleteRoom.setOnAction(e -> deleteRoom());
        backbutton.setOnAction(e -> new AdminDashboard(primaryStage, admin).show());
        primaryStage.setTitle("Rooms");
        textStyle(name);
        textStyle(maxCapacity);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setPadding(new Insets(15));
        inputBox.setVisible(false);

        orgVbox.getChildren().addAll(title, showAllbutton, addRoom, deleteRoom, inputBox, backbutton);
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

    public void showRoom() {
        if (!inputBox.isVisible()) {
            inputBox.setVisible(true);
            inputBox.setManaged(true);
            inputBox.getChildren().clear();
        } else {
            inputBox.setVisible(false);
            inputBox.setManaged(false);
            inputBox.getChildren().clear();
            return;
        }

        Label title = new Label("Rooms Available");
        title.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-font-family: Arial;");
        inputBox.getChildren().add(title);

        Label header = new Label("Room Name               Room Capacity");
        header.setStyle("-fx-font-size: 14px; -fx-text-fill: white; -fx-font-family: Arial; -fx-underline: true;");
        inputBox.getChildren().add(header);
        boolean flag = false;
        for (String[] room : admin.showRooms()) {
            flag = true;
            String line = String.format("%-25s %s", room[0], room[1]);
            Label roomInfo = new Label(line);
            roomInfo.setStyle("-fx-font-size: 12px; -fx-text-fill: white; -fx-font-family: Arial;");
            inputBox.getChildren().add(roomInfo);
        }
        if (!flag) {
            Label noRoom = new Label("No Room available");
            noRoom.setStyle("-fx-font-size: 14px; -fx-text-fill: yellow; -fx-font-family: Arial;");
            inputBox.getChildren().add(noRoom);
        }
    }

    private void addRoom() {
        if (addShow) {
            inputBox.getChildren().clear();
            inputBox.setVisible(false);
            inputBox.setManaged(false);
            addShow = false;
            return;
        }
        if (deleteShow){
            inputBox.getChildren().clear();
            deleteShow = false;
        }
        inputBox.getChildren().clear();
        Label title = new Label("Add New Room");
        title.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-font-family: Arial;");
        Label nameLabel = new Label("Room Name:");
        nameLabel.setStyle("-fx-text-fill: white; -fx-font-family: Arial;");
        Label maxCapacityLabel = new Label("Capacity:");
        maxCapacityLabel.setStyle("-fx-text-fill: white; -fx-font-family: Arial;");

        Button submitButton = buttonStyle("Submit");
        submitButton.setOnAction(e -> {
            String nameInput = name.getText();
            String capacityInput = maxCapacity.getText();
            if (nameInput.isBlank() || capacityInput.isBlank())
                return;
            int capacity;
            try {
                capacity = Integer.parseInt(maxCapacity.getText());
            } catch (InputMismatchException ex) {
                Label invalid = new Label("Capacity must be a number!");
                invalid.setStyle("-fx-text-fill: red; -fx-font-family: Arial;");
                invalid.setId("alert");
                inputBox.getChildren().add(invalid);
                return;
            } catch (NumberFormatException ex){
                Label invalid = new Label("Capacity must be an integer!");
                invalid.setStyle("-fx-text-fill: red; -fx-font-family: Arial;");
                invalid.setId("alert");
                inputBox.getChildren().add(invalid);
                return;
            }
            inputBox.getChildren().removeIf(node -> node instanceof Label && "alert".equals(node.getId()));
            if (admin.addRoom(nameInput,capacity)) {
                Label success = new Label("Room added successfully!");
                success.setStyle("-fx-text-fill: lightgreen; -fx-font-family: Arial;");
                success.setId("alert"); // for later removal
                inputBox.getChildren().add(success);
            }
            else if(!admin.isRoomUnique(nameInput))
            {
                Label invalid = new Label("Room Name is already taken!");
                invalid.setStyle("-fx-text-fill: red; -fx-font-family: Arial;");
                invalid.setId("alert");
                inputBox.getChildren().add(invalid);
            }
            else
            {
                Label invalid = new Label("Invalid Capacity range!");
                invalid.setStyle("-fx-text-fill: red; -fx-font-family: Arial;");
                invalid.setId("alert");
                inputBox.getChildren().add(invalid);
            }
            name.clear();
            maxCapacity.clear();
        });
        inputBox.getChildren().addAll(title, nameLabel, name, maxCapacityLabel, maxCapacity, submitButton);
        inputBox.setVisible(true);
        inputBox.setManaged(true);
        addShow = true;
    }

    private void deleteRoom() {
        if (deleteShow) {
            inputBox.setVisible(false);
            inputBox.setVisible(false);
            inputBox.setManaged(false);
            deleteShow = false;
            return;
        }
        if (addShow){
            inputBox.getChildren().clear();
            addShow = false;
        }
        inputBox.getChildren().clear();

        Label title = new Label("Delete Room");
        title.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-font-family: Arial;");
        Label nameLabel = new Label("Room Name:");
        nameLabel.setStyle("-fx-text-fill: white; -fx-font-family: Arial;");

        Button submitbutton = buttonStyle("Delete");
        submitbutton.setOnAction(e -> {
            inputBox.getChildren().removeIf(node -> node instanceof Label && "alert".equals(node.getId()));
            String nameInput = name.getText();
            if (nameInput.isBlank())
                return;
            if(admin.deleteRoom(nameInput)){
                Label success = new Label("Room removed successfully!");
                success.setStyle("-fx-text-fill: lightgreen; -fx-font-family: Arial;");
                success.setId("alert"); // for later removal
                inputBox.getChildren().add(success);
            }
            else {
                Label invalid = new Label("Incorrect Room name!");
                invalid.setStyle("-fx-text-fill: red; -fx-font-family: Arial;");
                invalid.setId("alert");
                inputBox.getChildren().add(invalid);
            }
            name.clear();
        });

        inputBox.getChildren().addAll(title, nameLabel, name, submitbutton);
        inputBox.setVisible(true);
        inputBox.setManaged(true);
        deleteShow = true;
    }

    private void textStyle(TextField textField) {
        textField.setStyle("-fx-background-color: rgba(255,255,255,0.2); " +
                "-fx-text-fill: white; -fx-font-family: Arial; " +
                "-fx-prompt-text-fill: #aaa; -fx-background-radius: 5;");
        textField.setMaxWidth(250);
    }
}