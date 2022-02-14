package fsd.week2.todolist2;

import fsd.week2.todolist2.datamodel.ToDoData;
import fsd.week2.todolist2.datamodel.ToDoItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private List<ToDoItem> toDoItems;

    @FXML
    private ListView<ToDoItem> toDoListView;

    @FXML
    private TextArea itemDetailsTextArea;

    @FXML
    private Label deadlineLabel;

    public void initialize() {

        toDoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ToDoItem>() {
        //对toDoListView进行监听
            @Override
            public void changed(ObservableValue<? extends ToDoItem> observable, ToDoItem oldValue, ToDoItem
            newValue) {
                //changed方法就是当点击新按钮的时候所做的改变方法

                if(newValue != null){//如果新点击的不为空
                    ToDoItem item = toDoListView.getSelectionModel().getSelectedItem();//得到点击的内容
                    itemDetailsTextArea.setText(item.getDetails());//显示上去
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM/d/yyyy");
                    //DateTimeFormatter时间格式转换类,固定写法
                    deadlineLabel.setText(df.format(item.getDeadline()));
                }
            }
        });//33到36是固定写法，说明监听哪一块，当出现指定变化时要做出反应。

        //populate the toDoListView
        toDoListView.getItems().setAll(ToDoData.getInstance().getToDoItems());//把数据显示出来。
        //set it to select one item at a time
        toDoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        toDoListView.getSelectionModel().selectFirst();//刚打开默认选择第一行
    }

    @FXML
    public void handleClickListView() {
        ToDoItem item = toDoListView.getSelectionModel().getSelectedItem();
        itemDetailsTextArea.setText(item.getDetails());
        deadlineLabel.setText(item.getDeadline().toString());
    }
}