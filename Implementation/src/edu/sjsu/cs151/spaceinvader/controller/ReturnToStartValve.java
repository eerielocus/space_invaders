package edu.sjsu.cs151.spaceinvader.controller;

import edu.sjsu.cs151.spaceinvader.message.Message;
import edu.sjsu.cs151.spaceinvader.message.ReturnToStartMessage;
import edu.sjsu.cs151.spaceinvader.model.Board;
import edu.sjsu.cs151.spaceinvader.view.View;

public class ReturnToStartValve implements Valve {
	private View view;
	private Board board;
	
	public ReturnToStartValve (View view, Board board) {
		this.view = view;
		this.board = board;
	}
	
	@Override
	public ValveResponse execute(Message message) {
		if (message.getClass() != ReturnToStartMessage.class) {
			return ValveResponse.MISS;
		}
		board.newGame();
		return ValveResponse.EXECUTED;
	}
}
