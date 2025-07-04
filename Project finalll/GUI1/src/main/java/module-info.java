module com.example.gui1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jdk.compiler;
    exports com.example.gui1.AttendeeGUI;
    exports com.example.gui1.LoginGUI;
    exports com.example.gui1.AdminGui;
    exports com.example.gui1.OrganizerGUI;
    exports com.example.gui1.RegisterGUI;
    opens com.example.gui1.AttendeeGUI to javafx.fxml;
    opens com.example.gui1 to javafx.fxml;

    exports Projectt;


    opens com.example.gui1.LoginGUI to javafx.fxml, javafx.graphics;
    opens com.example.gui1.AdminGui to javafx.fxml;

}