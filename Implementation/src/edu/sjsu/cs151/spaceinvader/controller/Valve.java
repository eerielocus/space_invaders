package edu.sjsu.cs151.spaceinvader.controller;

import edu.sjsu.cs151.spaceinvader.message.Message;

public interface Valve {
	enum ValveResponse { MISS, EXECUTED, FINISH };
	public ValveResponse execute(Message message);
}
