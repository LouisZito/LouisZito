import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class App {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
			//runnable code here	
				new MainFrame();	
			}//end run	
		});
		//end invokeLater
	}//end main
}//end App
