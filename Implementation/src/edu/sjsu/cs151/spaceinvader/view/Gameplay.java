package edu.sjsu.cs151.spaceinvader.view.messages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Gameplay extends MessageAdapter {
	
	public Gameplay() {
		
		gamePlay("Starting UI");
	}
	@Override
	public void gamePlay(String message) {
		gameFrame.setSize(600, 600);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setBackground(Color.black);
		gameFrame.setVisible(false);
		gameFrame.setResizable(false);
		gameFrame.setLayout(new BorderLayout());
		
		playerScore = new JLabel("   00000   ");
		playerLives = new JLabel("   00000   ");
		exitGame = new JButton("Exit Game");
		
		exitGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameFrame.setVisible(false);
				scoreFrame.setVisible(true);
			}	
		});
		
		gameContent.setBackground(Color.black);
		gameContent.setLayout(new BorderLayout());
		
		infoContent.setBackground(Color.white);
		infoContent.setLayout(new BoxLayout(infoContent, BoxLayout.X_AXIS));
		infoContent.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		playerScore.setBorder(BorderFactory.createTitledBorder("SCORE"));
		playerLives.setBorder(BorderFactory.createTitledBorder("LIVES"));
		playerScore.setAlignmentX(Component.LEFT_ALIGNMENT);
		playerLives.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		infoContent.add(playerScore);
		infoContent.add(Box.createHorizontalGlue());
		infoContent.add(exitGame);
		infoContent.add(Box.createHorizontalGlue());
		infoContent.add(playerLives);
		
		gameFrame.add(infoContent, BorderLayout.NORTH);
		gameFrame.add(gameContent, BorderLayout.CENTER);
		drawAliens();
		timer.start();
		
	}
}
