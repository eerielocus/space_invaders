package edu.sjsu.cs151.spaceinvader.message;

public class NewGameMessage implements Message {
	public NewGameMessage() {
		System.out.println("Received.");
	}
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}

}