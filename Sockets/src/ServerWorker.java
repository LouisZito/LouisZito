import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

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
		InputStream inputStream = clientSocket.getInputStream();
		OutputStream outputStream = clientSocket.getOutputStream();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String line;
		while((line = reader.readLine()) != null) {
			String[] tokens = StringUtils.split(line);
			if (tokens != null && tokens.length > 0) {
				String cmd = tokens[0];
				if("Bye".equalsIgnoreCase(cmd)) {
					break;
				}//end if
				else if ("login".equalsIgnoreCase(cmd)){
					handleLogin(outputStream, tokens);
				}
				else {
					String msg = "unknown " + cmd + "\n";
					outputStream.write(msg.getBytes());
				}//end else
			}//end if
		}//end while
		
		clientSocket.close();
	}//end handleClientSocket method

	private void handleLogin(OutputStream outputStream, String[] tokens) throws IOException {
		if (tokens.length == 3) {
			String login = tokens[1];
			String password = tokens[2];
			
			if (login.equals("guest") && password.equals("guest")) {
				String msg = "ok login";
				outputStream.write(msg.getBytes());
			}//end guest if
			else {
				String msg = "error login";
				outputStream.write(msg.getBytes());
			}//end else
		}//end if
		
	}//end handleLogin
	
	
}//end class












