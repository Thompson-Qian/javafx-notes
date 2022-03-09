package Part1;

public class Demo3 {

    static Object jianguanzhe = new Object();
    public static void main(String[] args) {
        Seller seller1 = new Seller("张三");
        Thread t1 = new Thread(seller1);//t1是有驾照的人
        t1.start();//start默认执行run方法


        Seller seller2 = new Seller("李四");
        Thread t2 = new Thread(seller2);
        t2.start();

    }
}
class Seller extends Thread{
    String name;
    static int ticket=1;
    public Seller(String name){
        this.name=name;
    }
   public  void run(){
        while(true){
            sellTicket();
        }

   }
   public  void sellTicket (){
    synchronized (Demo3.jianguanzhe) {//sychronized 锁,相当于一个人进入卫生间后可以锁门了,为了以防万一还需要一个门卫维护秩序,需要注意的是,所有的监管者必须是同一个对象,
        // 对象是什么类无所谓
        System.out.println("I am " + this.name + " ,I am selling ticket" + ticket);
        ticket++;
        try {
            Thread.sleep(1000);
        } catch (Exception r) {
        }
    }
   }
}
