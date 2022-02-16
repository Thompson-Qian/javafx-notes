package com.example.week2lab;

import com.example.week2lab.model.DataModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Controller {
    @FXML
    private TextField nameTextField;

    @FXML
    private ChoiceBox<String> choices;

    @FXML
    private TextArea commentsTextArea;

    List<DataModel> captureDetails = new ArrayList<DataModel>();


    private String name;

    private String option;

    private String comments;

    private static String filename = "StoreMessages.txt";




    public void initialize(){//要对choice box进行监听
        choices.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override

            public void changed(ObservableValue<? extends String> observable, String oldValue, String
                    newValue) {
                option = choices.getSelectionModel().getSelectedItem();//option是choice box中任意点击的

            }
    });
    }

    //IO流读取文件
    @FXML
    public void storeData()throws IOException {
        name = nameTextField.getText();
        comments = commentsTextArea.getText();
        DataModel capture = new DataModel(name, option, comments);
        captureDetails.add(capture);
        System.out.println(capture.getName() + "\t" + capture.getRequest() + "\t" + capture.getMessage());

        Path path = Paths.get(filename);
        BufferedWriter bw = Files.newBufferedWriter(path);
        try {
            Iterator<DataModel> it = captureDetails.iterator();
            while (it.hasNext()) {
                DataModel item = it.next();
                bw.write(String.format("%s\t%s\t%s",
                        item.getName(),
                        item.getRequest(),
                        item.getMessage()));
                bw.newLine();
            }
        } finally {
            if (bw != null) {
                bw.close();
            }
        }




    }











    @FXML
    protected void onHelloButtonClick() {

    }
}