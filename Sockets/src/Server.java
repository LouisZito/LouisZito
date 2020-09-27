
import java.io.IOException;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {
	

	
	public static void main(String[] args) throws InterruptedException {
		//used by particular software can be shared among users
		int port = 8818;
		try {
		ServerSocket serverSocket= new ServerSocket(port);
			//while continually returns a new socket to client until false cond met
			while(true) {
				//accept method returns a socket to the client
				//socket = port + IP, ID's machine and service within machine
				System.out.println("Preparing for client connection...");
				Socket clientSocket = serverSocket.accept();
				System.out.println("Connection accepted from " + clientSocket);
				
				ServerWorker worker = new ServerWorker(clientSocket);
				worker.start();
			}//end while
		}//end try
		catch (IOException e) {
			e.printStackTrace();
		}//end catch
	}//end Main method
	
}//end server class
