module com.example.week2lab {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.week2lab to javafx.fxml;
    exports com.example.week2lab;
}