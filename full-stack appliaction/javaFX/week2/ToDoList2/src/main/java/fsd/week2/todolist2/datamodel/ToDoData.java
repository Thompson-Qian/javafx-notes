package fsd.week2.todolist2.datamodel;

import javafx.collections.FXCollections;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.zip.DataFormatException;

public class ToDoData {
    private static ToDoData instance = new ToDoData();
    private static String filename = "ToDoListITems.txt";//文件地址

    private List<ToDoItem> toDoItems;
    private DateTimeFormatter formatter;

    public static ToDoData getInstance(){
        return instance;
    }

    private ToDoData(){//私有构造方法
        //formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    public List<ToDoItem> getToDoItems() {
        return toDoItems;
    }
//not needed when the hardcoding is removed
//    public void setToDoItems(List<ToDoItem> toDoItems){
//        this.toDoItems = toDoItems;
//    }

    public void loadToDoItems() throws IOException{//文件读取，io流，40到48行直接copy。
        toDoItems = FXCollections.observableArrayList();
        Path path = Paths.get(filename);
        BufferedReader br = Files.newBufferedReader(path);

        String input;

        try{
            while ((input = br.readLine()) != null){
                String [] itemPieces = input.split("\t");//根据tab划分

                String shortDescription = itemPieces[0];
                String details = itemPieces[1];
                String dateString = itemPieces[2];


                LocalDate date = LocalDate.parse(dateString, formatter);//转换文件里面日期，把日期变成字符串
                ToDoItem toDoItem = new ToDoItem(shortDescription, details, date);
                toDoItems.add(toDoItem);
            }
        }finally {
            if (br != null){
                br.close();
            }
        }
    }

    public void storeToDoItems() throws IOException{
        Path path = Paths.get(filename);
        BufferedWriter bw = Files.newBufferedWriter(path);
        try{
            Iterator<ToDoItem> it = toDoItems.iterator();
            while (it.hasNext()){
                ToDoItem item = it.next();
                bw.write(String.format("%s\t%s\t%s",
                        item.getShortDescription(),
                        item.getDetails(),
                        item.getDeadline().format(formatter)));
                bw.newLine();
            }
        }finally {
            if (bw != null){
                bw.close();
            }
        }
    }
}

