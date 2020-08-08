package dev.tilegame.Display;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Display {

	private JFrame frame;
	private Canvas canvas;
	
	private String title;
	private int width, height;
	
	//using THIS due to parameters having same name as variables
	public Display(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		//constructor of display contains JFrame constructor
		createDisplay();
	}
	
	private void createDisplay() {
		//needed JFrame param
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		//makes sure appears in center, not at sides
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));	
		
		frame.add(canvas);
		//makes sure entire canvas is visible
		frame.pack();
	}//end createDisplay
	
	public Canvas getCanvas(){
		return canvas;
	}
	
}//end main class
