package edu.sjsu.cs151.spaceinvader.model;

import edu.sjsu.cs151.spaceinvader.adapter.GameInterface;
import java.awt.event.KeyEvent;
/**
 * Player class extends the Sprite class and contains the properties for
 * the player vehicle and methods to read key presses for movement and firing.
 *
 */
public class Player extends Sprite implements GameInterface {
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

		if (x <= 2) {
			x = 2;	
		}
		
		if (x >= BOARD_WIDTH - 2 * width) {
			x = BOARD_WIDTH - 2 * width;
		}	
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
	
	@Override
	public void initGame() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void initScreen() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void start() throws InterruptedException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void exit() {
		// TODO Auto-generated method stub
		
	}
}
