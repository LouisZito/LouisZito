import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{

	private Socket client;
	private BufferedReader in;
	private PrintWriter out;
	private ArrayList<ClientHandler> clients;
	
	public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients) throws IOException {
		this.client = clientSocket;
		this.clients = clients;
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		out = new PrintWriter(client.getOutputStream(), true);
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				String request = in.readLine();
				if (request.contains("name")) {
					out.println(Server.getRandomName());
				}//end if
				//CMD: "say" for broadcast to all current members in clients ArrayList
				else if (request.startsWith("say")) {
					int firstSpace = request.indexOf(" ");
					if (firstSpace != -1) {
						outToAll( request.substring(firstSpace + 1));
					}
				}//else if
				else {
					out.println("IO exception in client handler");
				}//end else
			}//end while
		}//end try
		catch (IOException e) {
			System.err.println("IO exception in client handler");
			System.err.println(e.getStackTrace());
		}//end catch
		finally {
			out.close();
			try {
				in.close();
			}catch (IOException e) {
				e.printStackTrace();
			}//catch
		}//end finally
	}//end run

	//method for broadcast to all members
	private void outToAll(String msg) {
		for (ClientHandler aClient : clients) {
			aClient.out.println(msg);
		}//for
	}
}//end class

