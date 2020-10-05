import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;


//class for joining chat environment
public class LoginWindow extends JFrame{
	JTextField loginField = new JTextField();
	JPasswordField passwordField = new JPasswordField();
	JButton loginButton = new JButton("Login");
	private Client client;
	
	public LoginWindow() {
		super("Login");
		
		this.client = new Client("localhost", 8000);
		client.connect();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.add(loginField);
		p.add(passwordField);
		p.add(loginButton);
		
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doLogin();				
			}
		});//end addAL
		
		getContentPane().add(p, BorderLayout.CENTER);
		pack();
		setVisible(true);		
	}//end loginWindow
	

	private void doLogin() {
		String login = loginField.getText();
		String password = passwordField.getText();
		
		try {
			if (client.login(login,  password)) {
				//bring up return list window / else show error
				UserListPane userListPane = new UserListPane(client);
				JFrame frame = new JFrame("User List");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(400, 600);
				
				frame.getContentPane().add(userListPane, BorderLayout.CENTER);
				frame.setVisible(true);
				setVisible(false);
			}//end if
			else {
				JOptionPane.showMessageDialog(this, "Invalid login/password");
			}
		}//end try
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//end catch
	}//end doLogin
	
	public static void main(String[] args) {
		LoginWindow loginWin = new LoginWindow();
		loginWin.setVisible(true);
	}//end main
}//end class
