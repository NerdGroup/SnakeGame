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
        static int length = 30;
    static int snakeSize = 15;
	static Rectangle2D head = new Rectangle2D.Double(60, 60, snakeSize, snakeSize);
	static boolean onScreen[] = new boolean[900];
	static Rectangle2D snake[] = new Rectangle2D[900];

	public GUI()
	{
       // if grid starts at (15, 15) and ends at (435, 435)
		keyHandler listener = new keyHandler();
		addKeyListener(listener);
		setFocusable(true);
		
		for(int i = 0; i < onScreen.length; i++)
		{
			
		}	
		snake[0] = head;
		onScreen[0] = true;
	}
	
	public static void main(String[] args)
	{		
		JFrame frame = new JFrame("Snake!!!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Canvas canvas = new GUI();
		canvas.setSize(length * snakeSize, length * snakeSize);
		
		frame.setPreferredSize(new Dimension(length*snakeSize, length*snakeSize));
		frame.setMaximumSize(new Dimension(length*snakeSize, length*snakeSize));
		frame.setMinimumSize(new Dimension(length*snakeSize, length*snakeSize));
		frame.setResizable(false);
		
		frame.setSize(length*snakeSize, length*snakeSize);
		frame.getContentPane().add(canvas);
		frame.pack();
		frame.setVisible(true);
		
		
	}
	
	

	public void paint (Graphics g) 
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.GREEN);
		g2.fill(head);
		
		g2.setColor(Color.BLACK);
		for(int i = 0; i < length; i++)
		{
			g.drawLine(snakeSize * i, 0, snakeSize * i, length*snakeSize);
			g.drawLine(0, snakeSize * i, length*snakeSize, snakeSize * i);
		}
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
