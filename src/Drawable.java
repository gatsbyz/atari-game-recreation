import java.awt.Graphics;

/**
 * The Drawable interface is implemented by any object in the game that needs to
 * be drawn during gameplay. These objects must be able to draw themselves using
 * the draw(Graphics) method in this interface.
 * 
 */
public interface Drawable {
	public void draw(Graphics g);
}
