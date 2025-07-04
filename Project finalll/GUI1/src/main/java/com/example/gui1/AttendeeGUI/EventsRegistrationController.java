package com.example.gui1.AttendeeGUI;
import Projectt.Category;
import Projectt.Events;


import Projectt.Attendee;
import Projectt.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.InputMismatchException;

public class EventsRegistrationController {

    @FXML
    Label showEvent;
    @FXML
    TextField moneyDeposit;
    @FXML
    TreeView<Object> EventsTree;
    @FXML
    Button submitButton;
    private Attendee attendee;

    public EventsRegistrationController() {
    }

    public void setAttendee(Attendee attendee) {
        this.attendee = attendee;
    }

    public void initialize() {
        displayEventCategoriesTree(EventsTree);
    }

    public void displayEventCategoriesTree(TreeView<Object> treeView) {
        // if (attendee == null) {
        //   System.err.println("Attendee is null in EventsRegistrationController");
        // return;
        //}

        TreeItem<Object> root = new TreeItem<>("Categories");
        root.setExpanded(true);

        for (Category category : Database.categories) {
            TreeItem<Object> categoryItem = new TreeItem<>(category);
            for (Events event : Database.events) {
                if (event.getCategory().equalsIgnoreCase(category.getName())) {
                    categoryItem.getChildren().add(new TreeItem<>(event));
                }
            }
            root.getChildren().add(categoryItem);
        }

        if (EventsTree == null) {
            System.err.println("EventsTree is null");
            return;
        }

        EventsTree.setRoot(root);
        EventsTree.setShowRoot(false);
        EventsTree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


        submitButton.setOnAction(e -> {
            TreeItem<Object> selectedItem = EventsTree.getSelectionModel().getSelectedItem();
            if (selectedItem != null && selectedItem.getValue() instanceof Events) {
                Events selectedEvent = (Events) selectedItem.getValue();

                if (Attendee.registeredEvents.contains(selectedEvent)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Selection");
                    alert.setHeaderText("Invalid event selection");
                    alert.setContentText("Please select a valid event.");
                    alert.showAndWait();
                    return;
                }

                setSelectedEvent(selectedEvent);
                try {
                    /*Parent root1;
                    root1 = FXMLLoader.load(getClass().getResource("/org/example/myjavafxgui/ContinueRegistration.fxml"));
                    setAttendee(attendee);
                    display(selectedEvent);
                    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root1);
                    stage.setScene(scene);
                    stage.show();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/myjavafxgui/ContinueRegistration.fxml"));
                    Parent root1 = loader.load();
                    setAttendee(attendee);
                    display(selectedEvent);
                    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root1);
                    stage.setScene(scene);
                    stage.show();*/

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gui1/ContinueRegistration.fxml"));
                    Parent root1 = loader.load();
                    ContinueRegistrationController controller = loader.getController();
                    controller.display(selectedEvent, attendee);
                    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root1));
                    stage.show();

                    showEvent.setText("Selected: " + selectedEvent.getEventName());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No event is selected");
                alert.setHeaderText("You need to select an event!");
                alert.setContentText("Please select an event.");
                alert.showAndWait();
            }
        });
    }

   /* public void initialize(){

        TreeItem<Category> root = new TreeItem<>(new Category("All Categories", "All Categories"));
        root.setExpanded(true);
        for (Category category : Database.categories) {
            root.getChildren().add(new TreeItem<>(category));
        }
        EventsTree.setRoot(root);
        EventsTree.setShowRoot(false);
        EventsTree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        submitButton.setOnAction(e -> {
            TreeItem<Category> selectedItem = EventsTree.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                Parent root1 = null;
                try {
                    root1 = FXMLLoader.load(getClass().getResource("/org/example/myjavafxgui/ContinueRegistration.fxml"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                Scene scene = new Scene(root1);
                stage.setScene(scene);
                stage.show();
                showEvent.setText("Selected: " + selectedItem.getValue().getName());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No event selected");
                alert.setHeaderText("You need to select an event!");
                alert.setContentText("Please select an event.");
                alert.showAndWait();
            }
        });
    }*/


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
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    private static Events selectedEvent;

    public static void setSelectedEvent(Events event) {
        selectedEvent = event;
    }

    public static Events getSelectedEvent() {
        return selectedEvent;
    }


    public void confirmMoneyDeposited(ActionEvent event) {

        double deposit = 0;


            try {
                deposit = Double.parseDouble(moneyDeposit.getText());

                if (deposit <= 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Cannot deposit money");
                    alert.setHeaderText("Deposited money must be a number greater than 0!");
                    alert.setContentText("Please enter the amount to be deposited.");
                    alert.showAndWait();
                } else {
                    attendee.setBalance(attendee.getBalance()+deposit);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Money deposited!");
                    alert.setHeaderText(null);
                    alert.setContentText("Your money has been successfully deposited.");
                    alert.showAndWait();
                }
            } catch (InputMismatchException | NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Cannot deposit money");
                alert.setHeaderText("Deposited money must be a number!");
                alert.setContentText("Please enter the amount to be deposited.");
                alert.showAndWait();
            }
    }
}

