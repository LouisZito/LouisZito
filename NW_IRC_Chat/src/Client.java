import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class Client {
	
	private static final String SERVER_IP = "127.0.0.1";
	private static final int SERVER_PORT = 9090;
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket = new Socket(SERVER_IP, SERVER_PORT);
		
		ServerConnection serverConn = new ServerConnection(socket);
		
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		
		//single thread PER client here, therefore 1
		new Thread(serverConn).start();
		
		//loop that sends client activity into server
		while (true) {
			System.out.println("> ");
			String command = keyboard.readLine();
			
			//CMD: to exit "Bye"
			if (command.equals("Bye")) break;
			out.println(command);
		}
		
		socket.close();
		System.exit(0);
	}


}
