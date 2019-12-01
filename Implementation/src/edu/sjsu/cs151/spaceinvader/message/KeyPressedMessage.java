package edu.sjsu.cs151.spaceinvader.message;

public class KeyPressedMessage implements Message {
	public String key;
	public int x;
	
	public KeyPressedMessage(String key) {
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
