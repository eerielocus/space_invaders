package edu.sjsu.cs151.spaceinvader.message;

/**
 * Key event pressed message.
 */
public class KeyPressedMessage implements Message {
	public String key;	// Key pressed.
	
	/**
	 * Constructor takes string with key pressed information.
	 * @param key the key pressed from event
	 */
	public KeyPressedMessage(String key) { this.key = key; }
	
	/**
	 * Get key pressed from Key Event
	 * @return key pressed from event
	 */
	public String getMessage() { return key; }
}
