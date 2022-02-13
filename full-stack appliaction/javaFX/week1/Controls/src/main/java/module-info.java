module fxd.controlsradiobuttoncheckbox {
    requires javafx.controls;
    requires javafx.fxml;


    opens fxd.controlsradiobuttoncheckbox to javafx.fxml;
    exports fxd.controlsradiobuttoncheckbox;
}