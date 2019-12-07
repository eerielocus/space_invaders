package edu.sjsu.cs151.spaceinvader.model;

/**
 * Sprite class contains the necessary variables and methods to get/set positions
 * of objects, set visibility, and get/set the image itself.
 *
 */
public class Sprite {
	protected int x;					// X position.
	protected int y;					// Y position.
	protected int i;					// I position in array.
	protected int j;					// J position in array.
	private boolean visible = false;	// Visible flag.
	
	/**
	 * Constructor method for Sprite.
	 */
	public Sprite() { }
	
	/**
	 * This method is to return whether the object is visible or not. (destroyed or not)
	 */
	public boolean isVisible() {
		return visible;
	}
	
	/**
	 * This method is to set the visibility of object.
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
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
    
	/**
	 * This method is to set the object as dead.
	 */
	public void dead() {
		this.visible = false;
	}

	/**
	 * This method is to set the X-position of object.
	 * @param x position on x-axis
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * This method is to set the Y-position of object.
	 * @param y position on y-axis
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * This method is to get the X-position of object.
	 * @return x the x-position
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * This method is to get the Y-position of object.
	 * @return y the y-position
	 */
	public int getY() {
		return this.y;
	}
}