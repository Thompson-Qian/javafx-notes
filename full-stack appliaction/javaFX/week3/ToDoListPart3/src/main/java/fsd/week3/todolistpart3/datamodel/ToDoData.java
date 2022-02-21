package fsd.week3.todolistpart3.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

public class ToDoData {//ToDoData是用来装数据的仓库

    static ToDoData instance = new ToDoData();//instance是唯一一个可以用来装数据的盒子，所有数据都装在instance里面,
    // ToDoData是电脑类，instance就是一台真实的电脑。instance是大家的，公用的存储工具。
    static String filename = "ToDoListITems.txt";

    private ObservableList<ToDoItem> toDoItems;//用来装载从文件中读取的数据，把这些数据存在名叫toDoItems的ObservableList里面,相当于电脑的硬盘
    private DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd");;//时间强制转化器

    public static ToDoData getInstance() { //为了获取电脑的方法
        return instance;
    }

    public ObservableList<ToDoItem> getToDoItems() {//为了获取电脑硬盘的方法
        return toDoItems;
    }

    public void addToDoItem(ToDoItem item){//给硬盘添加资料的办法
        toDoItems.add(item);
    }


    public void loadToDoItems() throws IOException {//io流写在任何类都可以
        toDoItems = FXCollections.observableArrayList();//对toDoItems进行赋值，获取了一个toDoItems的盒子
        Path path = Paths.get(filename);
        BufferedReader br = Files.newBufferedReader(path);//读文件

        String input;

        try {
            while ((input = br.readLine()) != null) {
                String[] itemPieces = input.split("\t");//调用了String的split方法,根据tab空格进行拆分,因为一行字符串中有两个tab,所以拆分成了三个,分别编号为0,1,2

                String shortDescriptit = itemPieces[0];
                String details = itemPieces[1];
                String dateString = itemPieces[2];

                LocalDate date = LocalDate.parse(dateString, formatter);//强制转换，把当地时间转化为字符串形式
                ToDoItem toDoItem = new ToDoItem(shortDescriptit, details, date);
                toDoItems.add(toDoItem);
            }
        } finally {
            if (br != null) {
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

