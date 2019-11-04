package edu.sjsu.cs151.spaceinvader.view;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;


public class View implements ActionListener, ImageObserver{
	
	private static View view = new View();
	//change this path
	private final String path = "/Users/guillerdalit/Desktop/Workplace/Java/SpaceInvader-CS151/src/edu/sjsu/cs151/spaceinvader/view/plane.jpg";
	
	private Image image;
	private Panel panel = new Panel();
	private MaindFrame frame = new MaindFrame();
	private RectangleImage plane = null;
	
	
	//testing will use the variable in GameInterface
	private final int HEIGHT = 500;
	private final int WIDTH = 500;
	private final int speed = 10;	
	
	Rectangle plane1 = new Rectangle(600, 300, 10,10);
	
	Timer timer = new Timer(20, this);

	public void start() {
		
		frame.mainFrame();
	}
	@Override
	public void actionPerformed(ActionEvent e){
		panel.repaint();
	}
	public void repaint(Graphics g) {
		
		if (plane == null) {
			plane = new RectangleImage(200, 200, getImage(path));
		}
	
		Graphics2D g2 = (Graphics2D)g;
		
		plane.draw(g2, this); 
	
		
		//background
		g.setColor(Color.orange);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		//border designed
		g.setColor(Color.black);
		g.fillRect(0, HEIGHT - 70, WIDTH, 50);
		
		//Space Invader design
		g.setColor(Color.black);
		g.fillRect(0, HEIGHT - 450, WIDTH, 50);
		
		//testing will replace by RectangleImage
		g.setColor(Color.black);
		g.fillRect(plane1.x, plane1.y, plane1.width, plane1.height);
		//System.out.println("in repaint x " +  plane.x + " y " +plane.y);
		
	
		if(plane1.x != -10) {
			//plane.x -= speed;
			plane1.x -= speed;
		}
	}
	
	
	public Image getImage(String path) {
		Image tempImage = null;
		//String pth = "/Users/guillerdalit/Desktop/Workplace/Java/SpaceInvader-CS151/src/edu/sjsu/cs151/spaceinvader/view/plane.jpg";
		try {
			File tempFile = new File(path);
			
			if (tempFile.exists()) {
				System.out.println("File Exist");
				tempImage = ImageIO.read(tempFile);
			}
			else {
				System.out.println("File doesnt Exist");
			}
			
		}catch (Exception e){
			System.out.println("Cannot upload Image " + e.getMessage());
		}
		return tempImage;
	}
	@SuppressWarnings("unused")
	private class RectangleImage{
		
		private Image image = null;
		private Rectangle rect = null;
		
		RectangleImage(int x, int y, Image image){
			this.image = image;
			this.rect = new Rectangle(x, y, 30, 30);
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

	@SuppressWarnings("unused")
	private class MaindFrame extends JFrame{
		
		private static final long serialVersionUID = 1L;
		public void mainFrame() {
			frame.add(panel);
			frame.validate();
			frame.pack();
			frame.setSize(500, 500);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);		
			frame.setResizable(false);
			frame.setTitle("Space Invader");
			timer.start();
		}
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














