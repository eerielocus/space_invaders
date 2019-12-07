package edu.sjsu.cs151.spaceinvader.message;

/**
 * Key event pressed message.
 */
public class KeyPressedMessage implements Message {
	public String key;	// Key pressed.
	
	/**
	 * Constructor takes string with key pressed information.
	 * @param key
	 */
	public KeyPressedMessage(String key) { this.key = key; }
	
	@Override
	public String getMessage() { return key; }

}
