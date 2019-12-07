package edu.sjsu.cs151.spaceinvader.view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

/**
 * Customized image class that contains an explosion image to be used by the game pieces
 * as well as flags, draw, and set/get for positioning.
 */
public class MoveableImage {
	private Image image = null;
	private ImageIcon explode = new ImageIcon(new ImageIcon(this.getClass().getResource("explosion.png")).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
	private Image explosion = explode.getImage();
	private boolean visible = false;
	private boolean exploding = false;
	private int x = 0;
	private int y = 0;
	
	MoveableImage(int x, int y, Image image){
		this.image = image;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Set visible flag.
	 * @param flag indicating whether image is visible or not
	 */
	public void setVisible(boolean flag) {
		this.visible = flag;
	}
	
	/**
	 * Get visible flag
	 * @return flag indicating if image is visible or not
	 */
	public boolean getVisible() {
		return this.visible;
	}
	
	/**
	 * Set the x position for image.
	 * @param x position
	 */
	public void setX (int x) {
		this.x = x;
	}
	
	/**
	 * Set the y position for image.
	 * @param y position
	 */
	public void setY (int y) {
		this.y = y;
	}
	
	/**
	 * Get the x position.
	 * @return x position
	 */
	public int getX () {
		return x;
	}
	
	/**
	 * Get the y position.
	 * @return y position
	 */
	public int getY () {
		return y;
	}
	
	/**
	 * Get the current image set.
	 * @return image
	 */
	public Image getImage() {
		return this.image;
	}
	
	/**
	 * Draw the image onto the observer
	 * @param g graphics
	 * @param o observer (window/component/panel)
	 */
	public void draw(Graphics g, ImageObserver o) {	
		g.drawImage(this.image, x, y, o);
	}
	
	/**
	 * Reset image to former. (Used after explosion change)
	 * @param image
	 */
	public void reset(Image image) {
		this.image = image;
	}
	
	/**
	 * Set image to explosion and set flag for exploding to true.
	 */
	public void explode() {
		this.image = explosion;
		this.exploding = true;
	}
	
	/**
	 * Set exploding flag.
	 * @param flag indicating if image is exploding or not
	 */
	public void setExploding(boolean flag) {
		this.exploding = flag;
	}
	
	/**
	 * Get exploding flag.
	 * @return flag indicating if image is exploding or not
	 */
	public boolean getExploding() {
		return this.exploding;
	}
}