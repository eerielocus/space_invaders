package edu.sjsu.cs151.spaceinvader.controller;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

import edu.sjsu.cs151.spaceinvader.controller.Valve.ValveResponse;
import edu.sjsu.cs151.spaceinvader.message.Message;
import edu.sjsu.cs151.spaceinvader.model.*;
import edu.sjsu.cs151.spaceinvader.view.View;

public class Controller {

	private View view;
	private Board board;
	private BlockingQueue<Message> queue;
	private ArrayList<Valve> valves = new ArrayList<>();
	private boolean gameOver = false;
	private boolean gameWon = false;
	private int points = 0;
	
	public Controller(View view, Board board, BlockingQueue<Message> queue) {
		this.view = view;
		this.board = board;
		this.queue = queue;
		startUp();
	}
	
	private void startUp() {
		view.start(queue);
		valves.add(new KeyPressedValve(view, board));
		valves.add(new KeyReleasedValve(view, board));
		valves.add(new NewGameValve(view, board));
		valves.add(new ViewUpdateValve(view, board));
		System.out.println("Controller started.");
	}
	
	private void gameInfo() {
		if (view.getAliensCreated() && !gameOver) {
			board.update();
			gameOver = board.getGameOver();
			gameWon = board.getGameWon();
			points = board.getPoints();
			view.setPoints(points);
			if (gameOver) {
				view.gameOver();
			}
			if (gameWon) {
				view.gameWon();
			}
			view.setPlayerPosition(board.getPlayer().getX());
		}
		
		if (gameOver || gameWon) {
			gameOver = board.getGameOver();
			gameWon = board.getGameWon();
		}
	}
	
	public void mainLoop() throws Exception {
		ValveResponse response = ValveResponse.EXECUTED;
		Message message = null;
		while (response != ValveResponse.FINISH) {
			gameInfo();
			try {
				message = (Message) queue.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			for (Valve valve : valves) {
				response = valve.execute(message);
				if (response != ValveResponse.MISS) { break; }
			}
		}
	}
}
