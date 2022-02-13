module fsd.flowpane {
    requires javafx.controls;
    requires javafx.fxml;


    opens fsd.flowpane to javafx.fxml;
    exports fsd.flowpane;
}