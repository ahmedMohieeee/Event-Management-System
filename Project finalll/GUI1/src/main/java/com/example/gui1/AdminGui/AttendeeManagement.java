package com.example.gui1.AdminGui;
import Projectt.Admin;
import Projectt.Attendee;
import Projectt.Database;
import Projectt.Organizer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

public class AttendeeManagement {
    private Stage primaryStage;
    private boolean addShow,deleteShow;
    private VBox inputBox = new VBox(10);
    private TextField username = new TextField();
    private PasswordField password = new PasswordField();
    private Admin admin = null;
    private ComboBox<String> genderBox = new ComboBox<>();

    public AttendeeManagement(Stage primaryStage,Admin admin) {
        this.primaryStage = primaryStage;
        this.admin = admin;
    }

    public void show() {
        VBox orgVbox = new VBox(20);
        orgVbox.setAlignment(Pos.TOP_CENTER);
        orgVbox.setPadding(new Insets(25));
        orgVbox.setStyle("-fx-background-color: linear-gradient(to bottom right, #1A1A2E, #4A148C);");
        primaryStage.setTitle("Attendees");
        Label title = new Label("Attendees Management");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white; -fx-font-family: Arial;");

        Button showAllbutton = buttonStyle("Show All");
        Button addAttendee = buttonStyle("Add Attendee");
        Button deleteAttendee = buttonStyle("Delete Attendee");
        Button backbutton = buttonStyle("Back to Dashboard");
        showAllbutton.setOnAction(e-> showAtt());
        addAttendee.setOnAction(e -> addAtt());
        deleteAttendee.setOnAction(e -> deleteAtt());
        backbutton.setOnAction(e -> new AdminDashboard(primaryStage, admin).show());

        textStyle(username);
        textStyle(password);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setPadding(new Insets(15));
        inputBox.setVisible(false);
        orgVbox.getChildren().addAll(title, showAllbutton, addAttendee, deleteAttendee, inputBox, backbutton);
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

    public void showAtt(){
        if (!inputBox.isVisible()) {
            inputBox.setVisible(true);
            inputBox.setManaged(true);
            inputBox.getChildren().clear();
        }
        else {
            inputBox.setVisible(false);
            inputBox.setManaged(false);
            inputBox.getChildren().clear();
        }
        Label title = new Label("Attendees Available");
        title.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-font-family: Arial;");
        inputBox.getChildren().add(title);
        for (String att : admin.showAttendees()){
            Label attLabel = new Label(att);
            attLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: white; -fx-font-family: Arial;");
            inputBox.getChildren().add(attLabel);
        }
    }

    private void addAtt() {
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
        Label title = new Label("Add New Attendee");
        title.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-font-family: Arial;");
        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-text-fill: white; -fx-font-family: Arial;");
        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-text-fill: white; -fx-font-family: Arial;");
        Label genderLabel = new Label("Gender:");
        genderLabel.setStyle("-fx-text-fill: white; -fx-font-family: Arial;");
        genderBox.getItems().clear(); // clear if reused
        genderBox.getItems().addAll("Male", "Female");
        genderBox.setPromptText("Select Gender");
        genderBox.setStyle("-fx-background-color: rgba(255,255,255,0.2); " +
                "-fx-text-fill: white; -fx-font-family: Arial; " +
                "-fx-prompt-text-fill: #aaa; -fx-background-radius: 5;");
        genderBox.setMaxWidth(250);

        Button submitButton = buttonStyle("Submit");
        submitButton.setOnAction(e -> {
            String usernameInput = username.getText();
            String passwordInput = password.getText();
            String selectedGender = genderBox.getValue();
            if (usernameInput.isBlank() || passwordInput.isBlank() || selectedGender == null) {
                Label error = new Label("All fields including gender must be filled.");
                error.setStyle("-fx-text-fill: red; -fx-font-family: Arial;");
                error.setId("alert");
                inputBox.getChildren().removeIf(node -> node instanceof Label && "alert".equals(node.getId()));
                inputBox.getChildren().add(error);
                return;
            }
            inputBox.getChildren().removeIf(node -> node instanceof Label && "alert".equals(node.getId()));
            if (admin.usernameCheck(usernameInput) && admin.passwordCheck(passwordInput)){
                int genderValue = selectedGender.equals("Male") ? 1 : 2;
                if (admin.addAttendee(usernameInput,passwordInput,genderValue)){
                    Label success = new Label("Attendee added successfully!");
                    success.setStyle("-fx-text-fill: lightgreen; -fx-font-family: Arial;");
                    success.setId("alert");
                    inputBox.getChildren().add(success);
                }
                else
                {
                    Label taken = new Label("Username is already taken.");
                    taken.setStyle("-fx-text-fill: red; -fx-font-family: Arial;");
                    taken.setId("alert");
                    inputBox.getChildren().add(taken);
                }
            }
            else
            {
                Label invalid = new Label("Invalid input! Please check username or password.");
                invalid.setStyle("-fx-text-fill: red; -fx-font-family: Arial;");
                invalid.setId("alert");
                inputBox.getChildren().add(invalid);
            }
            username.clear();
            password.clear();
        });
        inputBox.getChildren().addAll(title, usernameLabel, username, passwordLabel, password,genderLabel, genderBox, submitButton);
        inputBox.setVisible(true);
        inputBox.setManaged(true);
        addShow = true;
    }

    private void deleteAtt() {
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

        Label title = new Label("Delete Attendee");
        title.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-font-family: Arial;");
        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-text-fill: white; -fx-font-family: Arial;");

        Button submitbutton = buttonStyle("Delete");
        submitbutton.setOnAction(e -> {
            inputBox.getChildren().removeIf(node -> node instanceof Label && "alert".equals(node.getId()));
            String usernameInput = username.getText();
            if (usernameInput.isBlank())
                return;
            if(admin.deleteAttendee(usernameInput)){
                Label success = new Label("Attendee removed successfully!");
                success.setStyle("-fx-text-fill: lightgreen; -fx-font-family: Arial;");
                success.setId("alert"); // for later removal
                inputBox.getChildren().add(success);
            }
            else {
                Label invalid = new Label("Incorrect username!");
                invalid.setStyle("-fx-text-fill: red; -fx-font-family: Arial;");
                invalid.setId("alert");
                inputBox.getChildren().add(invalid);
            }
            username.clear();
        });

        inputBox.getChildren().addAll(title, usernameLabel, username, submitbutton);
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
