package edu.sjsu.cs151.spaceinvader.controller;

import edu.sjsu.cs151.spaceinvader.message.Message;
import edu.sjsu.cs151.spaceinvader.message.ViewUpdateMessage;
import edu.sjsu.cs151.spaceinvader.model.*;
import edu.sjsu.cs151.spaceinvader.view.View;

public class ViewUpdateValve implements Valve {
	private View view;
	private Board board;
	private Alien[][] aliens;
	private int[] shot_y = new int[1];
	private int[][] alien_x = new int[4][7], alien_y = new int[4][7];
	
	public ViewUpdateValve (View view, Board board) {
		this.view = view;
		this.board = board;
	}
	
	@Override
	public ValveResponse execute(Message message) {
		if (message.getClass() != ViewUpdateMessage.class) {
			return ValveResponse.MISS;
		}
		view.updateBoard(alien_x, alien_y, shot_y);
		board.getShot().setY(shot_y[0]);
		if (!view.getShotFired()) board.getShot().setVisible(false);
		aliens = board.getAliens();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 7; j++) {
				aliens[i][j].setX(alien_x[i][j]);
				aliens[i][j].setY(alien_y[i][j]);
			}
		}
		board.collision();
		return ValveResponse.EXECUTED;
	}
}
