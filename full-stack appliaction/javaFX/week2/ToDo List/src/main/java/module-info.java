module fsd.week2.todolist {
    requires javafx.controls;
    requires javafx.fxml;


    opens fsd.week2.todolist to javafx.fxml;
    exports fsd.week2.todolist;
}