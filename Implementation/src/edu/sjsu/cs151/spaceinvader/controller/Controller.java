package edu.sjsu.cs151.spaceinvader.controller;

import java.util.ArrayList;

import edu.sjsu.cs151.spaceinvader.adapter.GameInterface;
import edu.sjsu.cs151.spaceinvader.model.*;

public class Controller implements GameInterface{
	
	private ArrayList<Alien> aliens;
	private Player player;
	private Bomb bomb;
		
	/**********
	 * Model
	 * @throws InterruptedException *
	 **********/
	@Override
	public void initGame() throws InterruptedException{
		initCreation();
	}
	@Override
	public void initScreen() {}
	@Override
	public void start() throws InterruptedException {
		initMovements();
	}
	@Override
	public void exit() {}
	
	
	
	protected void initCreation() throws InterruptedException {

		Board.getInstance().createAliens();
		
	//Board.getInstance().createPlayer();		
		
	}
	protected void initMovements() throws InterruptedException {
		Board.getInstance().aliensMove();
		//Board.getInstance().playerMove();
	}
	
	

	/**********
	 *  View  *
	 **********/
	
	
}