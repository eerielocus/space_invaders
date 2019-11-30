package edu.sjsu.cs151.spaceinvader.view;


public interface Message {

	public void message(String message);
	public void newGame(String message);
	public void startWindow(String message) throws InterruptedException;
	public void gamePlay(String message);
	public void scoreBoard(String message);

}
