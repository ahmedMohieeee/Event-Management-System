package com.example.gui1.AttendeeGUI;

import Projectt.Attendee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;

import java.io.IOException;
import Projectt.Category;

public class AddInterestsController {

    @FXML
    ListView<Category> categoriesListView;
    @FXML
    Button submitInterests;
    private Attendee attendee;

    public AddInterestsController() {
    }

    public AddInterestsController(Attendee attendee){
        this.attendee=attendee;
    }

    public void setAttendee(Attendee attendee) {
        this.attendee = attendee;
    }

    public void initialize() {
        String check=null;
        try{
            categoriesListView.setItems(Category.observableCategories);
            categoriesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        }catch(NullPointerException e)
        {
            System.out.println("categoriesListView is null");
        }
        submitInterests.setOnAction(event -> {
            if((categoriesListView.getSelectionModel().getSelectedItems()).equals(check)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid");
                alert.setHeaderText("You must select an interest to be added!");
                alert.setContentText("Select an interest.");
                alert.showAndWait();
            }else {
                for (Category selectedCategory : categoriesListView.getSelectionModel().getSelectedItems()) {
                    if(attendee.checkInterests(selectedCategory)){
                        attendee.addSelectedCategory(selectedCategory);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Interest added!");
                        alert.setHeaderText(null);
                        alert.setContentText("Your interest has been successfully added.");
                        alert.showAndWait();
                    }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid");
                    alert.setHeaderText("You already added this interest!");
                    alert.setContentText("Please select another interest.");
                    alert.showAndWait();}
                }


            }});
    }

    public void switchBack(ActionEvent event) throws IOException {
       /* Parent root = FXMLLoader.load(getClass().getResource("/org/example/myjavafxgui/hello-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();*/

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gui1/hello-view.fxml"));
        Parent root = loader.load();
        HelloController controller = loader.getController();
        controller.init(attendee);
        controller.setAttendee(attendee);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
