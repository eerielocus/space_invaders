package edu.sjsu.cs151.spaceinvader.view;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import javax.swing.*;

import edu.sjsu.cs151.spaceinvader.message.KeyPressedMessage;
import edu.sjsu.cs151.spaceinvader.message.KeyReleasedMessage;
import edu.sjsu.cs151.spaceinvader.message.Message;
import edu.sjsu.cs151.spaceinvader.message.NewGameMessage;
import edu.sjsu.cs151.spaceinvader.message.ViewUpdateMessage;

/**
 * View provides the user interface and graphics to be displayed for the game. Generates
 * events from user and stores as message objects to be sent to Controller.
 * 
 * @author Michael Kang and Guiller Dalit 
 */
public class View extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private BlockingQueue<Message> queue;
	
	private JFrame startFrame;  	// Start screen frame.
	private JFrame gameFrame;   	// Game screen frame.
	private JFrame scoreFrame;
	
	private JPanel startContent;	// Start screen panel.
	private JPanel gameContent;  	// Game screen panel.
	private JPanel scoreContent; 	// Score screen panel.
	private JPanel scoreBoard;		// Score board panel.
	private JPanel usernamePanel;	// Enter username panel

	private JTextField username;
	
	private JLabel gameName;     	// Start screen game title.
	private JLabel gameLogo;    	// Start screen alien logo.
	private JLabel gameControls;	// Start screen basic player control instructions.
	
	private JMenuBar menu;			// Start screen menu bar.
	private JMenu help;				// Start screen menu.
	private JMenuItem instructions;	// Start screen menu item containing help instructions.
	
	private JButton startGame;		// Start button at start screen.
	private JButton retGame;		// Return to start button at score screen.
	private JButton submit; 		// Submit button to update score board
	
	// Alien sprite.
	private ImageIcon enemy = new ImageIcon(new ImageIcon(this.getClass().getResource("alien.gif")).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
	private Image alien_img = enemy.getImage();
	private MoveableImage aliens[][] = new MoveableImage[4][7];
	
	// Player sprite.
	private ImageIcon tank = new ImageIcon(new ImageIcon(this.getClass().getResource("tank.gif")).getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
	private Image player_img = tank.getImage();
	private MoveableImage player = new MoveableImage(10, 520, player_img);
	
	// Barrier sprite.
	private ImageIcon block = new ImageIcon(new ImageIcon(this.getClass().getResource("barrier.gif")).getImage().getScaledInstance(10, 10, Image.SCALE_DEFAULT));
	private Image barrier_img = block.getImage();
	private MoveableImage barrier[][][] = new MoveableImage[4][3][6];
	
	// Player cannon shot sprite.
	private ImageIcon fire = new ImageIcon(new ImageIcon(this.getClass().getResource("shot.png")).getImage().getScaledInstance(1, 40, Image.SCALE_DEFAULT));
	private Image shot_img = fire.getImage();
	private MoveableImage shot = new MoveableImage(300, 700, shot_img);
	
	// Alien bomb sprite.
	private ImageIcon firebomb = new ImageIcon(new ImageIcon(this.getClass().getResource("bomb.png")).getImage().getScaledInstance(1, 40, Image.SCALE_DEFAULT));
	private Image bomb_img = firebomb.getImage();
	private MoveableImage bomb = new MoveableImage(300, 700, bomb_img);
	
	// For start screen animated alien.
	private MoveableShape logoAlien = new AlienShape(0, 0, 100);
	private ShapeIcon iconAlien = new ShapeIcon(logoAlien, 400, 100);
	private ImageIcon logo = new ImageIcon(this.getClass().getResource("title.gif"));

	// Instructions on how to control player.
	private ImageIcon controls = new ImageIcon(this.getClass().getResource("instructions.png"));
	
	private boolean bombDropped = false;	// Flag to check if bomb is on screen.
	private boolean shotFired = false;		// Flag to check if shot is on screen.
	private boolean barrierCreated = false;	// Flag to check if barrier is on screen.
	private boolean aliensCreated = false;	// Flag to check if aliens are created (avoid null exception)
	private boolean alien_dir = true;		// Alien fleet's direction of movement.
	private boolean gameWon = false;		// Game won flag.
	private boolean gameOver = false;		// Game over flag.
	private int currentScore = 0;
	private int player_x = 10;				// Initial player position.
	private int alien_speed = 1;			// Initial alien movement speed.
	private int alien_bound_left = 10;		// Screen left border.
	private int alien_bound_right = 530;	// Screen right border.
	private int[] alien_edge = {0, 6, 3};	// Alien fleet's left, right, bottom most row/column.
	private String lives = "";				// Number of player lives.
	private String points = "";				// Number of points.
	private String userName = "";
	private String[] scoresheet;
	private static final String NEWLINE = "\n";
	private Timer timer = new Timer(30, this);
	
	/**
	 * Constructor initializing new JFrames and JPanels.
	 */
	public View() {
		startFrame = new JFrame();
		gameFrame =  new JFrame();
		scoreFrame =  new JFrame();
		
		startContent =  new JPanel();
		gameContent = new JPanel();
		scoreContent = new JPanel();
		scoreBoard = new JPanel();
	}
	
	/**
	 * Initiates all windows and listeners. Receives queue link from Controller and 
	 * starts the timer.
	 * @param queue object sent from Controller to store messages from events
	 */
	public void start(BlockingQueue<Message> queue) {
		this.queue = queue;
		startWindow();
		gameWindow();
		scoreWindow();
		addListeners();

		timer.start();
	}
	
	/**
	 * Start window (welcome screen).
	 */
	private void startWindow() {
		startFrame.setSize(600, 600);
		startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startFrame.setResizable(false);
		startFrame.setTitle("Space Invader");
		// Create welcome screen panel.
		startContent = new JPanel();
		startContent.setBackground(Color.black);
		// Menu items for instructions on how to play.
		menu = new JMenuBar();
		help = new JMenu("Help");
		// Create menu item and add action to it.
		instructions = new JMenuItem("How to Play");
		instructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Objective: Destroy all aliens before they reach barriers.\n\n"
												  + "Barriers can be destroyed by aliens or player.\n"
												  + "Aliens will begin to move faster the more are destroyed.\n"
												  + "Upon winning, the game will restart and aliens spawn closer.\n"
												  + "To exit, simply close the window.");
			}
		});
		// Add items to menu and frame.
		help.add(instructions);
		menu.add(help);
		startFrame.setJMenuBar(menu);
		// Logo, animation, controls, and start button implementation.
		gameName = new JLabel(logo);
		gameLogo = new JLabel(iconAlien);
		gameControls = new JLabel(controls);
		startGame = new JButton("Start Game");
		// Start button action.
		startGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					queue.put(new NewGameMessage());
				} catch (InterruptedException exception) {
					exception.printStackTrace();
				}
				startFrame.setVisible(false);
				gameFrame.setVisible(true);
			}	
		});
		// Align elements to center.
		gameName.setAlignmentX(Component.CENTER_ALIGNMENT);
		gameLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
		gameControls.setAlignmentX(Component.CENTER_ALIGNMENT);
		startGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		// Add elements to panel and frame.
		startContent.setLayout(new BoxLayout(startContent, BoxLayout.Y_AXIS));
		startContent.add(Box.createRigidArea(new Dimension(20, 20)));
		startContent.add(gameName);
		startContent.add(Box.createRigidArea(new Dimension(50, 50)));
		startContent.add(gameLogo);
		startContent.add(gameControls);
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
				// Set limits for horizontal movement
				if (x == 1) { direction = false; } 
				else if (x == 250) { direction = true; }
				// Set limits for vertical movement.
				if (vert == 1) { 
					y = 1; 
					dir = 1;
				} else if (vert == 20) {
					y = -1; 
					dir = -1;
				}
				// Adjust height accordingly.
				vert += dir;
				logoAlien.repaint(direction);
				// Move based on direction set.
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
	
	/**
	 * Game window that contains the actual game.
	 */
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
	
	/**
	 * Score window that contains the high scores taken from Board.
	 */
	public void scoreWindow() {
		scoreFrame.setSize(600, 600);
		scoreFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		scoreFrame.setBackground(Color.black);
		scoreFrame.setVisible(false);
		scoreFrame.setResizable(false);
		scoreFrame.add(scoreContent);
		// Check if game is over, then check score if its considered high.
		// If it is, bring up addUsername. Else, go straight to score board.
		if (gameOver) {
			currentScore = Integer.parseInt(points.trim());
			if (isHighScore(currentScore) == true) { addUsername(); }
			else { drawScoreboard(); }
		}
		scoreContent.setLayout(new BoxLayout(scoreContent, BoxLayout.Y_AXIS));
		scoreContent.setBackground(Color.black);
		scoreContent.add(Box.createRigidArea(new Dimension(15, 15)));
	}
	
	/**
	 * Adding username window for the score board.
	 */
	private void addUsername() {
		usernamePanel = new JPanel();
		username = new JTextField();
		submit = new JButton("Submit");
		JLabel usernameTitle = new JLabel("Enter Player Name");

		usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.Y_AXIS));
		usernamePanel.setBackground(Color.black);
		usernamePanel.setMaximumSize(new Dimension(500, 350));
		usernamePanel.add(Box.createRigidArea(new Dimension(170, 190)));

		usernameTitle.setForeground(Color.white);
		usernameTitle.setFont(new Font("Serif", Font.BOLD, 40));
		usernameTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		usernamePanel.add(usernameTitle);
		usernamePanel.add(Box.createRigidArea(new Dimension(10, 10)));

		username.setFont(username.getFont().deriveFont(Font.BOLD, 80f));
		username.setAlignmentX(Component.CENTER_ALIGNMENT);
		username.setPreferredSize(new Dimension(150, 20));
		usernamePanel.add(username);
		usernamePanel.add(Box.createRigidArea(new Dimension(10, 10)));
		// Add action to submit to check name and score for replacement into score board.
		// Redraw score board.
		submit.setAlignmentX(Component.CENTER_ALIGNMENT);
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userName = username.getText();
				replaceScore(userName, currentScore);
				usernamePanel.setVisible(false);
				drawScoreboard();
			}
		});
		
		usernamePanel.add(submit);
		scoreContent.add(usernamePanel);
		scoreContent.add(Box.createRigidArea(new Dimension(10, 10)));

	}

	/**
	 * Method to get the score information from the Model package and display into a
	 * grid format.
	 */
	private void drawScoreboard() {
		FileReader file;
		BufferedReader buff;
		// Reset scoreBoard, remove all content for the new game.
		scoreBoard.removeAll();
		// Repaint scoreContent to remove previous components.
		scoreContent.revalidate();
		scoreContent.repaint();

		scoreBoard.setLayout(new BoxLayout(scoreBoard, BoxLayout.Y_AXIS));
		scoreBoard.setBackground(Color.black);
		scoreBoard.setAlignmentX(Component.CENTER_ALIGNMENT);
		scoreBoard.setMaximumSize(new Dimension(500, 450));
		scoreBoard.add(Box.createRigidArea(new Dimension(20, 20)));

		JLabel scoreTitle = new JLabel("HIGH SCORES");
		scoreTitle.setForeground(Color.white);
		scoreTitle.setFont(new Font("Serif", Font.BOLD, 42));
		scoreTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

		scoreBoard.add(scoreTitle);
		scoreBoard.add(Box.createRigidArea(new Dimension(20, 20)));

		try {
			file = new FileReader(new File("src/edu/sjsu/cs151/spaceinvader/model/scoreboard.txt"));
			buff = new BufferedReader(file);
			String line;
			while ((line = buff.readLine()) != null) {
				String[] scores = line.split("=");

				if (scores[1] != null && scores[0] != null) {
					String score = scores[1];
					String name = scores[0];
					JLabel playerScore = new JLabel(name + " " + score);

					playerScore.setAlignmentX(Component.CENTER_ALIGNMENT);
					playerScore.setForeground(Color.white);
					playerScore.setFont(new Font("Serif", Font.BOLD, 30));

					scoreBoard.add(playerScore);
					scoreBoard.add(Box.createRigidArea(new Dimension(10, 10)));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		scoreContent.add(scoreBoard);
		scoreContent.add(Box.createRigidArea(new Dimension(15, 15)));
		
		retGame = new JButton("Return to Start");
		retGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		retGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Reset scoreContent.
				scoreContent.removeAll();
				scoreWindow();
				scoreFrame.setVisible(false);
				startFrame.setVisible(true);
			}
		});

		scoreBoard.add(Box.createRigidArea(new Dimension(20, 20)));
		scoreBoard.add(retGame);
	}

	/**
	 * Check if the final score of the player qualifies as a high score.
	 * @param score points accumulated at end of game
	 * @return flag indicating if score is eligible for high score
	 */
	private boolean isHighScore(int score) {
		boolean isHighScore = false;
		scoresheet = getHighScores();
		int scoreCount = 0;
		// Iterate through the array that has the scores from the scoreboard.txt.
		for (int index = 0; index < scoresheet.length; index++) {
			if (scoresheet[index] != null) {
				scoreCount++;
				String[] scores = scoresheet[index].split("=");
				// Check if the current score beat at least one of the high scores.
				if (Integer.parseInt(scores[1].trim()) < score) {
					isHighScore = true;
				}
			}
		}
		if (scoreCount < 5) { isHighScore = true; }
		return isHighScore;
	}

	/**
	 * Read the scoreboard.txt and store the values into String array then return
	 * the array.
	 * @return string array of high scores
	 */
	private String[] getHighScores() {
		FileReader file;
		BufferedReader buff;
		String[] array = new String[5];
		// Open file, read file, store content into an array.
		try {
			file = new FileReader(new File("src/edu/sjsu/cs151/spaceinvader/model/scoreboard.txt"));
			buff = new BufferedReader(file);
			String line;
			int index = 0;
			while ((line = buff.readLine()) != null) {
				String[] scores = line.split("=");
				if (scores[1] != null && scores[0] != null) { array[index] = line; }
				index++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return array;
	}

	/**
	 * The method checks if the username is already in stored in the file.
	 * @param username string containing player name
	 * @return flag indicating if name is valid or not
	 */
	private boolean isInvalidName(String username) {
		boolean invalid = false;
		// Iterate through the array.
		for (int index = 0; index < scoresheet.length; index++) {
			if (scoresheet[index] != null) {
				String[] score = scoresheet[index].split("=");
				// Check if the current name is equal to the current element.
				if (score[0].equals(username)) {
					invalid = true;
					break;
				} else {
					invalid = false;
				}
			}
		}
		return invalid;
	}
	
	/**
	 * Check if the name entered by the player is already in the score board and
	 * if name is on the board, check if its a higher score. If not, then do not
	 * change score board.
	 * @param name string containing current player name
	 * @param score int containing current score
	 * @return flag indicating if the name is being replaced
	 */
	public void replaceScore(String name, int score) {
		// Check if name is the same or not.
		// If it is not (false), then replace lowest score with new name and score since
		// high score validity check was already done. (We know its a qualifying high score.
		if (isInvalidName(name) == false) {
			scoresheet[4] = name + "=" + score;
		} else {
			// If there is same name, find it and check if score is higher or not.
			for (int index = 0; index < scoresheet.length; index++) {
				if (scoresheet[index] != null) {
					String[] scores = scoresheet[index].split("=");
					// Check if the name of the current element is equal to username.
					if (name.equals(scores[0]) && score > Integer.parseInt(scores[1])) {
						scoresheet[index] = name + "=" + score;
					}
				}
			}
		}
		// Write to file the new score sheet.
		setHighScoresToFile();
	}

	/**
	 * Write the replace score into the file in the correct order.
	 */
	public void setHighScoresToFile() {
		BufferedWriter writer = null;
		try {
			sortArray(scoresheet);
			// Store array content to the file.
			writer = new BufferedWriter(new FileWriter("src/edu/sjsu/cs151/spaceinvader/model/scoreboard.txt", false));
			for (int index = 0; index < scoresheet.length; index++) {
				if (scoresheet[index] != null) {
					writer.append(scoresheet[index] + NEWLINE);
				}
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sort the array in descending order based on score.
	 * @param array containing names and scores
	 */
	private void sortArray(String[] array) {
		String temp = "";
		for (int index = 0; index < array.length; index++) {
			String[] score = array[index].split("=");
			for (int j = index + 1; j < array.length; j++) {
				String[] score1 = array[j].split("=");
				if (Integer.parseInt(score[1].trim()) < Integer.parseInt(score1[1].trim())) {
					temp = array[index];
					array[index] = array[j];
					array[j] = temp;
				}
			}
		}
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
	
	/**
	 * Customized, repeated paint method to update the game screen. After drawing, it will
	 * send an update message to the game's board to adjust position of aliens and shots.
	 * @param g graphics
	 */
	public void repaint(Graphics g) {
		drawGameScore(g);
		drawGameLives(g);
		drawPlayer(g);
		drawAliens(g);		
		drawShot(g);
		drawBomb(g);
		drawBarrier(g);
		if (gameWon) { drawGameWon(g); }
		if (gameOver) { drawGameOver(g); }

		try {
			queue.put(new ViewUpdateMessage());
		} catch (InterruptedException exception) {
			exception.printStackTrace();
		}
	}
	
	/**
	 * Draw player image onto game screen.
	 * @param g graphics
	 */
	private void drawPlayer(Graphics g) {
		// If visible, draw per usual and set position to new position.
		if (player.getVisible()) {
			player.draw(g, this); 
			player.setX(player_x);
		}
		// If not visible and exploding, draw new exploding image and reset player position.
		if (player.getExploding()) {
			player.draw(g, this);
			player.setExploding(false);
			setPlayerPosition(10);
		}
	}
	
	/**
	 * Draw the player's shot.
	 * @param g graphics
	 */
	private void drawShot(Graphics g) {
		// Check if shot is still in flight.
		// If not, reset position.
		if (shotFired && player.getVisible()) {
			// If shot is with the player, synchronize its position with player.
			if (shot.getY() == player.getY()) { 
				shot.setX(player.getX() + 20);
			}
			// If shot is below the screen border, continue drawing until it isn't
			// Else reset position, initiate explosion, reset, and set shotFired to false.
			if (shot.getY() > 25) {
				shot.draw(g, this);
				shot.setY(shot.getY() - 10);
			} else {
				shot.explode();
				shot.draw(g, this);
				shot.reset(shot_img);
				shot.setExploding(false);
				shotFired = false;
			}
		} else {
			shot.setX(player.getX() + 20);
			shot.setY(player.getY()); 
		}
	}
	
	/**
	 * Draw the bomb from aliens.
	 * @param g graphics.
	 */
	private void drawBomb(Graphics g) {
		// Wait for flag.
		if (bombDropped) {
			if (bomb.getY() < 520) {			// If it reaches this height, stop animating.
				bomb.draw(g, this);
				bomb.setY(bomb.getY() + 10);
			} else {
				bombDropped = false;
			}
		}
	}
	
	/**
	 * Draw the 4 barriers.
	 * @param g graphics
	 */
	private void drawBarrier(Graphics g) {
		int x = 40;			// Initial x and y positions.
		int y = 450;
		// Check if barrier is created or not.
		if (!barrierCreated) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 3; j++) {
					for (int k = 0; k < 6; k++) {
						barrier[i][j][k] = new MoveableImage(x + 10 * k, y + 10 * j, barrier_img);
						barrier[i][j][k].setVisible(true);
					}
				}
				x += 150;	// Adjust x position.
			}
			barrierCreated = true;
		}
		// Draw created barriers if visible.
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < 6; k++) {
					if (barrier[i][j][k].getVisible()) {
						barrier[i][j][k].draw(g, this);
					}
				}
			}
		}
	}
	
	/**
	 * Create alien images based on received Board's alien positions and setVisible to true.
	 * @param i column
	 * @param j row
	 * @param x position
	 * @param y position
	 */
	public void createAliens(int i, int j, int x, int y) {
		aliens[i][j] = new MoveableImage(x, y, alien_img);
		aliens[i][j].setVisible(true);
	}
	
	/**
	 * Draw the aliens using the aliens fleet object.
	 * @param g graphics
	 */
	private void drawAliens(Graphics g) {
		// Check if aliens are created to avoid null pointer exception.
		// Check if game is won or over.
		if (aliensCreated && !gameWon && !gameOver) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 7; j++) {
					// If alien is set to visible, draw the alien.
					if (aliens[i][j].getVisible()) {
						aliens[i][j].draw(g, this);
					}
					// If alien is set to explode (hit) and still visible, draw exploding image.
					if (aliens[i][j].getExploding() && aliens[i][j].getVisible()) {
						aliens[i][j].draw(g, this);
						aliens[i][j].setVisible(false);
					}
					// If alien is exploding and no longer visible.
					if (aliens[i][j].getExploding()) {
						aliens[i][j].setExploding(false);
					}
				}
			}
			// Adjust the movement each frame. Check if player is alive. Else stop movement.
			if (aliensCreated && player.getVisible()) {
				for (int i = 0; i < 4; i++) {
					for (int j = 0; j < 7; j++) {
						// Use the edge variable to make sure the aliens never move off screen
						// and based on current direction, adjust direction.
						if(aliens[0][alien_edge[0]].getX() - alien_speed > alien_bound_left && alien_dir ||
						   aliens[0][alien_edge[1]].getX() + alien_speed < alien_bound_right && !alien_dir) {
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
	}
	
	/**
	 * Draw the score on the top left.
	 * @param g graphics
	 */
	private void drawGameScore(Graphics g) {
		g.setFont(new Font("Serif", Font.BOLD, 20));
		g.setColor(Color.white);
		g.drawString("SCORE: " + points, 10, 30);
	}
	
	/**
	 * Set format for the string used to display points.
	 * @param total points accumulated in game
	 */
	public void setPoints(int points) {
		this.points = String.format("%4d", points);
	}
	
	/**
	 * Draw the player lives to top right.
	 * @param g graphics
	 */
	private void drawGameLives(Graphics g) {
		g.setFont(new Font("Serif", Font.BOLD, 20));
		g.setColor(Color.white);
		g.drawString("LIVES: " + lives, 480, 30);
	}
	
	/**
	 * Set the number of lives to be displayed.
	 * @param total lives left of player
	 */
	public void setLives(int lives) {
		this.lives = String.format("%4d", lives);
	}
	
	/**
	 * Display if the game was won.
	 * @param g graphics
	 */
	private void drawGameWon(Graphics g) {
		g.setFont(new Font("Serif", Font.BOLD, 50));
		g.setColor(Color.white);
		g.drawString("YOU WIN!", 170, 200);
	}
	
	/**
	 * Display if the game is over.
	 * @param g graphics
	 */
	private void drawGameOver(Graphics g) {
		g.setFont(new Font("Serif", Font.BOLD, 50));
		g.setColor(Color.white);
		g.drawString("YOU LOSE!", 155, 200);
	}
	
	/**
	 * Set the bomb dropped flag.
	 * @param flag indicating if bomb is dropped or not
	 */
	public void setBombDropped(boolean flag) {
		this.bombDropped = flag;
	}
	
	/**
	 * Get the bomb dropped flag.
	 * @return flag indicating if bomb is dropped or not
	 */
	public boolean getBombDropped() {
		return this.bombDropped;
	}
	
	/**
	 * Set the bomb's position.
	 * @param x position
	 * @param y position
	 */
	public void setBombPosition(int x, int y) {
		this.bomb.setX(x);
		this.bomb.setY(y);
	}
	
	/**
	 * Set the shotFired flag.
	 * @param boolean flag
	 */
	public void setShotFired(boolean flag) {
		this.shotFired = flag;
	}
	
	/**
	 * Get the shotFired flag.
	 * @return boolean flag
	 */
	public boolean getShotFired() {
		return this.shotFired;
	}

	/**
	 * Reset the y position of the shot to the player's y position.
	 */
	public void resetShotPosition() {
		this.shot.setY(this.player.getY());
	}
	
	/**
	 * Set the visibility of barrier that is hit to false.
	 * @param i position in array
	 * @param j position in array
	 * @param k position in array
	 */
	public void setBarrierHit(int i, int j, int k) {
		barrier[i][j][k].setVisible(false);
	}
	
	/**
	 * Set/reset the barrier created flag for new game.
	 * @param flag indicating whether barrier is created or not
	 */
	public void setBarrierCreated(boolean flag) {
		this.barrierCreated = flag;
	}
	
	/**
	 * Set the player position with parameter.
	 * @param x position
	 */
	public void setPlayerPosition(int x) {
		this.player_x = x;
	}
	
	/**
	 * Set the player object visible flag.
	 * @param flag indicating the player is visible or not
	 */
	public void setPlayerVisible(boolean flag) {
		this.player.setVisible(flag);
	}
	
	/**
	 * Get the player object visible flag.
	 * @return flag indicating the player is visible or not
	 */
	public boolean getPlayerVisible() {
		return this.player.getVisible();
	}
	
	/**
	 * Set the player to explode, then pause the game, and reset.
	 * @param flag indicating the player is destroyed or not
	 */
	public void setPlayerExplode(boolean flag) {
		this.player.setExploding(flag);
		if (player.getExploding()) { 
			player.explode();			// Set player to explode, change image.
			player.setVisible(false);	// To stop movements on screen.
			shotFired = false;			// Remove any shots fired prior to player explode.
			try {
			    Thread.sleep(2000);		// Pause game for 2 seconds.
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			player.setVisible(true);	// Resume movements.
			player.reset(player_img);	// Reset player image.
		}
	}
	
	/**
	 * Set the flag for aliens being created
	 * @param flag indicating if alien is created or not
	 */
	public void setAliensCreated(boolean flag) {
		this.aliensCreated = flag;
	}
	
	/**
	 * Get the flag for aliens being created
	 * @return flag indicating if alien is created or not
	 */
	public boolean getAliensCreated() {
		return this.aliensCreated;
	}
	
	/**
	 * Set the flag for alien's being hit, ie. exploding.
	 * @param i column
	 * @param j row
	 */
	public void setAlienExplode(int i, int j) {
		this.aliens[i][j].explode();
	}
	
	/**
	 * Set the edges for the alien fleet.
	 * @param array of alien row/column information
	 */
	public void setAlienEdge(int[] transfer) {
		this.alien_edge = transfer;
	}
	
	/**
	 * Game over method that will pause the screen for a moment before going to
	 * the high score screen.
	 */
	public void gameOver() {
		gameOver = true;
		alien_dir = true;				// Reset alien direction.
		scoreWindow();
		try {
		    Thread.sleep(3000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		gameFrame.setVisible(false);
		scoreFrame.setVisible(true);
		gameOver = false;
	}
	
	/**
	 * Game won method that will pause the screen for a moment before starting a
	 * new game.
	 */
	public void gameWon() {
		gameWon = true;
		alien_dir = true;				// Reset alien direction for respawn.
		try {
		    Thread.sleep(5000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		gameWon = false;
	}
	
	/**
	 * Set the speed of the alien movement based on the current score.
	 * @param total kill number
	 */
	public void setSpeed(int score) {
		if (score == 15) { alien_speed = 2; }
		else if (score == 25) { alien_speed = 5; }
		else if (score == 27) { alien_speed = 8; }
		else if (score == 0) { alien_speed = 1; }
	}
	
	/**
	 * Update method to be sent to the Board that shares alien and shot positions.
	 * @param alien_x position
	 * @param alien_y position
	 * @param array to hold shot/bomb position
	 * @return shot position [0], bomb position [1]
	 */
	public void updateBoard(int[][] alien_x, int[][] alien_y, int[] shotbomb) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 7; j++) {
				alien_x[i][j] = aliens[i][j].getX();
				alien_y[i][j] = aliens[i][j].getY();
			}
		}
		shotbomb[0] = shot.getY();
		shotbomb[1] = bomb.getY();
	}
	
	/**
	 * Add listeners to detect key presses/releases.
	 */
	private void addListeners() {
		gameFrame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {	}

			@Override
			public void keyPressed(KeyEvent e) {
				if (player.getVisible()) {
					try {
						queue.put(new KeyPressedMessage(Integer.toString(e.getKeyCode())));
					} catch (InterruptedException exception) {
						exception.printStackTrace();
					}
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