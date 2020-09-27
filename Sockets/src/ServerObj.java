import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerObj extends Thread{

	private int serverPort;
	
	private ArrayList<ServerWorker> workerList = new ArrayList<>();

	public ServerObj(int serverPort) {
		this.serverPort = serverPort;		
	}//end constructor
	
	public List<ServerWorker> getWorkerList(){
		return workerList;
	}//end get
	
	@Override
	public void run() {	
		try {
		ServerSocket serverSocket= new ServerSocket(serverPort);
			//while continually returns a new socket to client until false cond met
			while(true) {
				//accept method returns a socket to the client
				//socket = port + IP, ID's machine and service within machine
				System.out.println("Preparing for client connection...");
				Socket clientSocket = serverSocket.accept();
				System.out.println("Connection accepted from " + clientSocket);
				//using generated client socket for output/input streams
				//bidirectional host/client communications
				ServerWorker worker = new ServerWorker(this, clientSocket);
				workerList.add(worker);
				worker.start();
			}//end while
		}//end try
		catch (IOException e) {
			e.printStackTrace();
		}//end cat
		
	}

}//end class
