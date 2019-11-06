package animation_assignment;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This program implements an animation that moves an Alien shape.
 */
public class AnimationTester {
	public static void main(String[] args) {
		JFrame frame = new JFrame();

		final MoveableShape shape = new AlienShape(125, 0, ALIEN_WIDTH);

		ShapeIcon icon = new ShapeIcon(shape, ICON_WIDTH, ICON_HEIGHT);

		final JLabel label = new JLabel(icon);
		frame.setLayout(new FlowLayout());
		frame.add(label);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

		final int DELAY = 25;
		// Milliseconds between timer ticks
		Timer t = new Timer(DELAY, new ActionListener() {
			int x = 1;
			int y = 1;
			int vert = 1;
			int dir = 1;
			boolean direction = false;

			public void actionPerformed(ActionEvent event) {
				if (x == 1) { direction = false; } 
				else if (x == 50) { direction = true; }

				if (vert == 1) { 
					y = 1; 
					dir = 1;
				}
				else if (vert == 20) {
					y = -1; 
					dir = -1;
				}
				
				vert += dir;
				shape.repaint(direction);
				if (!direction) {
					shape.translate(1, y);
					x += 1;
				} else {
					shape.translate(-1, y);
					x -= 1;
				}
				label.repaint();
			}
		});
		t.start();
	}

	private static final int ICON_WIDTH = 400;
	private static final int ICON_HEIGHT = 100;
	private static final int ALIEN_WIDTH = 100;
}
