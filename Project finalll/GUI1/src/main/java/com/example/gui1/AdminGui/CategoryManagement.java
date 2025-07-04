package com.example.gui1.AdminGui;
import Projectt.Admin;
import Projectt.Category;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

public class CategoryManagement {
    private Stage primaryStage;
    private boolean addShow,deleteShow;
    private VBox inputBox = new VBox(10);
    private TextField name = new TextField();
    private TextField description = new TextField();
    private Admin admin = null;

    public CategoryManagement(Stage primaryStage,Admin admin) {
        this.primaryStage = primaryStage;
        this.admin = admin;
    }

    public void show() {
        VBox orgVbox = new VBox(20);
        orgVbox.setAlignment(Pos.TOP_CENTER);
        orgVbox.setPadding(new Insets(25));
        orgVbox.setStyle("-fx-background-color: linear-gradient(to bottom right, #1A1A2E, #4A148C);");
        Label title = new Label("Categories Management");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white; -fx-font-family: Arial;");

        Button showAllbutton = buttonStyle("Show All");
        Button addCategory = buttonStyle("Add Category");
        Button deleteCategory = buttonStyle("Delete Category");
        Button backbutton = buttonStyle("Back to Dashboard");
        showAllbutton.setOnAction(e-> showCat());
        addCategory.setOnAction(e -> addCat());
        deleteCategory.setOnAction(e -> deleteCat());
        backbutton.setOnAction(e -> new AdminDashboard(primaryStage, admin).show());
        primaryStage.setTitle("Categories");
        textStyle(name);
        textStyle(description);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setPadding(new Insets(15));
        inputBox.setVisible(false);

        orgVbox.getChildren().addAll(title, showAllbutton, addCategory, deleteCategory, inputBox, backbutton);
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

    public void showCat(){
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
        Label title = new Label("Categories Available");
        title.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-font-family: Arial;");
        inputBox.getChildren().add(title);
        for (String[] cat : admin.showCategories()) {
            Label catLabel = new Label(cat[0]);
            catLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: white; -fx-font-family: Arial;");
            Label descriptionLabel = new Label(cat[1]);
            descriptionLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: white; -fx-font-family: Arial;");
            inputBox.getChildren().addAll(catLabel,descriptionLabel);
        }
    }

    private void addCat() {
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
        Label title = new Label("Add New Category");
        title.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-font-family: Arial;");
        Label nameLabel = new Label("Category Name:");
        nameLabel.setStyle("-fx-text-fill: white; -fx-font-family: Arial;");
        Label descriptionLabel = new Label("Description:");
        descriptionLabel.setStyle("-fx-text-fill: white; -fx-font-family: Arial;");

        Button submitButton = buttonStyle("Submit");
        submitButton.setOnAction(e -> {
            String nameInput = name.getText();
            String descriptionInput = description.getText();
            if (nameInput.isBlank() || descriptionInput.isBlank())
                return;
            inputBox.getChildren().removeIf(node -> node instanceof Label && "alert".equals(node.getId()));
            if (admin.addCategory(nameInput,descriptionInput)) {
                Label success = new Label("Category added successfully!");
                success.setStyle("-fx-text-fill: lightgreen; -fx-font-family: Arial;");
                success.setId("alert"); // for later removal
                inputBox.getChildren().add(success);
            }
            else
            {
                Label invalid = new Label("Invalid Category Name.");
                invalid.setStyle("-fx-text-fill: red; -fx-font-family: Arial;");
                invalid.setId("alert");
                inputBox.getChildren().add(invalid);
            }
            name.clear();
            description.clear();
        });
        inputBox.getChildren().addAll(title, nameLabel, name, descriptionLabel, description, submitButton);
        inputBox.setVisible(true);
        inputBox.setManaged(true);
        addShow = true;
        Category.updateObservableList();
    }

    private void deleteCat() {
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

        Label title = new Label("Delete Category");
        title.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-font-family: Arial;");
        Label nameLabel = new Label("Category Name:");
        nameLabel.setStyle("-fx-text-fill: white; -fx-font-family: Arial;");

        Button submitbutton = buttonStyle("Delete");
        submitbutton.setOnAction(e -> {
            inputBox.getChildren().removeIf(node -> node instanceof Label && "alert".equals(node.getId()));
            String nameInput = name.getText();
            if (nameInput.isBlank())
                return;
            if(admin.deleteCategory(nameInput)){
                Label success = new Label("Category removed successfully!");
                success.setStyle("-fx-text-fill: lightgreen; -fx-font-family: Arial;");
                success.setId("alert"); // for later removal
                inputBox.getChildren().add(success);
            }
            else {
                Label invalid = new Label("Incorrect Category name!");
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
        Category.updateObservableList();
    }

    private void textStyle(TextField textField) {
        textField.setStyle("-fx-background-color: rgba(255,255,255,0.2); " +
                "-fx-text-fill: white; -fx-font-family: Arial; " +
                "-fx-prompt-text-fill: #aaa; -fx-background-radius: 5;");
        textField.setMaxWidth(250);
    }
}