package com.example.gui1.OrganizerGUI;

import Projectt.Organizer;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

public class PasswordEditView {
    private final Organizer organizer;

    public PasswordEditView(Organizer organizer) {
        this.organizer = organizer;
    }

    public void show(Stage primaryStage) {
        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 20;");

        Label title = new Label("🔒 Change Password");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold");

        PasswordField currentPasswordField = new PasswordField();
        PasswordField newPasswordField = new PasswordField();

        Label resultLabel = new Label();

        Button changeBtn = new Button("Change Password");
        Button backBtn = new Button("Back");

        HBox buttonBox = new HBox(10, backBtn, changeBtn);
        buttonBox.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(
                title,
                new Label("Current Password:"), currentPasswordField,
                new Label("New Password:"), newPasswordField,
                buttonBox,
                resultLabel
        );

        changeBtn.setOnAction(e -> {
            String current = currentPasswordField.getText();
            String newPass = newPasswordField.getText();

            if (!organizer.getPassword().equals(current)) {
                resultLabel.setText("❌ Current password is incorrect.");
                return;
            }

            boolean success = organizer.editPassword(newPass);
            if (success) {
                resultLabel.setText("✅ Password changed successfully!");
            } else {
                resultLabel.setText("❌ " + organizer.getPasswordErrorMessage());
            }
        });

        backBtn.setOnAction(e -> new ProfileView(organizer).show(primaryStage));

        Scene scene = new Scene(vbox, 400, 300);
        URL cssUrl = getClass().getResource("/com/example/gui1/buttonStyle.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("CSS file not found!");
        }
        primaryStage.setScene(scene);
        primaryStage.setTitle("Change Password");
        primaryStage.show();
    }
}