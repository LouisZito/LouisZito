import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

public class ChatServer extends JFrame implements Runnable{
	private static final long serialVerisonUID = 1L;
	private static final int PORT_NUMBER = 63458;
	
	private JTextArea logArea = new JTextArea(10, 30);
	private JButton startButton = new JButton("Start");
	
	private ServerSocket serverSocket;
	
	public ChatServer() {
		initGUI();
		setTitle("Chat Server");
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void log(String Message) {
		Date time = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy, HH;mm:ss ");
		String timeStamp = dateFormat.format(time);
		logArea.append(timeStamp + Message + "\n");
	}//end log
	
	private void start() {
		log("The server is running");
		
		try {
			serverSocket = new ServerSocket(PORT_NUMBER);
			while(true) {
				Socket socket = serverSocket.accept();
				log("Starting a new connection.");
			}//end while
		}
		catch(Exception e) {
			log("Exception caught when to to listen to port " + PORT_NUMBER + ".");
			log(e.getMessage());
		}
		//catch
		finally {
			try {
				if(!serverSocket.isClosed()) {
					serverSocket.close();
				}//and if
			}//end try
			catch(Exception e) {}
		}//finally
	}//end start
	
	public void initGUI() {
		TitleLabel titleLabel = new TitleLabel("Chat Server");
		add(titleLabel, BorderLayout.PAGE_START);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		add(mainPanel, BorderLayout.CENTER);
		
		logArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(logArea);
		mainPanel.add(scrollPane);
		DefaultCaret caret = (DefaultCaret)logArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		JPanel buttonPanel = new JPanel();
		add(buttonPanel, BorderLayout.PAGE_END);
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start();
			}
		});
		buttonPanel.add(startButton);
		getRootPane().setDefaultButton(startButton);
	}//initGUI
	
	private void startServer() {
		startButton.setEnabled(false);
		start();
	}
	
	public static void main(String[] args) {
		try {
			String className = UIManager.getCrossPlatformLookAndFeelClassName();
			UIManager.setLookAndFeel(className);
		}//try
		catch(Exception e) {}	
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new ChatServer();
			}//run
		});//invokeLater
	}//main
}//class
