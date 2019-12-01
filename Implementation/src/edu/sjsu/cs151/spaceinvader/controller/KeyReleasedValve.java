package edu.sjsu.cs151.spaceinvader.controller;

import edu.sjsu.cs151.spaceinvader.message.KeyReleasedMessage;
import edu.sjsu.cs151.spaceinvader.message.Message;
import edu.sjsu.cs151.spaceinvader.model.Board;
import edu.sjsu.cs151.spaceinvader.view.View;

public class KeyReleasedValve implements Valve {
	private View view;
	private Board board;
	
	public KeyReleasedValve (View view, Board board) {
		this.view = view;
		this.board = board;
	}
	
	@Override
	public ValveResponse execute(Message message) {
		if (message.getClass() != KeyReleasedMessage.class) {
			return ValveResponse.MISS;
		}
		int key = Integer.parseInt(message.getMessage());
		board.setKeyUp(key);
		board.update();
		return ValveResponse.EXECUTED;
	}
}
