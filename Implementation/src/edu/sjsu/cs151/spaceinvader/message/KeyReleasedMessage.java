package edu.sjsu.cs151.spaceinvader.message;

/**
 * Key event released message.
 */
public class KeyReleasedMessage implements Message {
	public String key;	// Key released.
	
	/**
	 * Constructor that takes key released.
	 * @param key
	 */
	public KeyReleasedMessage(String key) { this.key = key;	}
	
	@Override
	public String getMessage() { return key; }

}
