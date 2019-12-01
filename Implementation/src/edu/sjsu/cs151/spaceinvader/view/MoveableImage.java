package edu.sjsu.cs151.spaceinvader.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

public class MoveableImage {
	
	private Image image = null;
	int x = 0;
	int y = 0;
	
	MoveableImage(int x, int y, Image image){
		this.image = image;
		this.x = x;
		this.y = y;
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
	
	public void clear(Graphics g) {
		g.setColor(Color.black);
		g.clearRect(x, y, 5, 5);
	}
}