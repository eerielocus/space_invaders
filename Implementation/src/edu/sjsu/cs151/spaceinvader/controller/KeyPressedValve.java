package edu.sjsu.cs151.spaceinvader.controller;

import edu.sjsu.cs151.spaceinvader.message.KeyPressedMessage;
import edu.sjsu.cs151.spaceinvader.message.Message;
import edu.sjsu.cs151.spaceinvader.model.Board;
import edu.sjsu.cs151.spaceinvader.view.View;

/**
 * Valve that processes the Key Pressed event message by using the Board's set key down
 * method and setting the appropriate flags for shot and bomb.
 */
public class KeyPressedValve implements Valve {
	private View view;
	private Board board;
	private KeyPressedMessage sent;
	
	public KeyPressedValve (View view, Board board) {
		this.view = view;
		this.board = board;
	}
	
	@Override
	public ValveResponse execute(Message message) {
		if (message.getClass() != KeyPressedMessage.class) {
			return ValveResponse.MISS;
		}
		sent = (KeyPressedMessage) message;
		// Grab the key pressed and set it in board.
		int key = Integer.parseInt(sent.getMessage());
		board.setKeyDown(key);
		// Check if its spacebar, if it is, fire the cannon!
		if (key == 32 && !view.getShotFired()) {
			view.setShotFired(true);
		}
		return ValveResponse.EXECUTED;
	}
}
