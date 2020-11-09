import java.io.*;
import java.net.*;

public class GameServer {
	
	private ServerSocket ss;
	private int numPlayers;
	private ServerSideConnection player1;
	private ServerSideConnection player2;
	private int turnsMade;
	private int maxTurns;
	private int[] values;
	private int player1ButtonNum;
	private int player2ButtonNum;
	
	public GameServer() {
		System.out.println("Game Server");
		numPlayers = 0;
		turnsMade = 0;
		maxTurns = 4;
		values = new int[4];
		
		for (int i =0; i < values.length; i++) {
			values[i] = (int)Math.ceil(Math.random() * 100); //0 to 99
			System.out.println("Value # " + (i + 1) + " is " + values[i]);
		}
		
		try {
			ss = new ServerSocket(51734);
		}//end try
		catch (IOException ex) {
			System.out.println("IOException from GameServer constructor");
		}//end catch		
	}//end game server

	public void acceptConnections() {
		try {
			System.out.println("Waiting for connections...");
			while (numPlayers < 2) {
				Socket s = ss.accept();
				numPlayers++;
				System.out.println("Player #" + numPlayers + " has connected.");
				ServerSideConnection ssc = new ServerSideConnection(s, numPlayers);
				if (numPlayers == 1) {
					player1 = ssc;
				}
				else {
					player2 = ssc;
				}
				Thread t = new Thread(ssc);
				t.start();
			}//end while
			System.out.println("Two player max, no more players allowed");
		}//end try
		catch (IOException ex){
			System.out.println("IOException from acceptConnections()");
		}
	}//end acceptConnections
	
	private class ServerSideConnection implements Runnable{
		private Socket socket;
		private DataInputStream dataIn;
		private DataOutputStream dataOut;
		private int playerID;
		
		public ServerSideConnection(Socket s, int id) {
			socket = s;
			playerID = id;
			try {
				dataIn = new DataInputStream(socket.getInputStream());
				dataOut = new DataOutputStream(socket.getOutputStream());
			}
			catch (IOException ex) {
				System.out.println("IOException from the SSC contructor");
			}
		}
		
		public void run() {
			try { //Initializing all states at game start before loop
				dataOut.writeInt(playerID);
				dataOut.writeInt(maxTurns);
				dataOut.writeInt(values[0]);
				dataOut.writeInt(values[1]);
				dataOut.writeInt(values[2]);
				dataOut.writeInt(values[3]);
				dataOut.flush();
				
				while(true) {
					if (playerID == 1) {
						player1ButtonNum = dataIn.readInt();
						System.out.println("Player 1 clicked button #" + player1ButtonNum);
					}
					else {
						player2ButtonNum = dataIn.readInt();
						System.out.println("Palyer 2 clicked button #" + player2ButtonNum);
					}
				}//end while
			}//end try
			catch (IOException ex) {
				System.out.println("IOException from Run SSC");
			}
		}//end run
	}//end ServerSideConnection
	
	public static void main(String[] args) {
		GameServer gs = new GameServer();
		gs.acceptConnections();
	}//end main
	
	
	
	
	
	
}//end GameServer
