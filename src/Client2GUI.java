//Chaitanya Krishna Lanka
//1001675459
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.io.*;
import java.lang.*;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;
public class Client2GUI extends Frame implements ActionListener , Runnable{
    Frame mainframe;
    static Label textlabel;
    static TextArea ctext2;
    Button gen;
    Button exit;
    static volatile int exitcondition=1;
    ServerGUI num= new ServerGUI();
    Thread t;
    Readerwriter rw = new Readerwriter();
    public void ClientGUI(){ /*To initilize the GUI */
        mainframe = new Frame("Client 2");
        mainframe.setSize(500,500);
        mainframe.setLayout(new FlowLayout());
        mainframe.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                //System.exit(0);
                dispose();
            }
        });
        gen = new Button("Generate Random Number");
        exit = new Button("Exit");
        textlabel = new Label();
        ctext2 = new TextArea();
        gen.addActionListener(new Client2GUI());
        exit.addActionListener(new Client2GUI());
        //mainframe.add(textlabel);
        mainframe.add(ctext2);
        mainframe.add(gen);
        mainframe.add(exit);
        mainframe.setVisible(true);
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        //System.out.println(e.getActionCommand());
        t = new Thread(new Client2GUI());
        if(e.getActionCommand()=="Generate Random Number"){

            rw.increment();  //To increment the number of clients connected.This method is written in Readerwriter.java
            exitcondition=1;



            t.start();



        }else{
            rw.decrement();  //To decrement the number of clients connected.This method is written in Readerwriter.java


            exitcondition=0;



            return;
        }
    }
    public static void main(String[] args){
        Client2GUI cl = new Client2GUI();
        //cl.t = new Thread(new Client1GUI());
        cl.ClientGUI();
    }
    //Multi Thread GUI.This helps in running all the buttons at the same time.
    @Override
    public void run() {
        Socket s = null;
        PrintStream ps = null;
        int max = 10;
        int min = 3;
        int count=1;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        try {
            s = new Socket("127.0.0.1", 5000);
            //ps = new PrintStream(s.getOutputStream());
            while (exitcondition==1) { //This condition will terminate the loop if exitcondition is 0
                if(exitcondition==0)   //To break the loop if the exit condition is 0
                {
                    break;
                }else
                {dis = new DataInputStream(s.getInputStream());
                    dos = new DataOutputStream(s.getOutputStream());

                    if(count==1) {
                        long pid = ProcessHandle.current().pid();
                        dos.writeUTF(Long.toString(pid));
                        count++;
                    }
                    Random rand = new Random();
                    int num = rand.nextInt((max - min) + 1) + min;
                    dos.writeUTF(Integer.toString(num));

                    String text;
                    text=dis.readUTF();
                    text=text + "\n";
                    String[] body = text.split("\n\n");  //To split the http message
                    ctext2.append(body[1]);

                    System.out.println(text);
                }
            }


        }catch(Exception e1){System.out.println("Caught Here"); System.out.println(e1);
        }
        try {
            dis.close();
            dos.close();
        }catch(Exception e2){ }



    }
}

//Citation
//Introducing Threads in Socket Programming in Java
//https://www.geeksforgeeks.org/introducing-threads-socket-programming-java/
//I have taken the Architecture of the program and few code code snippets
