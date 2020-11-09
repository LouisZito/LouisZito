import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;

public class TitleLabel extends JLabel{
	/**
	 * @author LAZ
	 * 11/8/20
	 * Networking PA2
	 */
	private static final long serialVersionUID = 1L;
	
	public TitleLabel(String title) {
		Font font = new Font(Font.SERIF, Font.BOLD, 32);
		setFont(font);
		setBackground(Color.black);
		setForeground(Color.white);
		setOpaque(true);
		setHorizontalAlignment(JLabel.CENTER);
		setText(title);
	}//end constructor
}//end class
