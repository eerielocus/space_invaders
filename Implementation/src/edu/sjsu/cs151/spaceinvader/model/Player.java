package edu.sjsu.cs151.spaceinvader.model;

import java.awt.event.KeyEvent;
/**
 * Player class extends the Sprite class and contains the properties for
 * the player vehicle and methods to read key presses for movement and firing.
 *
 */
public class Player extends Sprite {
	private final int START_Y = 520;
	private final int START_X = 10;
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
		if (this.x + x > 5 && this.x + x <= 525) {
			this.x += x;	
		}
	}
}
