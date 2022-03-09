package Part1;

//step1:继承自Thread类
class MyThread extends Thread{//继承Thread父类创建新线程，但继承Thread类则不适合资源共享
    @Override
    public void run(){
        //线程的执行体

        for(int i = 0; i < 100; i++){
            try {
                Thread.sleep(1000);
            }catch(Exception e){

            }
            System.out.println("新开的线程："+i);
        }
    }
}
class Animal{
    public void run(){
        for(int i=0;i<10;i++){
            System.out.println("123");
        }
    }
    public void start(){
        run();
    }
}
public class Demo1 {
    public static void main(String[] args) throws InterruptedException {
        //首先 创建一个线程对象
        MyThread myThread = new MyThread();//MyTread继承了Thread,此时他具备线程的能力,但是并不是一个多线程,相当于会开车的司机没有驾照,只能自己玩自己的,和大家以上路很危险.
        //想要实现多线程,必须将线程实列以参数形式放到Thread构造方法中
        Thread t1 = new Thread(myThread);//此时需要将自己创建的线程类的对象 以参数的形式放到Thread中,成为真正的驾驶员,有了驾照,可以上路.
        t1.start();//开启一个新的线程，这个线程与main线程是平级的

        Thread.sleep(300*10);//让主线程停3秒

        for(int i =0;i<10;i++){//优先执行住线程
           System.out.println("主线程："+i);
        }

        MyThread myThread2 = new MyThread();
        //step3:调用start()方法
        Thread t2=new Thread(myThread2);
        t2.start();//开启一个新的线程，这个线程与main线程是平级的


    }
}
