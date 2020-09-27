package dev.tilegame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

//to see contents of images in res folder F5
//to access contents proj, prop, lib, add path of that parent folder eg res

import dev.tilegame.Display.Display;
import dev.tilegame.gfx.ImageLoader;

//implement Runnable to make a thread
public class Game implements Runnable{
	
	private Display display;
	public int width, height;
	public String title;
	
	private boolean running = false;
	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics g;
	
	
		public Game(String title, int width, int height) {
			this.width = width;
			this.height = height;
			this.title = title;
		
	}//end Game constructor
		
	//initialized game graphic in run method
	private void init() {
		display = new Display(title, width, height);
	}
	
	//update variables loop
	private void tick() {
		
	}
	
	//update graphics loop
	private void render() {
		//sets preload of graphics for buffering
		bs = display.getCanvas().getBufferStrategy();
		//if no bs set as first use set to 3 buffer screens, more than enough for this level game
		if(bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		
		//clear before ever render

		
		//draw here
		
		
		//end drawing
		bs.show();
		g.dispose();
	}
	
	//run method required with Runnable/threads, game code goes here
		public void run() {
			init();
			
			while(running) {
				tick();
				render();
			}
			
			stop();
			
		}
		
	//used sync when working with threads
		public synchronized void start() {
			if(running)
				return;
			running = true;
			//set thread to run the current Game class
			thread = new Thread(this);
			//method below calls the run method
			thread.start();
		}
		
		public synchronized void stop() {
			running = false;
			if(!running)
				return;
			//join method requires try/catch
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
}//end class
