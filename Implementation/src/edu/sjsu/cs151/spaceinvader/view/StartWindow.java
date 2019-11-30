package edu.sjsu.cs151.spaceinvader.view.messages;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import edu.sjsu.cs151.spaceinvader.view.Message;
import edu.sjsu.cs151.spaceinvader.view.Frame;

public class StartWindow extends MessageAdapter {
	

	public StartWindow(){
		
		System.out.println("start");
		startFrame.setSize(600, 600);
		startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		startFrame.setResizable(false);
		startFrame.setTitle("Space Invader");
		
		startContent = new JPanel();
		startContent.setBackground(Color.black);
		
		gameName = new JLabel(logo);
		gameLogo = new JLabel(iconAlien);
		startGame = new JButton("Start Game");
		
		startGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startFrame.setVisible(false);
				gameFrame.setVisible(true);
			}	
		});
		gameName.setAlignmentX(Component.CENTER_ALIGNMENT);
		gameLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
		startGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		startContent.setLayout(new BoxLayout(startContent, BoxLayout.Y_AXIS));
		startContent.add(Box.createRigidArea(new Dimension(50, 50)));
		startContent.add(gameName);
		startContent.add(Box.createRigidArea(new Dimension(50, 50)));
		startContent.add(gameLogo);
		startContent.add(Box.createRigidArea(new Dimension(50, 50)));
		startContent.add(startGame);
		startFrame.add(startContent);
		startFrame.setVisible(true);
		
		// Animation of start screen alien logo.
		Timer t = new Timer(15, new ActionListener() {
			
			int x = 1;
			int y = 1;
			int vert = 1;
			int dir = 1;
			boolean direction = false;

			public void actionPerformed(ActionEvent event) {
				if (x == 1) { direction = false; } 
				else if (x == 250) { direction = true; }

				if (vert == 1) { 
					y = 1; 
					dir = 1;
				} else if (vert == 20) {
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
		});t.start();
		
	
	}
	@Override
	public void startWindow(String message) {
		
	}
}
