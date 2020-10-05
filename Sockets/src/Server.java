

public class Server {
	

	
	public static void main(String[] args) throws InterruptedException {
		
		//used by particular software can be shared among users
		int port = 8000;
		//instance of server
		ServerObj server_obj = new ServerObj(port);
		server_obj.start();
	}//end Main method
	
}//end server class
