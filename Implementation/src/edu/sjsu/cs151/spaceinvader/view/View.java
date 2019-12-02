package edu.sjsu.cs151.spaceinvader.view;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import javax.swing.*;

import edu.sjsu.cs151.spaceinvader.message.KeyPressedMessage;
import edu.sjsu.cs151.spaceinvader.message.KeyReleasedMessage;
import edu.sjsu.cs151.spaceinvader.message.Message;
import edu.sjsu.cs151.spaceinvader.message.NewGameMessage;
import edu.sjsu.cs151.spaceinvader.message.ViewUpdateMessage;


public class View extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private BlockingQueue<Message> queue;
	
	private JFrame startFrame;   // Start screen frame.
	private JFrame gameFrame;    // Game screen frame.
	private JFrame scoreFrame;
	
	private JPanel startContent; // Start screen panel.
	private JPanel gameContent;  // Game screen panel.
	private JPanel scoreContent; // Score screen panel.
	
	private JLabel gameName;     // Start screen game title.
	private JLabel gameLogo;     // Start screen alien logo.
	
	private JButton startGame;
	private JButton retGame;
	
	// Alien sprite.
	private ImageIcon enemy = new ImageIcon(new ImageIcon("src/edu/sjsu/cs151/spaceinvader/view/alien.gif").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
	private Image alien_img = enemy.getImage();
	private MoveableImage aliens[][] = new MoveableImage[4][7];
	
	// Player sprite.
	private ImageIcon tank = new ImageIcon(new ImageIcon("src/edu/sjsu/cs151/spaceinvader/view/tank.gif").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
	private Image player_img = tank.getImage();
	private MoveableImage player = new MoveableImage(520, 520, player_img);
	
	// Player cannon shot sprite.
	private ImageIcon fire = new ImageIcon(new ImageIcon("src/edu/sjsu/cs151/spaceinvader/view/shot.png").getImage().getScaledInstance(15, 40, Image.SCALE_DEFAULT));
	private Image shot_img = fire.getImage();
	private MoveableImage shot = new MoveableImage(300, 700, shot_img);
	
	// For start screen animated alien.
	private MoveableShape logoAlien = new AlienShape(0, 0, 100);
	private ShapeIcon iconAlien = new ShapeIcon(logoAlien, 400, 100);
	private ImageIcon logo = new ImageIcon("src/edu/sjsu/cs151/spaceinvader/view/title.gif");
	
	private boolean shotFired = false;
	private boolean aliensCreated = false;
	private boolean gameWon = false;
	private boolean gameOver = false;
	private String points = "";
	private Timer timer = new Timer(20, this);
	
	public View() {
		startFrame = new JFrame();
		gameFrame =  new JFrame();
		scoreFrame =  new JFrame();
		
		startContent =  new JPanel();
		gameContent = new JPanel();
		scoreContent = new JPanel();
	}
	
	public void start(BlockingQueue<Message> queue) {
		this.queue = queue;
		startWindow();
		gameWindow();
		scoreWindow();
		addListeners();

		timer.start();
	}
	
	private void startWindow() {
		startFrame.setSize(600, 600);
		startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		startFrame.setResizable(false);
		startFrame.setTitle("Space Invader");
		
		startContent = new JPanel();
		startContent.setBackground(Color.black);
		
		gameName = new JLabel(logo);
		gameLogo = new JLabel(iconAlien);
		startGame = new JButton("Start Game");
		
		startGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					queue.put(new NewGameMessage());
				} catch (InterruptedException exception) {
					exception.printStackTrace();
				}
				System.out.println("Sent.");
				startFrame.setVisible(false);
				gameFrame.setVisible(true);
			}	
		});
		
		gameName.setAlignmentX(Component.CENTER_ALIGNMENT);
		gameLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
		startGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		startContent.setLayout(new BoxLayout(startContent, BoxLayout.Y_AXIS));
		startContent.add(Box.createRigidArea(new Dimension(50, 50)));
		startContent.add(gameName);
		startContent.add(Box.createRigidArea(new Dimension(50, 50)));
		startContent.add(gameLogo);
		startContent.add(Box.createRigidArea(new Dimension(50, 50)));
		startContent.add(startGame);
		startFrame.add(startContent);
		startFrame.setVisible(true);
		
		// Animation of start screen alien logo.
		Timer t = new Timer(15, new ActionListener() {
			int x = 1;
			int y = 1;
			int vert = 1;
			int dir = 1;
			boolean direction = false;

			public void actionPerformed(ActionEvent event) {
				if (x == 1) { direction = false; } 
				else if (x == 250) { direction = true; }

				if (vert == 1) { 
					y = 1; 
					dir = 1;
				} else if (vert == 20) {
					y = -1; 
					dir = -1;
				}
				
				vert += dir;
				logoAlien.repaint(direction);
				
				if (!direction) {
					logoAlien.translate(1, y);
					x += 1;
				} else {
					logoAlien.translate(-1, y);
					x -= 1;
				}
				
				gameLogo.repaint();
			}
		});
		t.start();	
	}
	
	public void gameWindow() {
		gameFrame.setSize(600, 600);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setBackground(Color.black);
		gameFrame.setVisible(false);
		gameFrame.setResizable(false);
		
		gameContent.setBackground(Color.black);
		this.setBackground(Color.black);
		
		gameFrame.add(this);
	}
	
	public void scoreWindow() {
		scoreFrame.setSize(600, 600);
		scoreFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		scoreFrame.setBackground(Color.black);
		scoreFrame.setVisible(false);
		scoreFrame.setResizable(false);
		scoreFrame.add(scoreContent);

		retGame = new JButton("Return to Start");
		retGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		retGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scoreFrame.setVisible(false);
				startFrame.setVisible(true);
			}	
		});
		
		JLabel scoreTitle = new JLabel("HIGH SCORES");
		scoreTitle.setForeground(Color.white);
		scoreTitle.setFont(new Font("Serif", Font.BOLD, 32));
		scoreTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		scoreContent.setLayout(new BoxLayout(scoreContent, BoxLayout.Y_AXIS));
		scoreContent.setBackground(Color.black);
		scoreContent.add(Box.createRigidArea(new Dimension(50, 50)));
		scoreContent.add(scoreTitle);
		drawScoreboard();
		scoreContent.add(Box.createRigidArea(new Dimension(50, 50)));
		scoreContent.add(retGame);
		scoreContent.add(Box.createRigidArea(new Dimension(50, 30)));
	}
	
	private void drawScoreboard() {
		File file = new File("src/edu/sjsu/cs151/spaceinvader/model/scoreboard.txt");
		JPanel scoreboard = new JPanel();
		scoreboard.setLayout(new GridLayout(7, 2, 5, 5));
		scoreboard.setBackground(Color.black);
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String name = scanner.nextLine();
				JLabel scoreName = new JLabel(name);
				String score = scanner.nextLine();
				JLabel scoreNum = new JLabel(score);
				scoreName.setForeground(Color.white);
				scoreNum.setForeground(Color.white);
				scoreName.setFont(new Font("Serif", Font.BOLD, 16));
				scoreNum.setFont(new Font("Serif", Font.BOLD, 16));
				scoreName.setHorizontalAlignment(SwingConstants.RIGHT);
				scoreboard.add(scoreName);
				scoreboard.add(scoreNum);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		scoreboard.setAlignmentX(Component.CENTER_ALIGNMENT);
		scoreContent.add(scoreboard);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}
	

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		repaint(g);
	}
	
	// Initial player starting point.
	// Movement settings.
	private int player_x = 520;
	private int alien_speed = 1;
	private int alien_bound_left = 20;
	private int alien_bound_right = 530;
	private int[] alien_edge = {0, 6, 3};
	private boolean alien_dir = true;
	
	public void repaint(Graphics g) {
		drawGameScore(g);
		drawGameLives(g);
		drawPlayer(g);
		drawAliens(g);		
		drawShot(g);

		try {
			queue.put(new ViewUpdateMessage());
		} catch (InterruptedException exception) {
			exception.printStackTrace();
		}
	}
	
	private void drawPlayer(Graphics g) {
		player.draw(g, this); 
		player.setX(player_x);
	}
	
	public void setPlayerPosition(int x) {
		player_x = x;
	}
	
	private void drawShot(Graphics g) {
		if (shotFired) {
			if (shot.getY() == player.getY()) { 
				shot.setX(player.getX() + 14);
				}
			
			if (shot.getY() > 25) {
				shot.draw(g, this);
				shot.setY(shot.getY() - 10);
			} else {
				shot.setY(player.getY());
				shot.setX(player.getX() + 14);
				shotFired = false;
			}
		} else {
			shot.setX(player.getX() + 14);
			shot.setY(player.getY()); 
		}
	}
	
	public void createAliens(int i, int j, int x, int y) {
		aliens[i][j] = new MoveableImage(x, y, alien_img);
		aliens[i][j].setVisible(true);
	}
	
	private void drawAliens(Graphics g) {
		if (this.aliensCreated) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 7; j++) {
					if (aliens[i][j].getVisible()) {
						aliens[i][j].draw(g, this);
					}
					
					if (aliens[i][j].getExploding()) {
						aliens[i][j].draw(g, this);
						aliens[i][j].setVisible(false);
						aliens[i][j].setExploding(false);
					}
				}
			}
			
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 7; j++) {
					if(aliens[i][alien_edge[0]].getX() - alien_speed > alien_bound_left && alien_dir ||
					   aliens[i][alien_edge[1]].getX() + alien_speed < alien_bound_right && !alien_dir) {
						if (alien_dir) {
							aliens[i][j].setX(aliens[i][j].getX() - alien_speed);
						}
						else {
							aliens[i][j].setX(aliens[i][j].getX() + alien_speed);
						}
					} else {
						if (alien_dir) {
							for (int k = 0; k < 4; k++) {
								for (int m = 0; m < 7; m++) {
									aliens[k][m].setY(aliens[k][m].getY() + 20);
								}
							}
							alien_dir = false;
						}
						else {
							for (int k = 0; k < 4; k++) {
								for (int m = 0; m < 7; m++) {
									aliens[k][m].setY(aliens[k][m].getY() + 20);
								}
							}
							alien_dir = true;
						}	
					}
				}
			}
		}
	}
	
	private void drawGameScore(Graphics g) {
		g.setFont(new Font("Serif", Font.BOLD, 20));
		g.setColor(Color.white);
		g.drawString("SCORE: " + points, 10, 30);
	}
	
	public void setPoints(int points) {
		this.points = String.format("%4d", points);
	}
	
	private void drawGameLives(Graphics g) {
		g.setFont(new Font("Serif", Font.BOLD, 20));
		g.setColor(Color.white);
		g.drawString("LIVES: 0000", 460, 30);
	}
	
	public void setShotFired(boolean flag) {
		this.shotFired = flag;
	}
	
	public boolean getShotFired() {
		return this.shotFired;
	}
	
	public int getShot_x() {
		return shot.getX();
	}
	
	public void setAliensCreated(boolean flag) {
		this.aliensCreated = flag;
	}
	
	public boolean getAliensCreated() {
		return this.aliensCreated;
	}
	
	public void setAlienExplode(int i, int j) {
		this.aliens[i][j].explode();
	}
	
	public void setAlienEdge(int[] transfer) {
		this.alien_edge = transfer;
	}
	
	public void gameOver() {
		gameOver = true;
		try {
		    Thread.sleep(1000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		gameFrame.setVisible(false);
		scoreFrame.setVisible(true);
	}
	
	public void gameWon() {
		gameWon = true;
		try {
		    Thread.sleep(1000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		gameFrame.setVisible(false);
		scoreFrame.setVisible(true);
	}
	
	public void setSpeed(int score) {
		if (score == 15) { alien_speed = 2; }
		else if (score == 25) { alien_speed = 5; }
		else if (score == 0) { alien_speed = 1; }
	}
	
	public int updateBoard(int[][] alien_x, int[][] alien_y) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 7; j++) {
				alien_x[i][j] = aliens[i][j].getX();
				alien_y[i][j] = aliens[i][j].getY();
			}
		}
		return shot.getY();
	}
	
	private void addListeners() {
		gameFrame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyPressed(KeyEvent e) {
				try {
					queue.put(new KeyPressedMessage(Integer.toString(e.getKeyCode())));
				} catch (InterruptedException exception) {
					exception.printStackTrace();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				try {
					queue.put(new KeyReleasedMessage(Integer.toString(e.getKeyCode())));
				} catch (InterruptedException exception) {
					exception.printStackTrace();
				}
			}
		});
	}
}









 