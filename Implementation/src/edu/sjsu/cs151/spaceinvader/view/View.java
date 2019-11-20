package edu.sjsu.cs151.spaceinvader.view;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import javax.swing.*;

import edu.sjsu.cs151.spaceinvader.model.*;

public class View implements ActionListener {
	private Board board;

	private JFrame startFrame;   // Start screen frame.
	private JFrame gameFrame;    // Game screen frame.
	
	private JPanel startContent; // Start screen panel.
	private JPanel gameContent = new JPanel();  // Game screen panel.
	private JPanel infoContent = new JPanel();  // Score/lives panel during game.
	private JPanel scoreContent; // Score screen panel.
	private JPanel aliens;       //
	
	private JLabel gameName;     // Start screen game title.
	private JLabel gameLogo;     // Start screen alien logo.
	private JLabel playerScore;
	private JLabel playerLives;
	private JLabel player;
	private JLabel playerShot;
	private JLabel alienBomb;
	
	private JButton startGame;

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
		gameFrame.setBackground(new Color(0, 0, 255));
		gameFrame.setVisible(false);
		gameFrame.setResizable(false);
		gameFrame.setLayout(new BorderLayout());
		
		playerScore = new JLabel("   00000   ");
		playerLives = new JLabel("   00000   ");
		
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
		infoContent.add(playerLives);
		
		gameFrame.add(infoContent, BorderLayout.NORTH);
		gameFrame.add(gameContent, BorderLayout.CENTER);

		drawAliens();
		drawPlayer();
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

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
