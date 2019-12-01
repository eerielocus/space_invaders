package edu.sjsu.cs151.spaceinvader.model;


public class Alien extends Sprite {

    private String face = " - ";
    /**
	 * Constructor method for Alien.
	 */
    protected Alien(int x, int y){
        initAlien(x, y);
    }
    /**
	 * Initializing method to set position, set correct image, and create a bomb.
	 * @param x position on x-axis
	 * @param y position on y-axis
	 */
    protected void initAlien(int x, int y) {
    	setX(x);	
    	setY(y);
    }
    /**
	 * This method creates a new bomb for the Alien.
	 */
    protected Bomb getBomb() {
    	return new Bomb();
    }
}