package edu.sjsu.cs151.spaceinvader.model;

import edu.sjsu.cs151.spaceinvader.adapter.GameInterface;
import java.util.ArrayList;

public class Board implements GameInterface{
	
	private ArrayList<Alien> aliens;
	private String gameOverMessage = "Game Over";
	private Shot shot;
	private Player player;
    
	public Board() {
		createGame();
	}
	/**
	 * Initializing method to add the Player and Aliens, and begin animations.
	 */
	public void createGame() {
		player = new Player();
		shot = new Shot();
		aliens = new ArrayList<>();
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 6; j++) {
				Alien alien = new Alien(ALIEN_INIT_X + 15 * j, ALIEN_INIT_Y + 15 *i);
				aliens.add(alien);
			}
		}
		//GUI Implementation below 
	}
	public String gameover() {
		return this.gameOverMessage;
	}


	/***********************************************************
	 * GUI Implementation for the methods below, NOT IMPLEMENTED 
	 ************************************************************/
	/**
	 * Initializing method to set board dimensions, color, and key listeners.
	 */
	public void createBoard() {
		//In this method Graphical Interface will be used, not implemented 
		
	}
	/**
	 * This method displays the sprite for the Player when alive, and hides sprite when dead.
	 */
	public void drawPlayer() {
	}
	/**
	 * This method displays the sprite for the Alien when alive, and hides sprite when destroyed.
	 */
	public void drawAlien() {
	}
	/**
	 * This method displays the sprite for the cannon shot.
	 */
	public void drawShot() {
	}
	/**
	 * This method controls the movement of the Player based on key presses.
	 */
	public void move(){
	}
	/**
	 * This method calls on the draw methods and color properties for the graphics.
	 */
	public void paintComponents() {
	}
	/**
	 * This method handles the actions involved in moving both player and alien,
	 * movements for both and shots/bombs fired, events when shots hit alien or 
	 * bomb hits player.
	 */
	public void animationCycle() {
	}
	
	
	@Override
	public void initGame() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void initScreen() {
		// TODO Auto-generated method stub
		
	}
}


