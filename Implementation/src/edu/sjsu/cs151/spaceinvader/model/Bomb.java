package edu.sjsu.cs151.spaceinvader.model;

/**
 * Bomb class extends the Sprite class and contains the methods and
 * properties to set, move and destroy the player object.
 *
 */
public class Bomb extends Sprite {
	/**
	 * Constructor method for Bomb.
	 */
	public Bomb() { }
	
	public Bomb(int x, int y) {
		initShot(x, y);
	}
	
	/**
	 * This method sets the correct image and position of the bomb.
	 * @param x position on x-axis
	 * @param y position on y-axis
	 */
	public void initShot(int x, int y) {
		setX(x);
		setY(y);
	}
}

