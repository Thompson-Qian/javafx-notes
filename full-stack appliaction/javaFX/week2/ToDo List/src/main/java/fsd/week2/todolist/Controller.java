package fsd.week2.todolist;

import fsd.week2.todolist.datamodel.ToDoItem;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    @FXML
    private ListView<ToDoItem> toDoListView;//因为listview里面要展示的东西是ToDoItem class中的,listView是图像集合

    @FXML
    private TextArea itemDetailsTextArea;

    private List<ToDoItem>  toDoItems;//有个list类名叫toDoItem里面放ToDoItem类，list是一个接口。这边告诉计算机，将会有个叫toDoItems的list去装ToDoItem类

    public void initialize() {
        ToDoItem item1 = new ToDoItem("Post birthday card", "Buy and write out birthday card",
                LocalDate.of(2022, Month.JULY, 17));//实列化事件1
        ToDoItem item2 = new ToDoItem("Weekend away", "Book for the weekend away and get pets booked in",
                LocalDate.of(2022, Month.APRIL, 10));
        ToDoItem item3 = new ToDoItem("Plan birthday party", "Send out invites for the birthday and book venue",
                LocalDate.of(2022, Month.SEPTEMBER, 01));
        ToDoItem item4 = new ToDoItem("Get car serviced", "Book a service date for before we go away for the weekend",
                LocalDate.of(2022, Month.MARCH, 05));
        ToDoItem item5 = new ToDoItem("Assignments for FSD", "Plan for assignment deadlines in term 2",
                LocalDate.of(2022, Month.JANUARY, 25));

        toDoItems = new ArrayList<ToDoItem>();//多态，list是arraylist的父类
        toDoItems.add(item1);
        toDoItems.add(item2);
        toDoItems.add(item3);
        toDoItems.add(item4);
        toDoItems.add(item5);//将每个事件都添加在arraylist之中
        toDoListView.getItems().setAll(toDoItems);//将arraylist里面的东西显示在screen上面
        //getItems方法是获得toDoListView这个盒子的控制权
        // setall方法：假设toDoListView之前有内容，用了setall之后可以把仓库清空刷新，只放toDoItems里面的东西
        toDoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);//每次只能点一个
    }

    public void handleClickListView(){//每次点击时所触发的方法。
        ToDoItem item = toDoListView.getSelectionModel().getSelectedItem();//获取到被点击的对象的信息
        //getSelectionModel方法是用来锁定鼠标所点击的对象,getSelectedItem 是用来获取选中对象所包含的灵魂
        //StringBuilder sb = new StringBuilder(item.getDetails());//获取属性
        String s=item.getDetails();
        //sb.append("\n\n\n\n");
        s=s+"\n\n\n\n";
        //sb.append("Due:");
        s=s+"Due:";
        s=s+"\n";
        //sb.append(item.getDeadline().toString());//把时间转化成字符串
        s=s+item.getDeadline().toString();
       //// itemDetailsTextArea.setText(sb.toString()
        itemDetailsTextArea.setText(s);
    }
}