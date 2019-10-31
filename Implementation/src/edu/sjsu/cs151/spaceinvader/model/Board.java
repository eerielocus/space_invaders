package edu.sjsu.cs151.spaceinvader.model;
import java.util.ArrayList;
import java.util.Scanner;
import edu.sjsu.cs151.spaceinvader.controller.Controller;

//This class is a singleton
public class Board extends Controller {

	private static Board instance = new Board();
	private ArrayList<Tile> tiles = new ArrayList<Tile>();
	private Player player;
	
	/**
	 * Private constructor for singleton Board class
	 */
	private Board() { }
	
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
		for(int i = 0, tile_index = 0; i < BOARD_WIDTH; i++) {
			 for(int j = 0; j < BOARD_HEIGHT; j++) {
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
	 * Displays the playing tiles		  
	 * @param board is placeholder tiles 
	 */
	private void displayBoard(ArrayList<Tile> board) {
		int tile_index = 0;
		for(tile_index = 0; tile_index < board.size(); tile_index++) {
			if((tile_index % 10) == 0) {
				System.out.println();
			}
			System.out.print(board.get(tile_index).getAlien());
		}
	}
	
	/**
	 * Creates placeholder Aliens on Board for testing
	 * @param temp_tiles is placeholder tiles
	 */
	private void createDummyAliens(ArrayList<Tile> temp_tiles) {	
		for(int i = 0, tile_index = 0; i < BOARD_WIDTH; i++) {
			for(int j = 0; j < BOARD_HEIGHT; j++) {
				temp_tiles.add(new Tile(i,j));
				temp_tiles.get(tile_index).setAlienFace(" - ");
				tile_index++;	 
			}
		}
	}
	
	/**
	 * Creates a placeholder Player on Board for testing
	 * @param temp_tiles is placeholder tiles
	 */
	private void createDummyPlayer(ArrayList<Tile> temp_tiles) {
		for(int i = 0; i < BOARD_WIDTH; i++) {
			 temp_tiles.add(new Tile(i,0));
			 temp_tiles.get(i).setAlienFace(" _ ");
		} 
		temp_tiles.get(5).setAlienFace(player.getPlayerFace());
	}
	
	/**
	 * Creates a placeholder fleet of Aliens to be Shot at
	 * @param temp_tiles is placeholder tiles
	 */
	private void createTargetPractice(ArrayList<Tile> temp_tiles) {
		for(int i = 0, tile_index = 0; i < BOARD_WIDTH; i++) {
			for(int j = 0; j < 4; j++) {
				temp_tiles.add(new Tile(i,j));
				temp_tiles.get(tile_index).setAlienFace(" - ");
				tile_index++;	 
			}
		}
	}
	
	/**
	 * Creates a placeholder Shot on Board from Player for testing
	 * @param shotrow is index of Shot
	 * @param index is index of bottom wave of Aliens
	 * @param temp_tiles is the dummy Board
	 */
	private void createDummyShot(int shotrow, int index, ArrayList<Tile> temp_tiles) {
		temp_tiles.get(shotrow + index).setAlienFace(" | ");
	}
	
	/**
	 * Creates a placeholder collision between Shot and Alien
	 * @param row is index of bottom wave of Aliens
	 * @param temp_tiles is the dummy Board
	 */
	private void createDummyCollision(int row, int index, ArrayList<Tile> temp_tiles) {
		if(temp_tiles.get(row + index).alien.isHit() == false) {
			temp_tiles.get(row + index).setAlienFace(" - ");
			temp_tiles.get(row + index).alien.setHit();
			temp_tiles.get(row + index).alien.dead();
		}
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
		while(tile_index < temp_tiles.size()) {
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
		System.out.println("This is a simple console demonstration of Space Invaders Model.");
		System.out.println("It will show aliens descending and halfway player will shoot one.");
		System.out.println("Starting soon.");
		Thread.sleep(5000);
		
		ArrayList<Tile> movers = new ArrayList<Tile>();
		ArrayList<Tile> players = new ArrayList<Tile>();
		ArrayList<Tile> targets = new ArrayList<Tile>();
		ArrayList<Tile> tar_aliens = (ArrayList<Tile>) tiles.clone();
		ArrayList<Tile> temp_tiles = (ArrayList<Tile>) tiles.clone();
		
		createDummyAliens(movers);
		createDummyPlayer(players);
		
		int firstrow = 10;
		int secondrow = 30;
		int shotrow = 190;
		int step_count = 0;
		int index = 5;
		int score = 1;
		
		while(step_count < BOARD_HEIGHT - 2) {
			movedown(firstrow, secondrow, movers, temp_tiles);
			displayBoard(movers);
			displayBoard(players);
			
			temp_tiles = (ArrayList<Tile>) movers.clone();
			movers.clear();
			createDummyAliens(movers);
			
			if(secondrow >= 100) {
				if(shotrow <= secondrow && secondrow < 200) {
					createDummyCollision(secondrow - 10, index, temp_tiles);
				}
				else if(shotrow >= secondrow) {
					createDummyShot(shotrow, index, movers);
					shotrow -= 10;
				}
			}
			
			firstrow += 10;
			secondrow += 10;
			step_count++;
			Thread.sleep(THREAD_SLEEP_TIME);
		}
		
		System.out.println("\n\n\n\nDemo completed.");
		System.out.println("Now to test player movement and fire control.");
		
		createTargetPractice(targets);
		movedown(10, 30, targets, tar_aliens);
		shotrow = 30;
		
		while(score < 20) {
			displayBoard(targets);
			displayBoard(players);
			System.out.println("Score: " + score);
			System.out.println("\n\nPlease enter command:");
			System.out.println("1. Move Right\n2. Move Left\n3. Fire\n");
			Scanner in = new Scanner(System.in);
			int input = in.nextInt();
			index = playerMove(input, index, shotrow, players, targets);
			score = scoreKeeper(targets);
		}
	}

	/**
	 * Manages player movement (implementation temporary)
	 * Requires key press action listener not yet implemented
	 */
	private int playerMove(int direction, int index, int shotrow, ArrayList<Tile> players, ArrayList<Tile> targets) {
		int temp_row = shotrow;
		// Move right
		if(direction == 1 && index + 1 < 10) {
			players.get(index).setAlienFace(" _ ");
			players.get(index + 1).setAlienFace(player.getPlayerFace());
			return index + 1;
		}
		// Move left
		else if(direction == 2 && index - 1 >= 0) {
			players.get(index).setAlienFace(" _ ");
			players.get(index - 1).setAlienFace(player.getPlayerFace());
			return index - 1;
		}
		// Fire
		else if(direction == 3) {
			createDummyShot(temp_row, index, targets);
			temp_row -= 10;
			if(targets.get(temp_row + index).alien.isHit() == false) {
				createDummyCollision(temp_row, index, targets);
			}
			else {
				createDummyCollision(temp_row - 10, index, targets);
			}
		}
		return index;
	}
	
	/**
	 * Player shoots Shot (not implemented until GUI)
	 */
	private void playerShoot() {
		
	}
	
	/**
	 * Simple score keeper that uses Alien object flag to determine score
	 * @param targets is fleet of Aliens
	 * @return the score
	 */
	private int scoreKeeper(ArrayList<Tile> targets) {
		int temp_score = 0;
		for(Tile target : targets) {
			if(target.alien.isVisible() == false) {
				temp_score++;
			}
		}
		return temp_score;
	}
	
	/**
	 * Returns Board instance
	 * @return this Board instance
	 */
	public static Board getInstance() {
		return instance;
	}
	
}


