package edu.sjsu.cs151.spaceinvader.controller;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

import edu.sjsu.cs151.spaceinvader.controller.Valve.ValveResponse;
import edu.sjsu.cs151.spaceinvader.message.Message;
import edu.sjsu.cs151.spaceinvader.model.*;
import edu.sjsu.cs151.spaceinvader.view.View;

/**
 * Controller class that handles communication between View and Model as well as handle
 * the state of the game.
 * 
 * @author Michael Kang and Guiller Dalit 
 */
public class Controller {

	private View view;							// View class object.
	private Board board;						// Board class object.
	private BlockingQueue<Message> queue;		// Queue containing messages for processing.
	private ArrayList<Valve> valves = new ArrayList<>();	// List of valves to process messages.
	private Alien[][] aliens;					// 2D array of Alien objects.
	private boolean gameOver = false;			// Flags to check game status.
	private boolean gameWon = false;
	private int points = 0;						// Points for the game.
	private int lives = 0;						// Lives for the game.
	
	public Controller(View view, Board board, BlockingQueue<Message> queue) {
		this.view = view;
		this.board = board;
		this.queue = queue;
		startUp();
	}
	
	/**
	 * Starts the view and passes queue object. Initiates and adds valves for processing.
	 */
	private void startUp() {
		view.start(queue);
		valves.add(new KeyPressedValve(view, board));
		valves.add(new KeyReleasedValve(view, board));
		valves.add(new NewGameValve(view, board));
		valves.add(new ReturnToStartValve(view, board));
		valves.add(new ViewUpdateValve(view, board));
	}
	
	/**
	 * Check game status for player position and game statistics. Sets bomb position/flags.
	 * Adjust speed of aliens based on updated score. Reset player visibility if already 
	 * destroyed. Create new alien fleet upon game win at a lower position to increase
	 * difficulty.
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
			// Update player position if it is alive.
			if (board.getPlayer().isVisible()) { view.setPlayerPosition(board.getPlayer().getX()); }
			view.setLives(lives);		// Set view's lives display.
			view.setPoints(points);		// Set view's points display.
			// Check if player is visible, if not, set view's player to false.
			if (!board.getPlayer().isVisible()) { 
				view.setPlayerExplode(true);
				board.setBombDrop(false);
				board.getPlayer().setVisible(true);
			}
			// Check game over status.
			if (gameOver || lives == 0) {
				view.gameOver();
				view.setAliensCreated(false);
				view.setBarrierCreated(false);
			}
			// Check game won status, if yes: make new level.
			if (gameWon) {
				view.gameWon();
				view.setAliensCreated(false);		// Set to false to pause alien movement for respawn.
				view.setBarrierCreated(false);
				board.nextLevel();;					// New game that keeps score, adjusts alien start Y.
				aliens = board.getAliens();			// Recreate aliens for new game with new start position.
				for (int i = 0; i < 4; i++) {
					for (int j = 0; j < 7; j++) {
						view.createAliens(i, j, aliens[i][j].getX(), aliens[i][j].getY());
					}
				}
				view.setSpeed(board.getScore());	// Reset alien speed.
				view.setAliensCreated(true);		// Flag that aliens have been recreated.
			}
		}
		// If its either game over or won, check to see if game is reset.
		if (gameOver || gameWon) {
			gameOver = board.getGameOver();
			gameWon = board.getGameWon();
		}
	}
	
	/**
	 * Game's main loop to read and execute all communications between Model and View.
	 * @throws Exception interrupted exception during try/catch
	 */
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
