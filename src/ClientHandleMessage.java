import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class ClientHandleMessage extends Thread {
    static volatile Queue<Integer> q = new LinkedList<>();  //A queue to hold the random number generated
    static volatile Queue<DataInputStream> datain = new LinkedList<>();  //A queue to hold the Data input stream of the clients
    static volatile Queue<DataOutputStream> dataou = new LinkedList<>(); //A queue to hold the Data Output stream of the Clients
    static TextArea ht;
    String name;
    DataOutputStream dos1 = null;
    static volatile long millitime;
    //static volatile long curtime;
    //static volatile long atime;
    static long time;
    static long id;
    static volatile Queue<Long> t = new LinkedList<>();  //A queue to hold the timestamp of the client request


    public ClientHandleMessage(Queue<Integer> q, Queue<DataInputStream> datain, Queue<DataOutputStream> dataou,TextArea ht,String name,DataOutputStream dos1,long millitime){
        //this.q = q;
        //this.datain=datain;
        //this.dataou=dataou;
        this.ht=ht;
        //this.name=name;
        //this.dos1=dos1;
        this.millitime=millitime;
    }

    public static synchronized void run1(){  //This methods controls the queue and mutual exclusion
        try{
            Date date = new Date();
            long curtime = date.getTime();  //Getting the current time
            System.out.println("The queue is " + q);
            time=popQueueTime();  //To get the random number generated
            DataOutputStream dos=popQueueinop();
            id = time%10000;  //To get the id
            time=(time-id)/10000;
            //int process=Integer.parseInt(name);
            System.out.println("Time is "+ time);
            System.out.println("The id is "+ id);
            System.out.println("The process is "+ id);



            Thread.sleep(time*1000);
            //Creating the HTTP message
            long temptime= time;
            Date date1 = new Date(); //To retirive the current date
            long newtime = date1.getTime();
            long atime = date1.getTime();
            curtime = TimeUnit.MILLISECONDS.toSeconds(curtime);
            atime= TimeUnit.MILLISECONDS.toSeconds(atime);
            //millitime= TimeUnit.MILLISECONDS.toSeconds(millitime);
            newtime= TimeUnit.MILLISECONDS.toSeconds(newtime);
            //time=newtime-millitime;
            System.out.println("Current time is  "+ curtime);
            System.out.println("After time is  "+ newtime);
            time= atime-curtime;

            time=atime - TimeUnit.MILLISECONDS.toSeconds(popQueueaTime());  //To calculate the total time waited
            String message="Server Waited for " + (time) + " seconds";  //To get the length of the content

            String http="";
            http="HTTP/1.1 200 OK\nDate: " +date.toString()+"\n"+"Host: localhost:8000\nUser-Agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36\n";
            http= http + "Content-Type: multipart/form-data;\nContent-Length:"+message.length()+"\n\n";
            http=http + "Server Waited for " + (time) + " seconds for client id "+ id + "The number genereted is " + temptime;
            ht.append("*************************\n"+http + "\n" + "****************************" + "\n");

            dos.writeUTF(http);

        }catch (Exception e){ System.out.println(e);}

    }

    public void run(){
        ClientHandleMessage.run1();
    }

    public static synchronized int popQueueTime(){  //To get the random number from the queue
        int time = q.poll();
        return time;
    }

    public static synchronized long popQueueaTime(){  //To get the timestamp when the client has requested
        long time = t.poll();
        return time;
    }

    public static synchronized DataOutputStream popQueueinop(){  //To get the dos
        DataInputStream dain= datain.poll();
        DataOutputStream dato =dataou.poll();
        return dato;
    }
}
