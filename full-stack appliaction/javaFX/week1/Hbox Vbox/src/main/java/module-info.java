module com.example.hboxlayout {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.hboxlayout to javafx.fxml;
    exports com.example.hboxlayout;
}