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
	public void draw(Graphics2D g2) {
		
	    width = 70;
	    
	    
		Rectangle2D.Double head = new Rectangle2D.Double(width/x + 20, width/y, width / 4 , width - 15);
		
		Rectangle2D.Double head_point = new Rectangle2D.Double(width/x + 21, width/y - 4, width / 4 - 3 , width - 68);
		
		Rectangle2D.Double head_point_mid = new Rectangle2D.Double(width/x + 23.5, width/y - 8, width / 4 - 8 , width - 68);
		
		Rectangle2D.Double head_point_mid_1 = new Rectangle2D.Double(width/x + 25, width/y - 12, width / 4 - 11 , width - 68);
		
		Rectangle2D.Double leftArm = new Rectangle2D.Double(width/x, width/y + 25, width - 10, width / 5);
		
		Rectangle2D.Double wing_point = new Rectangle2D.Double(width/x + 61, width/y + 26, width - 68, width / 5 - 3);
		
		Rectangle2D.Double wing_point_head = new Rectangle2D.Double(width/x + 65, width/y + 28, width - 68, width / 5 - 6);
		
	//	Rectangle2D.Double leftCannon = new Rectangle2D.Double(x - 28/width , y + 20/width, width - 50, width/3);
		
	//	Rectangle2D.Double rightCannon = new Rectangle2D.Double(x + 42/width , y + 20/width, width - 50, width/3);
		
		Rectangle2D.Double tail = new Rectangle2D.Double(width/x + 9, width/y + 55, width - 30, width/5 - 5);
		
	//	Rectangle2D.Double elesi = new Rectangle2D.Double(x - 2/width  , y - 8/width, width - 35, width/12);
		
	//	Rectangle2D.Double elesi_connector = new Rectangle2D.Double(x - 2/width  , y - 8/width, width - 35, width/12);
			

		
		Timer t = new Timer(25, new ActionListener() {
		
			public void actionPerformed(ActionEvent event) {

				
			}
		});
		
		//Rectangle2D.Double rightArm = new Rectangle2D.Double(x + width - 1, y, width / 6, width / 6);
		//Rectangle2D.Double leftLeg = new Rectangle2D.Double(x, y - 1 + width / 2, width / 6, width / 5);
		//Rectangle2D.Double rightLeg = new Rectangle2D.Double(x + width - 1 - width / 6, y - 1 + width / 2, width / 6, width / 5);
		//Rectangle2D.Double rightEye = new Rectangle2D.Double(x + 10, y + width / 6, width / 8, width / 8);
		//Rectangle2D.Double leftEye = new Rectangle2D.Double(x + width - 23, y + width / 6, width / 8, width / 8);

		g2.setColor(currColor);
	     // g2.fill(myPath); 
		
		g2.draw(head_point);
		g2.draw(head_point_mid);
		g2.draw(head_point_mid_1);
		//g2.draw(body);
		g2.draw(head);
		//g2.draw(rightarm);
		g2.draw(leftArm);
		g2.draw(tail);
		g2.draw(wing_point);
		g2.draw(wing_point_head);
		
		/*g2.draw(leftCannon);
		g2.draw(rightCannon);
		g2.draw(tail);
		g2.draw(elesi);*/
		
		
		g2.setColor(Color.ORANGE);
		//g2.fill(body);
	
		g2.fill(head);
		g2.setColor(Color.black);
		//g2.fill(leftCannon);
		g2.fill(leftArm);
		//g2.fill(rightCannon);
		//g2.setColor(Color.GRAY);
		//g2.fill(rightarm);
		g2.setColor(Color.red);
	
		g2.fill(head_point);
		g2.fill(head_point_mid);
		g2.setColor(Color.blue);
		g2.fill(tail);
		g2.fill(wing_point);
		g2.setColor(Color.red);
		g2.fill(wing_point_head);
		g2.fill(head_point_mid_1);
		
		/*g2.setColor(Color.blue);
		g2.fill(tail);
		
		g2.setColor(Color.blue);
		g2.fill(elesi);*/
		
		//g2.setColor(Color.black);
		//g2.draw(rightEye);
		//g2.draw(leftEye);
		//g2.fill(rightEye);
		//g2.fill(leftEye);	
	}

	

}
