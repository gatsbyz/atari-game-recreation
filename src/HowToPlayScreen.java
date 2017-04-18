import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;


public class HowToPlayScreen extends JPanel {
	JButton btnToStartScreen;
	
	/**
	 * Create the panel.
	 */
	public HowToPlayScreen() {
		setLayout(null);
		
		JLabel backgroundLabel = new JLabel();
		backgroundLabel.setBounds(0,0,900,600);
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("IMAGES/instruction.jpg"));
		} catch (IOException e) {
		    //e.printStackTrace();
		}
		Image dimg = img.getScaledInstance(backgroundLabel.getWidth(),
				backgroundLabel.getHeight(), Image.SCALE_SMOOTH);
		backgroundLabel.setIcon(new ImageIcon(dimg));
		//backgroundLabel.setIcon(new ImageIcon("IMAGES/instruction.jpg"));
		add(backgroundLabel);
		
		btnToStartScreen = new JButton("Back to Main Menu");
		btnToStartScreen.setFont(new Font("Narkisim", Font.BOLD, 15));
		btnToStartScreen.setBounds(682, 555, 200, 30);
		btnToStartScreen.setBackground(new Color(255, 255, 255, 150));
		add(btnToStartScreen);
	}
	
	public JButton getToStartScreenButton(){
		return btnToStartScreen;
	}
}
