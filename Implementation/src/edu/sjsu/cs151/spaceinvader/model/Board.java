package edu.sjsu.cs151.spaceinvader.model;
import java.util.ArrayList;
import edu.sjsu.cs151.spaceinvader.controller.Controller;

//this class is a singleton
public class Board extends Controller{

	private static Board instance = new Board();
	private ArrayList<Tile> tiles = new ArrayList<Tile>();
	private Player player;
	
	private class Tile extends Board{
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
			this.alien.getBomb().createBomb​(x, y);
		}
	}	
	/**************************
	 * Create Aliens and Bomb *
	 **************************/
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
	/**************************
	 * Display Board		  *
	 * @param Arraylist<Tile> *
	 **************************/
	public void displayBoard(ArrayList<Tile> board){
		int tile_index = 0;
		for (tile_index = 0; tile_index < board.size(); tile_index++) {
			if((tile_index % 10) == 0) {
				System.out.println();
				
			}
			System.out.print(board.get(tile_index).getAlien());
		}
	}
	/**************************************
	 * Create Placeholder Aliens on Board *
	 * @param Arraylist<Tile>             *
	 *************************************/
	public void createDummyAliens(ArrayList<Tile> temp_tiles) {
		
		for (int i = 0, tile_index = 0; i < BOARD_WIDTH; i++) {
			 for (int j = 0; j < BOARD_HEIGHT; j++) {
				 temp_tiles.add(new Tile(i,j));
				 temp_tiles.get(tile_index).setAlienFace(" - ");
				 tile_index++;	 
			 }
		}
	}
	/*******************************************
	 * Tracker - track aliens movements by row *
	 * @param int           				   *
	 *******************************************/
	public int getIndexMove(int firstrow) {
		int index = 0;
		switch(firstrow) {
			case 10: index = 0; break;
			case 20: index = 10; break;
			case 30: index = 20; break;
			case 40: index = 30; break;
			case 50: index = 40; break;
			case 60: index = 50; break;
			case 70: index = 60; break;
			case 80: index = 70; break;
			case 90: index = 80; break;
			case 100: index = 90; break;
			case 110: index = 100; break;
			case 120: index = 110; break;
			case 130: index = 120; break;
			case 140: index = 130; break;
			case 150: index = 140; break;
			case 160: index = 150; break;
			case 170: index = 160; break;
			case 180: index = 170; break;
			case 190: index = 180; break;
			case 200: index = 190; break;
		}
		return index;
	}
	/******************************************************
	 * move the aliens on the board downward on the board *
	 * @param int, Arraylist<Tile>, Arraylist<Tile>       *
	 ******************************************************/
	
	public void movedown(int firstrow, int secondrow,ArrayList<Tile> movers, ArrayList<Tile> temp_tiles) {
		
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
	/*********************************
	 * Init aliens movements 		 *
	 * @throws InterruptedException  *
	 *********************************/
	@SuppressWarnings("unchecked")
	public void aliensMove() throws InterruptedException {
		Thread.sleep(1000);
		ArrayList<Tile> movers = new ArrayList<Tile>();
		ArrayList<Tile> temp_tiles =  (ArrayList<Tile>) tiles.clone();
		createDummyAliens(movers);
		int firstrow = 10;
		int secondrow = 30;
		int step_count = 0;
		while(step_count < BOARD_HEIGHT - 2) {
			movedown(firstrow, secondrow, movers, temp_tiles);
			displayBoard(movers);
			temp_tiles =  (ArrayList<Tile>) movers.clone();
			movers.clear();
			createDummyAliens(movers);
			firstrow += 10;
			secondrow += 10;
			step_count++;
			Thread.sleep(THREAD_SLEEP_TIME);
		}
	}
	

	
	public void createPlayer() {
		this.player = new Player();
	}
	public void playerMove() {
		
	}
	
	public static Board getInstance() {
		return instance;
	}
	
}


