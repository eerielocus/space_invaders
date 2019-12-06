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
		// Grab the key pressed and set it in board.
		int key = Integer.parseInt(message.getMessage());
		board.setKeyDown(key);
		// Check if its spacebar, if it is, fire the cannon!
		if (key == 32) {
			board.getShot().setX(view.getShot_x());
			view.setShotFired(true);
		}
		
		if (!view.getBombDropped()) { board.setBombDrop(true); }
		return ValveResponse.EXECUTED;
	}
}
