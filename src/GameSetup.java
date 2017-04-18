import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class GameSetup extends JPanel {

	private JButton btnNewButton;
	private JComboBox<String> playerCountBox;
	private JComboBox<String> difficultyBox;
	private JComboBox<String> mapTypeBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameSetup frame = new GameSetup();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public JButton getButton() {
		return btnNewButton;
	}

	/**
	 * Create the panel.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GameSetup() {
		setLayout(null);

		JLabel lblNumberOfPlayer = new JLabel("Number of Players:");
		lblNumberOfPlayer.setBounds(351, 410, 182, 29);
		lblNumberOfPlayer.setFont(new Font("Narkisim", Font.BOLD, 17));
		add(lblNumberOfPlayer);

		playerCountBox = new JComboBox();
		playerCountBox.setBounds(351, 435, 82, 27);
		playerCountBox.setFont(new Font("Narkisim", Font.BOLD, 13));
		playerCountBox.setModel(new DefaultComboBoxModel(new String[] { "2",
				"3", "4" }));
		playerCountBox.setToolTipText("");
		add(playerCountBox);

		JLabel lblDifficulty = new JLabel("Difficulty:");
		lblDifficulty.setBounds(351, 514, 153, 29);
		lblDifficulty.setFont(new Font("Narkisim", Font.BOLD, 17));
		add(lblDifficulty);

		difficultyBox = new JComboBox();
		difficultyBox.setBounds(351, 544, 124, 27);
		difficultyBox.setFont(new Font("Narkisim", Font.BOLD, 13));
		difficultyBox.setModel(new DefaultComboBoxModel(new String[] {
				"Beginner", "Standard", "Tournament" }));
		difficultyBox.setToolTipText("");
		add(difficultyBox);

		JLabel lblMapType = new JLabel("Map Type:");
		lblMapType.setBounds(352, 460, 153, 29);
		lblMapType.setFont(new Font("Narkisim", Font.BOLD, 17));
		add(lblMapType);

		mapTypeBox = new JComboBox();
		mapTypeBox.setBounds(351, 488, 133, 27);
		mapTypeBox.setFont(new Font("Narkisim", Font.BOLD, 13));
		mapTypeBox.setModel(new DefaultComboBoxModel(new String[] { "Standard", "Plain Map"
				,"River Map", "Mountain Map","GangNamStyle Map", "Coordinate River Map", "Random" }));
		add(mapTypeBox);

		btnNewButton = new JButton("Next");
		btnNewButton.setBounds(777, 543, 117, 29);
		btnNewButton.setFont(new Font("Narkisim", Font.BOLD, 15));
		btnNewButton.setBackground(new Color(255, 255, 255, 150));
		add(btnNewButton);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(0, 0, 900, 710);
		lblNewLabel.setIcon(new ImageIcon("IMAGES/GameSetupImage.png"));
		add(lblNewLabel);
	}

	public void focusNameBox() {
		btnNewButton.requestFocusInWindow();
	}

	public int getPlayerCount() {
		return Integer.parseInt((String) playerCountBox.getSelectedItem());
	}

	public String getDifficulty() {
		return (String) difficultyBox.getSelectedItem();
	}

	public String getMapType() {
		return (String) mapTypeBox.getSelectedItem();
	}

}
