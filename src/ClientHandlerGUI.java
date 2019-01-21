//Chaitanya Krishna Lanka
//1001675459
//import java.awt.*;
import java.awt.TextArea;
import java.awt.Label;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.*;

public class ClientHandlerGUI extends Thread {
    Socket s = null;
    DataInputStream dis = null;
    DataOutputStream dos = null;
    static TextArea ta;
    static TextArea ht;
    static Label connections;
    static volatile int[] random_queue = new int[100];
    volatile Queue<Integer> q = new LinkedList<>();
    static volatile Queue<DataInputStream> datain = new LinkedList<>();
    static volatile Queue<DataOutputStream> dataou = new LinkedList<>(); static long millitime;
    //ServerGUI number_connections= new ServerGUI();
    private static volatile int clients = 0;
    Readerwriter rw = new Readerwriter();  //Used To increment or decrement the num of clients
    ClientHandlerGUI(Socket s, DataInputStream dis, DataOutputStream dos, TextArea ta, TextArea ht,Label connections){
        this.s=s;
        this.dis=dis;
        this.dos=dos;
        this.ta=ta;
        this.ht=ht;
        this.connections=connections;
    }
    public void run(){
        int num;
        String number;
        String name="";
        int i=0;
        try {
            name = dis.readUTF();
            ta.append("Server Connected to process"+ name + "\n");
            System.out.println("Server Connected to process " + name);
        } catch(Exception e) { }
        if(i==0){ //This if is to avoid Unreachable code error
            while(true) {
                try {
                    //name = dis.readUTF();
                    //System.out.println("Server Connected to process "+ name);
                    //System.out.println("The con is " + number_connections.number);

                    connections.setText("The no. of clients connected is  " + rw.read());

                    number = dis.readUTF();  //Reading the random number from Client
                    Date date1 = new Date();
                    millitime = date1.getTime();
                    ClientHandlerGUI.addIntoQueue(number,name,dis,dos,millitime); //Add the number dos and time in queue
                    //Date date1 = new Date();
                    //millitime = date1.getTime();
                    //addmillitimetoQueue(millitime);
                    System.out.println("The Queqe is "+ q);
                    //Date date1 = new Date();
                    //millitime = date1.getTime();
                    Thread tadd = new ClientHandleMessage(q,datain,dataou,ht,name,dos,millitime);
                    tadd.start();
                    System.out.println("In the client handler after the thread is called");
                    //dos.writeUTF("W");
                    tadd.join();

                    //q.add((10000*Integer.parseInt(number))+Integer.parseInt(name));

                    /*
                    num = Integer.parseInt(number);  //Converting Integer to string
                    num=num*1000;

                    /*
                    Thread.sleep(num);
                    //Creating the HTTP message
                    Date date = new Date(); //To retirive the current date
                    //String message="Server Waited for " + (num/1000) + " seconds";  //To get the length of the content

                    String http="";
                    http="HTTP/1.1 200 OK\nDate: " +date.toString()+"\n"+"Host: localhost:8000\nUser-Agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36\n";
                    http= http + "Content-Type: multipart/form-data;\nContent-Length:30"+"\n\n";
                    http=http + " ";
                    ht.append("*************************\n"+http + "\n" + "****************************" + "\n");

                    dos.writeUTF(http);
                    */

                } catch (Exception e) {
                }
            }
        }
        try{
            this.dis.close();
            this.dos.close();
        } catch(Exception e){ }
    }

    /*
    public void increment()
    {
        clients = clients + 1;
    }
    public void decrement()
    {
        clients = clients -1;
    }
    */

    public static synchronized void addIntoQueue(String number,String name,DataInputStream dis,DataOutputStream dos,long millitime){
        ClientHandleMessage.q.add((10000*Integer.parseInt(number))+Integer.parseInt(name));
        //q.add(Integer.parseInt(number));
        ClientHandleMessage.datain.add(dis);
        ClientHandleMessage.dataou.add(dos);
        ClientHandleMessage.t.add(millitime);

    }

}

//Citation
//Introducing Threads in Socket Programming in Java
//https://www.geeksforgeeks.org/introducing-threads-socket-programming-java/
//I have taken the Architecture of the program and few code code snippets
