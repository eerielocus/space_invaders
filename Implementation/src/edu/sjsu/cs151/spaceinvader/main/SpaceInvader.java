package edu.sjsu.cs151.spaceinvader.main;

import edu.sjsu.cs151.spaceinvader.adapter.GameInterface;
import edu.sjsu.cs151.spaceinvader.controller.Controller;

public class SpaceInvader {
	
	public static void main(String args[]) throws InterruptedException {
		GameInterface gameInt = new Controller();
		gameInt.initGame();
		gameInt.start();
	}
}
