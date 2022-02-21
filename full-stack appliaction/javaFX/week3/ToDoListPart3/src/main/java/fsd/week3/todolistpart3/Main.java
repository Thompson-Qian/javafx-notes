package fsd.week3.todolistpart3;

import fsd.week3.todolistpart3.datamodel.ToDoData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-window-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 400);
        stage.setTitle("Todo List Application");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() throws IOException{//结束，点❎号才会执行
        try{
            //ToDoData.getInstance().storeToDoItems();
            ToDoData.getInstance().storeToDoItems();//数据存储完毕，存到硬盘
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void init() throws IOException{//上来做的第一件事，准备工作
        try{ //load the data in
            //ToDoData.getInstance().loadToDoItems();
            ToDoData.getInstance().loadToDoItems();//电脑获取硬盘并且加载文件。
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

}