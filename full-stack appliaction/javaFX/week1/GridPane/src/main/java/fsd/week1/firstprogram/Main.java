package fsd.week1.firstprogram;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

//model-view-controller
public class Main extends Application {
    @Override
    //stage is a window
    public void start(Stage stage) throws IOException {

        //stage是窗口，scene是场景，layout是布局
        //Javafx最外面是application,里面一层是窗口(stage)，再里面一层是场景(scene)，最里面一层层是布局(layout)。
        //fxml是用来画页面的语言。
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-window-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}