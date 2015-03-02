package snake;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.Timer;

public class GUI extends Canvas 
{
	public GUI()
	{
		keyHandler listener = new keyHandler();
		addKeyListener(listener);
		setFocusable(true);
	}
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Canvas canvas = new GUI();
		canvas.setSize(400, 420);
		
		frame.getContentPane().add(canvas);
		
		frame.pack();
		frame.setVisible(true);
		
		
	}
	
	public void paint (Graphics g) 
	{
		Graphics2D g2 = (Graphics2D) g;
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
