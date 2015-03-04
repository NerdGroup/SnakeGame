import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.JFrame;
import java.util.*;

public class GUI extends Canvas
{
    static JFrame frame = new JFrame("Snake!!!");
    
    static String direction = "DOWN";
    static boolean gameEnd = false;
    static Random rand = new Random();
    static final int length = 30;
    static final int snakeSize = 15;
    static Rectangle2D head = new Rectangle2D.Double(60, 60, snakeSize, snakeSize);
    static Rectangle2D food = new Rectangle2D.Double(rand.nextInt(30)*15, rand.nextInt(30)*15, snakeSize, snakeSize);
    static boolean onScreen[] = new boolean[length * length];
    static Rectangle2D snake[] = new Rectangle2D[length * length];
    static Thread t = new Thread (new Runnable ()
                                  {
        @Override
        public void run ()
        {
            while(!gameEnd)
            {
                try
                {
                    if (head.getY() == 435.00  )
                    {
                        if (head.getX() == 435)
                        {
                            direction = "UP";
                        }
                        else
                        {
                            direction ="RIGHT";
                        }
                        
                    }
                    if(head.getX() == food.getX() && head.getY() == food.getY())
                    {
                        food.setRect(rand.nextInt(30)*15, rand.nextInt(30)*15, snakeSize, snakeSize);
                    }
                    arrowKey(direction, (Graphics)frame.getGraphics());
                    Thread.sleep(250);
                    // System.out.println("YO");
                }
                catch (InterruptedException e)
                {
                    System.out.println("Interrupted");
                }
            }
            
            System.out.println("Game Over!!!");
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
    
    public static void start() // Usless Method, just declare thread globally, and start it in main method
    {
        /*if (t == null) // Why is this here
         {*/
        t.start();
        /*}*/
    }
    
    public static void main(String[] args)
    {
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        canvas.setSize(length * snakeSize, length * snakeSize);
        
        frame.setPreferredSize(new Dimension(length*snakeSize, length*snakeSize));
        frame.setMaximumSize(new Dimension(length*snakeSize, length*snakeSize));
        frame.setMinimumSize(new Dimension(length*snakeSize, length*snakeSize));
        frame.setResizable(false);
        
        frame.setSize(length*snakeSize, length*snakeSize);
        frame.getContentPane().add(canvas);
        frame.pack();
        frame.setVisible(true);
        t.start();
    }
    
    
    
    public void paint (Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
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
