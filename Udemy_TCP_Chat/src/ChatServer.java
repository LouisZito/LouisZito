//Louis Zito
//10/5/20
//Networking PA1

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ChatServer {

      static ArrayList<String> userNames = new ArrayList<String>();
      static ArrayList<PrintWriter> printWriters = new ArrayList<PrintWriter>();

      public static void main(String[] args) throws Exception{
           System.out.println("Waiting for clients..."); 
           ServerSocket ss = new ServerSocket(9806);
           while (true) {
             Socket soc = ss.accept();
               System.out.println("Connection established");
             ConversationHandler handler = new ConversationHandler(soc);
             handler.start();
           }//while
      }//main
}//class

class ConversationHandler extends Thread{
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    String name;
    PrintWriter pw;
    static FileWriter fw;
    static BufferedWriter bw;

   public ConversationHandler(Socket socket) throws IOException {
        this.socket = socket;
        fw = new FileWriter("C:\\Users\\LAZ\\Desktop\\ChatServer-Logs.txt",true);
        bw = new BufferedWriter(fw);
        pw = new PrintWriter(bw,true);
    }

   public void run(){
        try{
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            int count = 0;
            while (true){
               if(count > 0){
                    out.println("NAMEALREADYEXISTS");
               }
               else{
                    out.println("NAMEREQUIRED");
               }
               name = in.readLine();
               if (name == null){
                   return;
               }
               if (!ChatServer.userNames.contains(name)){
                  ChatServer.userNames.add(name);
                  break;
               }
             count++;
           }//while
            out.println("NAMEACCEPTED"+name);
            ChatServer.printWriters.add(out);
            out.println(name + " just joined the chat!");

            while (true) {
                String message = in.readLine();
                if (message == null){
                    return;
                }
                pw.println(name + ": " + message);
                for (PrintWriter writer : ChatServer.printWriters) {
                    writer.println(name + ": " + message);
                }
            }//while
        }//try
        catch (Exception e){
            System.out.println(e);
        }
   }//end run
}//end class


