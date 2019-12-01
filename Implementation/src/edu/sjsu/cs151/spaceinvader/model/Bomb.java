package edu.sjsu.cs151.spaceinvader.model;

public class Bomb {
	private int x;
	private int y;
	public Bomb() {
		
	}
	
	public void createBomb(int x,int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
}
