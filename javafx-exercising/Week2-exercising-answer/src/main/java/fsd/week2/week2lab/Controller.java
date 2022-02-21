package fsd.week2.week2lab;

import fsd.week2.week2lab.model.DataModel;
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

    static String filename = "StoreMessages.txt";

    //List<DataModel> captureDetails = new ArrayList<DataModel>();//有个arraylist叫captureDetials里面存的DataModel

    ArrayList<DataModel> captureDetails = new ArrayList<DataModel>();
    @FXML
    private TextField nameTextField;

    @FXML
    private TextArea commentsTextArea;

    private String name;

    private String option;

    private String comments;


    @FXML
    private ChoiceBox<String> choice;

    public void initialize(){//初始化里面一般添加监听
        choice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {//43添加监听

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                option = choice.getSelectionModel().getSelectedItem();//option得到点击的字符串
            }//extends <string>,<>里面的内容需要和choicebox<>里面值的类型一致
        });
    }

    @FXML
    public void storeData()throws IOException{//属于io流。这边用来存数据，把数据存到filename里面。
        name = nameTextField.getText();//获取输入的文字，然后赋值给name
        comments = commentsTextArea.getText();//获取输入的文字，然后赋值给comments
        DataModel capture = new DataModel(name, option, comments);
        captureDetails.add(capture);
        System.out.println(capture.getName()+"\t"+capture.getRequest() +"\t"+capture.getMessage());

        Path path = Paths.get(filename);//找路径
        BufferedWriter bw = Files.newBufferedWriter(path);
        try{
            Iterator<DataModel> it = captureDetails.iterator();
            while (it.hasNext()){//while里面的一行也不可以抄
                DataModel item = it.next();
                bw.write(String.format("%s\t%s\t%s",
                        item.getName(),
                        item.getRequest(),
                        item.getMessage()));
                bw.newLine();
            }
        }finally {
            if (bw != null){
                bw.close();
            }
        }
    }

}