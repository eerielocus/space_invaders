package edu.sjsu.cs151.spaceinvader.model;

/**
 * Barrier object that extends Sprite class.
 */
public class Barrier extends Sprite {
	private int i = 0;
	private int j = 0;
	private int k = 0;
	
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
    
    /**
     * Set position in array.
     * @param i position
     * @param j position
     * @param k position
     */
	public void setPositionIJK(int i, int j, int k) {
    	this.i = i;
    	this.j = j;
    	this.k = k;
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
	
	/**
	 * Get the K position in array.
	 * @return k position
	 */
	public int getPositionK() {
		return this.k;
	}
}