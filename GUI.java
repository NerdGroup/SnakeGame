package snake;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.*;
import javax.swing.JFrame;
import java.util.*;

public class GUI extends Canvas
{
	FileReader fr;
	static BufferedReader br;

	static File highS = new File("high.txt");
	static FileWriter fw;
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
	static int prevHigh;

	//created class rect, made it all in one array
	static rect snake[] = new rect[length * length];

	static Rectangle2D readInstr = new Rectangle2D.Double(120, 210, 210, 60);
	static boolean firstGame = false;
	public static Thread t = new Thread (new Runnable ()
	{
		@Override
		public void run()
		{
			Graphics2D g2 = ((Graphics2D)canvas.getGraphics());
			while(true)
			{                
				while(!gameEnd)
				{
					try
					{
						for(int i = 0; i < length*length; i++)
						{
							if(!snake[i].onScreen)
								break;
							arrowKey(snake[i].direction, canvas.getGraphics(), i);
						}
						if (head.getX() == food.getX() && head.getY() == food.getY())
						{
							score+= 5;
							food.setFrame(rand.nextInt(29)*15, rand.nextInt(29)*15, snakeSize, snakeSize);
							currentLength++;
							canvas.repaint();

							if(snake[currentLength-2].direction.equals("DOWN"))
								snake[currentLength-1].rectangle = new Rectangle2D.Double(snake[currentLength-2].rectangle.getX(), snake[currentLength-2].rectangle.getY() - snakeSize, snakeSize, snakeSize);
							else if(snake[currentLength-2].direction.equals("RIGHT"))
								snake[currentLength-1].rectangle = new Rectangle2D.Double(snake[currentLength-2].rectangle.getX() - snakeSize, snake[currentLength-2].rectangle.getY(), snakeSize, snakeSize);
							else if(snake[currentLength-2].direction.equals("LEFT"))
								snake[currentLength-1].rectangle = new Rectangle2D.Double(snake[currentLength-2].rectangle.getX() + snakeSize, snake[currentLength-2].rectangle.getY(), snakeSize, snakeSize);
							else if(snake[currentLength-2].direction.equals("UP"))
								snake[currentLength-1].rectangle = new Rectangle2D.Double(snake[currentLength-2].rectangle.getX(), snake[currentLength-2].rectangle.getY() + snakeSize, snakeSize, snakeSize);
							else
								System.out.println("ERROR!");

							snake[currentLength-1].direction = snake[currentLength-2].direction;
							snake[currentLength-1].onScreen = true;



						}

						if(head.getX() < 0 || head.getY() < 0 || head.getX() >= snakeSize * length || head.getY() >= snakeSize * length)
							gameEnd = true;
						Thread.sleep(100);

					}
					catch (InterruptedException e)
					{
						System.out.println("Interrupted");

					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						
					}
				}
				g2.setColor(Color.WHITE);
				g2.fill(readInstr);

				g2.setColor(Color.BLACK);
				g2.drawString("GAME OVER", length * snakeSize / 2 - 40, length*snakeSize/2);
				g2.drawString("Click ESC to quit...", length * snakeSize / 2 - 53, length*snakeSize/2 + 20);
				g2.drawString("Press any other key to play again...", length * snakeSize / 2 - 92, length*snakeSize/2 + 40);
				if (score > prevHigh)
				{
					prevHigh = score;
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					
				}
			}

		}
	});

	public static Thread snakeMovement = new Thread (new Runnable ()
	{
		@Override
		public void run()
		{
			while(true)
			{                
				while(!gameEnd)
				{
					for(int i = 1; i < currentLength; i++)
					{
						if(!snake[i].onScreen)
							break;

						if(!snake[i].direction.equals(snake[i-1].direction))
						{
							String dir = snake[i-1].direction;
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								
							}
							snake[i].direction = dir;


						}


					}
				}

				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					
				}
			}
		}
	});


	public static Thread snakeMovement2 = new Thread (new Runnable ()
	{
		@Override
		public void run()
		{
			while(true)
			{                
				while(!gameEnd)
				{
					for(int i = currentLength; i > 0; i--)
					{
						if(!snake[i].onScreen)
							continue;
						if(snake[i].direction.equals("UP"))
						{
							snake[i].rectangle.setFrame(snake[i-1].rectangle.getX(), snake[i-1].rectangle.getY() + snakeSize, snakeSize, snakeSize);
						}
						else if(snake[i].direction.equals("DOWN"))
						{
							snake[i].rectangle.setFrame(snake[i-1].rectangle.getX(), snake[i-1].rectangle.getY() - snakeSize, snakeSize, snakeSize);
						}
						else if(snake[i].direction.equals("LEFT"))
						{
							snake[i].rectangle.setFrame(snake[i-1].rectangle.getX() + snakeSize, snake[i-1].rectangle.getY(), snakeSize, snakeSize);
						}
						else if(snake[i].direction.equals("RIGHT"))
						{
							snake[i].rectangle.setFrame(snake[i-1].rectangle.getX() - snakeSize, snake[i-1].rectangle.getY(), snakeSize, snakeSize);
						}
						else
							System.out.println("Bug: String = " + snake[i].direction);

					}

					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						
					}
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					
				}

			}
		}
	});


	public GUI()
	{
		keyHandler listener = new keyHandler();
		addKeyListener(listener);
		setFocusable(true);

		try {
			fr = new FileReader(highS);
			br = new BufferedReader(fr);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			
		}


	}

	static Canvas canvas = new GUI();


	public static void main(String[] args)
	{
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		canvas.setSize(length * snakeSize, length * snakeSize +15);

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
		snakeMovement.start();
		snakeMovement2.start();
		if (highS.exists())
		{
			try {
				prevHigh = Integer.parseInt(br.readLine());
			} catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				
			}
		}
	}



	public void paint (Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(Color.GREEN);
		//g2.fill(head);

		for(int i = 0; i < length*length; i++)
		{
			if(!snake[i].onScreen)
				break;
			g2.fill(snake[i].rectangle);
		}

		g2.setColor(Color.RED);
		g2.fill(food);
		g2.setColor(Color.BLACK);
		for(int i = 0; i < length; i++)
		{
			g2.drawLine(snakeSize * i, 0, snakeSize * i, length*snakeSize);
			g2.drawLine(0, snakeSize * i, length*snakeSize, snakeSize * i);
		}


		g2.drawString("Current Score: " + score + "", length * snakeSize / 9, length*snakeSize + 15);
		g2.drawString("High Score :" + prevHigh + "", length * snakeSize / 3 * 2, length*snakeSize + 15);
	}


	public static void arrowKey(String string, Graphics g, int x) throws FileNotFoundException
	{
		Graphics2D g2 = (Graphics2D) g;

		if(string.equals("RIGHT"))
		{
			snake[x].rectangle.setFrame(snake[x].rectangle.getX() + snakeSize, snake[x].rectangle.getY(), snakeSize, snakeSize);
		}
		else if(string.equals("LEFT"))
		{
			snake[x].rectangle.setFrame(snake[x].rectangle.getX() - snakeSize, snake[x].rectangle.getY(), snakeSize, snakeSize);
		}
		else if(string.equals("UP"))
		{
			snake[x].rectangle.setFrame(snake[x].rectangle.getX(), snake[x].rectangle.getY() - snakeSize, snakeSize, snakeSize);
		}
		else if(string.equals("DOWN"))
		{
			snake[x].rectangle.setFrame(snake[x].rectangle.getX(), snake[x].rectangle.getY() + snakeSize, snakeSize, snakeSize);
		}
		else if(string.equals("ESC"))
		{
			

			try {
				fw = new FileWriter(highS);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
			}
			try {
				fw.write(prevHigh + "");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
			}
			try {
				fw.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
			}
			System.exit(0);
		}
		else
		{
			gameEnd = false;
			for(int i = 1; i < length * length; i++)
			{
				if(!snake[i].onScreen)
				{
					break;
				}
				snake[i].onScreen = false;
				snake[i].rectangle = null;
				snake[i].direction = null;
			}
			score = 0;
			currentLength = 1;
			snake[0].direction = "DOWN";
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
			g2.drawString("High Score :" + prevHigh + "", length * snakeSize / 2 - 10, length*snakeSize + 25);
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
				snake[0].direction = "RIGHT";
			}
			else if (e.getKeyCode() == KeyEvent.VK_LEFT )
			{
				snake[0].direction = "LEFT";
			}
			else if (e.getKeyCode() == KeyEvent.VK_UP )
			{
				snake[0].direction = "UP";

			}
			else if (e.getKeyCode() == KeyEvent.VK_DOWN )
			{
				snake[0].direction = "DOWN";

			}
			else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			{
				try {
					arrowKey("ESC", e.getComponent().getGraphics(), 0);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					
				}
			}
			else
			{
				if(gameEnd)
					try {
						arrowKey("ELSE", e.getComponent().getGraphics(), 0);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						
					}
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
