import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class gui
{
    public static void main (String[]args)
    {
        frame frame = new frame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
class frame extends JFrame
{
    public frame()
	{
        setTitle("HELLO");
        setSize(250,350);
        panel panel = new panel();
        add(panel);
	}
}
class panel
{
    public panel()
    {
        KeyHandler key = new KeyHandler();
        addMouseListener(key);
        setFocusable(true);
    }
	public void paintComponent(Graphics g)
	{
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
    }
	public void arrowKey(String string, Graphics g)
	{
				
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
    private class KeyHandler implements KeyListener
    {
        public void keyTyped(KeyEvent e) {}
        public void keyPressed(KeyEvent e)
        {
            int keycode = e.getKeyCode();
            if (keycode == KeyEvent.VK_LEFT)
            {
                arrowkey("LEFT", (Graphics2D)e.getComponent().getGraphics());
            }
            if (keycode == KeyEvent.VK_RIGHT)
            {
                arrowkey("RIGHT", (Graphics2D)e.getComponent().getGraphics());
            }
            if (keycode == KeyEvent.VK_DOWN)
            {
                arrowkey("DOWN", (Graphics2D)e.getComponent().getGraphics());
            }
            if (keycode == KeyEvent.VK_UP)
            {
                arrowkey("UP", (Graphics2D)e.getComponent().getGraphics());
            }
            if (keycode == KeyEvent.VK_ESCAPE)
            {
                System.exit(0);
            }
        }
        public void keyReleased(KeyEvent e) {}		
    }
}
