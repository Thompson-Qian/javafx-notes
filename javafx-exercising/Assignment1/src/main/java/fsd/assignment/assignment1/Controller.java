package fsd.assignment.assignment1;

import fsd.assignment.assignment1.datamodel.Student;
import fsd.assignment.assignment1.datamodel.StudentData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Controller {

    //these variables correspond to the <top> of main-view.fxml
    @FXML
    private TextField studId;

    @FXML
    private TextField yearStudy;

    @FXML
    private ChoiceBox<String> mod1Choice;

    @FXML
    private ChoiceBox<String> mod2Choice;

    @FXML
    private ChoiceBox<String> mod3Choice;

    private String choice1, choice2, choice3;

    private String modChoices[] = {"OOP", "Data Algo", "DS", "Maths", "AI",
            "Adv Programming", "Project"};//集合

    @FXML
    private Label validateStudent; //remember this is the Label that you only see when there is an invalid "add"

    //validateStudent is the last element corresponding to <top>

    //these variables correspond to the <left> i.e. the studentListView
    @FXML
    private ListView<Student> studentListView;

    //these variables correspond to the <bottom> part of the border
    @FXML
    private Label yearStudyView;

    @FXML
    private Label mod1View;

    @FXML
    private Label mod2View;

    @FXML
    private Label mod3View;

    //mod3View is the last element for the bottom part of the border

    //the contextMenu is used for the right-click regarding Edit / Delete
    @FXML
    private ContextMenu listContextMenu;//右击后出现的菜单

    //this variable is used when switching windows from add to edit
    @FXML
    private BorderPane mainWindow;

    //used to add a student to the ArrayList for addStudentData()
    public Student studentToAdd;


    public void initialize() {//初始化方法

        studentListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Student>() {//添加监听
            @Override
            public void changed(ObservableValue<? extends Student> observable, Student oldValue, Student newValue) {
                /* TODO: complete the code so that if a studId is selected then the changed()
                         should ensure that the other fields related to the selected item appear at the bottom of the
                          window
                */if (newValue != null) {

                    Student item = studentListView.getSelectionModel().getSelectedItem();//把最新点击的studentListView
                    // 里面的对象赋值给item
                    //studentListView.getSelectionModel().select(newValue);
                    yearStudyView.setText(item.getYearOfStudy());
                    mod1View.setText(item.getModule1());
                    mod2View.setText(item.getModule2());
                    mod3View.setText(item.getModule3());


                }
            }

        });
        //the setOnAction ensures that when a ChoiceBox is selected the getChoice() grabs the selected choice
        mod1Choice.setOnAction(this::getChoice);
        mod2Choice.setOnAction(this::getChoice);
        mod3Choice.setOnAction(this::getChoice);

        /* TODO: the array declared above for modChoices must be added to each Choicebox
                 include the code here to addAll()
        */
        //insert the code to addAll() the modChoices [] to each ChoiceBox here
        mod1Choice.getItems().addAll(modChoices);//将choice box里面的option添加内容，内容就是modChoices集合。
        mod2Choice.getItems().addAll(modChoices);
        mod3Choice.getItems().addAll(modChoices);
        //deleting a student
        /* TODO: create a new listContextMenu -> defined above in the variables
        */
        listContextMenu = new ContextMenu();
        /* TODO: create a MenuItem object so that when the user right-clicks a studId
                 the word Delete? appears
         */
        MenuItem deleteStudent = new MenuItem("Delete?");

        deleteStudent.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                /* TODO: get  item to be deleted and call the deleteStudent()
                 */
                Student item = studentListView.getSelectionModel().getSelectedItem();//获取所点击的item的属性
                deleteStudent(item);
            }
        });

        //editing a student
        /* TODO: create a new listContextMenu -> defined above in the variables
         */
        listContextMenu = new ContextMenu();

        /* TODO: create a MenuItem object so that when the user right-clicks a studId
                 the word Edit?? appears
         */
        MenuItem editStudent = new MenuItem("Edit?");//创建对listview右击后出现的菜单

        editStudent.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                /* TODO: get the item to be edited and call the editStudent()
                */
                Student stu=studentListView.getSelectionModel().getSelectedItem();
                editStudent(stu);
            }
        });

        //code provided to ensure that contextMenu appears as part of the above actions
        listContextMenu.getItems().add(deleteStudent);//listContextMenu是一个装按钮的容器。
        listContextMenu.getItems().add(editStudent);


        studentListView.setCellFactory(new Callback<ListView<Student>, ListCell<Student>>() {//属性加载工厂
            public ListCell<Student> call(ListView<Student> param) {//call是个方法名
                ListCell<Student> cell = new ListCell<Student>() {
                    @Override
                    protected void updateItem(Student stu, boolean empty) {
                        /* TODO: ensure that the studentListView has studId's or not when
                                 the delete a student takes place
                         */
                        super.updateItem(stu, empty);
                        if (empty) {//此处是将数据进行可视化处理,如果数据不为空,将学生id显示出来
                            setText(null);
                        } else {
                            setText(stu.getStudId());
                        }

                    }//end of update()
                };
                //code included as part of the delete
                cell.emptyProperty().addListener(//向ListView的每一个cell(每一行）中添加一个菜单。右击。
                        (obs, wasEmpty, isNowEmpty) -> {
                            if (isNowEmpty) {
                                cell.setContextMenu(null);
                            } else {
                                cell.setContextMenu(listContextMenu);//右击后，出现菜单，菜单里面添加按钮（delete,edit)
                            }
                        });
                return cell;
            }
        }); //end of setting the cell factory

        /* TODO: ensure that the studId's are sorted according to year of study in ascending order
        */
        Predicate<Student> allItems;
        allItems = new Predicate<Student>() {//对allItems进行初始化，对allItems里面的方法进行重写。
            @Override
            public boolean test(Student toDoItem) {
                return true;
            }
        };
        FilteredList filteredList = new FilteredList<Student>(StudentData.getInstance().getStudents(), allItems);
        SortedList<Student> sortedByYear = new SortedList<Student>(filteredList, new Comparator<Student>() {

            @Override
            public int compare(Student o1, Student o2) {//01 02的时间进行比较，谁早谁排前面
                return o1.getYearOfStudy().compareTo(o2.getYearOfStudy());
            }
        });
        //gets commented out when sorting
        //toDoListView.setItems(ToDoData.getInstance().getToDoItems());


        /* TODO: step 1 - set items using the sorted list
                 step 2 - ensure that only one studId can be selected at one time
                 step 3 - ensure that the first studId is highlighted when the application commences
         */
        studentListView.setItems(sortedByYear);
        studentListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        studentListView.getSelectionModel().selectFirst();
    }

    public void getChoice(ActionEvent event) {
        /* TODO: make use of event to determine each choice and assign each module choice to
                 choice1, choice2 and choice3
         */
        Student selectedItem = studentListView.getSelectionModel().getSelectedItem();
        studentListView.getSelectionModel().select(selectedItem);
        }


    @FXML
    public void addStudentData() {
        /* TODO: get the values from the textfields
         */
        String studIdS =studId.getText() ;
        String yearStudyS = yearStudy.getText();
        /* TODO: validate whether the studIdS and yearStudyS are occupied, BOTH have to be occupied
                 for the add to take place, if one or both are unoccupied print the following message
                 in the validateStudent label -> message to be printed is
                 "Error: cannot add student if studId or year of study not filled in"
                 If both are occupied then go ahead with the addStudentData()
         */
        //do the if...here
        if(studIdS.isEmpty() || yearStudyS.isEmpty()){
            validateStudent.setText("Error:cannot add student if studid or year of study not fill....");
        }

        //do the else...here, first ensure that the validateStudent label is clear of any text

        else{
            validateStudent.setText("");
            String c1=mod1Choice.getValue();
            String c2 = mod2Choice.getValue();
            String c3 = mod3Choice.getValue();
            studentToAdd = new Student(studIdS,yearStudyS,c1,c2,c3);
            StudentData.getInstance().addStudentData(studentToAdd);//把数据存到电脑内存里面

            studentListView.getSelectionModel().select(studentToAdd);//把光标移到刚添加的数据上面去
        };
        //use the getInstance() to addStudentData()
        //select the student that has been added so that it is highlighted on the list
    }

    public void deleteStudent(Student stu) {
        /* TODO: create an alert object to confirm that a user wants to delete
         */
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        /* TODO: set the title with "Delete a student from the list"
         */
        alert.setTitle("Delete a student from the list");
        //insert the line of code here
        /* TODO: set the header text with "Deleting student " xxx - where xxx is the studId
         */
        alert.setHeaderText("Deleteing student: " + stu.getStudId());
        //insert the line of code here
        /* TODO: have a message that asks the user "Are you sure you want to delete the student?"
         */
        //insert the line of code here
        alert.setContentText("Are you sure you want to delete the student?");
        //the result object with the showAndWait() has been completed for you
        Optional<ButtonType> result = alert.showAndWait();
        /* TODO: include a test to verify if the result is present and whether the OK button was
                 pressed, if so go ahead and call the deleteStudent()
         */
        //insert the 2 lines of code here
        if (result.isPresent() && result.get() == ButtonType.OK)
            StudentData.getInstance().deleteStudent(stu);//从硬盘中删除item。

    }

    public void editStudent(Student stu) {
        //the dialog object has been completed for you
        Dialog<ButtonType> dialog = new Dialog<>();
        /* TODO: use the dialog object to set the owner, the title and the header text
                 the title must state "Edit a student's details"
                 the header text must state: Editing student Id: xxx - where xxx is the studId
         */
         //insert the 3 lines of code here
        dialog.initOwner(mainWindow.getScene().getWindow());
        dialog.setTitle("Edit a student's details");
        dialog.setHeaderText("Editing student Id:"+stu.getStudId());

        /* TODO: complete the FXMLLoader statement
         */
        FXMLLoader fxmlLoader = new FXMLLoader();
        /* TODO: use the fxmlLoader to set the edit-students.fxml
         */
        //insert the line of code here
        fxmlLoader.setLocation(getClass().getResource("edit-students.fxml"));
        //remove the comments and complete the try...catch

        try {
            /* TODO: load the fxml
             */
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException event) {
            /* TODO: print an appropriate message if it cannot be loaded
                     print stacktrace
             */
            System.out.println("Could not load the dialog");
            event.printStackTrace();
            return;
        }
        /* TODO: complete the ec controller statement
             */
        EditStudentController ec = fxmlLoader.getController();
        /* TODO: use the ec object to call setToEdit()
             */
        ec.setToEdit(stu);
        //insert the line of code here
        /* TODO: create the OK and CANCEL buttons using dialog
         */
        //insert the 2 lines of code here
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        //the result object with the showAndWait() has been completed for you
        Optional<ButtonType> result = dialog.showAndWait();

         /* TODO: verify if there is an edit to complete, complete the editStudent call processEdit()
                  ensure that the student edited is selected
         */
        //remove the comments and complete the if...
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Student editStudent = ec.processEdit(stu); ;
            //select the edited studId here
            studentListView.getSelectionModel().select(editStudent);
        }
    }
}