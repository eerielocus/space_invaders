package SpaceInvader;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import edu.sjsu.cs151.spaceinvader.adapter.GameInterface;
import edu.sjsu.cs151.spaceinvader.controller.Controller;
import edu.sjsu.cs151.spaceinvader.model.Board;
import edu.sjsu.cs151.spaceinvader.view.Message;
import edu.sjsu.cs151.spaceinvader.view.View;

public class SpaceInvader {
	
	
	private static BlockingQueue<Message> queue = new LinkedBlockingQueue<Message>();
	private static View view = new View();
	private static Board board = new Board(); 
	
	public static void main(String args[]) throws InterruptedException {
		
		GameInterface gameInt = new Controller(view, board, queue);
		
		gameInt.initGame();
		view.dispose();
		queue.clear();
		//gameInt.start();
	}

}
