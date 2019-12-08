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
	
	/**
	 * Constructor takes View, Board and queue object and initiates start up.
	 * @param view class object 
	 * @param board class object
	 * @param queue blocking queue of message objects
	 */
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
			board.update();							// Get update from Board.
			gameOver = board.getGameOver();			// Get game over flag from Board.
			gameWon = board.getGameWon();			// Get game won flag.
			points = board.getPoints();				// Get update on points from Board.
			lives = board.getLives();				// Get update on lives.
			// Launch alien bombs based on whether bomb is currently visible, view has not
			// drawn bomb yet, and if Board successfully rolled for bomb dropping (getChance).
			if (board.getBomb().isVisible() && !view.getBombDropped() && board.getChance()) {
				view.setBombPosition(board.getBomb().getX(), board.getBomb().getY());
				view.setBombDropped(true);			// Begin drawing bomb in view.
				board.setChance(false);				// Allow for next drop bomb roll chance.
			}
			// Update player position if it is alive.
			if (board.getPlayer().isVisible()) { view.setPlayerPosition(board.getPlayer().getX()); }
			view.setLives(lives);					// Set view's lives display.
			view.setPoints(points);					// Set view's points display.
			// Check if player is hit (not visible).
			if (!board.getPlayer().isVisible()) { 
				view.setPlayerExplode(true);		// Chance player image to explosion.
				board.setBombDrop(false);			// Set Board's bomb drop check to false.
				board.getPlayer().setVisible(true);	// Reset player visibility after death.
			}
			// Check game over status.
			if (gameOver || lives == 0) {
				view.gameOver();					// Display game over in view.
				view.setAliensCreated(false);		// Set to false to pause alien movements.
				view.setBarrierCreated(false);		// Set to false to initiate barrier recreation.
			}
			// Check game won status, if yes: make new level.
			if (gameWon) {
				view.gameWon();						// Display game won in view.
				view.setAliensCreated(false);		// Set to false to pause alien movement for respawn.
				view.setBarrierCreated(false);		// Set to false to recreate barriers.
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
