package fsd.week3.todolistpart3;

import fsd.week3.todolistpart3.datamodel.ToDoData;
import fsd.week3.todolistpart3.datamodel.ToDoItem;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.time.LocalDate;

public class DialogController {

    @FXML
    private TextField shortDescrip;

    @FXML
    private TextArea details;

    @FXML
    private DatePicker deadline;


    public ToDoItem processResults(){
        String shortS = shortDescrip.getText().trim();//.trim把空格全部去掉
        String detailsS = details.getText().trim();
        LocalDate deadlineS = deadline.getValue();

        ToDoItem newItem = new ToDoItem(shortS, detailsS, deadlineS);
        //ToDoData.getInstance().addToDoItem(newItem);
        ToDoData.getInstance().addToDoItem(newItem);//往硬盘里面存东西
        return newItem;
    }
}
