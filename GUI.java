package snake;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.JFrame;
import javax.swing.Timer;
import java.util.*;

public class GUI extends Canvas 
{
    Random rand = new Random();
    int[] places = new int[29]; //why 29? what is this array for?

	
	
	public GUI()
	{
        	int[] places = new int[29]; //why again???
        	for (int i = 15, l=0; i < 450; i+= 15, l++)
        	{
        	    places[l] = i;
        	}// if grid starts at (15, 15) and ends at (435, 435)
		keyHandler listener = new keyHandler();
		addKeyListener(listener);
		setFocusable(true);
	}
	//Note: we can't do it at a random coordinate unless we specify a random coordinate in the top left quarter (don't want player starting going off the screen right at the start)
	static Rectangle2D head = new Rectangle2D.Double(places[rand.nextInt(30)], places[rand.nextInt(30)], 15, 15);
	static boolean onScreen[] = new boolean[900];
	static Rectangle2D snake[] = new Rectangle2D[900];
	
	public static void main(String[] args)
	{
		snake[0] = head;
		onScreen[0] = true;
		
		JFrame frame = new JFrame("Snake!!!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Canvas canvas = new GUI();
		canvas.setSize(450, 450);
		
		frame.getContentPane().add(canvas);
		
		frame.pack();
		frame.setVisible(true);
		
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
	        	/*else
	        	{
	        		arrowKey("ELSE", (Graphics)e.getComponent().getGraphics());
	        	}*/
		}
		
		@Override
		public void keyReleased(KeyEvent arg0) 
		{}
		
		@Override
		public void keyTyped(KeyEvent arg0) 
		{}	
	}
		
}
