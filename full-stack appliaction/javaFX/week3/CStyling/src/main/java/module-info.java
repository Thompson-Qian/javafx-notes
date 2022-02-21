module com.example.cstyling {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cstyling to javafx.fxml;
    exports com.example.cstyling;
}