package edu.sjsu.cs151.spaceinvader.message;

/**
 * Key event released message.
 */
public class KeyReleasedMessage implements Message {
	public String key;	// Key released.
	
	/**
	 * Constructor that takes key released.
	 * @param key the key pressed from event
	 */
	public KeyReleasedMessage(String key) { this.key = key;	}
	
	/**
	 * Get the key sent by Key Event.
	 * @return key event released
	 */
	public String getMessage() { return key; }
}
