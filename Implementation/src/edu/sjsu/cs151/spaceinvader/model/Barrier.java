package edu.sjsu.cs151.spaceinvader.model;

/**
 * Barrier object that extends Sprite class.
 */
public class Barrier extends Sprite {
	int k = 0;
    /**
	 * Constructor method for Barrier.
	 */
    protected Barrier(int x, int y){
        initBarrier(x, y);
    }
    /**
	 * Initializing method to set position, set correct image, and create a bomb.
	 * @param x position on x-axis
	 * @param y position on y-axis
	 */
    protected void initBarrier(int x, int y) {
    	setX(x);	
    	setY(y);
    }
    
	public void setPositionIJK(int i, int j, int k) {
    	this.i = i;
    	this.j = j;
    	this.k = k;
    }
	
	public int getPositionK() {
		return this.k;
	}
}