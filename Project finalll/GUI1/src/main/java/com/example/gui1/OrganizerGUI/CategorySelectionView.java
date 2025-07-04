package com.example.gui1.OrganizerGUI;

import Projectt.Category;
import Projectt.Database;
import Projectt.Events;
import Projectt.Organizer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;

public class CategorySelectionView {

    private final Organizer organizer;
    private final Events event;

    public CategorySelectionView(Organizer organizer, Events event) {
        this.organizer = organizer;
        this.event = event;
    }

    public void show(Stage primaryStage) {
        VBox vbox = new VBox(13);
        vbox.setPadding(new Insets(30));

        Label title = new Label("Categories 📋");
        title.setStyle("-fx-padding:20;-fx-font-size:18px;-fx-font-weight:bold");
        HBox Htitle = new HBox(title);
        Htitle.setAlignment(Pos.CENTER);

        List<String> categories = organizer.showCategories();
        ListView<String> categoriesView = new ListView<>();
        categoriesView.getItems().addAll(categories);

        Label enter = new Label("Category index: ");
        TextField tfnum = new TextField();
        HBox hbox2 = new HBox(10, enter, tfnum);
        hbox2.setPadding(new Insets(20));
        hbox2.setAlignment(Pos.CENTER);

        Button backBtn = new Button("Back to dashboard");
        Button selectBtn = new Button("Next");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox hbox1 = new HBox(10, backBtn, spacer, selectBtn);
        hbox1.setPadding(new Insets(20));
        hbox1.setAlignment(Pos.CENTER);

        backBtn.setOnAction(e -> new EventCreationView(organizer).show(primaryStage));

        selectBtn.setOnAction(e -> {
            String num = tfnum.getText();
            try {
                int index = Integer.parseInt(num);
                if (index >= 0 && index < categories.size()) {
                    Category selectedCategory = Database.categories.get(index);
                    selectedCategory.addEvent(event);
                    event.setCategory(selectedCategory.getName());
                    showAlert("Event created successfully in the " + selectedCategory.getName() + " category.");
                } else {
                    showAlert("Invalid index. Please enter a number between 0 and " + (categories.size() - 1));
                }
            } catch (NumberFormatException ex) {
                showAlert("Please enter a valid integer");
            }
        });

        vbox.getChildren().addAll(Htitle, categoriesView, hbox2, hbox1);

        Scene scene = new Scene(vbox, 400, 500);
        URL cssUrl = getClass().getResource("/com/example/gui1/buttonStyle.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("CSS file not found!");
        }
        primaryStage.setTitle("Category List");
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