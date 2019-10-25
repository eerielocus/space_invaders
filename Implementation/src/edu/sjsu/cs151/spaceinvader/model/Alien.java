package edu.sjsu.cs151.spaceinvader.model;

import javax.swing.ImageIcon;

public class Alien extends Sprite {

    private Bomb bomb;
    private ImageIcon icon;
    private int x, y;
    private String face = "";

    public Alien(int x, int y){
        initAlien(x, y);
    }

    public void initAlien(int x, int y) {
    	this.x = x;	
    	this.y = y;
    }
    public void act(int direction) {
        
     
    }
    public static Bomb getBomb() {
        
    	return new Bomb();

    }
    public void setAlienFace(String face) {
    	this.face = face;
    }
	public String getAlienFace() {
		return this.face;
	}
 
}