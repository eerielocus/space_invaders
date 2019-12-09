package edu.sjsu.cs151.spaceinvader.controller;

import edu.sjsu.cs151.spaceinvader.message.KeyReleasedMessage;
import edu.sjsu.cs151.spaceinvader.message.Message;
import edu.sjsu.cs151.spaceinvader.model.Board;
import edu.sjsu.cs151.spaceinvader.view.View;

/**
 * Valve that processes Key Released event message by using the Board's set key up
 * method and updating.
 */
public class KeyReleasedValve implements Valve {
	private Board board;
	private KeyReleasedMessage sent;
	
	public KeyReleasedValve (View view, Board board) {
		this.board = board;
	}
	
	@Override
	public ValveResponse execute(Message message) {
		if (message.getClass() != KeyReleasedMessage.class) {
			return ValveResponse.MISS;
		}
		sent = (KeyReleasedMessage) message;
		int key = Integer.parseInt(sent.getMessage());
		board.setKeyUp(key);
		board.update();
		return ValveResponse.EXECUTED;
	}
}
