//Louis Zito
//10/2/20
//Networking PA1
//Chat app
//cmds: login, bye, msg (DM), join (topic-based group messaging)
import java.io.IOException;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {
	

	
	public static void main(String[] args) throws InterruptedException {
		
		//used by particular software can be shared among users
		int port = 8888;
		//instance of server
		ServerObj server_obj = new ServerObj(port);
		server_obj.start();
	}//end Main method
	
}//end server class
