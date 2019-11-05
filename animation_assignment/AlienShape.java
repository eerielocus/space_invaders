package animation_assignment;

import java.awt.*;
import java.awt.geom.*;

/**
 * A car that can be moved around.
 */
public class AlienShape implements MoveableShape {
	/**
	 * Constructs a car item.
	 * 
	 * @param x     the left of the bounding rectangle
	 * @param y     the top of the bounding rectangle
	 * @param width the width of the bounding rectangle
	 */
	public AlienShape(int x, int y, int width) {
		this.x = x;
		this.y = y;
		this.width = width;
	}

	public void translate(int dx, int dy) {
		x += dx;
		y += dy;
	}

	public void repaint(boolean flag) {
		if (flag) {
			currColor = Color.red;
		} else {
			currColor = Color.green;
		}
	}

	public void draw(Graphics2D g2) {
		Rectangle2D.Double body = new Rectangle2D.Double(x, y + width / 6, width - 1, width / 3);
		Rectangle2D.Double head = new Rectangle2D.Double(x + 10, y, width - 20, width / 6);
		Rectangle2D.Double leftArm = new Rectangle2D.Double(x - width / 6, y, width / 6, width / 6);
		Rectangle2D.Double rightArm = new Rectangle2D.Double(x + width - 1, y, width / 6, width / 6);
		Rectangle2D.Double leftLeg = new Rectangle2D.Double(x, y + width / 2, width / 6, width / 5);
		Rectangle2D.Double rightLeg = new Rectangle2D.Double(x + width - 1 - width / 6, y + width / 2, width / 6,
				width / 5);
		Rectangle2D.Double rightEye = new Rectangle2D.Double(x + 10, y + width / 6, width / 8, width / 8);
		Rectangle2D.Double leftEye = new Rectangle2D.Double(x + width - 23, y + width / 6, width / 8, width / 8);

		g2.setColor(currColor);
		g2.draw(body);
		g2.draw(head);
		g2.draw(rightArm);
		g2.draw(leftArm);
		g2.draw(leftLeg);
		g2.draw(rightLeg);
		g2.fill(body);
		g2.fill(head);
		g2.fill(rightArm);
		g2.fill(leftArm);
		g2.fill(rightLeg);
		g2.fill(leftLeg);
		
		g2.setColor(Color.black);
		g2.draw(rightEye);
		g2.draw(leftEye);
		g2.fill(rightEye);
		g2.fill(leftEye);	
	}

	private int x;
	private int y;
	private int width;
	private Color currColor = Color.green;
}
