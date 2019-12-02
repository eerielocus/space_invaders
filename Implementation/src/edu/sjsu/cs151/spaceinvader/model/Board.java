package edu.sjsu.cs151.spaceinvader.model;

import java.awt.event.KeyEvent;

//This class is a singleton
public class Board {
    public static final int THREAD_SLEEP_TIME = 800;
    public static final int BOMB_HEIGHT = 5;
    public static final int ALIEN_HEIGHT = 50;
    public static final int ALIEN_WIDTH = 50;
    public static final int NUMBER_OF_ALIENS_TO_DESTROY = 24;
    public static final int CHANCE = 5;
    public static final int DELAY = 17;
    public static final int PLAYER_WIDTH = 15;
    public static final int PLAYER_HEIGHT = 10;
    public static final int ALIEN_INIT_X = 210;
    public static final int ALIEN_INIT_Y = 75;
    
	private boolean keyDownMap[];
	private Alien aliens[][] = new Alien[4][7];
	private Player player;
	private Shot shot = new Shot();

	public Board() {
		this.keyDownMap = new boolean[256];
	}
	
	public void update() {
		movePlayer();
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
			//System.out.println("In.");
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 7; j++) {
					if (aliens[i][j].isVisible() && shot.isVisible()) {
						if (shot.getX() >= aliens[i][j].getX() &&
							shot.getX() <= aliens[i][j].getX() + ALIEN_WIDTH &&
							shot.getY() >= aliens[i][j].getY() &&
							shot.getY() <= aliens[i][j].getY() + ALIEN_HEIGHT) {
								aliens[i][j].dead();
								shot.dead();
								System.out.println("Hit.");
								return aliens[i][j];
						}
					}
				}
			}
		}
		return null;
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
		System.out.println("Up." + key);
	}
	
	public boolean isKeyPressed(int key) {
		if (key > 255) { return false; }
		return this.keyDownMap[key];
	}
}
