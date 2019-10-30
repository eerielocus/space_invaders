package edu.sjsu.cs151.spaceinvader.model;

/**
 * Shot class extends the Sprite class and contains the methods and
 * properties to set, move and destroy the Alien object.
 *
 */
public class Shot extends Sprite {
	/**
	 * Constructor method for Shot.
	 */
	public Shot() {
		
	}
	
	public Shot(int x, int y) {
		initShot(x, y);
	}
	/**
	 * This method sets the correct image and position of the shot.
	 * @param x position on x-axis
	 * @param y position on y-axis
	 */
	public void initShot(int x, int y) {
		setX(x);
		setY(y);
	}
}

