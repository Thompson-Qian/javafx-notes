package fsd.assignment.assignment1.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

public class StudentData {//数据存储器类
    /* TODO: include an instance of StudentData
    */
    private static StudentData instance = new StudentData();//StudentData是电脑类，instance是一台真实的电脑
    private static String filename = "student-data.txt";

    private ObservableList<Student> students;//名叫students的容器，用来装Student

    public static StudentData getInstance() {//get方法，用来获取电脑
        /* TODO: complete the getter for the instance created
        */
        return instance;
    }

    public ObservableList<Student> getStudents() {//用来获取容器中的Student类
        /* TODO: complete the getter for the observable arraylist
        */
        return students;
    }

    public void loadStudentData() throws IOException {//io流，读文件，将txt文件里面的内容塞入到电脑instance里面去。
        students = FXCollections.observableArrayList();//通过请求系统来获取学生信息类的存储集合
        Path path = Paths.get(filename);
        BufferedReader br = Files.newBufferedReader(path);
        String input;

        try {
            while ((input = br.readLine()) != null) {//每读一行就要把这一行数据临时存储到input里面去
                /* TODO: split each input line using a tab
                 */
                String[] studentItem = input.split("\t");//根据tab健分割
                /* TODO: using the String create each piece of data so that all the instance variables
                         have a value accordingly
                 */
                String studId=studentItem[0];
                String yearStudy=studentItem[1];
                String mod1=studentItem[2];
                String mod2=studentItem[3];
                String mod3=studentItem[4];
                /* TODO: complete the call to the constructor by passing in the parameters
                 */
                Student studDataItem =new Student(studId,yearStudy,mod1,mod2,mod3);//已经读完，把这些灵魂塞入一个名叫studDataItem
                // 的盒子，这个盒子也是新对象。 studDataItem是一个实体的学生
                /* TODO: add the studentDataItem to the students array
                 */
                instance.addStudentData(studDataItem);//调用下面写的方法addStudentData，将studDataItem先塞入ObservableList容器类，然后一起将把ObservableList容器塞入电脑（instance）
                //>include the statement here
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    public void storeStudentData() throws IOException {//当触发关闭系统操作时会被调用到,将存储文件清空,然后根据list集合中的内容逐条写入.
        Path path = Paths.get(filename);
        BufferedWriter bw = Files.newBufferedWriter(path);
        try {
            /* TODO: complete the iterator
             */
            Iterator<Student> it = instance.students.listIterator();//运用迭代器。
            while (it.hasNext()) {
                /* TODO: accept the next item from the iterated list
                 */
                Student item = it.next();
                /* TODO: complete the write() using String.format
                         remember to separate each string with a tab
                 */
                bw.write(item.getStudId()+"\t"+item.getYearOfStudy()+"\t"+item.getModule1()+"\t"+item.getModule2()+
                        "\t"+item.getModule3());
                /* TODO: once a student item is written to the file ensure that
                         the next item is stored on a new line
                 */
                //>insert statement here
                bw.newLine();
            }
        } finally {
            if (bw != null) {
                bw.close();
            }
        }
    }

    public void addStudentData(Student studentToAdd){
        /* TODO: complete the addStudentData so that a student can be added
                 to students
         */
        students.add(studentToAdd);//将传过来的Student类的盒子，塞入ObservableList容器里面。
    }
    public void deleteStudent(Student stu){
        /* TODO: complete the addStudentData so that a student can be removed
                 from students
         */
        instance.getStudents().remove(stu);
    }
}
