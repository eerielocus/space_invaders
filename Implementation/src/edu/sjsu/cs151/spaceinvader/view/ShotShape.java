package edu.sjsu.cs151.spaceinvader.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class ShotShape implements MoveableShape {
	private int x;
	private int y;
	private int width; 
	private Color currColor = Color.green;
	
	public ShotShape (int x, int y, int width){
		this.x = x;
		this.y = y;
		this.width = width;
		
	}
	public void translate (int dx, int dy) {
		this.x += dx;
		this.x += dy;
		
	}
	public void repaint(boolean flag) {
		
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}

	public void draw(Graphics2D g2) {

	}

	

}
