module fsd.week1.firstprogram {
    requires javafx.controls;
    requires javafx.fxml;


    opens fsd.week1.firstprogram to javafx.fxml;
    exports fsd.week1.firstprogram;
}