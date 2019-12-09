package edu.sjsu.cs151.spaceinvader.controller;

import edu.sjsu.cs151.spaceinvader.message.Message;
import edu.sjsu.cs151.spaceinvader.message.ViewUpdateMessage;
import edu.sjsu.cs151.spaceinvader.model.*;
import edu.sjsu.cs151.spaceinvader.view.View;

/**
 * Valve to process the View Update message sent by View during repaint. Updates alien
 * and shot positions and checks for collision of aliens to shot.
 */
public class ViewUpdateValve implements Valve {
	private View view;
	private Board board;
	private Alien[] alien = new Alien[1];
	private Alien[][] aliens;
	private Barrier[] barrier = new Barrier[2];
	private int[] shotbomb = new int[2];
	private int[] edges = new int[3];
	private int[][] alien_x = new int[4][7], alien_y = new int[4][7];
	
	public ViewUpdateValve (View view, Board board) {
		this.view = view;
		this.board = board;
	}
	
	@Override
	public ValveResponse execute(Message message) {
		if (message.getClass() != ViewUpdateMessage.class) {
			return ValveResponse.MISS;
		}
		// Get view shot's y position and set in board shot.
		view.updateBoard(alien_x, alien_y, shotbomb);
		board.getShot().setY(shotbomb[0]);	
		board.getBomb().setY(shotbomb[1]);
		// If view no longer has shot drawn, set board shot visible to false.
		if (!view.getShotFired()) { board.getShot().setVisible(false); }
		// If view no longer has bomb drawn, set board bomb visible to false.
		if (!view.getBombDropped()) { board.getBomb().setVisible(false); }
		// Update x and y position of board aliens with view alien positions.
		aliens = board.getAliens();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 7; j++) {
				aliens[i][j].setX(alien_x[i][j]);
				aliens[i][j].setY(alien_y[i][j]);
			}
		}
		alien[0] = null;					// Alien hit, set to null for check.
		barrier[0] = null;					// Barrier hit by shot, set to null for check.
		barrier[1] = null;					// Barrier hit by bomb, set to null for check.
		board.collision(alien, barrier);	// Check collision.
		// Check if barrier is hit by shot.
		if (barrier[0] != null) {
			view.setBarrierHit(barrier[0].getPositionI(), barrier[0].getPositionJ(), barrier[0].getPositionK());
			view.setShotFired(false);		// Despawn shot and reset its position.
			view.resetShotPosition();
		}
		// Check if barrier is hit by bomb.
		if (barrier[1] != null) {
			view.setBarrierHit(barrier[1].getPositionI(), barrier[1].getPositionJ(), barrier[1].getPositionK());
			view.setBombDropped(false);		// Despawn bomb.
		}
		// Check if there is collision with shot and alien.
		if (alien[0] != null) {
			view.setAlienExplode(alien[0].getPositionI(), alien[0].getPositionJ());
			view.setShotFired(false);		// Despawn shot, reset its position, and update speed if applicable.
			view.resetShotPosition();
			view.setSpeed(board.getScore());
		}
		// Update edges of alien fleet for movement bounds in view.
		edges = board.getEdgeAliens();
		view.setAlienEdge(edges);
		return ValveResponse.EXECUTED;
	}
}
