import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class UserListPane extends JPanel implements UserStatusListener{

	private Client client;
	private JList<String> userListUI;
	private DefaultListModel<String> userListModel;

	public UserListPane(Client client) {
		this.client = client;
		this.client.addUserStatusListener(this);
		
		userListModel = new DefaultListModel<>();
		userListUI = new JList<>(userListModel);
		setLayout(new BorderLayout());
		add(new JScrollPane(userListUI), BorderLayout.CENTER);
		
		//adds event lister for GUI-message-on-click
		userListUI.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() > 1) {
					String login = userListUI.getSelectedValue();
					MessagePane messagePane = new MessagePane(client, login);
					//creation of click-message frame details
					JFrame f = new JFrame("Message: " + login);
					f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					f.setSize(500, 500);
					f.getContentPane().add(messagePane, BorderLayout.CENTER);
					f.setVisible(true);			
				}//end if
			}//end mouseClicked
		});//end mousse listener
	}//end constructor

	public static void main(String[] args) {
		Client client = new Client("localhost", 8000);
		
		//create window and add userListPane as main component
		UserListPane userListPane = new UserListPane(client);
		JFrame frame = new JFrame("User List");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 600);
		
		frame.getContentPane().add(userListPane, BorderLayout.CENTER);
		frame.setVisible(true);
		
		if (client.connect()) {
			try {
				client.login("jim", "jim");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}//end if
		
	}//end main

	@Override
	public void online(String login) {
		userListModel.addElement(login);
		
	}//end online

	@Override
	public void offline(String login) {
		userListModel.removeElement(login);
	}//end offline
	
}//end class
