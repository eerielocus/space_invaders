package edu.sjsu.cs151.spaceinvader.model;

import java.awt.event.KeyEvent;
/**
 * Player class extends the Sprite class and contains the properties for
 * the player vehicle and methods to read key presses for movement and firing.
 *
 */
public class Player extends Sprite {
	private int width;
	private final int START_Y = 300;
	private final int START_X = 200;
	/**
	 * Constructor method for Player.
	 */
	public Player() {
		createPlayer();
	}
	/**
	 * This method sets the correct image and start position for player.
	 */
	public void createPlayer() {
		width = 5; // Placeholder until implementation of graphics.
		setX(START_X);
		setY(START_Y);
	}
	/**
	 * This method sets the movement of the player.
	 */
	public void act() {
		x += move_x;	
	}
	/**
	 * This method takes the user key press and translates into an integer
	 * to use for movement.
	 */
	public void keyPressed(KeyEvent button) {
		int key = button.getKeyCode();
		
		if (key == KeyEvent.VK_LEFT) {
			move_x = -2;	
		}
		
		if (key == KeyEvent.VK_RIGHT) {
			move_x = 2;		
		}    
	}
}
