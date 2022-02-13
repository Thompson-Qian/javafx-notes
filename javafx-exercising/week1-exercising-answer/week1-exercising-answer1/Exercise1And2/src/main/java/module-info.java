module fsd.week1.exercise1and2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens fsd.week1.exercise1and2 to javafx.fxml;
    exports fsd.week1.exercise1and2;
}