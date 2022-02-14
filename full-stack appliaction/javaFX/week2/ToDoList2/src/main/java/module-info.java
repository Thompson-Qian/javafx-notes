module fsd.week2.todolist2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens fsd.week2.todolist2 to javafx.fxml;
    exports fsd.week2.todolist2;
}