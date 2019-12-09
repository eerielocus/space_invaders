package edu.sjsu.cs151.spaceinvader.model;

/**
 * Sprite class contains the necessary variables and methods to get/set positions
 * of objects, set visibility, and get/set the image itself.
 */
public class Sprite {
	protected int x;					// X position.
	protected int y;					// Y position.
	private boolean visible = false;	// Visible flag.
	
	/**
	 * Constructor method for Sprite.
	 */
	public Sprite() { }
	
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