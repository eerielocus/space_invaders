package edu.sjsu.cs151.spaceinvader.adapter;

public interface GameInterface {
	
	public static final int BOARD_WIDTH = 400;
    public static final int BOARD_HEIGHT = 400;
    public static final int GROUND = 300;
    public static final int BOMB_HEIGHT = 5;
    public static final int ALIEN_HEIGHT = 12;
    public static final int ALIEN_WIDTH = 12;
    public static final int BORDER_RIGHT = 30;
    public static final int BORDER_LEFT = 5;
    public static final int GO_DOWN = 15;
    public static final int NUMBER_OF_ALIENS_TO_DESTROY = 24;
    public static final int CHANCE = 5;
    public static final int DELAY = 17;
    public static final int PLAYER_WIDTH = 15;
    public static final int PLAYER_HEIGHT = 10;
    public static final int ALIEN_INIT_X = 150;
    public static final int ALIEN_INIT_Y = 5;
    
	public void initGame();
	public void initScreen();
}
