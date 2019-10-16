package edu.sjsu.cs151.spaceinvader.controller;

import java.util.ArrayList;

import edu.sjsu.cs151.spaceinvader.adapter.GameInterface;
import edu.sjsu.cs151.spaceinvader.model.*;

public class Controller implements GameInterface{
	
	private ArrayList<Alien> aliens;
	private Player player;
	private Bomb bomb;
	
	
	
	
	/**********
	 * Module *
	 **********/
	
	@Override
	public void initGame() {
		initPlayer();
		initAlien();
		initBomb();
		initBoard();
		
	}
	@Override
	public void initScreen() {
		// TODO Auto-generated method stub
		
	}
	protected void initPlayer() {
		player = new Player();
	}
	protected void initAlien() {
		 aliens = new ArrayList<>();
	     for (int i = 0; i < 4; i++) {
	         for (int j = 0; j < 6; j++) {
	        	 Alien alien = new Alien(ALIEN_INIT_X + 18 * j, ALIEN_INIT_Y + 18 * i);
	             aliens.add(alien);
	         }
	     }
	}
	protected void initBomb() {
		
	}
	protected void initBoard() {
		
	}

	/**********
	 *  View  *
	 **********/
	
	
}
