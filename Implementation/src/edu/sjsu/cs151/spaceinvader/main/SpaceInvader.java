package edu.sjsu.cs151.spaceinvader.main;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import edu.sjsu.cs151.spaceinvader.controller.Controller;
import edu.sjsu.cs151.spaceinvader.message.Message;
import edu.sjsu.cs151.spaceinvader.model.Board;
import edu.sjsu.cs151.spaceinvader.view.View;

/**
 * Space Invaders game implementation using MVC, Command, Facade, Singleton, and Template 
 * design patterns for CS151 in SJSU. Compiled in JAVA 8 (JDK 1.8).
 * 
 * Utilizes Blocking Queue of class Message to communicate between Model and
 * View while controlling multi-thread activities. It uses the Valve system for processing 
 * commands in Message object form to fulfill the COMMAND pattern.
 * 
 * Implements Facade class, which hides the complexities of the system and provides an 
 * interface to the client for ease of use. This class provides simplified methods that
 * will delegate the appropriate call for objects.
 * 
 * Implements Singleton class for random number generation for alien bomb attacks:
 * SINGLETON design pattern involves a single class which is responsible in creating only one 
 * object of itself. The class will have a private constructor and will only contain public 
 * methods that access the object without need of instantiating the object.
 * 
 * Implements Template class for starting the game model:
 * TEMPLATE design pattern an abstract class exposes defined ways/templates to execute its 
 * methods. Subclasses can override the method implementation as per need but the invocation 
 * is to be in the same way as defined by the abstract class. Behavior pattern.
 * 
 * @author Michael Kang and Guiller Dalit
 */
public class SpaceInvader {
	private static BlockingQueue<Message> queue = new LinkedBlockingQueue<Message>();
	private static View view;
	private static Board board;
	
	public static void main(String args[]) throws Exception {
		view = new View();		// Initiate View.
		board = new Board();	// Initiate Board (Model).
		board.play();			// Start Model using Template method.
		// Initiate Controller and pass all relevant objects.
		Controller controller = new Controller(view, board, queue);
		controller.mainLoop();	// Begin main loop of game.
	}
}
