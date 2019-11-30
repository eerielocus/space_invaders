package edu.sjsu.cs151.spaceinvader.view;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

@SuppressWarnings("unused")
public class RectangleImage{
	
	private Image image = null;
	private Rectangle rect = null;
	
	RectangleImage(int x, int y, Image image){
		this.image = image;
		this.rect = new Rectangle(x, y, 30, 30);
	}
	public void setX (int x) {
		rect.x = x;
	}
	public void setY (int y) {
		rect.y = y;
	}
	public int getX () {
		return rect.x;
	}
	public int getY () {
		return rect.y;
	}
	public Rectangle getRect() {
		return this.rect;
	}
	public Image getImage() {
		return this.image;
	}
	public void move(int x, int y) {
		
		this.rect.setBounds(x , y, rect.width, rect.height);
	}
	public void draw(Graphics2D g2, ImageObserver o) {	
		
		g2.drawImage(this.image, this.rect.x, this.rect.y, this.rect.width, this.rect.height, o);
	}
}