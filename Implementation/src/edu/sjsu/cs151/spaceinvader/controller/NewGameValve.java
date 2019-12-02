package edu.sjsu.cs151.spaceinvader.controller;

import edu.sjsu.cs151.spaceinvader.message.Message;
import edu.sjsu.cs151.spaceinvader.message.NewGameMessage;
import edu.sjsu.cs151.spaceinvader.model.*;
import edu.sjsu.cs151.spaceinvader.view.View;

public class NewGameValve implements Valve {
	private View view;
	private Board board;
	private Alien[][] aliens;
	
	public NewGameValve (View view, Board board) {
		this.view = view;
		this.board = board;
	}
	
	@Override
	public ValveResponse execute(Message message) {
		if (message.getClass() != NewGameMessage.class) {
			return ValveResponse.MISS;
		}
		System.out.println("Creating aliens.");
		board.createAliens();
		board.createPlayer();
		aliens = board.getAliens();
		System.out.println("Created aliens.");
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 7; j++) {
				view.createAliens(i, j, aliens[i][j].getX(), aliens[i][j].getY());
			}
		}
		view.createShot();
		view.setAliensCreated(true);
		board.newGame();
		return ValveResponse.EXECUTED;
	}

}
