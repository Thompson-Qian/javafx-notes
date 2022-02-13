package fsd.week1.exercise1and2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private TextField nameField;

    @FXML
    private Label nameLabel;

    @FXML
    public void onButtonAction(ActionEvent e) {
        //Exercise 2
        //System.out.println(nameField.getText());
        //Exercise 2
        nameLabel.setText(nameField.getText());
    }
}