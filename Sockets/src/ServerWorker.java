import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;

//class for generating thread: dedicated path of execution
//for each user that logs on to the server
public class ServerWorker extends Thread{

	private Socket clientSocket;

	public ServerWorker(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	
	//every thread must have a run method
	@Override
	public void run() {
		try {
			handleClientSocket();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}//end run
	
	private void handleClientSocket() throws IOException, InterruptedException {
		OutputStream outputStream = clientSocket.getOutputStream();
			for(int i = 0; i < 10;	 i++){
				outputStream.write(("Time now is " + new Date() + "\n").getBytes());
				Thread.sleep(1000);
			}//end for loop
		//write method requires int, conversion to bytes in output allows its use
		outputStream.write("Welcome to Chitty Chat\n".getBytes());
		clientSocket.close();
	}//end handleClientSocket method
	
	
}//end class
