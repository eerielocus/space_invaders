package edu.sjsu.cs151.spaceinvader.model;

/**
 * Implementation of TEMPLATE DESIGN PATTERN: an abstract class exposes defined ways/templates to
 * execute its methods. Subclasses can override the method implementation as per need but the
 * invocation is to be in the same way as defined by the abstract class. Behavior pattern.
 *
 */
public abstract class Game {
	protected abstract void start();		// Initiate the game.
	protected abstract void newGame();		// Start the game over.
	protected abstract void nextGame();		// Continue game to next level.
	
	/**
	 * Initiates the game model.
	 */
	public final void play() { start();	}
	
	/**
	 * Starts the game over: game was lost/over.
	 */
	public final void startOver() { newGame(); }
	
	/**
	 * Starts the next level of game: game was won.
	 */
	public final void nextLevel() { nextGame();	}
}
