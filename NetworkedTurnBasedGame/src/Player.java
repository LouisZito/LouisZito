import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.awt.event.*;



public class Player extends JFrame{
	private int width;
	private int height;
	private Container contentPane;
	private JTextArea message;
	private JButton b1;
	private JButton b2;
	private JButton b3;
	private JButton b4;
	private int playerID;
	private int otherPlayer;
	private int[] values;
	private int maxTurns;
	private int turnsMade;
	private int myPoints;
	private int enemyPoints;
	private boolean buttonsEnabled;
	
	private ClientSideConnection csc;
	
	public Player(int w, int h) {
		width = w;
		height = h;
		contentPane = this.getContentPane();
		message = new JTextArea();
		b1 = new JButton("1");
		b2 = new JButton("2");
		b3 = new JButton("3");
		b4 = new JButton("4");
		values = new int[4];
		turnsMade = 0;
		myPoints = 0;
		enemyPoints = 0;
	}
	
	public void setUpGUI() {
		this.setSize(width, height);
		this.setTitle("Player #: " + playerID);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane.setLayout(new GridLayout(1, 5));
		contentPane.add(message);
		message.setText("Creating simple turn based game in Java");
		message.setWrapStyleWord(true);
		message.setLineWrap(true);
		message.setEditable(false);
		contentPane.add(b1);
		contentPane.add(b2);
		contentPane.add(b3);
		contentPane.add(b4);
		
		if(playerID == 1){
			message.setText("You are player 1, you go first.");
			otherPlayer = 2;
			buttonsEnabled = true;
		} 
		else {
			message.setText("You are player 2, you go after player 1.");
			otherPlayer = 1; 
			buttonsEnabled = false;
			//Additional thread for reading turn 
			//keep clear path for GUI updates
			Thread t = new Thread(new Runnable() {
				public void run() {
					updateTurn();
				}//run
			});
			t.start();
		}//end else
		
		toggleButtons();
		
		this.setVisible(true);
	}//end setUpGUI
	
	public void connectToServer() {
		csc = new ClientSideConnection();
	}
	
	public void setUpButtons() {
		ActionListener al = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JButton b = (JButton) ae.getSource();
				int bNum = Integer.parseInt(b.getText());
				
				message.setText("You clicked button #" + bNum + ". Now wait for player #" + otherPlayer);
				turnsMade++;
				System.out.println("Turns made: " + turnsMade);
				
				buttonsEnabled = false;
				toggleButtons();
				
				myPoints += values[bNum = 1];
				System.out.println("My points: " + myPoints);
				csc.sendButtonNum(bNum);
				
				//player2 winner check
				//Separate check area from P1
				//as last turn always made by P2
				//need final 2 to properly calc end of game
				if (playerID == 2 && turnsMade == maxTurns) {
					checkWinner();
				}//if
				else {				
					Thread t = new Thread(new Runnable() {
						public void run() {
							updateTurn();
						}//end run
					});//end Thread
					t.start();
				}//else
			}//end ActionPerformed
		};//anonymous class ending
		
		b1.addActionListener(al);
		b2.addActionListener(al);
		b3.addActionListener(al);
		b4.addActionListener(al);
	}//end setUpButtons
	
	public void toggleButtons() {
		b1.setEnabled(buttonsEnabled);
		b2.setEnabled(buttonsEnabled);
		b3.setEnabled(buttonsEnabled);
		b4.setEnabled(buttonsEnabled);
	}
	
	public void updateTurn() {
		int n = csc.receiveButtonNum();
		message.setText("Other player clicked button #" + n + ". Your turn.");
		enemyPoints += values[n-1];
		System.out.println("Your enemy has " + enemyPoints + " points.");
		if (playerID == 1 && turnsMade == maxTurns) {
			checkWinner();
		}
		else {
			buttonsEnabled = true;
		}
		toggleButtons();
	}
	
	private void checkWinner() {
		buttonsEnabled = false;
		if (myPoints > enemyPoints) {
			message.setText("You Won\n" + "You: " + myPoints + "\n" + "Enemy: " + enemyPoints);
		}else if (myPoints < enemyPoints) {
			message.setText("You LOST\n" + "You: " + myPoints + "\n" + "Enemy: " + enemyPoints);
		}
		else {
			message.setText("It's a tie! You both got " + myPoints + " points.");
		}
		
		csc.closeConnection();
	}
	
	//inner client class to connect to server
	private class ClientSideConnection{
		
		private Socket socket;
		private DataInputStream dataIn;
		private DataOutputStream dataOut;
		
		public ClientSideConnection() {
			System.out.println("Client Connection");
			try {
				socket = new Socket("localhost", 51734);
				dataIn = new DataInputStream(socket.getInputStream());
				dataOut = new DataOutputStream(socket.getOutputStream());
				playerID = dataIn.readInt();
				System.out.println("Connected to server as Player #" + playerID + ".");
				maxTurns = dataIn.readInt() / 2; //2 turns each player
				values[0] = dataIn.readInt();
				values[1] = dataIn.readInt();
				values[2] = dataIn.readInt();
				values[3] = dataIn.readInt();
				System.out.println("maxTurns: " + maxTurns);
				System.out.println("Value #1 is " + values[0]);
				System.out.println("Value #1 is " + values[1]);
				System.out.println("Value #1 is " + values[2]);
				System.out.println("Value #1 is " + values[3]);
			}//end try
			catch (IOException ex) {
				System.out.println("IO Exception from CSC constructor");
			}
		}
		
		public void sendButtonNum(int n) {
			try {
				dataOut.writeInt(n);
				dataOut.flush();
			}
			catch (IOException ex) {
				System.out.println("IOException from sendButtonNUm() CSC");
			}
		}
		
		public int receiveButtonNum() {
			int n = -1;
			try {
				n = dataIn.readInt();
				System.out.println("Player#" + otherPlayer + " clicked button #" + n);
			}
			catch (IOException ex) {
				System.out.println("IOException from receiveButtonNum() CSC");
			}
			return n;
		}
		
		public void closeConnection() {
			try {
				socket.close();
				System.out.println("Connection Closed.");
			}//try
			catch (IOException ex) {
					System.out.println("IOException on closeConnection player-side.");
			}//catch
		}//closeConnection
		
	}//ClientSideConnection
	
	
	
	public static void main(String[] args) {
		Player p = new Player(500, 100);
		p.connectToServer();
		p.setUpGUI();
		p.setUpButtons();
		
	}//end main
}//end Player




































