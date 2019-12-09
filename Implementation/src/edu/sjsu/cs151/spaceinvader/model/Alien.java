package edu.sjsu.cs151.spaceinvader.model;

/**
 * Alien object that implements Sprite interface.
 */
public class Alien implements Sprite {
	private int x;						// X position.
	private int y;						// Y position.
	private int i = 0;
	private int j = 0;
	private boolean visible = false;	// Visible flag.
	
    /**
     * Constructor that takes x y positions.
     * @param x position
     * @param y position
     */
    protected Alien(int x, int y){
        this.x = x;
        this.y = y;
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
	 * This method is to return whether the object is visible or not. (destroyed or not)
	 * @return visible the visibility of the Sprite
	 */
	public boolean isVisible() {
		return visible;
	}
	
	/**
	 * This method is to set the visibility of object.
	 * @param visible flag indicating whether object is visible or not
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
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