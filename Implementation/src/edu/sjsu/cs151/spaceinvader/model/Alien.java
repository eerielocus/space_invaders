package edu.sjsu.cs151.spaceinvader.model;

/**
 * Alien object that extends Sprite class.
 */
public class Alien extends Sprite {
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
}