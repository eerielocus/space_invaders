package edu.sjsu.cs151.spaceinvader.model;

/**
 * Implementation of FACADE design pattern, which hides the complexities of the system and
 * provides an interface to the client for ease of use. This class provides simplified methods
 * that will delegate the appropriate call for objects.
 */
public class SpriteMaker {
	
	/**
	 * Constructor for the Sprite maker.
	 */
	public SpriteMaker() {	}
	
	/**
	 * Makes a new Alien object using provided x y positions.
	 * @param x position
	 * @param y position
	 * @return Alien object with x y position
	 */
	public Alien makeAlien(int x, int y) {
		return new Alien(x, y);
	}
	
	/**
	 * Makes new player object.
	 * @return Player object
	 */
	public Player makePlayer() {
		return new Player();
	}
	
	/**
	 * Makes new barrier object using provided x y positions.
	 * @param x position
	 * @param y position
	 * @return Barrier object
	 */
	public Barrier makeBarrier(int x, int y) {
		return new Barrier(x, y);
	}
	
	/**
	 * Makes new projectile object.
	 * @return Projectile object
	 */
	public Projectile makeProjectile() {
		return new Projectile();
	}
}
