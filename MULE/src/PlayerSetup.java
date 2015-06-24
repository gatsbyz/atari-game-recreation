import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.text.*;


@SuppressWarnings("serial")
public class PlayerSetup extends JPanel {

	// private JPanel contentPane;
	private JLabel playerNumberLabel;
	private static JTextField textField;
	private JButton nextButton;
	private JButton randomizeBtn;
	private static JComboBox<String> raceBox;
	private static JComboBox<String> colorBox;
	private static JComboBox<String> humanAI;
	private String[] colorOptions = { "Red", "Blue", "Green", "Yellow" };
	private JLabel noInputLabel;

	public JButton getButton() {
		return nextButton;
	}
	
	public JButton getRandomizeButton(){
		return randomizeBtn;
	}

	/**
	 * Create the frame.
	 * 
	 * @throws IOException
	 * @throws HeadlessException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PlayerSetup() {
		setBounds(0, 0, 900, 600);
		setLayout(null);

		textField = new JTextField();
		textField.setBounds(351, 435, 153, 28);
		// The next line adds a custom filter to the text field that only allows
		// 20 characters to be entered.
		((AbstractDocument) textField.getDocument())
				.setDocumentFilter(new NameFieldFilter());
		add(textField);
		textField.setColumns(10);

		nextButton = new JButton("Next");
		nextButton.setFont(new Font("Narkisim", Font.BOLD, 13));
		nextButton.setBounds(777, 543, 117, 29);
		nextButton.setBackground(new Color(255, 255, 255, 150));
		add(nextButton);
		
		randomizeBtn = new JButton("Randomize");
		randomizeBtn.setFont(new Font("Narkisim", Font.BOLD, 13));
		randomizeBtn.setBounds(77, 543, 100, 25);
		randomizeBtn.setBackground(new Color(255, 255, 255, 150));
		randomizeBtn.addActionListener(new RandomizePlayer());
		add(randomizeBtn);

		JLabel lblNameOfPlayer = new JLabel("Name of Player");
		lblNameOfPlayer.setFont(new Font("Narkisim", Font.BOLD, 17));
		lblNameOfPlayer.setBounds(351, 410, 182, 29);
		add(lblNameOfPlayer);

		colorBox = new JComboBox();
		colorBox.setModel(new DefaultComboBoxModel(colorOptions));
		colorBox.setFont(new Font("Narkisim", Font.BOLD, 13));
		colorBox.setBounds(351, 544, 124, 27);
		add(colorBox);

		JLabel lblColor = new JLabel("Color:");
		lblColor.setFont(new Font("Narkisim", Font.BOLD, 17));
		lblColor.setBounds(351, 514, 153, 29);
		add(lblColor);

		raceBox = new JComboBox();
		raceBox.setModel(new DefaultComboBoxModel(new String[] { "Flapper",
				"Human", "Bonzoid", "Ugaite", "Buzzite", "Yuna" }));
		raceBox.setFont(new Font("Narkisim", Font.BOLD, 13));
		raceBox.setBounds(351, 488, 104, 27);
		add(raceBox);
		
		humanAI = new JComboBox();
		humanAI.setModel(new DefaultComboBoxModel(new String[] {"HUMAN", "AI"}));
		humanAI.setFont(new Font("Narkisim", Font.BOLD, 13));
		humanAI.setBounds(150, 435, 104, 27);
		add(humanAI);

		JLabel lblRace = new JLabel("Race:");
		lblRace.setFont(new Font("Narkisim", Font.BOLD, 17));
		lblRace.setBounds(352, 460, 53, 29);
		add(lblRace);

		playerNumberLabel = new JLabel("Player Number: X");
		playerNumberLabel.setFont(new Font("DecoType Naskh", Font.BOLD, 19));
		playerNumberLabel.setBounds(72, 392, 267, 35);
		add(playerNumberLabel);

		noInputLabel = new JLabel("Player name cannot be blank!");
		playerNumberLabel.setFont(new Font("Narkisim", Font.BOLD, 12));
		noInputLabel.setBounds(516, 442, 224, 15);
		add(noInputLabel);
		noInputLabel.setVisible(false);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("IMAGES/PlayerSetupImage.jpeg"));
		label.setBounds(0, 0, 900, 710);
		add(label);
	}

	public void showNoInputLabel() {
		noInputLabel.setVisible(true);
	}

	public void clearNoInputLabel() {
		noInputLabel.setVisible(false);
	}

	public void setPlayerNumber(int number) {
		playerNumberLabel.setText("Player Number: ".concat(Integer
				.toString(number)));
		playerNumberLabel.setFont(new Font("Narkisim", Font.BOLD, 25));
	}

	public void clearPlayerName() {
		textField.setText("");
	}

	/**
	 * Removes a color from the list of color options Used when a player selects
	 * a color
	 * 
	 * @param color
	 *            the color to be removed
	 */
	public void removeColor(String color) {
		ArrayList<String> newColors = new ArrayList<String>();
		for (String current : colorOptions)
			if (!current.equals(color))
				newColors.add(current);
		this.colorOptions = new String[newColors.size()];
		for (int i = 0; i < newColors.size(); i++)
			this.colorOptions[i] = newColors.get(i);
		colorBox.setModel(new DefaultComboBoxModel<String>(colorOptions));
	}

	public String getPlayerName() {
		return textField.getText();
	}

	/**
	 * Gets the race that the user selected from the race box. Returns the
	 * selection as a String, with all spaces removed so the String will match
	 * the approriate image filename.
	 * 
	 * @return Selected race with no spaces.
	 */
	public String getPlayerRace() {
		return raceBox.getSelectedItem().toString().replaceAll(" ", "");
	}

	public String getPlayerColor() {
		return colorBox.getSelectedItem().toString();
	}
	
	public String getPlayerType(){
		return humanAI.getSelectedItem().toString();
	}

	public String getName() {
		return "";
	}

	public void focusNameBox() {
		textField.requestFocusInWindow();
	}
	
	/**
	 * This is an ActionListener class used by the randomize button. When
	 * called it will fill the player attribute selection fields with a random
	 * name, race, and color.
	 *
	 */
	private class RandomizePlayer implements ActionListener{
		private String[] adjectives = {"Tired","Smart","Drunk",
									   "Awkward","Hungry","Angry","Happy",
									   "Studying","Greek","Funny","Paranoid",
									   "Worried","Frantic","Hung-Over"};
		private String[] nouns = {"Student","CS Major","AE Major",
								  "EE Major","Professor","TA","Buzz",
								  "Ramblin' Wreck","Freshman","Sophomore",
								  "Junior","Senior","Sixth-Year"};
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Random rand = new Random();
			int adjInd = rand.nextInt(adjectives.length);
			int nounInd = rand.nextInt(nouns.length);
			int raceInd = rand.nextInt(raceBox.getItemCount());
			int colorInd = rand.nextInt(colorBox.getItemCount());
			
			textField.setText(adjectives[adjInd]+" "+nouns[nounInd]);
			raceBox.setSelectedIndex(raceInd);
			colorBox.setSelectedIndex(colorInd);
		}
	}

	/**
	 * This private inner class is used as a filter by the name field. It makes
	 * sure that the user enters no more than 20 characters in the name field.
	 * 
	 */
	private class NameFieldFilter extends DocumentFilter {
		@Override
		public void insertString(DocumentFilter.FilterBypass fb, int offset,
				String string, AttributeSet attr) throws BadLocationException {
			if (fb.getDocument().getLength() + string.length() > 20)
				return;
			fb.insertString(offset, string, attr);
		}

		@Override
		public void remove(DocumentFilter.FilterBypass fb, int offset,
				int length) throws BadLocationException {
			fb.remove(offset, length);
		}

		@Override
		public void replace(DocumentFilter.FilterBypass fb, int offset,
				int length, String text, AttributeSet attrs)
				throws BadLocationException {
			if (fb.getDocument().getLength() + text.length()-length > 20)
				return;
			fb.replace(offset, length, text, attrs);
		}
	}
}
