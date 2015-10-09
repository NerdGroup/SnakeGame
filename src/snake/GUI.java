import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//Eclipse Test
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
	static boolean newHigh = false;

	//created class rect, made it all in one array
	static Rectangle snake[] = new Rectangle[length * length];

	static Rectangle2D readInstr = new Rectangle2D.Double(120, 210, 210, 60);
	static boolean firstGame = false;

	public static void main(String[] args) throws NumberFormatException, IOException
	{		
		JFrame start = new JFrame("Final Project!!!");
		start.getContentPane().setBackground(Color.green);
		start.setVisible(true);
		start.setSize(600, 600);
		start.setResizable(false);
		start.getContentPane().setLayout(new GridLayout(3, 1));

		JPanel pane = new JPanel();
		pane.setSize(start.getWidth()*2/3, start.getHeight()*2/3);
		pane.setLocation(0, start.getHeight()/3);
		pane.setLayout(new GridLayout(2, 2));
		JLabel welcomeText = new JLabel("WELCOME!!!");
		welcomeText.setFont(new Font("Serif", Font.BOLD, 66));
		welcomeText.setForeground(Color.red);
		welcomeText.setBackground(Color.green);
		welcomeText.setHorizontalAlignment(WIDTH/2);


		JButton buttonStart = new JButton("Play Game");
		buttonStart.setFont(new Font("Serif", Font.CENTER_BASELINE, 66));
		JButton buttonReset = new JButton("Reset High Score");
		buttonReset.setFont(new Font("Serif", Font.CENTER_BASELINE, 66));

		JLabel labelDone = new JLabel();
		labelDone.setFont(new Font("Serif", Font.CENTER_BASELINE, 60));
		labelDone.setForeground(Color.red);

		start.add(welcomeText);
		start.add(pane);
		pane.add(buttonStart);
		pane.add(buttonReset);
		start.add(labelDone);



		//game
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		canvas.setSize(length * snakeSize, length * snakeSize +15);

		frame.setPreferredSize(new Dimension(length*snakeSize + 7, length*snakeSize + 50));
		frame.setMaximumSize(new Dimension(length*snakeSize + 7, length*snakeSize + 50));
		frame.setMinimumSize(new Dimension(length*snakeSize + 7, length*snakeSize + 50));
		frame.setResizable(false);

		frame.setSize(length*snakeSize + 7, length*snakeSize + 50);
		frame.getContentPane().add(canvas);
		frame.pack();
		frame.setVisible(false);

		for(int i = 0; i < length * length; i++)
			snake[i] = new Rectangle();

		snake[0].rectangle = head;
		snake[0].onScreen = true;
		snake[0].direction = "DOWN";
		if (highS.exists())
		{
			try {
				String l = br.readLine();
				prevHigh = Integer.parseInt(l);	
			}
			catch(NullPointerException | NumberFormatException e) {
				prevHigh = 0;
			}
		}
		buttonStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("yo");
				start.setVisible(false);
				frame.setVisible(true);

				Graphics g = canvas.getGraphics();

				for(int i = 3; i > 0; i--)
				{
					g.setColor(Color.white);
					g.setFont(new Font("Serif", Font.CENTER_BASELINE, 66));
					g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
					g.setColor(Color.black);
					g.drawString(i+"", (length*snakeSize + 7)/2 - 15, (length*snakeSize + 50)/2);
					try {Thread.sleep(1000);} catch (InterruptedException e1) {}
				}
				g.setColor(Color.white);
				g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
				g.setColor(Color.black);
				g.drawString("GO!", (length*snakeSize + 7)/2 - 60, (length*snakeSize + 50)/2);
				try {Thread.sleep(1000);} catch (InterruptedException e1) {}

				g.setColor(Color.white);
				g.fillRect(0, 0, frame.getWidth(), frame.getHeight());

				t.start();
				snakeMovement.start();
				snakeMovement2.start();
				updateHigh.start();
			}
		});


		buttonReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				prevHigh = 0;

				labelDone.setText("High Score Reset Finished");

				try {
					fw = new FileWriter(highS);
					fw.write(("0"));
				} catch (IOException e1) {e1.printStackTrace();} 

			}});

	}


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
					newHigh = false;
					try
					{
						for(int i = 0; i < length*length; i++)
						{
							if(!snake[i].onScreen)
								break;
							try {
								arrowKey(snake[i].direction, canvas.getGraphics(), i);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						if (head.getX() == food.getX() && head.getY() == food.getY())
						{
							score+= 5;
							boolean checks = false;
							while(!checks)
							{
								checks = true;
								food.setFrame(rand.nextInt(29)*15, rand.nextInt(29)*15, snakeSize, snakeSize);
								for(int i = 0; i < currentLength; i++)
								{
									if(food.getX() == snake[i].rectangle.getX() && food.getY() == snake[i].rectangle.getY())
										checks = false;	
								}
							}
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

					}
				}
				g2.setColor(Color.WHITE);
				g2.fill(readInstr);

				try {
					fw = new FileWriter(highS); 
					fw.write((prevHigh+""));
					fw.close();
					fw = null;
				} catch (IOException e1) {}

				if (score > prevHigh)
				{
					prevHigh = score;
					newHigh = true;
				}

				g2.setColor(Color.BLACK);
				if(!newHigh)
					g2.drawString("GAME OVER", length * snakeSize / 2 - 40, length*snakeSize/2);
				else
				{
					g2.drawString("NEW HIGH SCORE!", length * snakeSize / 2 - 60, length*snakeSize/2);
				}
				g2.drawString("Click ESC to quit...", length * snakeSize / 2 - 53, length*snakeSize/2 + 20);
				g2.drawString("Press any other key to play again...", length * snakeSize / 2 - 92, length*snakeSize/2 + 40);

				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {}
			}

		}
	});


	public static Thread updateHigh = new Thread (new Runnable()
	{
		public void run ()
		{
			while (true)
			{
				if (score > prevHigh)
				{
					prevHigh = score;
					newHigh = true;
				}
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {}
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
							} catch (InterruptedException e) {}
							snake[i].direction = dir;
						}
					}
				}

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {}
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
						else if(snake[i].direction.equals("UP"))
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
		} catch (FileNotFoundException e) {}
	}

	static Canvas canvas = new GUI();


	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(Color.GREEN);

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

		for(int i = 1; snake[i].onScreen; i++)
		{
			if(!snake[i].onScreen)
				break;
			if(snake[i] == null)
				break;
			if(head.getX() == snake[i].rectangle.getX() && head.getY() == snake[i].rectangle.getY())
				gameEnd = true;
		}



		g2.drawString("Current Score: " + score + "", length * snakeSize / 9, length*snakeSize + 15);
		g2.drawString("High Score: " + prevHigh + "", length * snakeSize /3 * 2, length*snakeSize + 15);

	}



	public static void arrowKey(String string, Graphics g, int x) throws FileNotFoundException, IOException
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

			g2.drawString("CurrentScore: " + score + "", length * snakeSize / 2 - 10, length*snakeSize + 15);
			g2.drawString("High Score: " + prevHigh + "", length * snakeSize / 2 - 10, length*snakeSize + 25);
		}
		canvas.repaint();
	}

	public class keyHandler implements KeyListener  
	{
		@Override
		public void keyPressed(KeyEvent e)
		{

			if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
			{
				snake[0].direction = "RIGHT";
			}
			else if (e.getKeyCode() == KeyEvent.VK_LEFT|| e.getKeyCode() == KeyEvent.VK_A )
			{
				snake[0].direction = "LEFT";
			}
			else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)
			{
				snake[0].direction = "UP";

			}
			else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
			{
				snake[0].direction = "DOWN";

			}
			else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			{
				try {
					arrowKey("ESC", e.getComponent().getGraphics(), 0);
				}
				catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			else
			{
				if(gameEnd)
					try {
						arrowKey("ELSE", e.getComponent().getGraphics(), 0);
					} catch (IOException e1) {}
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
