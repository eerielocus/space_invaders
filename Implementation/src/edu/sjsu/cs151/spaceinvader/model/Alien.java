package edu.sjsu.cs151.spaceinvader.model;

/**
 * Alien object that extends Sprite class.
 */
public class Alien extends Sprite {
	private int i = 0;
	private int j = 0;
	
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
	 * Set I J positions in array for object.
	 * @param i position
	 * @param j position
	 */
    public void setPositionIJ(int i, int j) {
    	this.i = i;
    	this.j = j;
    }
	
    /**
     * Get I position.
     * @return i position
     */
    public int getPositionI() {
    	return this.i;
    }
    
    /**
     * Get J position.
     * @return j position
     */
    public int getPositionJ() {
    	return this.j;
    }
}