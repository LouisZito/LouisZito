import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

//class for on-double-click private message window to user
public class MessagePane extends JPanel implements MessageListener{

	private final Client client;
	private final String login;
	
	private DefaultListModel<String> listModel = new DefaultListModel<>();
	private JList<String> messageList = new JList<>(listModel);
	private JTextField inputField = new JTextField();

	public MessagePane(Client client, String login) {
		this.client = client;
		this.login = login;
		
		client.addMessageListener(this);
		
		setLayout(new BorderLayout());
		add(new JScrollPane(messageList), BorderLayout.CENTER);
		add(inputField, BorderLayout.SOUTH);
		
		inputField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String text = inputField.getText();
					client.msg(login, text);
					listModel.addElement("You: " + text);
					inputField.setText("");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}//end actionPerformed
		});//end addActionListener
			
	}//end MessagePane constructor
	
	@Override
	public void onMessage(String fromLogin, String msgBody) {
		if (login.equalsIgnoreCase(fromLogin)) {
			String line = fromLogin + ": " + msgBody;
			listModel.addElement(line);
		}//end if
	}//end onMessage
	
}//end class
