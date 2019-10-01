import static org.junit.Assert.*;

import org.junit.Test;

public class SpriteTest {
	 @Test
	 public void spriteTester() {
		 Sprite s = new Sprite();
		 
		 // Check if initial variables are set to 0.
		 assertEquals(0, s.getX());
		 assertEquals(0, s.getY());
		 
		 // Set new values for sprite variables.
		 s.setX(5);
		 s.setY(2);
		 
		 // Check if new values are set.
		 assertEquals(5, s.getX());
		 assertEquals(2, s.getY());
	 }
}
