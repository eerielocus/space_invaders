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
	private Alien[][] aliens;
	private boolean gameOver = false;
	private boolean gameWon = false;
	private int points = 0;
	private int lives = 0;
	
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
		valves.add(new ReturnToStartValve(view, board));
		valves.add(new ViewUpdateValve(view, board));
	}
	
	/**
	 * Check game status for player position and game stats.
	 */
	private void gameInfo() {
		// If aliens are on the screen and its not game over or game won.
		if (view.getAliensCreated() && !gameOver && !gameWon) {
			board.update();
			gameOver = board.getGameOver();
			gameWon = board.getGameWon();
			points = board.getPoints();
			lives = board.getLives();
			// Launch alien bombs.
			if (board.getBomb().isVisible() && !view.getBombDropped() && board.getChance()) {
				view.setBombPosition(board.getBomb().getX(), board.getBomb().getY());
				view.setBombDropped(true);
				board.setChance(false);
			}
			// Update player position and points.
			if (board.getPlayer().isVisible()) { view.setPlayerPosition(board.getPlayer().getX()); }
			view.setLives(lives);
			view.setPoints(points);
			// Check if player is visible, if not, set view's player to false.
			if (!board.getPlayer().isVisible()) { 
				view.setPlayerExplode(true);
				board.getPlayer().setVisible(true);
			}
			// Check game over status.
			if (gameOver || lives == 0) {
				view.gameOver();
				board.newGame();
			}
			// Check game won status, if yes: make new level.
			if (gameWon) {
				view.gameWon();
				board.nextGame();
				aliens = board.getAliens();
				for (int i = 0; i < 4; i++) {
					for (int j = 0; j < 7; j++) {
						view.createAliens(i, j, aliens[i][j].getX(), aliens[i][j].getY());
					}
				}
				view.setSpeed(board.getScore());
				view.setAliensCreated(true);
			}
		}
		// If its either game over or won, check to see if game is reset.
		if (gameOver || gameWon) {
			gameOver = board.getGameOver();
			gameWon = board.getGameWon();
		}
	}
	
	public void mainLoop() throws Exception {
		ValveResponse response = ValveResponse.EXECUTED;
		Message message = null;
		while (response != ValveResponse.FINISH) {
			try {
				message = (Message) queue.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (Valve valve : valves) {
				gameInfo();
				response = valve.execute(message);
				if (response != ValveResponse.MISS) { break; }
			}
		}
	}
}
