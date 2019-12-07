package edu.sjsu.cs151.spaceinvader.model;

import java.util.Random;

/**
 * Implementation of SINGLETON design pattern which involves a single class which is responsible
 * in creating only one object of itself. The class will have a private constructor and will only
 * contain public methods that access the object without need of instantiating the object.
 */
public class RandomSingleton {
	// Create Random generator and instance of class.
	private Random generator;
	private static RandomSingleton instance = new RandomSingleton();
	
	/**
	 * Private constructor to restrict creation of class object.
	 */
	private RandomSingleton() { generator = new Random(); }
	
	/**
	 * Public method to generator and range parameter.
	 * @param range exclusive range number (10 = 0-9 range)
	 * @return generated random number
	 */
	public int nextInt(int range) { return generator.nextInt(range); }
	
	/**
	 * Public access to class object.
	 * @return instance of class object
	 */
	public static RandomSingleton getInstance() { return instance; }
}
