package edu.sjsu.cs151.spaceinvader.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Panel extends JPanel{

	
	//private static View view = View.getInstance();
	private Frame frame = new Frame();
	
	private static final long serialVersionUID = 1L;
	public Panel() {
		
		
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		frame.repaint(g);
	}
	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}
	

}
