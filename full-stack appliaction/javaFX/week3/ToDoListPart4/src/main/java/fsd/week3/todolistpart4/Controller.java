package fsd.week3.todolistpart4;

import fsd.week3.todolistpart4.datamodel.ToDoData;
import fsd.week3.todolistpart4.datamodel.ToDoItem;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

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

    @FXML
    private ContextMenu listContextMEnu;//ContextMenu类就是鼠标右击后出现的菜单。

    @FXML
    private ToggleButton filterToggleButton;

    private FilteredList<ToDoItem> filteredList;//FilteredList是用来过滤的，如果不符合条件，加进去也会消失。

    private Predicate<ToDoItem> allItems;//Predicate类是一个容器，相当是用来存值的备份。
    private Predicate<ToDoItem> onlyToday;

    public void initialize() {//初始化，方法里面套方法

        listContextMEnu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");//右击后菜单里面的一项
        MenuItem deleteMenuItem1 = new MenuItem("Undo");
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {//监听，点击delete后所触发的效果。
            @Override
            public void handle(ActionEvent event) {
                ToDoItem item = toDoListView.getSelectionModel().getSelectedItem();//获取所点击的item的属性
                deleteItem(item);
            }
        });

        listContextMEnu.getItems().addAll(deleteMenuItem);//加按钮的方法
        listContextMEnu.getItems().addAll(deleteMenuItem1);




        //监听
        toDoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ToDoItem>() {

            @Override
            public void changed(ObservableValue<? extends ToDoItem> observable, ToDoItem oldValue, ToDoItem newValue) {
                if (newValue != null) {
                    ToDoItem item = toDoListView.getSelectionModel().getSelectedItem();
                    itemDetailsTextArea.setText(item.getDetails());
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d, yyyy");//df为实列化的一个时间转换器
                    deadlineLabel.setText(df.format(item.getDeadline()));
                }
            }
        });

        allItems = new Predicate<ToDoItem>() {//对allItems进行初始化，对allItems里面的方法进行重写。
            @Override
            public boolean test(ToDoItem toDoItem) {
                return true;
            }
        };

        onlyToday = new Predicate<ToDoItem>() {
            @Override
            public boolean test(ToDoItem toDoItem) {

                return (toDoItem.getDeadline().equals(LocalDate.now()));//返回true or false，测是否是当前时间。

            }

        };
        filteredList = new FilteredList<ToDoItem>(ToDoData.getInstance().getToDoItems(), allItems);
        //filteredList是用来存ToDoItem类的东西。括号里面是判断部分，ToDoData.getInstance().getToDoItems()传进来的东西，allItems是判断条件。

        //SortedList是用来排序的，括号里filteredList是所有排序的值，逗号后面是排序的规则。
        SortedList<ToDoItem> sortedList = new SortedList<ToDoItem>(filteredList, new Comparator<ToDoItem>() {
            //new Comparator是比较条件，确定时间优先级。
                //SortedList<ToDoItem> sortedList = new SortedList<ToDoItem>(ToDoData.getInstance().getToDoItems(),

                    //pass in the items and compare to get it sorted
                    //the observable list above is used to sort the items and this list puts items on the ListView

                    @Override
                    public int compare(ToDoItem o1, ToDoItem o2) {//01 02的时间进行比较，谁早谁排前面
                        return o1.getDeadline().compareTo(o2.getDeadline());
                    }
                });
        //gets commented out when sorting
        //toDoListView.setItems(ToDoData.getInstance().getToDoItems());
        toDoListView.setItems(sortedList);
        toDoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        toDoListView.getSelectionModel().selectFirst();
        //set cell factory
        //setCellFactory用来将数据独立化,进行单独分析
        toDoListView.setCellFactory(new Callback<ListView<ToDoItem>, ListCell<ToDoItem>>() {
            @Override
            public ListCell<ToDoItem> call(ListView<ToDoItem> param) {
                ListCell<ToDoItem> cell = new ListCell<ToDoItem>() {
                    @Override
                    protected void updateItem(ToDoItem item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(item.getShortDescription());
                            if (item.getDeadline().isBefore(LocalDate.now().plusDays(1))) {//如果当前时间早于当前时间+1天，
                                setTextFill(Color.AQUA);
                            } else if (item.getDeadline().equals(LocalDate.now().plusDays(1))) {
                                setTextFill(Color.GREEN);
                            }
                        }
                    }
                };
                //this is for the delete, the lambda expression not including the return
                cell.emptyProperty().addListener(
                        (obs, wasEmpty, isNowEmpty) -> {
                            if (isNowEmpty) {
                                cell.setContextMenu(null);
                            } else {
                                cell.setContextMenu(listContextMEnu);
                            }
                        });
                return cell;
            }
        });
    }

    @FXML
    public void showItemDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add a new item");
        dialog.setHeaderText("Create a new todo item");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("todo-item-dialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Could not load the dialog");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("Ok pressed");
            DialogController controller = fxmlLoader.getController();
            ToDoItem newItem = controller.processResults();
            toDoListView.getSelectionModel().select(newItem);
        } else
            System.out.println("Cancel pressed");

    }

    public void handleKeyPressed(KeyEvent qqy) {//首先获取当前正在点击的对象，然后判断是否点击delete。
        //fn + backspace -> delete key on Mac
        ToDoItem selectedItem = toDoListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            if (qqy.getCode().equals(KeyCode.DELETE)) {
                deleteItem(selectedItem);
            }
        }
    }



    public void deleteItem(ToDoItem item) {//在同一个类里面，可以互相调用。删除作用。
        //use confirmation dialog to confirm the delete
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);//Alert类是信息提示类，当点击删除时会跳出提示框。
        alert.setTitle("Delete ToDo Item");
        alert.setHeaderText("Delete item: " + item.getShortDescription());
        alert.setContentText("Are you sure you want to delete?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ToDoData.getInstance().deleteToDoItem(item);//从硬盘中删除item。
        }
    }

    public void handleToggle() {//点today's item这个按钮时，判断是否是今天的事件，如果不是，就不显示
        ToDoItem selectedItem = toDoListView.getSelectionModel().getSelectedItem();
        if (filterToggleButton.isSelected()) {
            filteredList.setPredicate(onlyToday);
            if (filteredList.isEmpty()) {
                itemDetailsTextArea.clear();
                deadlineLabel.setText("");
            } else if (filteredList.contains(selectedItem)) {
                toDoListView.getSelectionModel().select(selectedItem);
            } else
                toDoListView.getSelectionModel().selectFirst();

        } else {
            filteredList.setPredicate(allItems);
            toDoListView.getSelectionModel().select(selectedItem);


        }
    }

    @FXML
    public void handleExit(){
        Platform.exit();
    }
}