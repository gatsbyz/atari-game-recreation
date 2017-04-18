import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class StartScreen extends JPanel {

	private JButton btnNewGame;
	private JButton btnLoadGame;
	private JButton btnHowToPlay;

	public JButton getNewGameButton() {
		return btnNewGame;
	}

	public JButton getLoadGameButton() {
		return btnLoadGame;
	}
	
	public JButton getHowToPlayButton(){
		return btnHowToPlay;
	}

	/**
	 * Create the frame.
	 */
	public StartScreen() {
		setLayout(null);

		btnNewGame = new JButton("New Game");
		btnNewGame.setFont(new Font("Narkisim", Font.BOLD, 15));
		btnNewGame.setBounds(725, 497, 125, 30);
		btnNewGame.setBackground(new Color(255, 255, 255, 150));
		add(btnNewGame);

		btnLoadGame = new JButton("Load Game");
		btnLoadGame.setFont(new Font("Narkisim", Font.BOLD, 15));
		btnLoadGame.setBounds(55, 497, 125, 30);
		btnLoadGame.setBackground(new Color(255, 255, 255, 150));
		add(btnLoadGame);
		
		btnHowToPlay = new JButton("How to Play");
		btnHowToPlay.setFont(new Font("Narkisim", Font.BOLD, 15));
		btnHowToPlay.setBounds(385, 560, 130, 30);
		btnHowToPlay.setBackground(new Color(255, 255, 255, 150));
		add(btnHowToPlay);

		ImageIcon image = new ImageIcon("IMAGES/StartImage.png");
		JLabel lblNewLabel = new JLabel(image);
		lblNewLabel.setBounds(0, 0, 900, 600);
		add(lblNewLabel);
		
	}
}
