package edu.sjsu.cs151.spaceinvader.model;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;

public class Board {
    private static final int BOMB_HEIGHT = 40;
    private static final int ALIEN_HEIGHT = 40;
    private static final int ALIEN_WIDTH = 45;
    private static final int NUMBER_OF_ALIENS_TO_DESTROY = 28;
    private static final int CHANCE = 5;
    private static final int PLAYER_WIDTH = 45;
    private static final int PLAYER_HEIGHT = 45;
    private static final int ALIEN_INIT_X = 210;
    private static final int ALIEN_INIT_Y = 75;
    
	private boolean keyMap[];			// Boolean map of keys pressed down.
	private boolean gameOver = false;
	private boolean gameWon = false;	
	private boolean chanceRoll = false;
	private int lives;
	private int level;					// Current level modifier.
	private int score;					// Current game score.
	private int total_score;			// Total score.
	private int[] edges; 				// Contains edges of alien fleet: int[0] = left, int[1] = right, int[2] = bottom
	private int[] lowest;
	private Alien aliens[][];			// Alien fleet.
	private Player player;				// Player.
	private Shot shot;					// Player shot.
	private Bomb bomb;
	private Random random = new Random();

	public Board() {
		this.keyMap = new boolean[256];
		this.score = 0;
		this.level = 0;
		this.lives = 3;
		this.total_score = 0;
		this.shot = new Shot();
		this.bomb = new Bomb();
		this.aliens = new Alien[4][7];
		this.edges = new int[3];
		this.lowest = new int[7];
	}
	
	/**
	 * New game method that creates new alien/player and resets score/flags.
	 */
	public void newGame() {
		this.level = 0;
		this.score = 0;
		this.lives = 3;
		this.total_score = 0;
		this.gameOver = false;
		this.gameWon = false;
		this.keyMap = new boolean[256];
		createAliens();
		createPlayer();
	}
	/**
	 * Next level if game was previously won.
	 */
	public void nextGame() {
		if (this.level <= 100) { this.level += 50; }
		this.score = 0;
		this.gameWon = false;
		this.keyMap = new boolean[256];
		createAliens();
	}
	
	/**
	 * Board update method to send information to View.
	 */
	public void update() {
		movePlayer();
		getLowestAliens();
		dropBomb();
		gameStatus();
	}
	
	/**
	 * Creates aliens and sets properties for each.
	 */
	private void createAliens() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 7; j++) {
				aliens[i][j] = new Alien(ALIEN_INIT_X + 50 * j, ALIEN_INIT_Y + level + 50 * i);
				aliens[i][j].setVisible(true);
				aliens[i][j].setPositionIJ(i, j);
			}
		}
	}

	/**
	 * Get the fleet of aliens.
	 * @return 2D array of aliens
	 */
	public Alien[][] getAliens() {
		return this.aliens;
	}
	
	/**
	 * Find the lowest position alien per column.
	 * @return int[] lowest
	 */
	private void getLowestAliens() {
		Arrays.fill(this.lowest, -1);
		int row = 3;
		while (row != -1) {
			for (int i = 0; i < 7; i++) {
				if (aliens[row][i].isVisible() && lowest[i] == -1) {
					lowest[i] = row;
				}
			}
			row--;
		}
	}
	
	/**
	 * Get the left/right/bottom edges of the alien fleet to determine where the left,
	 * right, and bottom most alien resides.
	 * 
	 * @return int[] contains edge row/column information
	 */
	public int[] getEdgeAliens() {
		boolean edge = true;
		int j = 0;
		if (score != NUMBER_OF_ALIENS_TO_DESTROY) {
			// Left edge of fleet.
			while (edge) {
				for (int i = 0; i < 4; i++) {
					if (aliens[i][j].isVisible()) {
						edges[0] = j;
						edge = false;
						break;
					}
				}
				j++;
			}
			edge = true;
			j = 6;
			// Right edge of fleet.
			while (edge) {
				for (int i = 0; i < 4; i++) {
					if (aliens[i][j].isVisible()) {
						edges[1] = j;
						edge = false;
						break;
					}
				}
				j--;
			}
			edge = true;
			j = 3;
			// Bottom edge of fleet.
			while (edge) {
				for (int i = 0; i < 7; i++) {
					if (aliens[j][i].isVisible()) {
						edges[2] = j;
						edge = false;
						break;
					}
				}
				j--;
			}
		}
		return edges;
	}

	/**
	 * Creates player object.
	 */
	private void createPlayer() {
		this.player = new Player();
		this.player.setVisible(true);
	}
	
	/**
	 * Gets the player object.
	 * @return player
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Moves the player based on the key pressed down. Checks using isKeyPressed
	 * method to find whether a key is still 'down'.
	 */
	private void movePlayer() {
		// Check for each appropriate key to see if pressed.
		boolean leftKey = this.isKeyPressed(KeyEvent.VK_LEFT);
		boolean rightKey = this.isKeyPressed(KeyEvent.VK_RIGHT);
		boolean spaceKey = this.isKeyPressed(KeyEvent.VK_SPACE);
		
		if (player.isVisible()) {
			if (leftKey && !rightKey) { player.act(-1);	} 
			else if (!leftKey && rightKey) { player.act(1);	}
		}
		
		if (spaceKey && !shot.isVisible()) { shot.setVisible(true); }
	}
	
	/**
	 * Randomized alien bomb generator. Chooses alien selected from lowest position
	 * and rolls a chance to drop.
	 */
	private void dropBomb() {
		int drop = random.nextInt(30);
		int ship = random.nextInt(6);
		if (drop == CHANCE && !bomb.isVisible() && lowest[ship] != -1) {
			bomb.setX(aliens[lowest[ship]][ship].getX());
			bomb.setY(aliens[lowest[ship]][ship].getY() + BOMB_HEIGHT);
			bomb.setVisible(true);
			chanceRoll = true;
		}
	}
	
	/**
	 * Collision package to be sent to view.
	 * @return Alien object containing information for view display.
	 */
	public Alien collision() {
		playerCollision();
		return alienCollision();
	}
	
	/**
	 * Checks whether the player shot hits an alien.
	 * @return alien object that is hit
	 */
	private Alien alienCollision() {
		if (shot.isVisible()) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 7; j++) {
					if (aliens[i][j].isVisible() && shot.isVisible()) {
						if (shot.getX() >= aliens[i][j].getX() &&
							shot.getX() <= aliens[i][j].getX() + ALIEN_WIDTH &&
							shot.getY() >= aliens[i][j].getY() &&
							shot.getY() <= aliens[i][j].getY() + ALIEN_HEIGHT) {
								aliens[i][j].dead();
								shot.dead();
								score++;
								total_score++;
								return aliens[i][j];
						}
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Player collision with alien bomb.
	 */
	private void playerCollision() {
		if (bomb.isVisible()) {
			if (bomb.getX() >= player.getX() - 15 &&
				bomb.getX() <= player.getX() + 15 &&
				bomb.getY() >= player.getY() &&
				bomb.getY() <= player.getY() + 40) {
				if (lives > 1) {
					bomb.dead();
					player.dead();
					player.setX(10);
					lives--;
				} else {
					lives--;
					player.dead();
				}
			}
		}
	}
	
	/**
	 * Sets the gameWon/Over flag based on the score or alien position.
	 */
	private void gameStatus() {
		if (score == NUMBER_OF_ALIENS_TO_DESTROY) { gameWon = true; }
		if (aliens[edges[2]][0].getY() >= 450) { gameOver = true; }
	}
	
	/**
	 * Get the flag for gameWon.
	 * @return boolean gameWon
	 */
	public boolean getGameWon() {
		return gameWon;
	}
	
	/**
	 * Get the flag for gameOver.
	 * @return boolean gameOver
	 */
	public boolean getGameOver() {
		return gameOver;
	}
	
	/**
	 * Get the game's score.
	 * @return int score
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Get the game's total points. Per alien kill = 10 points.
	 * @return int score
	 */
	public int getPoints() {
		return total_score * 10;
	}
	
	/**
	 * Get the game's total lives.
	 * @return
	 */
	public int getLives() {
		return lives;
	}
	
	/**
	 * Get the shot object to access properties.
	 * @return shot object
	 */
	public Shot getShot() {
		return this.shot;
	}
	
	/**
	 * Get the bomb object.
	 * @return bomb object
	 */
	public Bomb getBomb() {
		return this.bomb;
	}
	
	/**
	 * Set the chance flag for rolling on random alien bombing.
	 * @param flag
	 */
	public void setChance(boolean flag) {
		this.chanceRoll = flag;
	}
	
	/**
	 * Get the chance flag for random alien bombing.
	 * @return boolean chanceRoll
	 */
	public boolean getChance() {
		return this.chanceRoll;
	}
	/**
	 * Sets the key pressed, if within the standard keyboard, to true.
	 * @param keyEvent key
	 */
	public void setKeyDown(int key) {
		if(key > 255) { return; }
		this.keyMap[key] = true;
	}

	/**
	 * Set the key released, meaning set the location in keyMap to false.
	 * @param keyEvent key
	 */
	public void setKeyUp(int key) {
		if(key > 255) { return; }
		this.keyMap[key] = false;
	}
	
	/**
	 * Check the keyMap to see if the key is still pressed down or not.
	 * @param keyEvent key
	 * @return boolean true/false
	 */
	public boolean isKeyPressed(int key) {
		if (key > 255) { return false; }
		return this.keyMap[key];
	}
}
