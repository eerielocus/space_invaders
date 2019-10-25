package edu.sjsu.cs151.spaceinvader.model;


import java.util.ArrayList;

import edu.sjsu.cs151.spaceinvader.controller.Controller;
//this class is a singleton
@SuppressWarnings("unused")
public class Board extends Controller{
	
	
	private static Board instance = new Board();
	//private ArrayList<ArrayList<Tile>> tiles = new ArrayList<ArrayList<Tile>>();
	private ArrayList<Tile> tiles = new ArrayList<Tile>();

	private ArrayList<Alien> aliens;
	private Player player;
	private Bomb bomb;
	
	private class Tile extends Board {
		Alien alien;
		private int location_x;
		private int location_y;
		
		protected Tile(int x, int y) {
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
	}
	
	public void createAliens() throws InterruptedException {
		
		for (int i = 0, tile_index = 0; i < BOARD_WIDTH; i++) {
			 for (int j = 0; j < BOARD_HEIGHT; j++) {
				 
				 if(i < 2 && j < 10) {
					 //System.out.println(" omg  " + i + " " + tile_index + " " + BOARD_WIDTH);
					 tiles.add(new Tile(i,j));
					 tiles.get(tile_index).setAlienFace(" * ");
					 //System.out.println(tiles.get(tile_index).getAlien() + "tile " + tile_index);
					 tile_index++;
				 }
				 else {
					 tiles.add(new Tile(i,j));
				 }
			 } 
	     }
	}
	
	public void displayBoard() throws InterruptedException {
		
		int board_size = BOARD_WIDTH * BOARD_HEIGHT;
		int tile_index = 0;
		int modolus = 9;
		
		for (tile_index = 0; tile_index < tiles.size(); tile_index++) {
			System.out.print(tiles.get(tile_index).getAlien());
			
			if(( tile_index % modolus) == 0 && tile_index > 0) {
				//System.out.println("tile index "+ tile_index);
				System.out.println();			
			}
		}
	}
	
	public void aliensMove() throws InterruptedException {

		Thread.sleep(1000);
		int board_size = BOARD_WIDTH * BOARD_HEIGHT;
		int tile_index = 0;
		ArrayList<Tile> temp_tiles = new ArrayList<Tile>();
		int move_down_step = 0;
		int move_down = BOARD_WIDTH + BOARD_WIDTH;
		int temp_index = 0;
		
		
		while (move_down_step < 5) {
			while(temp_index < move_down) {
				temp_tiles.add(tiles.get(temp_index));
				temp_index++;
			}
			
			move_down += BOARD_WIDTH;
			tiles.clear();
			temp_index = 0;
			
			while(tile_index < move_down) {
				if(tile_index <= move_down) {
					tiles.add(temp_tiles.get(temp_index));
					temp_index++;
				}
				
				Thread.sleep(1000);
				displayBoard();
				tile_index++;
			}
			move_down_step++;
		}
	
	}
	
	public void createPlayer() {
		this.player = new Player();
	}
	
	public void playerMove() {
		
	}
	
	public void createBomb() {
		
	}
	
	
	
	
	
	
	public static Board getInstance() {
		return instance;
	}
	
}


