import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

//creation of Client instance
public class Client {
	private final String serverName;
	private final int serverPort;
	private Socket socket;
	private OutputStream serverOut;
	private InputStream serverIn;
	private BufferedReader bufferedIn;
	
	//Arrays of users and messages using interfaces
	private ArrayList<UserStatusListener> userStatusListeners = new ArrayList<>();
	private ArrayList<MessageListener> messageListeners = new ArrayList<>();
	
	public Client(String serverName, int serverPort) {
		this.serverName = serverName;
		this.serverPort = serverPort;
	}//end constructor

	public static void main(String[] args) throws IOException {
		Client client = new Client("localhost", 8000);
		client.addUserStatusListener(new UserStatusListener() {

			@Override
			public void online(String login) {
				System.out.println("ONLINE: " + login);			
			}//end online

			@Override
			public void offline(String login) {
				System.out.println("OFFLINE: " + login);			
			}//end offline
			
		});//end add listener
		
		client.addMessageListener(new MessageListener(){
			@Override
			public void onMessage(String fromLogin, String msgBody) {
				System.out.println("You got a message from " + fromLogin + " +++>" + msgBody);
			}
		});//end addML
		
		
		if (!client.connect()) {
			System.err.println("Connection failed.");
		} // end if
		else {
			System.out.println("Connect Successful ");
			if(client.login("jim", "jim")) {
				System.out.println("Login successful");
				
				client.msg("jim", "Hello World");
			}//end if
			else {
				System.err.println("Login failed");
			}//end else
			
			client.logoff();
		}
	}// end main

	//method for DM to recipient 
	public void msg(String sendTo, String msgBody) throws IOException {
		String cmd = "msg " + sendTo + " " + msgBody + "\n";
		serverOut.write(cmd.getBytes());
	}//end msg

	public boolean login(String login, String password) throws IOException {
		String cmd = "login " + login + " " + password + "\n";
		serverOut.write(cmd.getBytes());
		String response = bufferedIn.readLine();
		System.out.println("Response line: " + response);
		
		if ("ok login".equalsIgnoreCase(response)) {
			startMessageReader();
			return true;
		}//end if
		else {
			return false;
		}//end else
	}//end login
	
	public void logoff() throws IOException{
		String cmd = "logoff\n";		
		serverOut.write(cmd.getBytes());
	}//end logoff

	private void startMessageReader() {
		Thread t = new Thread() {
			@Override
			public void run() {
				readMessageLoop();
			}//end run
		};//end thread
		t.start();
	}//end messageReader
	

	private void readMessageLoop() {
		try {
			String line;
			//client while-loop mirrors server while-loop
			while((line = bufferedIn.readLine()) != null){
				String[] tokens = StringUtils.split(line);
				if (tokens != null && tokens.length > 0) {
					String cmd = tokens[0];
					if("online".equalsIgnoreCase(cmd)) {
						handleOnline(tokens);
					}//end if
					else if ("offline".equalsIgnoreCase(cmd)) {
						handleOffline(tokens);
					}//end else if
					else if ("msg".equalsIgnoreCase(cmd)){
						String[] tokenMsg = StringUtils.split(line, null, 3);
						handleMessage(tokenMsg);
					}//end else if
				}//end if
			}//end while
		}//end try
		catch (Exception ex){
			ex.printStackTrace();
			try {
			socket.close();
			}//end try
			catch (IOException e) {
				e.printStackTrace();
			}//end catch
		}//end catch
	}//end readMessageLoop

	//3rd token split by StringUtils.split contains entire msg body
	private void handleMessage(String[] tokenMsg) {
		String login = tokenMsg[1];
		String msgBody = tokenMsg[2];
		for(MessageListener listener : messageListeners) {
			listener.onMessage(login, msgBody);
		}//end for
	}//end handleMessage

	private void handleOffline(String[] tokens) {
		String login = tokens[1];
		for(UserStatusListener listener : userStatusListeners) {
			listener.offline(login);
		}//end for	
		
	}//end handleOffline

	private void handleOnline(String[] tokens) {
		String login = tokens[1];
		for(UserStatusListener listener : userStatusListeners) {
			listener.online(login);
		}//end for	
	}//end handleOnline

	public boolean connect() {
		try {
			this.socket = new Socket(serverName, serverPort);
			System.out.println("Client port is " + socket.getLocalPort());
			this.serverOut = socket.getOutputStream();
			this.serverIn = socket.getInputStream();
			this.bufferedIn = new BufferedReader(new InputStreamReader(serverIn));
			return true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return false;
	}//end connect
	
	//below 4 methods implements interfaces
	//track users and messages active on the server
	public void addUserStatusListener(UserStatusListener listener) {
		userStatusListeners.add(listener);
	}//end addUserSL
	
	public void removeUserStatusListener(UserStatusListener listener) {
		userStatusListeners.remove(listener);
	}//end removeUserSL
	
	public void addMessageListener(MessageListener listener) {
		messageListeners.add(listener);
	}//end addMessListen
	
	public void removeMessageListener(MessageListener listener) {
		messageListeners.remove(listener);
	}//end removeMessListen
	
}
