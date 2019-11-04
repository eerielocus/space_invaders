package edu.sjsu.cs151.spaceinvader.controller;
import edu.sjsu.cs151.spaceinvader.adapter.GameInterface;
import edu.sjsu.cs151.spaceinvader.model.*;
import edu.sjsu.cs151.spaceinvader.view.View;

public class Controller implements GameInterface{
		
	
	View view = new View();
	/**
	 * Model
	 * @throws InterruptedException
	 */
	@Override
	public void initGame() throws InterruptedException{
		view.start();
		//initCreation();
	}
	@Override
	public void initScreen() {}
	@Override
	public void start() throws InterruptedException {
		initMovements();
	}
	@Override
	public void exit() {}

	
	protected void initCreation() {
		Board.getInstance().createAliens();
		Board.getInstance().createPlayer();		
		
	}
	protected void initMovements() throws InterruptedException {
		Board.getInstance().initDummyMove();
		//Board.getInstance().aliensMove();
		//Board.getInstance().playerMove();
	}
	
	/**********
	 *  View  *
	 **********/
	
}
