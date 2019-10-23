package edu.sjsu.cs151.spaceinvader.model;


import java.util.ArrayList;

import org.junit.Test;
public class Board {
	
	private ArrayList<Alien> aliens;
	private String gameOverMessage = "Game Over";
	private Player player;

    private final int ALIEN_INIT_X = 150;
    private final int ALIEN_INIT_Y = 5;
  //Modified class Alien is declared inside board class for testing purposes
	public class Alien{
		private int x = 0;
		private int y = 0;
		public Alien(int x ,int y) {
			this.x = x;
		    this.y = y;
		}
		@SuppressWarnings("unused")
		public int getX() {
			return this.x;
		}
		@SuppressWarnings("unused")
		public int getY() {
			return this.y;
		}
	}
	//Modified class Player is declared inside board class for testing purposes
	private class Player{
		public Player() {
			System.out.println("Testing create createPlayer");
		}
	}
	public Board() {
		createGame();
	}
	/**
    Initializing method to add the Player and Aliens, and begin animations.
	 */
	public void createPlayer() {
		this.player = new Player();
	}
	public void createAlien() {
		 aliens = new ArrayList<>();
		 for (int i = 0; i < 4; i++) {
			 for (int j = 0; j < 6; j++) {
				 Alien alien = new Alien(ALIEN_INIT_X + 18 * j, ALIEN_INIT_Y + 18 * i);
	             aliens.add(alien);
	         }
	     }
	}
	public void createGame() {
		createAlien();
		createPlayer();
		//GUI Implementation below 
	}
	public String gameover() {
		return this.gameOverMessage;
	}
	//test methods
	@Test
	public Player getPlayer() {
		return this.player;
	}
	@Test
	public ArrayList<Alien> getAliens() {
		return this.aliens;
	}

	
	
	
	
	/***********************************************************
	 * GUI Implementation for the methods below, NOT IMPLEMENTED 
	 ************************************************************/
	/**
    Initializing method to set board dimensions, color, and key listeners.
	 */
	public void createBoard() {
		//In this method Graphical Interface will be used, not implemented 
		
	}
	/**
    This method displays the sprite for the Player when alive, and hides sprite when dead.
	 */
	public void drawPlayer() {
	}
	/**
    This method displays the sprite for the Alien when alive, and hides sprite when destroyed.
	 */
	public void drawAlien() {
	}
	/**
    This method displays the sprite for the cannon shot.
	 */
	public void drawShot() {
	}
	/**
    This method controls the movement of the Player based on key presses.
	 */
	public void move(){
	}
	/**
    This method calls on the draw methods and color properties for the graphics.
	 */
	public void paintComponents() {
	}
	/**
    This method handles the actions involved in moving both player and alien,
    movements for both and shots/bombs fired, events when shots hit alien or 
    bomb hits player.
	 */
	public void animationCycle() {
	}
}


