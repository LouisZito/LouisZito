import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;

//class for generating thread: dedicated path of execution
//for each user that logs on to the server
public class ServerWorker extends Thread{

	private final Socket clientSocket;
	private String login = null;
	private final ServerObj serverObj;
	private OutputStream outputStream;
	private HashSet<String> topicSet = new HashSet<>();

	public ServerWorker(ServerObj serverObj, Socket clientSocket) {
		this.clientSocket = clientSocket;
		this.serverObj = serverObj;
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
		this.outputStream = clientSocket.getOutputStream();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String line;
		while((line = reader.readLine()) != null) {
			String[] tokens = StringUtils.split(line);
			if (tokens != null && tokens.length > 0) {
				String cmd = tokens[0];
				if("Bye".equalsIgnoreCase(cmd)) {
					handleLogoff();
					break;
				}//end if
				else if ("login".equalsIgnoreCase(cmd)){
					handleLogin(outputStream, tokens);
				}//end else if
				//direct messaging option
				else if ("msg".equalsIgnoreCase(cmd)){
					//split into three tokens with last containing complete msg
					String[] tokensMsg = StringUtils.split(line, null, 3);
					handleMessage(outputStream, tokensMsg);
				}//end else if
				else if ("join".equalsIgnoreCase(cmd)) {
					handleJoin(tokens);
				}//end else if
				else if ("leave".equalsIgnoreCase(cmd)) {
					handleLeave(tokens);
				}//end else if
				else {
					String msg = "unknown " + cmd + "\n";
					outputStream.write(msg.getBytes());
				}//end else
			}//end if
		}//end while
		
		clientSocket.close();
	}//end handleClientSocket method
	
	//leave #topic-grouped chat environment
	//cmd: leave #topic
	private void handleLeave(String[] tokens) {
		if (tokens.length > 1) {
			String topic = tokens[1];
			topicSet.remove(topic);			
		}//end if	
	}//end handleLeave

	//testing for topic being established
	public boolean isMemberOfTopic(String topic) {
		return topicSet.contains(topic);
	}//end isMemberOfTopic
	
	//method creates new #topic grouping for chat environment
	//cmd: join #topic
	private void handleJoin(String[] tokens) {
		if (tokens.length > 1) {
			String topic = tokens[1];
			topicSet.add(topic);			
		}//end handleJoin
		
	}//end handleJoin

	//method added for direct messaging option
	private void handleMessage(OutputStream outputStream2, String[] tokens) throws IOException {
		String sendTo = tokens[1];
		String body = tokens[2];
		//bool test for topic group starting with #
		boolean isTopic = sendTo.charAt(0) == '#';		
		List<ServerWorker> workerList = serverObj.getWorkerList();
		//scroll through online workers to get msg recipient
		for(ServerWorker worker: workerList) {		
			//topic FOR
			//1st char == #, enter topic grouped chat
			if (isTopic) {
				if (worker.isMemberOfTopic(sendTo)) {
					String outMsg = "Topic-msg: " + sendTo + "from " + login + ": " + body + "\n";
					worker.send(outMsg);
				}//end if
				
			}//end if
			else {
				if (sendTo.equalsIgnoreCase(worker.getLogin())) {
					String outMsg = "msg: " + login + " " + body + "\n";
					worker.send(outMsg);
				}//end if
			}//end else
		}//end for
		
	}//end handleMessage

	private void handleLogoff() throws IOException {
		ServerObj.removeWorker(this);
		List<ServerWorker> workerList = serverObj.getWorkerList();

		//send other online users current users status
		String onlineMsg = "offline " + login + "\n";
		for(ServerWorker worker: workerList) {
			//avoid notification of self
			if (!login.contentEquals(worker.getLogin())) {
				worker.send(onlineMsg);
			}//end if
		}//end for
		clientSocket.close();
	}

	public String getLogin() {
		return login;
	}//end getLogin

	private void handleLogin(OutputStream outputStream, String[] tokens) throws IOException {
		if (tokens.length == 3) {
			String login = tokens[1];
			String password = tokens[2];
			
			if ((login.equals("louis") && password.equals("louis")) || (login.contentEquals("jim") && password.contentEquals("jim")))  {
				String msg = "ok login\n";
				outputStream.write(msg.getBytes());
				this.login = login;
				System.out.println("User logged in successfully: " + login);
				

				List<ServerWorker> workerList = serverObj.getWorkerList();
					//sends current user all other online logins
					for(ServerWorker worker: workerList) {
						if (worker.getLogin() != null) {
							//avoid notification of self
							if (!login.contentEquals(worker.getLogin())) {							
								String msg2 = "online " + worker.getLogin() + "\n";
								send(msg2);
							}//end if
						}//end if
					}//end for
					
					//send other online users current users status
					String onlineMsg = "online " + login + "\n";
					for(ServerWorker worker: workerList) {
						//avoid notification of self
						if (!login.contentEquals(worker.getLogin())) {
							worker.send(onlineMsg);
						}//end if
					}//end for
					
			}//end guest if
			else {
				String msg = "error login\n";
				outputStream.write(msg.getBytes());
				System.err.println("Login failed for " + login);
			}//end else
		}//end if
		
	}//end handleLogin

	private void send(String msg) throws IOException {
		//avoid notification of nulls in List
		if(login != null) {
			outputStream.write(msg.getBytes());
		}//end if
	}//end send
	
	
}//end class












