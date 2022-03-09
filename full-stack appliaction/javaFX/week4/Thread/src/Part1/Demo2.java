package Part1;

class MyRunnable implements  Runnable{//用Runnable接口创建新线程，很容易实现资源的共享

    @Override
    public void run(){
        //线程的执行体
        for(int i =0;i < 3; i++){
            System.out.println("新开的线程："+i);
        }

    }

}

public class Demo2 {
    public static void main(String[] args) {

        //step2:创建实现类的对象
        MyRunnable myRunnable = new MyRunnable();
        Thread thread = new Thread(myRunnable);//Thread对象才是真正的线程对象

        //step3:启动一个线程
        thread.start();


        //主线程
        for(int i = 0; i < 10 ;i++){
            System.out.println("主线程"+i);
        }

    }
}
