package com.example.gui1.AdminGui;
import Projectt.Admin;
import Projectt.Database;
import Projectt.Organizer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

public class OrganizerManagement {
    private Stage primaryStage;
    private boolean addShow,deleteShow;
    private VBox inputBox = new VBox(10);
    private TextField username = new TextField();
    private PasswordField password = new PasswordField();
    private Admin admin = null;

    public OrganizerManagement(Stage primaryStage,Admin admin) {
        this.primaryStage = primaryStage;
        this.admin = admin;
    }

    public void show() {
        VBox orgVbox = new VBox(20);
        orgVbox.setAlignment(Pos.TOP_CENTER);
        orgVbox.setPadding(new Insets(25));
        orgVbox.setStyle("-fx-background-color: linear-gradient(to bottom right, #1A1A2E, #4A148C);");
        primaryStage.setTitle("Organizers");
        Label title = new Label("Organizers Management");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white; -fx-font-family: Arial;");

        Button showAllbutton = buttonStyle("Show All");
        Button addOrganizerbutton = buttonStyle("Add Organizer");
        Button deleteOrganizerbutton = buttonStyle("Delete Organizer");
        Button backbutton = buttonStyle("Back to Dashboard");
        showAllbutton.setOnAction(e-> showOrg());
        addOrganizerbutton.setOnAction(e -> addOrg());
        deleteOrganizerbutton.setOnAction(e -> deleteOrg());
        backbutton.setOnAction(e -> new AdminDashboard(primaryStage, admin).show());

        textStyle(username);
        textStyle(password);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setPadding(new Insets(15));
        inputBox.setVisible(false);
        orgVbox.getChildren().addAll(title, showAllbutton, addOrganizerbutton, deleteOrganizerbutton, inputBox, backbutton);
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

    public void showOrg(){
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
        Label title = new Label("Organizers Available");
        title.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-font-family: Arial;");
        inputBox.getChildren().add(title);
        for (String org : admin.showOrganizers()){
            Label orgLabel = new Label(org);
            orgLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: white; -fx-font-family: Arial;");
            inputBox.getChildren().add(orgLabel);
        }
    }

    private void addOrg() {
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
        Label title = new Label("Add New Organizer");
        title.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-font-family: Arial;");
        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-text-fill: white; -fx-font-family: Arial;");
        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-text-fill: white; -fx-font-family: Arial;");

        Button submitButton = buttonStyle("Submit");
        submitButton.setOnAction(e -> {
            String usernameInput = username.getText();
            String passwordInput = password.getText();
            if (usernameInput.isBlank() || passwordInput.isBlank())
                return;
            inputBox.getChildren().removeIf(node -> node instanceof Label && "alert".equals(node.getId()));
            if (admin.usernameCheck(usernameInput) && admin.passwordCheck(passwordInput)){
                if (admin.addOrganizer(usernameInput)){
                    Label success = new Label("Organizer added successfully!");
                    Database.organizers.add(new Organizer(usernameInput,passwordInput));
                    success.setStyle("-fx-text-fill: lightgreen; -fx-font-family: Arial;");
                    success.setId("alert"); // for later removal
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
        inputBox.getChildren().addAll(title, usernameLabel, username, passwordLabel, password, submitButton);
        inputBox.setVisible(true);
        inputBox.setManaged(true);
        addShow = true;
    }

    private void deleteOrg() {
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

        Label title = new Label("Delete Organizer");
        title.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-font-family: Arial;");
        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-text-fill: white; -fx-font-family: Arial;");

        Button submitbutton = buttonStyle("Delete");
        submitbutton.setOnAction(e -> {
            inputBox.getChildren().removeIf(node -> node instanceof Label && "alert".equals(node.getId()));
            String usernameInput = username.getText();
            if(usernameInput.isBlank())
                return;
            if(admin.deleteOrganizer(usernameInput)){
                Label success = new Label("Organizer removed successfully!");
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