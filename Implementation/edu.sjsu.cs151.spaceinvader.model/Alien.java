package edu.sjsu.cs151.spaceinvader.model;

import javax.swing.ImageIcon;

public class Alien extends Sprite {

    private Bomb bomb;
    private final String alienImg = "src/images/alien.png";
    private ImageIcon icon;

    public Alien(int x, int y){

        initAlien(x, y);
    }

    public void initAlien(int x, int y) {
    	
    }

    public void act(int direction) {
        
     
    }
    public void setImage() {
    	icon = new ImageIcon(alienImg);	
    	
    }
    //Singleton Design Pattern
    public static Bomb getBomb() {
        
    	return new Bomb();
 
    }
  
}