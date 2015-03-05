package snake;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import javax.swing.JFrame;

import java.util.*;

public class GUI extends Canvas
{
    static JFrame frame = new JFrame("Snake Game!");
    
    static int score = 0;
    static int currentLength = 1;
    static boolean gameEnd = false;
    static String changeDirection;
    static Random rand = new Random();
    static final int length = 30;
    static final int snakeSize = 15;
    static Rectangle2D head = new Rectangle2D.Double(60, 60, snakeSize, snakeSize);
    static Ellipse2D food = new Ellipse2D.Double(rand.nextInt(29)*15, rand.nextInt(29)*15, snakeSize, snakeSize);
    
    //created class rect, made it all in one array
    static rect snake[] = new rect[length * length];
    
    
    static Rectangle2D readInstr = new Rectangle2D.Double(120, 210, 210, 60);
    static boolean changeD = false;
    static int numOfRectToMove = 0;
    
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
	                	if(changeD)
	                	{
	                		snake[numOfRectToMove].direction = changeDirection;
	                		numOfRectToMove++;
	                	}
	                	if(numOfRectToMove >= currentLength)
	                	{
	                		changeD = false;
	                		numOfRectToMove = 0;
	                	}
	                	for(int i = 0; i < length*length; i++)
	                	{
	                		if(!snake[i].onScreen)
	                			break;
	                		arrowKey(snake[i].direction, (Graphics)frame.getGraphics(), i);
	                	}
	                    if (head.getX() == food.getX() && head.getY() == food.getY())
	                    {
	                        score+= 5;
	                        food.setFrame(rand.nextInt(29)*15, rand.nextInt(29)*15, snakeSize, snakeSize);
	                        canvas.repaint();
	                        snake[currentLength] = new rect();
	                        snake[currentLength].rectangle = new Rectangle2D.Double(snake[currentLength -1].rectangle.getX(), snake[currentLength -1].rectangle.getY(), snakeSize, snakeSize);
	                        snake[currentLength].direction = snake[currentLength-1].direction;
	                        snake[currentLength].onScreen = true;
	                        
	                        Graphics2D g = (Graphics2D)frame.getGraphics();
	                        g.fill(snake[currentLength].rectangle);
	                        
	                        currentLength++;
	                        if(changeD)
	                        	numOfRectToMove++;
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
        
        for(int i = 0; i < length * length; i++)
        	snake[i] = new rect();
        snake[0].rectangle = head;
        snake[0].onScreen = true;
        snake[0].direction = "DOWN";
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
    
    
    public static void arrowKey(String string, Graphics g, int x)
    {
        Graphics2D g2 = (Graphics2D) g;
        
        if(string.equals("RIGHT"))
        {
            snake[x].rectangle.setFrame(head.getX() + snakeSize, head.getY(), snakeSize, snakeSize);
        }
        else if(string.equals("LEFT"))
        {
        	snake[x].rectangle.setFrame(head.getX() - snakeSize, head.getY(), snakeSize, snakeSize);
        }
        else if(string.equals("UP"))
        {
        	snake[x].rectangle.setFrame(head.getX(), head.getY() - snakeSize, snakeSize, snakeSize);
        }
        else if(string.equals("DOWN"))
        {
        	snake[x].rectangle.setFrame(head.getX(), head.getY() + snakeSize, snakeSize, snakeSize);
        }
        else if(string.equals("ESC"))
        {
        	System.exit(0);
        }
        else
        {
        	gameEnd = false;
        	for(int i = 4; i < length * length; i++)
        	{
        		if(!snake[i].onScreen)
        		{
        			break;
        		}
        		snake[i].onScreen = false;
        		snake[i].rectangle = null;
        	}
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
                changeDirection = "RIGHT";
                changeD = true;
            }
            else if (e.getKeyCode() == KeyEvent.VK_LEFT )
            {
                changeDirection = "LEFT";
                changeD = true;
            }
            else if (e.getKeyCode() == KeyEvent.VK_UP )
            {
                changeDirection = "UP";
                changeD = true;
            }
            else if (e.getKeyCode() == KeyEvent.VK_DOWN )
            {
                changeDirection = "DOWN";
                changeD = true;
            }
            else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            {
            	arrowKey("ESC", e.getComponent().getGraphics(), 0);
            }
            else
            {
            	if(gameEnd)
            		arrowKey("ELSE", e.getComponent().getGraphics(), 0);
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
