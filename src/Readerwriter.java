//Chaitanya Krishna Lanka
//1001675459
import java.net.*;
import java.io.*;
import java.lang.*;

public class Readerwriter {
    //Thsi Class Contains Methods to Read and Write from File
    //This Class Is Used to Display the number of clients connected to Server at Real Time



    BufferedReader br = null;
    BufferedWriter b=null;

    //To Read The Number in the file
    public String read()
    {   String number="";
        try {
        br = new BufferedReader(new FileReader("read.txt"));
        number= br.readLine();
        //System.out.println(number);
    }catch(Exception e){ }
        return number;
    }

    //To Increment the number in a file
    public void increment()
    {
        try {
            String number;
            number = read();
            int num = Integer.parseInt(number);
            num = num + 1;
            write(Integer.toString(num));
        }catch(Exception e){ }
    }
    //To decrement the number in a file
    public void decrement(){
        try {
            String number;
            number = read();
            int num = Integer.parseInt(number);
            num = num - 1;
            write(Integer.toString(num));
        }catch(Exception e){ }
    }

    //To write into a file
    public void write(String number)
    {
        try {
            b = new BufferedWriter(new FileWriter("read.txt"));
            //number= br.readLine();
            b.write(number);
            //System.out.println(number);
        }catch(Exception e){ }
        finally {
            try {
                b.close();
            }catch (Exception e){ }
        }
    }

    //This is used to set the value in the file to zero when the server starts
    public void set()
    {
        try {
            b = new BufferedWriter(new FileWriter("read.txt"));
            //number= br.readLine();
            b.write("0");
            //System.out.println(number);
        }catch(Exception e){ }
        finally {
            try {
                b.close();
            }catch (Exception e){ }
        }
    }



}
