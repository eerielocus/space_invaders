package edu.sjsu.cs151.spaceinvader.model;

/**
 * Sprite interface contains the necessary methods to get/set positions
 * of objects, and set visibility.
 */
public interface Sprite {

	/**
	 * This method is to return whether the object is visible or not. (destroyed or not)
	 * @return visible the visibility of the Sprite
	 */
	public boolean isVisible();
	
	/**
	 * This method is to set the visibility of object.
	 * @param visible flag indicating whether object is visible or not
	 */
	public void setVisible(boolean visible);
    
	/**
	 * This method is to set the object as dead.
	 */
	public void dead();

	/**
	 * This method is to set the X-position of object.
	 * @param x position on x-axis
	 */
	public void setX(int x);
	
	/**
	 * This method is to set the Y-position of object.
	 * @param y position on y-axis
	 */
	public void setY(int y);
	
	/**
	 * This method is to get the X-position of object.
	 * @return x the x-position
	 */
	public int getX();
	
	/**
	 * This method is to get the Y-position of object.
	 * @return y the y-position
	 */
	public int getY();
}