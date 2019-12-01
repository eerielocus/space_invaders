package edu.sjsu.cs151.spaceinvader.message;

public class KeyReleasedMessage implements Message {
	public String key;
	public int x;
	
	public KeyReleasedMessage(String key) {
		this.key = key;
	}
	
	public int getX() {
		return x;
	}
	
	@Override
	public String getMessage() {
		return key;
	}

}
