package edu.sjsu.cs151.spaceinvader.controller;

import edu.sjsu.cs151.spaceinvader.message.Message;

/**
 * Part of COMMAND design pattern implementation.
 * Interface that handles the client requests for specific valve processing.
 */
public interface Valve {
	enum ValveResponse { MISS, EXECUTED, FINISH };	// State of the message processing.
	public ValveResponse execute(Message message);
}
