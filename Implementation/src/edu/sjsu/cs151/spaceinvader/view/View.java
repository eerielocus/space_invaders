package edu.sjsu.cs151.spaceinvader.view;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;

import edu.sjsu.cs151.spaceinvader.model.*;

public class View implements ActionListener {
	private Board board;

	private JFrame startFrame;   // Start screen frame.
	private JFrame gameFrame;    // Game screen frame.
	private JFrame scoreFrame;
	
	private JPanel startContent; // Start screen panel.
	private JPanel gameContent = new JPanel();  // Game screen panel.
	private JPanel infoContent = new JPanel();  // Score/lives panel during game.
	private JPanel scoreContent = new JPanel(); // Score screen panel.
	private JPanel aliens;
	
	private JLabel gameName;     // Start screen game title.
	private JLabel gameLogo;     // Start screen alien logo.
	private JLabel playerScore;
	private JLabel playerLives;
	private JLabel player;
	private JLabel playerShot;
	private JLabel alienBomb;
	private JLabel scoreListName;
	
	private JButton startGame;
	private JButton exitGame;
	private JButton retGame;

	private MoveableShape logoAlien = new AlienShape(0, 0, 100);
	private ShapeIcon iconAlien = new ShapeIcon(logoAlien, 400, 100);
	private ImageIcon logo = new ImageIcon("src/edu/sjsu/cs151/spaceinvader/view/title.gif");
	
	private GridBagConstraints con = new GridBagConstraints();
	
	public View(Board board) {
		this.board = board;
		start();
	}

	public void start() {
		startWindow();
		gameWindow();
		scoreWindow();
	}
	
	/**
	 * Start screen window, what the game initially opens with. Contains
	 * game name, icon, and start button.
	 */
	private void startWindow() {
		startFrame = new JFrame("Space Invaders");
		startFrame.setSize(600, 600);
		startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startFrame.setVisible(true);
		startFrame.setResizable(false);
		
		startContent = new JPanel();
		startContent.setBackground(Color.black);
		gameName = new JLabel(logo);
		gameLogo = new JLabel(iconAlien);
		startGame = new JButton("Start Game");
		
		startGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
	
	/**
	 * Game screen window, contains score and player lives and the game itself.
	 */
	private void gameWindow() {
		gameFrame = new JFrame("Space Invaders");
		gameFrame.setSize(600, 600);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setBackground(Color.black);
		gameFrame.setVisible(false);
		gameFrame.setResizable(false);
		gameFrame.setLayout(new BorderLayout());
		
		playerScore = new JLabel("   00000   ");
		playerLives = new JLabel("   00000   ");
		exitGame = new JButton("Exit Game");
		
		exitGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameFrame.setVisible(false);
				scoreFrame.setVisible(true);
			}	
		});
		
		gameContent.setBackground(Color.black);
		gameContent.setLayout(new BorderLayout());
		
		infoContent.setBackground(Color.white);
		infoContent.setLayout(new BoxLayout(infoContent, BoxLayout.X_AXIS));
		infoContent.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		playerScore.setBorder(BorderFactory.createTitledBorder("SCORE"));
		playerLives.setBorder(BorderFactory.createTitledBorder("LIVES"));
		playerScore.setAlignmentX(Component.LEFT_ALIGNMENT);
		playerLives.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		infoContent.add(playerScore);
		infoContent.add(Box.createHorizontalGlue());
		infoContent.add(exitGame);
		infoContent.add(Box.createHorizontalGlue());
		infoContent.add(playerLives);
		
		gameFrame.add(infoContent, BorderLayout.NORTH);
		gameFrame.add(gameContent, BorderLayout.CENTER);

		drawAliens();
		drawPlayer();
	}
	
	private void scoreWindow() {
		scoreFrame = new JFrame("Space Invaders");
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
	/**
	 * Draw player onto gameContent panel.
	 */
	private void drawPlayer() {
		MoveableShape logoPlayer = new PlayerShape(60, 2, 40);
		ShapeIcon iconPlayer = new ShapeIcon(logoPlayer, 50, 70);
		player = new JLabel(iconPlayer, JLabel.CENTER);
		gameContent.add(player, BorderLayout.SOUTH);
	}
	/**
	 * Draw aliens onto gameContent panel.
	 */
	private void drawAliens() {
		// Temporary until game implementation.
		JPanel container = new JPanel();
		container.setOpaque(false);
		
		aliens = new JPanel();
		aliens.setOpaque(false);
		aliens.setLayout(new GridLayout(4, 7, 2, 2));
		for (int i = 0; i < 28; i++) {
			MoveableShape enemy = new AlienShape(0, 0, 40);
			ShapeIcon enemyIcon = new ShapeIcon(enemy, 60, 40);
			JLabel enemyLabel = new JLabel(enemyIcon);
			aliens.add(enemyLabel);
		}
		container.add(aliens);
		gameContent.add(container, BorderLayout.NORTH);
	}
	/**
	 * Score board window that takes data from file provided in Model package and
	 * displays them on a grid.
	 */
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
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		scoreboard.setAlignmentX(Component.CENTER_ALIGNMENT);
		scoreContent.add(scoreboard);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
