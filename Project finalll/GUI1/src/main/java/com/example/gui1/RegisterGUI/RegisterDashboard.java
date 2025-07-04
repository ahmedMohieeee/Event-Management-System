package com.example.gui1.RegisterGUI;

import Projectt.Attendee;
import Projectt.Database;
import Projectt.Gender;
import com.example.gui1.LoginGUI.LoginLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import java.net.URL;

public class RegisterDashboard {
    private Stage primaryStage;

    public void RegisterDashboard(Stage stage) {
        this.primaryStage = stage;
    }

    public void show() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Attendee Registration");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        scenetitle.setFill(Color.WHITE);
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userNameLabel = new Label("Username:");
        grid.add(userNameLabel, 0, 1);
        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pwLabel = new Label("Password:");
        grid.add(pwLabel, 0, 2);
        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        Label confirmPwLabel = new Label("Confirm Password:");
        grid.add(confirmPwLabel, 0, 3);
        PasswordField confirmPwBox = new PasswordField();
        grid.add(confirmPwBox, 1, 3);

        Label dobLabel = new Label("Date of Birth (dd/mm/yyyy):");
        grid.add(dobLabel, 0, 4);
        TextField dobField = new TextField();
        grid.add(dobField, 1, 4);

        Label genderLabel = new Label("Gender:");
        genderLabel.setTextFill(Color.WHITE);
        grid.add(genderLabel, 0, 5);

        ToggleGroup genderGroup = new ToggleGroup();
        HBox genderBox = new HBox(10);

        RadioButton maleRadio = new RadioButton("Male");
        maleRadio.setToggleGroup(genderGroup);
        maleRadio.setSelected(true);
        maleRadio.setTextFill(Color.WHITE);

        RadioButton femaleRadio = new RadioButton("Female");
        femaleRadio.setToggleGroup(genderGroup);
        femaleRadio.setTextFill(Color.WHITE);

        genderBox.getChildren().addAll(maleRadio, femaleRadio);
        grid.add(genderBox, 1, 5);

        Button submitBtn = new Button("Submit");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(submitBtn);
        grid.add(hbBtn, 1, 6);

        final Text actiontarget = new Text();
        actiontarget.setStyle("-fx-fill: #d9534f; -fx-font-weight: bold;");
        grid.add(actiontarget, 1, 7);

        submitBtn.setOnAction(e -> {
            String username = userTextField.getText().trim();
            String password = pwBox.getText();
            String confirmPassword = confirmPwBox.getText();
            String dob = dobField.getText().trim();
            Gender gender = maleRadio.isSelected() ? Gender.MALE : Gender.FEMALE;

            actiontarget.setText("");

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || dob.isEmpty()) {
                actiontarget.setText("Please fill in all fields");
                return;
            }

            if (!password.equals(confirmPassword)) {
                actiontarget.setText("ERROR: Passwords do not match!");
                pwBox.setStyle("-fx-border-color: #d9534f;");
                confirmPwBox.setStyle("-fx-border-color: #d9534f;");
                return;
            } else {
                pwBox.setStyle("");
                confirmPwBox.setStyle("");
            }

            if (username.length() < 4 || username.length() > 20) {
                actiontarget.setText("Username must be 4-20 characters long");
                return;
            }
            if (username.contains(" ")) {
                actiontarget.setText("Username cannot contain spaces");
                return;
            }
            if (Database.attendees.stream().anyMatch(a -> a.getUsername().equalsIgnoreCase(username))) {
                actiontarget.setText("Username already exists");
                return;
            }

            if (password.length() < 8) {
                actiontarget.setText("Password must be at least 8 characters");
                return;
            } else if (!password.chars().anyMatch(Character::isUpperCase)) {
                actiontarget.setText("Password must contain at least one uppercase letter");
                return;
            } else if (!password.chars().anyMatch(Character::isLowerCase)) {
                actiontarget.setText("Password must contain at least one lowercase letter");
                return;
            } else if (!password.chars().anyMatch(Character::isDigit)) {
                actiontarget.setText("Password must contain at least one digit");
                return;
            } else if (!password.matches(".*[!@#$%^&()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) {
                actiontarget.setText("Password must contain at least one special character");
                return;
            }

            Attendee newAttendee = new Attendee(username, password, dob, gender, "Not specified", 0.0);
            Database.attendees.add(newAttendee);

            actiontarget.setStyle("-fx-fill: #5cb85c;");
            actiontarget.setText("Registration successful!");

            userTextField.clear();
            pwBox.clear();
            confirmPwBox.clear();
            dobField.clear();
            maleRadio.setSelected(true);
            LoginLoader.showLoginDashboard(primaryStage);
        });

        Scene scene = new Scene(grid, 450, 400);
        URL cssUrl = getClass().getResource("/com/example/gui1/buttonStyle.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("CSS file not found!");
        }
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean isValidDate(String date) {
        if (!date.matches("\\d{2}/\\d{2}/\\d{4}")) {
            return false;
        }

        String[] parts = date.split("/");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);

        if (day < 1 || day > 31) return false;
        if (month < 1 || month > 12) return false;
        if (year < 1900 || year > java.time.LocalDate.now().getYear()) return false;

        return true;
    }
}
