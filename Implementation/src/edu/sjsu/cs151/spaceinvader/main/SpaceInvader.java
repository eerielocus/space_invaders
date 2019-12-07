package edu.sjsu.cs151.spaceinvader.main;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import edu.sjsu.cs151.spaceinvader.controller.Controller;
import edu.sjsu.cs151.spaceinvader.message.Message;
import edu.sjsu.cs151.spaceinvader.model.Board;
import edu.sjsu.cs151.spaceinvader.view.View;

/**
 * Space Invaders game implementation using MVC, Command, and Template design patterns
 * for CS151 in SJSU. 
 * 
 * @author Michael Kang and Guiller Dalit
 *
 */
public class SpaceInvader {
	private static BlockingQueue<Message> queue = new LinkedBlockingQueue<Message>();
	private static View view;
	private static Board board;
	
	public static void main(String args[]) throws Exception {
		view = new View();
		board = new Board();
		board.play();
		Controller controller = new Controller(view, board, queue);
		controller.mainLoop();
	}
}
