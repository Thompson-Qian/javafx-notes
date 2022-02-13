module com.example.borderpanelayout {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.borderpanelayout to javafx.fxml;
    exports com.example.borderpanelayout;
}