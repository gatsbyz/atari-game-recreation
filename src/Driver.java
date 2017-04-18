import java.awt.HeadlessException;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * This is the main game driver that launches the rest of the game; it primarily
 * just creates and set up the game window.
*/
public class Driver {
	public static void main(String[] args) throws HeadlessException,
			IOException {
		JFrame frame = new JFrame("M.U.L.E. at Georgia Tech!");
		frame.add(new MULEMainPanel());
		frame.setResizable(false);// Locks the size of the frame.
		frame.setIconImage(new ImageIcon("IMAGES/gameIcon.png").getImage());
		frame.pack();
		frame.setLocationRelativeTo(null); // This line tells the window to open
											// in the center of the screen.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
