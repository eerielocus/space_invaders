package edu.sjsu.cs151.spaceinvader.controller;

import java.util.concurrent.BlockingQueue;

import edu.sjsu.cs151.spaceinvader.adapter.GameInterface;
import edu.sjsu.cs151.spaceinvader.model.*;
import edu.sjsu.cs151.spaceinvader.view.Message;
import edu.sjsu.cs151.spaceinvader.view.View;

public class Controller implements GameInterface, Valve{
	
	private BlockingQueue<Message> messageQueue;
	private View view;
	private Board board;
	
	public Controller (View view, Board board,BlockingQueue<Message> messageQueue ) {
		
		this.view = view;
		this.messageQueue = messageQueue;
		this.board = board;
	}
	/**
	 * Model
	 * 
	 * @throws InterruptedException
	 */
	@Override
	public void initGame() throws InterruptedException {
		System.out.println("starting UI...");
		view.initQueue(messageQueue);
		view.dispose();
		//initCreation();
	}

	@Override
	public void initScreen() {
	}
	
	@Override
	public void start() throws InterruptedException {
		initMovements();
	}
	@Override
	public void exit() {
	}

	protected void initCreation() {
		Board.getInstance().createAliens();
		Board.getInstance().createPlayer();
		//view = new View(Board.getInstance());
	}

	protected void initMovements() throws InterruptedException {
		Board.getInstance().initDummyMove();
		// Board.getInstance().aliensMove();
		// Board.getInstance().playerMove();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	/**********
	 * View *
	 **********/

}
