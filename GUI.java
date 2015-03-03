package snake;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.JFrame;
import javax.swing.Timer;
import java.util.*;

public class GUI extends Canvas 
{
	static Rectangle2D head = new Rectangle2D.Double(45, 45, 15, 15);
	static boolean onScreen[] = new boolean[900];
	static Rectangle2D snake[] = new Rectangle2D[900];
	
	
	
	public GUI()
	{
		keyHandler listener = new keyHandler();
		addKeyListener(listener);
		setFocusable(true);
	}
	
	public static void main(String[] args)
	{
		snake[0] = head;
		onScreen[0] = true;
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Canvas canvas = new GUI();
		canvas.setSize(450, 450);
		
		frame.getContentPane().add(canvas);
		
		frame.pack();
		frame.setVisible(true);
		
		gameLoop();
	}
	
	static void gameLoop() 
	{
		
		
	}

	public void paint (Graphics g) 
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.GREEN);
		g2.draw(head);
		g2.fill(head);
	}
	
	
	public void arrowKey(String string, Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;	

		if(string.equals("RIGHT"))
		{
			
		}
		else if(string.equals("LEFT"))
		{
			
		}
		else if(string.equals("UP"))
		{
			
		}
		else if(string.equals("DOWN"))
		{
			
		}
		
		repaint();
		
	}
	public class keyHandler implements KeyListener
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			if (e.getKeyCode() == KeyEvent.VK_RIGHT ) 
			{
	            arrowKey("RIGHT", (Graphics)e.getComponent().getGraphics());
			}
	        
	        else if (e.getKeyCode() == KeyEvent.VK_LEFT ) 
	        {
	        	arrowKey("LEFT", (Graphics)e.getComponent().getGraphics());
	        }
	        else if (e.getKeyCode() == KeyEvent.VK_UP ) 
	        {
	        	arrowKey("UP", (Graphics)e.getComponent().getGraphics());
	        }
	        
	        else if (e.getKeyCode() == KeyEvent.VK_DOWN ) 
	        {
	        	arrowKey("DOWN", (Graphics)e.getComponent().getGraphics());
	        }
	        else
	        {
	        	arrowKey("ELSE", (Graphics)e.getComponent().getGraphics());
	        }
		}
		
		@Override
		public void keyReleased(KeyEvent arg0) 
		{}
		
		@Override
		public void keyTyped(KeyEvent arg0) 
		{}	
	}
		
}
