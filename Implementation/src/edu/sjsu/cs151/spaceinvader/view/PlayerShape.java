package edu.sjsu.cs151.spaceinvader.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

 
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class PlayerShape implements MoveableShape {
	private int x;
	private int y;
	private int width; 
	private Color currColor = Color.green;
	
	public PlayerShape (int x, int y, int width){
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
	
	private int speed = 10;
	
	public void draw(Graphics2D g2) {
		
	    
		Rectangle2D.Double body = new Rectangle2D.Double(width/x + 20, width/y, width - 15 , width - 5);
		
		Rectangle2D.Double cannon = new Rectangle2D.Double(width/x + 27, width/y - 15, width - 30 , width );
		
		Rectangle2D.Double left_tires = new Rectangle2D.Double(width/x + 10, width/y -3, width - 30 , width );
		
		Rectangle2D.Double right_tires = new Rectangle2D.Double(width/x + 45, width/y -3, width - 30 , width );
		
	
		g2.setColor(currColor);
		g2.draw(body);
		g2.draw(cannon);
		g2.draw(left_tires);
		g2.draw(right_tires);
		g2.setColor(Color.GREEN.darker());
		g2.fill(body);
		g2.setColor(Color.GREEN.darker().darker().darker());
		g2.fill(left_tires);
		g2.fill(right_tires);
		g2.setColor(Color.black);
		g2.fill(cannon);
		
	}

	

}
