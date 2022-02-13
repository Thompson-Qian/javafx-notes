module com.example.week1exercising {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.week1exercising to javafx.fxml;
    exports com.example.week1exercising;
}