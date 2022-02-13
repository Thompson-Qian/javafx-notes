package fsd.week2.todolist.datamodel;

import java.time.LocalDate;

public class ToDoItem {
    private String shortDescription;
    private String details;
    private LocalDate deadline; //LocalDate时间类

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public ToDoItem(String shortDescription, String details, LocalDate deadline) {//将要做的事情的构造方法
        this.shortDescription = shortDescription;
        this.details = details;
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return shortDescription;//会返回shortDescription这个字符串
    }
}
