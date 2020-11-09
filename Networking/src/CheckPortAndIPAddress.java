import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class CheckPortAndIPAddress extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	public JTextArea infoArea = new JTextArea(6, 25);
	
	private void initGUI() {
		add(infoArea, BorderLayout.CENTER);
	}
	
	public CheckPortAndIPAddress() {
		initGUI();
		setTitle("Check Port and IP Address");
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		try { //server socket 0 = random available port
			ServerSocket serverSocket = new ServerSocket(0);
			int port = serverSocket.getLocalPort();
			serverSocket.close();
			infoArea.setText("Available port: " + port);
			
			//get IP address
			InetAddress host = InetAddress.getLocalHost();
			String hostName = host.getHostAddress();
			String privateIPAddress = host.getHostAddress();
			infoArea.append("\n\nPrivate Host Name: " + hostName);
			infoArea.append("\nPrivate IP Address: " + privateIPAddress);	
			
			//obtain public IP address
			URL url = new URL("http://checkip.amazonaws.com/");
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String publicIPAddress = in.readLine();
			in.close();
			infoArea.append("\n\nPublic IP Address: " + publicIPAddress);
		}//try
		catch(Exception e) {
			infoArea.append(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		
		try {
			String className = UIManager.getCrossPlatformLookAndFeelClassName();
				UIManager.setLookAndFeel(className);			
		}//try
		catch (Exception e) {};
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new CheckPortAndIPAddress();
			}//end run
		});//invokeLater
		
		
	}//main

}//class
