import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server{
	
	private static String[] names = {"Louis", "Ron", "Mary", "Loup"};
	private static String[] passwords = {"Louis", "Ron", "Mary", "Loup"};
	private static final int PORT = 9090;
	
	private static ArrayList<ClientHandler> clients = new ArrayList<>();
	private static ExecutorService pool = Executors.newFixedThreadPool(4);
	
	public static void main(String[] args) throws IOException  {		
		ServerSocket listener = new ServerSocket(PORT);
		
		while (true) {
			System.out.println("[SERVER] is waiting for client connection...");
			Socket client = listener.accept();
			System.out.println("[SERVER] connected to client!");
			ClientHandler clientThread = new ClientHandler(client, clients);
			clients.add(clientThread);
			
			pool.execute(clientThread);
		}//while

	}//end main
	
	public static String getRandomName() {
		String name = names[(int)(Math.random()*names.length)];
		String pass = passwords[(int)(Math.random()*passwords.length)];
		return name + " " + pass;
	}
	
}