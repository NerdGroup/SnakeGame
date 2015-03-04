package snake;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import javax.swing.JFrame;

import java.util.*;

public class GUI extends Canvas
{
    static JFrame frame = new JFrame("Snake Game!");
    
    static String direction = "DOWN";
    static int score = 0;
    static boolean gameEnd = false;
    static Random rand = new Random();
    static final int length = 30;
    static final int snakeSize = 15;
    static Rectangle2D head = new Rectangle2D.Double(60, 60, snakeSize, snakeSize);
    static Ellipse2D food = new Ellipse2D.Double(rand.nextInt(29)*15, rand.nextInt(29)*15, snakeSize, snakeSize);
    static boolean onScreen[] = new boolean[length * length];
    static Rectangle2D snake[] = new Rectangle2D[length * length];
    
    static Rectangle2D readInstr = new Rectangle2D.Double(120, 210, 210, 60);
    
    public static Thread t = new Thread (new Runnable ()
    {
        @Override
        public void run()
        {
        	while(true)
        	{
	            while(!gameEnd)
	            {
	                try
	                {
	                    arrowKey(direction, (Graphics)frame.getGraphics());
	                    if (head.getX() == food.getX() && head.getY() == food.getY())
	                    {
	                        score+= 5;
	                        food.setFrame(rand.nextInt(29)*15, rand.nextInt(29)*15, snakeSize, snakeSize);
	                        canvas.repaint();
	                        
	                    }
	                    
	                    if(head.getX() < 0 || head.getY() < 0 || head.getX() >= snakeSize * length || head.getY() >= snakeSize * length)
	                    	gameEnd = true;
	                    Thread.sleep(100);
	                    
	                }
	                catch (InterruptedException e)
	                {
	                    System.out.println("Interrupted");
	                    
	                }
	            }
	            
	            Graphics2D g = ((Graphics2D)canvas.getGraphics());
	            g.setColor(Color.WHITE);
	            g.fill(readInstr);
	           
	            canvas.getGraphics().drawString("GAME OVER", length * snakeSize / 2 - 40, length*snakeSize/2);
	            canvas.getGraphics().drawString("Click ESC to quit...", length * snakeSize / 2 - 53, length*snakeSize/2 + 20);
	            canvas.getGraphics().drawString("Press any other key to play again...", length * snakeSize / 2 - 92, length*snakeSize/2 + 40);
	            try {
					Thread.sleep(100);
					System.out.print(" ");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	
        	}
            
        }
        
    });;
    
    public GUI()
    {
        // if grid starts at (15, 15) and ends at (435, 435)
        keyHandler listener = new keyHandler();
        addKeyListener(listener);
        setFocusable(true);
        for(int i = 0; i < onScreen.length; i++)
        {
            onScreen[i] = false;
        }
        snake[0] = head;
        onScreen[0] = true;
    }
    
    static Canvas canvas = new GUI();
    
    
    public static void main(String[] args)
    {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        canvas.setSize(length * snakeSize, length * snakeSize);
        
        frame.setPreferredSize(new Dimension(length*snakeSize + 7, length*snakeSize + 50));
        frame.setMaximumSize(new Dimension(length*snakeSize + 7, length*snakeSize + 50));
        frame.setMinimumSize(new Dimension(length*snakeSize + 7, length*snakeSize + 50));
        frame.setResizable(false);
        
        frame.setSize(length*snakeSize + 7, length*snakeSize + 50);
        frame.getContentPane().add(canvas);
        frame.pack();
        frame.setVisible(true);
        t.start();
    }
    
    
    
    public void paint (Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.drawRect(0, 0, length * snakeSize, length * snakeSize + 50);
        
        g2.setColor(Color.GREEN);
        g2.fill(head);
        g2.setColor(Color.RED);
        g2.fill(food);
        g2.setColor(Color.BLACK);
        
        for(int i = 0; i < length; i++)
        {
            g.drawLine(snakeSize * i, 0, snakeSize * i, length*snakeSize);
            g.drawLine(0, snakeSize * i, length*snakeSize, snakeSize * i);
        }
        
        g2.drawString(score + "", length * snakeSize / 2 - 10, length*snakeSize + 15);
    }
    
    
    public static void arrowKey(String string, Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        
        if(string.equals("RIGHT"))
        {
            head.setFrame(head.getX() + snakeSize, head.getY(), snakeSize, snakeSize);
        }
        else if(string.equals("LEFT"))
        {
            head.setFrame(head.getX() - snakeSize, head.getY(), snakeSize, snakeSize);
        }
        else if(string.equals("UP"))
        {
            head.setFrame(head.getX(), head.getY() - snakeSize, snakeSize, snakeSize);
        }
        else if(string.equals("DOWN"))
        {
            head.setFrame(head.getX(), head.getY() + snakeSize, snakeSize, snakeSize);
        }
        else if(string.equals("ESC"))
        {
        	System.exit(0);
        }
        else
        {
        	gameEnd = false;
        	direction = "DOWN";
        	score = 0;
        	head.setFrame(60, 60, snakeSize, snakeSize);
        	g2.setColor(Color.GREEN);
            g2.fill(head);
            g2.setColor(Color.RED);
            g2.fill(food);
            g2.setColor(Color.BLACK);
            food.setFrame(rand.nextInt(29)*15, rand.nextInt(29)*15, snakeSize, snakeSize);
            for(int i = 0; i < length; i++)
            {
                g.drawLine(snakeSize * i, 0, snakeSize * i, length*snakeSize);
                g.drawLine(0, snakeSize * i, length*snakeSize, snakeSize * i);
            }
            
            g2.drawString(score + "", length * snakeSize / 2 - 10, length*snakeSize + 15);
        }
        
        
        canvas.repaint();
        
    }
    public class keyHandler implements KeyListener
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT )
            {
                direction = "RIGHT";
            }
            else if (e.getKeyCode() == KeyEvent.VK_LEFT )
            {
                direction = "LEFT";
            }
            else if (e.getKeyCode() == KeyEvent.VK_UP )
            {
                direction = "UP";
            }
            else if (e.getKeyCode() == KeyEvent.VK_DOWN )
            {
                direction = "DOWN";
            }
            else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            {
            	arrowKey("ESC", e.getComponent().getGraphics());
            }
            else
            {
            	if(gameEnd)
            		arrowKey("ELSE", e.getComponent().getGraphics());
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
