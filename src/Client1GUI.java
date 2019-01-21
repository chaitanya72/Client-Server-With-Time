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
public class Client1GUI extends Frame implements ActionListener , Runnable{
    Frame mainframe;
    static Label textlabel;
    static TextArea ctext;
    Button gen;
    Button exit;
    static volatile int exitcondition=1;
    ServerGUI num= new ServerGUI();
    Thread t;
    Readerwriter rw = new Readerwriter();
    public void ClientGUI(){ /*To Initilize the GUI */
        mainframe = new Frame("Client 1");
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
        ctext = new TextArea();
        gen.addActionListener(new Client1GUI());
        exit.addActionListener(new Client1GUI());
        //mainframe.add(textlabel);
        mainframe.add(ctext);
        mainframe.add(gen);
        mainframe.add(exit);
        mainframe.setVisible(true);
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        //System.out.println(e.getActionCommand());
        t = new Thread(new Client1GUI());
        if(e.getActionCommand()=="Generate Random Number"){

                rw.increment();  //When the button is clicked,The number of clients connected in server is incremented
                exitcondition=1;



                t.start();



        }else{
            rw.decrement(); //When the button is clicked, The number of clients connected in server is decremented


            exitcondition=0;



            return;
        }
    }
    public static void main(String[] args){
        Client1GUI cl = new Client1GUI();

        cl.ClientGUI();
    }

    //Multi Threading in GUI.This allows all the buttons to be active at the same time.
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
            while (exitcondition==1) {//This condition is used to exit the loop
                if(exitcondition==0)  // To make sure that the loop exits if exitcondition is change in between
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
                if(text.equals("W")){}
                else {
                    text = text + "\n";
                    String[] body = text.split("\n\n");  //Used to Split the String from http
                    System.out.println(body[1]);
                    if (body[1] == " ") {

                    } else {
                        ctext.append(body[1]);  //After split the second element will be the content

                    }
                    System.out.println(text);

                }
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