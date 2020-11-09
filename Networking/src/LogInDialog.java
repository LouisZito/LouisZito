import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LogInDialog extends JDialog{
	
	private static final long serialVersionUID = 1L;
	
	private boolean canceled = false;
	
	private JTextField ipAddressField = new JTextField(2);
	private JTextField userNameField = new JTextField(2);
	
	private void initGUI() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		add(mainPanel, BorderLayout.CENTER);
		
		JLabel ipAddressLabel = new JLabel();
		mainPanel.add(ipAddressLabel);
		mainPanel.add(ipAddressField);
		JLabel userNameLabel = new JLabel();
		mainPanel.add(userNameLabel);
		mainPanel.add(userNameField);
		
		//buttons
		JPanel buttonPanel = new JPanel();
		add(buttonPanel, BorderLayout.PAGE_END);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ok();
			}//actionPerformed
		});//addAction
		buttonPanel.add(okButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancel();
			}//actionPerformed
		});		
		buttonPanel.add(cancelButton);
		getRootPane().setDefaultButton(okButton);
		
		//listeners
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				cancel();
			}
		});
	}//end initGUI
	
	public void ok() {
		canceled = false;
		setVisible(false);
	}//ok
	
	public void cancel() {
		canceled = true;
		setVisible(false);
	}//cancel
	
	public LogInDialog(String appName) {
		setTitle("Login to " + appName);
		initGUI();
		setModal(true);
		pack();
		setLocationRelativeTo(null);
		setResizable(false);	
	}//end constructor
	
	
	
	

}
