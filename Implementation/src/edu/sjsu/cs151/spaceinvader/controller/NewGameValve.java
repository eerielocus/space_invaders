package edu.sjsu.cs151.spaceinvader.controller;

import edu.sjsu.cs151.spaceinvader.message.Message;
import edu.sjsu.cs151.spaceinvader.message.NewGameMessage;
import edu.sjsu.cs151.spaceinvader.model.*;
import edu.sjsu.cs151.spaceinvader.view.View;

/**
 * Valve to process New Game message, initates start over command and creates new aliens
 * and resets speed and visibility.
 */
public class NewGameValve implements Valve {
	private View view;
	private Board board;
	private Sprite[][] aliens;
	
	public NewGameValve (View view, Board board) {
		this.view = view;
		this.board = board;
	}
	
	@Override
	public ValveResponse execute(Message message) {
		if (message.getClass() != NewGameMessage.class) {
			return ValveResponse.MISS;
		}
		board.startOver();
		aliens = board.getAliens();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 7; j++) {
				view.createAliens(i, j, aliens[i][j].getX(), aliens[i][j].getY());
			}
		}
		view.setBarrierCreated(false);
		view.setPlayerVisible(true);
		view.setAliensCreated(true);
		view.setSpeed(0);
		return ValveResponse.EXECUTED;
	}

}
