package edu.sjsu.cs151.spaceinvader.model;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;

/**
 * Board (Model) stores all information about the current state of the game and handles
 * collision detection and player movement.
 */
public class Board extends Game{
    private static final int BOMB_HEIGHT = 40;
    private static final int ALIEN_HEIGHT = 40;
    private static final int PLAYER_HEIGHT = 40;
    private static final int NUMBER_OF_ALIENS_TO_DESTROY = 28;
    private static final int CHANCE = 5;
    private static final int ALIEN_INIT_X = 210;
    private static final int ALIEN_INIT_Y = 75;
    
	private boolean keyMap[];			// Boolean map of keys pressed down.
	private boolean gameOver = false;	// Game over flag.
	private boolean gameWon = false;	// Game won flag.
	private boolean chanceRoll = false;	// Alien bomb drop chance roll flag.
	private boolean bombDrop = false;	// Alien bomb dropped flag.
	private int lives;					// Total number of player lives.
	private int level;					// Current level modifier.
	private int score;					// Current game score.
	private int total_score;			// Total score.
	private int[] edges; 				// Contains edges of alien fleet: int[0] = left, int[1] = right, int[2] = bottom
	private int[] lowest;
	private Alien aliens[][];			// Alien fleet.
	private Player player;				// Player.
	private Shot shot;					// Player shot.
	private Bomb bomb;					// Alien bomb.
	private Random random = new Random();

	public Board() { }
	
	/**
	 * Start a new game, set all variables to proper settings.
	 */
	@Override
	public void start() {
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
	@Override
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
		getEdgeAliens();
	}
	
	/**
	 * Next level if game was previously won.
	 */
	@Override
	public void nextGame() {
		if (this.level <= 100) { this.level += 50; }
		this.score = 0;
		this.lives = 3;
		this.gameWon = false;
		this.keyMap = new boolean[256];
		createAliens();
		createPlayer();
		getEdgeAliens();
	}
	
	/**
	 * Board update method to send information to View.
	 */
	public void update() {
		movePlayer();			// Update player position and shot trigger.
		getLowestAliens();		// Update lowest positioned alien in fleet.
		dropBomb();				// Update dropping of bomb.
		gameStatus();			// Update score/lives status of game.
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
	 * @return array of aliens
	 */
	public Alien[][] getAliens() {
		return this.aliens;
	}
	
	/**
	 * Find the lowest position alien per column.
	 * @return array containing lowest positioned alien's row number
	 */
	private void getLowestAliens() {
		// Initiate array with -1 to allow for checks.
		Arrays.fill(this.lowest, -1);
		int row = 3;	// Start from bottom.
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
	 * @return array containing edge row/column information
	 */
	public int[] getEdgeAliens() {
		boolean edge = true;
		int j = 0;	// Column variable.
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
			j = 6;	// Column variable starting from right.
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
			j = 3;	// Row variable starting from bottom.
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
	 * @return player object
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
		// If player is alive, move based on key pressed.
		if (player.isVisible()) {
			if (leftKey && !rightKey) { player.act(-1);	} 
			else if (!leftKey && rightKey) { player.act(1);	}
		}
		// If shot is not on screen, and space is pressed, fire the cannon!
		if (spaceKey && !shot.isVisible()) { shot.setVisible(true); }
	}
	
	/**
	 * Randomized alien bomb generator. Chooses alien selected from lowest position
	 * and rolls a chance to drop.
	 */
	private void dropBomb() {
		if (bombDrop) {
			int drop = random.nextInt(30);	// Randomized chance for bomb drop.
			int ship = random.nextInt(6);	// Randomized alien to drop bomb.
			// Check the chance and if bomb is visible and the selected 'lowest' ship is not destroyed.
			if (drop == CHANCE && !bomb.isVisible() && lowest[ship] != -1) {
				bomb.setX(aliens[lowest[ship]][ship].getX());
				bomb.setY(aliens[lowest[ship]][ship].getY() + BOMB_HEIGHT);
				bomb.setVisible(true);
				chanceRoll = true;
			}
		}
	}
	
	/**
	 * Collision package to be sent to view.
	 * @return alien object containing information for view display.
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
						if (shot.getX() >= aliens[i][j].getX() - 5 &&
							shot.getX() <= aliens[i][j].getX() + 35 &&
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
			if (bomb.getX() >= player.getX() - 18 &&
				bomb.getX() <= player.getX() + 15 &&
				bomb.getY() >= player.getY() &&
				bomb.getY() <= player.getY() + PLAYER_HEIGHT) {
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
	 * @return flag that indicates whether game is won or not
	 */
	public boolean getGameWon() {
		return gameWon;
	}
	
	/**
	 * Get the flag for gameOver.
	 * @return flag that indicates whether game is over or not
	 */
	public boolean getGameOver() {
		return gameOver;
	}
	
	/**
	 * Get the game's score.
	 * @return total number of aliens destroyed
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Get the game's total points. Per alien kill = 10 points.
	 * @return total points accumulated in the game
	 */
	public int getPoints() {
		return total_score * 10;
	}
	
	/**
	 * Get the game's total lives.
	 * @return number of player lives left
	 */
	public int getLives() {
		return lives;
	}
	
	/**
	 * Get the shot object to access properties.
	 * @return player shot object
	 */
	public Shot getShot() {
		return this.shot;
	}
	
	/**
	 * Get the bomb object.
	 * @return alien bomb object
	 */
	public Bomb getBomb() {
		return this.bomb;
	}
	
	/**
	 * Set the flag for bomb dropping.
	 * @param flag indicating if the bomb is dropped or not
	 */
	public void setBombDrop(boolean flag) {
		this.bombDrop = flag;
	}
	
	/**
	 * Set the chance flag for rolling on random alien bombing.
	 * @param flag indicating if the random roll for alien attack is done or not
	 */
	public void setChance(boolean flag) {
		this.chanceRoll = flag;
	}
	
	/**
	 * Get the chance flag for random alien bombing.
	 * @return flag indicating if random roll has been rolled or not
	 */
	public boolean getChance() {
		return this.chanceRoll;
	}
	
	/**
	 * Sets the key pressed, if within the standard keyboard, to true.
	 * @param key event pressed as an integer
	 */
	public void setKeyDown(int key) {
		if(key > 255) { return; }
		this.keyMap[key] = true;
	}

	/**
	 * Set the key released, meaning set the location in keyMap to false.
	 * @param key event pressed as an integer
	 */
	public void setKeyUp(int key) {
		if(key > 255) { return; }
		this.keyMap[key] = false;
	}
	
	/**
	 * Check the keyMap to see if the key is still pressed down or not.
	 * @param key event pressed as an integer
	 * @return flag indicating if key is pressed or not
	 */
	public boolean isKeyPressed(int key) {
		if (key > 255) { return false; }
		return this.keyMap[key];
	}
}
