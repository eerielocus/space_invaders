package edu.sjsu.cs151.spaceinvader.model;
import java.util.ArrayList;
import edu.sjsu.cs151.spaceinvader.controller.Controller;

//This class is a singleton
public class Board extends Controller {

	private static Board instance = new Board();
	private ArrayList<Tile> tiles = new ArrayList<Tile>();
	private Player player;
	
	private class Tile extends Board {
		Alien alien;
		private int location_x;
		private int location_y;
		
		protected Tile(int x, int y){
			this.location_x = x;
			this.location_y = y;
			alien = new Alien(location_x, location_y);
		}
		
		public int getX() {
			return this.location_x;
		}
		
		public int getY() {
			return this.location_y;
		}
		
		public void setAlienFace(String face) {
			this.alien.setAlienFace(face);
		}
		
		public String getAlien() {
			return this.alien.getAlienFace();
		}
		
		public void setBomb(int x, int y) {
			this.alien.getBomb().createBomb(x, y);
		}
	}	
	
	/**
	 * Creates Aliens and Bomb
	 */
	public void createAliens() {
		for (int i = 0, tile_index = 0; i < BOARD_WIDTH; i++) {
			 for (int j = 0; j < BOARD_HEIGHT; j++) {
				 if(i < 2 && j < 10) {
					 tiles.add(new Tile(i,j));
					 tiles.get(tile_index).setAlienFace(" O ");
					 tiles.get(tile_index).setBomb(i, j);
					 tile_index++;
				 }
				 else {
					 tiles.add(new Tile(i,j));
				 }
			 } 
	     }
	}	
	
	/**
	 * Creates Player (not fully implemented until GUI)
	 */
	public void createPlayer() {
		this.player = new Player();
	}
	
	/**
	 * Displays Board		  
	 * @param Arraylist<Tile> 
	 */
	private void displayBoard(ArrayList<Tile> board) {
		int tile_index = 0;
		for (tile_index = 0; tile_index < board.size(); tile_index++) {
			if((tile_index % 10) == 0) {
				System.out.println();
			}
			System.out.print(board.get(tile_index).getAlien());
		}
	}
	
	/**
	 * Creates placeholder Aliens on Board for testing
	 * @param temp_tiles is placeholder Board
	 */
	private void createDummyAliens(ArrayList<Tile> temp_tiles) {
		
		for (int i = 0, tile_index = 0; i < BOARD_WIDTH; i++) {
			for (int j = 0; j < BOARD_HEIGHT; j++) {
				temp_tiles.add(new Tile(i,j));
				temp_tiles.get(tile_index).setAlienFace(" - ");
				tile_index++;	 
			}
		}
	}
	
	/**
	 * Creates a placeholder Player on Board for testing
	 * @param ArrayList<Tile>
	 */
	private void createDummyPlayer(ArrayList<Tile> temp_tiles) {
		for (int i = 0; i < BOARD_WIDTH; i++) {
			 temp_tiles.add(new Tile(i,0));
			 temp_tiles.get(i).setAlienFace(" _ ");
		} 
		temp_tiles.get(5).setAlienFace("(+)");
	}
	
	/**
	 * Creates a placeholder Shot on Board from Player for testing
	 * @param shotrow is index of Shot
	 * @param secondrow is index of bottom wave of Aliens
	 * @param movers is the dummy Board
	 */
	private void createDummyShot(int shotrow, int secondrow, ArrayList<Tile> movers) {
		movers.get(shotrow + 5).setAlienFace(" | ");
	}
	
	/**
	 * Creates a placeholder collision between Shot and Alien
	 * @param secondrow is index of bottom wave of Aliens
	 * @param temp_tiles is the dummy Board
	 */
	private void createDummyCollision(int secondrow, ArrayList<Tile> temp_tiles) {
		temp_tiles.get(secondrow + 5).setAlienFace(" X ");
	}
	
	/**
	 * Tracker will track aliens movements by row
	 * @param firstrow is top wave of Aliens
	 */
	private int getIndexMove(int firstrow) {
		return firstrow - 10;
	}
	
	/**
	 * Moves placeholder Aliens downward on the Board
	 * @param firstrow is top wave of Aliens
	 * @param secondrow is bottom wave of Aliens
	 * @param movers is placeholder Board
	 * @param temp_tiles is placeholder fleet of Aliens
	 */
	private void movedown(int firstrow, int secondrow, ArrayList<Tile> movers, ArrayList<Tile> temp_tiles) {
		int temp_tiles_index = getIndexMove(firstrow);
		int tile_index = 0;
		while (tile_index < temp_tiles.size()) {
			if(tile_index >= firstrow && tile_index < secondrow) {
				movers.set(tile_index, temp_tiles.get(temp_tiles_index));
				temp_tiles_index++;
			}
			tile_index++;
		}
	}
	/**
	 * Initiates aliens movements, player placement, and shot
	 * @throws InterruptedException 
	 */
	@SuppressWarnings("unchecked")
	public void initDummyMove() throws InterruptedException {
		Thread.sleep(1000);
		ArrayList<Tile> movers = new ArrayList<Tile>();
		ArrayList<Tile> players = new ArrayList<Tile>();
		ArrayList<Tile> temp_tiles = (ArrayList<Tile>) tiles.clone();
		createDummyAliens(movers);
		createDummyPlayer(players);
		int firstrow = 10;
		int secondrow = 30;
		int shotrow = 190;
		int step_count = 0;
		while(step_count < BOARD_HEIGHT - 2) {
			movedown(firstrow, secondrow, movers, temp_tiles);
			displayBoard(movers);
			displayBoard(players);
			temp_tiles = (ArrayList<Tile>) movers.clone();
			movers.clear();
			createDummyAliens(movers);
			if (secondrow >= 100) {
				if (shotrow <= secondrow && secondrow < 200) {
					createDummyCollision(secondrow - 10, temp_tiles);
				}
				else if (shotrow >= secondrow) {
					createDummyShot(shotrow, secondrow, movers);
					shotrow -= 10;
				}
			}
			firstrow += 10;
			secondrow += 10;
			step_count++;
			Thread.sleep(THREAD_SLEEP_TIME);
		}
	}

	/**
	 * Manages player movement
	 * Requires key press action listener not yet implemented
	 */
	public void playerMove() {
		
	}
	
	/**
	 * Returns Board instance
	 * @return this Board instance
	 */
	public static Board getInstance() {
		return instance;
	}
	
}


