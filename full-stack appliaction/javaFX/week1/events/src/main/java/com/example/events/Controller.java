package com.example.events;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {
    @FXML//让fxml与controller连接
    private Button HelloButton;//必须与fxml中button id 一致
    @FXML
    private TextField nameField;//必须与fxml中textfield id一致
    @FXML
    private Button ByeByeButton;
    @FXML
    public void initialize(){//setDisable方法将按钮设置为不可用
        HelloButton.setDisable(true);
        ByeByeButton.setDisable(true);
    }

    @FXML
    public void onButtonClicked(ActionEvent e){//e就是被监听到的按钮
        //System.out.println("新年快乐"+nameField.getText());
        if(e.getSource().equals(HelloButton)){
            System.out.println("新年快乐"+nameField.getText());
        }
        else{
            System.out.println("再见"+nameField.getText());
        }
    }

    @FXML
    public void handleKeyReleased(){
        String text = nameField.getText();//传入参数
        boolean disableButtons;//设置为布尔型
        disableButtons = text.isEmpty() || text.trim().isEmpty() ;//如果text.isEmpty为true
        HelloButton.setDisable(disableButtons);//那么HelloButton就隐身
        ByeByeButton.setDisable(disableButtons);
    }
}