package edu.sjsu.cs151.spaceinvader.model;
/**
 * Player class implements the Sprite interface and contains the properties for
 * the player vehicle and methods to read key presses for movement and firing.
 */
public class Player implements Sprite {
	private final int START_Y = 520;
	private final int START_X = 10;
	private int x;						// X position.
	private int y;						// Y position.
	private boolean visible = false;	// Visible flag.
	
	/**
	 * Constructor method for Player.
	 */
	protected Player() {
		initPlayer();
	}
	
	/**
	 * This method sets the correct image and start position for player.
	 */
	protected void initPlayer() {
		setX(START_X);
		setY(START_Y);
	}
	
	/**
	 * This method sets the movement of the player.
	 */
	public void act(int x) {
		if (this.x + x > 5 && this.x + x <= 545) {
			this.x += x;	
		}
	}

	/**
	 * This method is to return whether the object is visible or not. (destroyed or not)
	 * @return visible the visibility of the Sprite
	 */
	public boolean isVisible() {
		return visible;
	}
	
	/**
	 * This method is to set the visibility of object.
	 * @param visible flag indicating whether object is visible or not
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
    
	/**
	 * This method is to set the object as dead.
	 */
	public void dead() {
		this.visible = false;
	}

	/**
	 * This method is to set the X-position of object.
	 * @param x position on x-axis
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * This method is to set the Y-position of object.
	 * @param y position on y-axis
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * This method is to get the X-position of object.
	 * @return x the x-position
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * This method is to get the Y-position of object.
	 * @return y the y-position
	 */
	public int getY() {
		return this.y;
	}
}
