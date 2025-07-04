package com.example.gui1.AdminGui;

import Projectt.Admin;
import Projectt.Database;
import com.example.gui1.LoginGUI.LoginLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.URL;

public class adminProfile {
    private Stage primaryStage;
    private Admin admin;
    private VBox inputBox = new VBox(10);
    private TextField username = new TextField();
    private TextField role = new TextField();
    private TextField birthdate = new TextField();
    private TextField gender = new TextField();
    private CheckBox[] workingHours = new CheckBox[24];
    private boolean isEditable = false;
    private Label alertLabel = new Label();

    public adminProfile(Stage primaryStage, Admin admin) {
        this.primaryStage = primaryStage;
        this.admin = admin;
    }

    public void show() {
        VBox profileVbox = new VBox(20);
        profileVbox.setAlignment(Pos.TOP_CENTER);
        profileVbox.setPadding(new Insets(25));
        profileVbox.setStyle("-fx-background-color: linear-gradient(to bottom right, #1A1A2E, #4A148C);");

        Label title = new Label("Profile Management");
        primaryStage.setTitle(admin.getUsername()+"'s Profile");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white; -fx-font-family: Arial;");


        Button editButton = buttonStyle("Edit");
        Button saveButton = buttonStyle("Save");
        Button backButton = buttonStyle("Back to Dashboard");
        Button logoutButton = buttonStyle("Log Out");
        editButton.setOnAction(e -> toggleEdit());
        saveButton.setOnAction(e -> {
            if (validateInputs()) {
                updateAdminData();
            }
        });
        backButton.setOnAction(e -> {
            new AdminDashboard(primaryStage, admin).show();
        });
        logoutButton.setOnAction(e -> logout());

        textStyle(username);
        textStyle(role);
        textStyle(birthdate);
        username.setText(admin.getUsername());
        role.setText(admin.getRole());
        birthdate.setText(admin.getBirthdate());
        username.setEditable(false);
        role.setEditable(false);
        birthdate.setEditable(false);
        textStyle(gender);
        String genderStr = admin.getGender().toString().charAt(0) + admin.getGender().toString().substring(1).toLowerCase();
        gender.setText(genderStr);
        gender.setText(genderStr);
        gender.setEditable(false);

        Label usernameLabel = fieldLabel("Username:");
        Label roleLabel = fieldLabel("Role:");
        Label birthdateLabel = fieldLabel("Birthdate:");
        Label hoursLabel = fieldLabel("Working Hours:");
        Label genderLabel = fieldLabel("Gender:");

        GridPane hoursGrid = new GridPane();
        hoursGrid.setAlignment(Pos.CENTER);
        hoursGrid.setHgap(10);
        hoursGrid.setVgap(5);
        boolean[] savedHours = admin.getWorkinghours();
        if (savedHours == null || savedHours.length != 24) {
            savedHours = new boolean[24];
        }
        for (int i = 0; i < 24; i++) {
            workingHours[i] = new CheckBox(i + ":00");
            workingHours[i].setStyle("-fx-text-fill: white;");
            workingHours[i].setSelected(savedHours[i]);
            workingHours[i].setDisable(true);
            hoursGrid.add(workingHours[i], i % 4, i / 4);
        }

        alertLabel.setStyle("-fx-text-fill: lightgreen; -fx-font-family: Arial;");
        alertLabel.setVisible(false);

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(editButton, saveButton);

        inputBox.setAlignment(Pos.CENTER);
        inputBox.setPadding(new Insets(15));
        inputBox.setVisible(true);
        inputBox.getChildren().addAll(
                usernameLabel, username,
                roleLabel, role,
                genderLabel, gender,
                birthdateLabel, birthdate,
                hoursLabel, hoursGrid,
                alertLabel, buttons
        );
        profileVbox.getChildren().addAll(title, inputBox, backButton,logoutButton);
        Scene scene = new Scene(profileVbox, 500, 640);
        URL cssUrl = getClass().getResource("/com/example/gui1/buttonStyle.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("CSS file not found!");
        }
        primaryStage.setTitle("Admin Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setScene(scene);
    }

    private void toggleEdit() {
        isEditable = !isEditable;
        username.setEditable(isEditable);
        role.setEditable(isEditable);
        birthdate.setEditable(isEditable);
        for (CheckBox cb : workingHours) {
            cb.setDisable(!isEditable);
        }
    }

    private boolean validateInputs() {
        alertLabel.setVisible(false);
        String uname = username.getText();
        String r = role.getText();
        String bd = birthdate.getText();

        if (uname.isBlank() || r.isBlank() || bd.isBlank()) {
            showAlert("All fields must be filled.", "red");
            return false;
        }

        boolean atLeastOneSelected = false;
        for (CheckBox cb : workingHours) {
            if (cb.isSelected()) {
                atLeastOneSelected = true;
                break;
            }
        }

        if (!atLeastOneSelected) {
            showAlert("At least one working hour must be selected.", "red");
            return false;
        }

        if (!admin.usernameCheck(uname)) {
            showAlert("Invalid username format.", "red");
            return false;
        }

        for (Admin a : Database.admins) {
            if (a.getUsername().equalsIgnoreCase(uname) && !a.equals(admin)) {
                showAlert("Username already taken.", "red");
                return false;
            }
        }

        if (!admin.dateformat(bd)) {
            showAlert("Invalid birthdate format.", "red");
            return false;
        }
        return true;
    }

    private void updateAdminData() {
        String uname = username.getText();
        String r = role.getText();
        String bd = birthdate.getText();
        boolean[] selectedHours = new boolean[24];

        for (int i = 0; i < 24; i++) {
            selectedHours[i] = workingHours[i].isSelected();
        }

        boolean isSameUsername = uname.equals(admin.getUsername());
        boolean isSameRole = r.equals(admin.getRole());
        boolean isSameBirthdate = bd.equals(admin.getBirthdate());
        boolean[] currentHours = admin.getWorkinghours();
        if (currentHours == null || currentHours.length != 24) {
            currentHours = new boolean[24];
        }
        boolean isSameHours = true;
        for (int i = 0; i < 24; i++) {
            if (selectedHours[i] != currentHours[i]) {
                isSameHours = false;
                break;
            }
        }

        if (isSameUsername && isSameRole && isSameBirthdate && isSameHours) {
            showAlert("No changes detected.", "orange");
            return;
        }

        admin.setUsername(uname);
        admin.setRole(r);
        admin.setBirthdate(bd);
        admin.setWorkinghoursarray(selectedHours);

        showAlert("Data changed successfully!", "lightgreen");
    }

    private void showAlert(String msg, String color) {
        alertLabel.setText(msg);
        alertLabel.setStyle("-fx-text-fill: " + color + "; -fx-font-family: Arial;");
        alertLabel.setVisible(true);
        inputBox.requestLayout();
    }

    private void textStyle(TextField textField) {
        textField.setStyle("-fx-background-color: rgba(255,255,255,0.2); " +
                "-fx-text-fill: white; -fx-font-family: Arial; " +
                "-fx-prompt-text-fill: #aaa; -fx-background-radius: 5;");
        textField.setMaxWidth(250);
    }


    private Label fieldLabel(String labelText) {
        Label label = new Label(labelText);
        label.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-family: Arial;");
        return label;
    }


    private Button buttonStyle(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("button");
        return button;
    }

    private void logout() {
        LoginLoader.showLoginDashboard(primaryStage);
    }
}