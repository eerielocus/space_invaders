package edu.sjsu.cs151.spaceinvader.view;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class View implements ActionListener, ImageObserver {
	private static View view = new View();

	private JFrame startFrame;
	private JFrame gameFrame;
	
	private BoxLayout box;
	private JPanel startContent;
	private JPanel gameContent;
	private JPanel infoContent;
	
	private JLabel gameName;
	private JLabel gameLogo;
	private JLabel player;
	private JLabel playerShot;
	private JLabel alienBomb;
	private JLabel[][] aliens;
	
	private JButton startGame;

	private MoveableShape logoAlien = new AlienShape(125, 0, 100);
	private ImageIcon logo = new ImageIcon("logo.jpg");
	private ShapeIcon iconAlien = new ShapeIcon(logoAlien, 400, 100);

	private final int HEIGHT = 500;
	private final int WIDTH = 500;
	private final int speed = 10;

	Rectangle plane1 = new Rectangle(600, 300, 10, 10);

	Timer timer = new Timer(20, this);

	public void start() {
		startWindow();
	}
	
	private void startWindow() {
		startFrame = new JFrame("Space Invaders");
		startFrame.setSize(600, 600);
		startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startFrame.setBackground(new Color(0, 0, 255));
		startFrame.setVisible(true);
		startFrame.setResizable(false);
		
		startContent = new JPanel();
		gameName = new JLabel(logo);
		gameLogo = new JLabel(iconAlien);
		startGame = new JButton("Start Game");
		box = new BoxLayout(startContent, BoxLayout.Y_AXIS);
		
		gameName.setAlignmentX(Component.CENTER_ALIGNMENT);
		gameLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
		startGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		startContent.setLayout(box);
		startContent.setBorder(new EmptyBorder(new Insets(20, 20, 20, 20)));
		startContent.add(gameName);
		startContent.add(gameLogo);
		startContent.add(startGame);
		startFrame.add(startContent);
		startFrame.pack();
		
		Timer t = new Timer(25, new ActionListener() {
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
				logoAlien.repaint(direction);
				if (!direction) {
					logoAlien.translate(1, y);
					x += 1;
				} else {
					logoAlien.translate(-1, y);
					x -= 1;
				}
				gameLogo.repaint();
			}
		});
		t.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	public void repaint(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;

		// background
		g.setColor(Color.orange);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		// border designed
		g.setColor(Color.black);
		g.fillRect(0, HEIGHT - 70, WIDTH, 50);

		// Space Invader design
		g.setColor(Color.black);
		g.fillRect(0, HEIGHT - 450, WIDTH, 50);

		// testing will replace by RectangleImage
		g.setColor(Color.black);
		g.fillRect(plane1.x, plane1.y, plane1.width, plane1.height);
		// System.out.println("in repaint x " + plane.x + " y " +plane.y);

		if (plane1.x != -10) {
			// plane.x -= speed;
			plane1.x -= speed;
		}
	}

	public Image getImage(String path) {
		Image tempImage = null;
		// String pth =
		// "/Users/guillerdalit/Desktop/Workplace/Java/SpaceInvader-CS151/src/edu/sjsu/cs151/spaceinvader/view/plane.jpg";
		try {
			File tempFile = new File(path);

			if (tempFile.exists()) {
				System.out.println("File Exist");
				tempImage = ImageIO.read(tempFile);
			} else {
				System.out.println("File doesnt Exist");
			}

		} catch (Exception e) {
			System.out.println("Cannot upload Image " + e.getMessage());
		}
		return tempImage;
	}

	@SuppressWarnings("unused")
	private class Panel extends JPanel {

		private static final long serialVersionUID = 1L;

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			view.repaint(g);
		}
	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}

}
