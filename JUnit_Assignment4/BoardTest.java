

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

public class BoardTest {


	@Test
	public void testCreateAlien() {
		//create aliens 
		Board board = new Board();
		board.createAlien();
		assertFalse(board.getAliens().isEmpty());
		//status success, means Arraylist is not empty
	}
	@Test
	public void testAliensPositions() {
		Board board = new Board();
		
		//check if aliens default position (0,0) are change to non-zero
		board.createAlien();
	    for (int index = 0; index < board.getAliens().size();index++) {
	    	assertThat(0, not(equalTo(board.getAliens().get(index).getX())));
	    	assertThat(0, not(equalTo(board.getAliens().get(index).getY())));
	     }
		//status success, aliens have assigned location x,y
	}
	@Test
	public void testCreatePlayer() {
		//test if the createPlayer() allocate memory for a new Player
		Board board = new Board();
		board.createPlayer();
		assertNotNull(board.getPlayer());//status success
		
	}
	@Test
	public void testCreateGame() {
		//test if the method calls createPlayer and CreateAlien
		Board board = new Board();
		assertFalse(board.getAliens().isEmpty());
		assertNotNull(board.getPlayer());
		//status success
	}

	@Test
	public void testGameover() {
		Board board = new Board();
		String output = "Game Over";
		assertEquals(output, board.gameover());
		//status success
		
	}

}
