package com.example.week1exercising;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {


    @FXML
    private TextField nameField;

    @FXML
    private Label nameLabel;

    @FXML
    public void onButtonAction(ActionEvent e) {
        nameLabel.setText(nameField.getText());
    }
}