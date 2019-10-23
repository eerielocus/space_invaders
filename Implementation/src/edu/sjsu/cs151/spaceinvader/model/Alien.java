package edu.sjsu.cs151.spaceinvader.model;

/**
 * Alien class extends the Sprite class and contains the necessary methods
 * and properties to provide action for the Alien object.
 *
 */
public class Alien extends Sprite {
	/**
	 * Constructor method for Alien.
	 */
	public Alien(int x, int y) {
		createAlien(x, y);
	}
	/**
	 * Initializing method to set position, set correct image, and create a bomb.
	 * @param x position on x-axis
	 * @param y position on y-axis
	 */
	public void createAlien(int x, int y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * This method sets the movement of the Alien.
	 */
	public void act(int move) {
		this.x += move;
	}
	/**
	 * This method creates a new bomb for the Alien.
	 */
	public static Bomb getBomb() {
		return new Bomb();
	}
}
