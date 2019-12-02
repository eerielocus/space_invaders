package edu.sjsu.cs151.spaceinvader.controller;

import edu.sjsu.cs151.spaceinvader.message.KeyPressedMessage;
import edu.sjsu.cs151.spaceinvader.message.Message;
import edu.sjsu.cs151.spaceinvader.model.Board;
import edu.sjsu.cs151.spaceinvader.view.View;

public class KeyPressedValve implements Valve {
	private View view;
	private Board board;
	
	public KeyPressedValve (View view, Board board) {
		this.view = view;
		this.board = board;
	}
	
	@Override
	public ValveResponse execute(Message message) {
		if (message.getClass() != KeyPressedMessage.class) {
			return ValveResponse.MISS;
		}
		int key = Integer.parseInt(message.getMessage());
		board.setKeyDown(key);
		board.update();
		if (key == 32) {
			board.getShot().setX(view.getShot_x());
			view.setShotFired(board.getShot().isVisible());
		} else if (key == 37 || key == 39) {
			view.setPlayerPosition(board.getPlayer().getX());
		}
		return ValveResponse.EXECUTED;
	}
}
