package fsd.week3.todolistpart3;

import fsd.week3.todolistpart3.datamodel.ToDoData;
import fsd.week3.todolistpart3.datamodel.ToDoItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class Controller {

    private List<ToDoItem> toDoItems;

    @FXML
    private ListView<ToDoItem> toDoListView;

    @FXML
    private TextArea itemDetailsTextArea;

    @FXML
    private Label deadlineLabel;

    @FXML
    private BorderPane mainBorderPane;

    public void initialize() {
        //下列三行是对界面的listView进行初始化
        //toDoListView.setItems(ToDoData.getInstance().getToDoItems());//给toDoListView添加值
        toDoListView.setItems(ToDoData.getInstance().getToDoItems());
        toDoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);//鼠标点一下就能显示出来
        toDoListView.getSelectionModel().selectFirst();//刚上来默认显示第一行的内容

        //开始监听
        toDoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ToDoItem>() {
            @Override
            public void changed(ObservableValue<? extends ToDoItem> observable, ToDoItem oldValue, ToDoItem newValue) {
                if (newValue != null) {
                    ToDoItem item = toDoListView.getSelectionModel().getSelectedItem();//获取点击那一行所对于的属性，相当于new
                    itemDetailsTextArea.setText(item.getDetails());//让textarea显示内容
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d, yyyy");//df这个对象是时间转换器
                    deadlineLabel.setText(df.format(item.getDeadline()));
                }
            }
        });


    }

    @FXML
    public void showItemDialog() {//用来展示item的方法
        //dialog不支持加容器arraylist
        Dialog<ButtonType> dialog = new Dialog<>();//Dialog类就是弹框面板，然后设置最基本的内容，ButtonType.
        dialog.initOwner(mainBorderPane.getScene().getWindow());//设定了这个dialog归属于第一个界面，设定归属者
        dialog.setTitle("To add an item");//给dialog加最顶上的标题
        dialog.setHeaderText("Create a new todo item: =>");//在界面的第一行设置文本信息。
        FXMLLoader fxmlLoader = new FXMLLoader();//FXMLLoader类是加载器，是用来加载fxml文件，把fxml文件展示到页面上去。
        fxmlLoader.setLocation(getClass().getResource("todo-item-dialog.fxml"));//括号内代码是用来获取dialog fxml的地址,
        // 然后将地址设置给fxmlLoader。
        try {
            //先获取dialog的dialogPane面板
            //在获取到面板的情况下,调用获取到的面板的setContent方法,用来设置面板的内容,括号内的信息就是设置的内容。
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch (Exception e) {//当获取不到地址的时候会报错
            System.out.println("Could not load the dialog");
            e.printStackTrace();//把e抓取到的错误打印出来
            return;
        }
        //先获取dialog的面板,然后获取ButtonType(用来装载按钮的集合,本质类似于arraylist),OK CANCEL是已经设置好的按钮。
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        Optional<ButtonType> result = dialog.showAndWait();//Optional是选择类,将按钮的选择点击情况装载到result中
        if (result.isPresent() && result.get() == ButtonType.OK) {//如果result中的信息不为空,通过get获取后的返回结果为ok,则触发以下效果.
            DialogController controller = fxmlLoader.getController();//获取当前正在使用的控制层,就是当前的DialogController.
            ToDoItem newItem = controller.processResults();
            toDoListView.getSelectionModel().select(newItem);//不懂

        } else
            System.out.println("Cancel pressed");
    }



}







