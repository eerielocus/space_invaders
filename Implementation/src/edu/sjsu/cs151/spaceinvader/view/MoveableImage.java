package edu.sjsu.cs151.spaceinvader.view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

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
	
	public void setVisible(boolean flag) {
		this.visible = flag;
	}
	
	public boolean getVisible() {
		return this.visible;
	}
	
	public void setX (int x) {
		this.x = x;
	}
	
	public void setY (int y) {
		this.y = y;
	}
	
	public int getX () {
		return x;
	}
	
	public int getY () {
		return y;
	}
	
	public Image getImage() {
		return this.image;
	}
	
	public void draw(Graphics g, ImageObserver o) {	
		g.drawImage(this.image, x, y, o);
	}
	
	public void reset(Image image) {
		this.image = image;
	}
	
	public void explode() {
		this.image = explosion;
		this.exploding = true;
	}
	
	public void setExploding(boolean flag) {
		this.exploding = flag;
	}
	
	public boolean getExploding() {
		return this.exploding;
	}
}