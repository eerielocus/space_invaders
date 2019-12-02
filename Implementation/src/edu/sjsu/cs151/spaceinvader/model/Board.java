package edu.sjsu.cs151.spaceinvader.model;

import java.awt.event.KeyEvent;

//This class is a singleton
public class Board {
    private static final int BOMB_HEIGHT = 5;
    private static final int ALIEN_HEIGHT = 40;
    private static final int ALIEN_WIDTH = 40;
    private static final int NUMBER_OF_ALIENS_TO_DESTROY = 28;
    private static final int CHANCE = 5;
    private static final int DELAY = 17;
    private static final int PLAYER_WIDTH = 15;
    private static final int PLAYER_HEIGHT = 10;
    private static final int ALIEN_INIT_X = 210;
    private static final int ALIEN_INIT_Y = 75;
    
	private boolean keyDownMap[];
	private boolean gameOver = false;
	private int score;
	private Alien aliens[][];
	private Player player;
	private Shot shot;

	public Board() {
		this.keyDownMap = new boolean[256];
		this.score = 0;
		this.shot = new Shot();
		this.aliens = new Alien[4][7];
	}
	
	public void newGame() {
		this.score = 0;
		this.gameOver = false;
	}
	
	public boolean update() {
		movePlayer();
		return gameOver();
	}
	
	/**
	 * Creates Aliens and Bomb
	 */
	public void createAliens() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 7; j++) {
				aliens[i][j] = new Alien(ALIEN_INIT_X + 50 * j, ALIEN_INIT_Y + 50 * i);
				aliens[i][j].setVisible(true);
				aliens[i][j].setPositionIJ(i, j);
			}
		}
		System.out.println("Aliens created.\n");
	}
	
	public Alien[][] getAliens() {
		return this.aliens;
	}
	
	public int[] getEdgeAliens() {
		boolean edge = true;
		int[] edges = new int[3];
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
			j = 0;
			// Bottom edge of fleet.
			while (edge) {
				for (int i = 0; i < 7; i++) {
					if (aliens[j][i].isVisible()) {
						edges[2] = j;
						edge = false;
						break;
					}
				}
				j++;
			}
		}
		return edges;
	}

	/**
	 * Creates Player (not fully implemented until GUI)
	 */
	public void createPlayer() {
		this.player = new Player();
	}
	
	public Player getPlayer() {
		return player;
	}
	
	private void movePlayer() {
		boolean leftKey = this.isKeyPressed(KeyEvent.VK_LEFT);
		boolean rightKey = this.isKeyPressed(KeyEvent.VK_RIGHT);
		boolean spaceKey = this.isKeyPressed(KeyEvent.VK_SPACE);
		
		if (leftKey && !rightKey) {
			if (spaceKey && !shot.isVisible()) {
				player.act(-5);
				shot.setVisible(true);
			} else {
				player.act(-5);
			}
		} else if (!leftKey && rightKey) {
			if (spaceKey && !shot.isVisible()) {
				player.act(5);
				shot.setVisible(true);
			} else {
				player.act(5);
			}
		}
		
		if (spaceKey && !shot.isVisible()) {
			shot.setVisible(true);
		}
	}
	
	public Alien collision() {
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
								return aliens[i][j];
						}
					}
				}
			}
		}
		return null;
	}
	
	private boolean gameOver() {
		if (score == NUMBER_OF_ALIENS_TO_DESTROY) {
			gameOver = true;
			return gameOver;
		}
		return gameOver;
	}
	
	public boolean getGameOver() {
		return gameOver;
	}
	
	public int getScore() {
		return score;
	}
	
	public Shot getShot() {
		return this.shot;
	}
	
	public void setKeyDown(int key) {
		if(key > 255) { return; }
		this.keyDownMap[key] = true;
	}

	public void setKeyUp(int key) {
		if(key > 255) { return; }
		this.keyDownMap[key] = false;
	}
	
	public boolean isKeyPressed(int key) {
		if (key > 255) { return false; }
		return this.keyDownMap[key];
	}
}
