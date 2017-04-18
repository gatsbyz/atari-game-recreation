import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.lang.reflect.Field;
/**
 * Jpanel object for M.U.L.E. game that displays everything that goes on during
 * the normal game play. 1) must use setMapAndPlayers(MULEMap, Players[]) before
 * displaying anything 2) must use repaint() directly after a Tile is altered to
 * change update the panel
 */
@SuppressWarnings("serial")
public class GameplayPanel extends JPanel {
	// Building Menus
	private final String STORE = "STORE";
	private final String PUB = "PUB";
	private final String LAND = "LAND";
	private final String NONE = "NONE";
	private BuildingFactory buildingFactory = new BuildingFactory();
	private StoreMenu storeMenu;
	private PubMenu pubMenu;
	private LandMenu landMenu;
	private JButton landMenuDoneBtn;

	// Game play objects
	private MULEMap gameMap; // map of game
	private Player[] playerList; // list of players (playerList.length = # of
									// players)
	private Player activePlayer;
	private Store store;
	private JLabel[] moneyLabels = new JLabel[4];
	private JLabel[] foodLabels = new JLabel[4];
	private JLabel[] energyLabels = new JLabel[4];
	private JLabel[] oreLabels = new JLabel[4];
	private JLabel timerLabel;
	private JLabel messageLabel;
	private JLabel messageLabel2;
	private String buildingDisplayed;

	private JLabel screenLabel1;
	private JLabel spaceBarLabel;

	private JButton landGrantPass;

	/**
	 * Makes a new gameplayPanel
	 */
	public GameplayPanel() {
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		buildingDisplayed = NONE;

		storeMenu = (StoreMenu)buildingFactory.factory("STORE", this);
		pubMenu = (PubMenu)buildingFactory.factory("PUB", this);
		landMenu = (LandMenu)buildingFactory.factory("LAND", this);

		landGrantPass = new JButton("PASS");
		landGrantPass.setFont(new Font("Narkisim", Font.BOLD, 13));
		landGrantPass.setBounds(783, 471, 117, 29);
		landGrantPass.setBackground(new Color(255, 255, 255, 150));
		landGrantPass.setOpaque(true);
		landGrantPass.setContentAreaFilled(true);
		
		landMenuDoneBtn = new JButton("DONE");
		landMenuDoneBtn.setFont(new Font("Narkisim", Font.BOLD, 13));
		landMenuDoneBtn.setBounds(783, 471, 117, 29);
		landMenuDoneBtn.setBackground(new Color(255, 255, 255, 150));
		landMenuDoneBtn.setOpaque(true);
		landMenuDoneBtn.setContentAreaFilled(true);

		screenLabel1 = new JLabel();
		screenLabel1.setBounds(0, 0, 900, 600);
		screenLabel1.setFont(new Font("Narkisim", Font.BOLD, 20));
		screenLabel1.setHorizontalAlignment(JLabel.CENTER);
		screenLabel1.setVerticalAlignment(JLabel.CENTER);
		screenLabel1.setForeground(Color.WHITE);
		add(screenLabel1);

		spaceBarLabel = new JLabel();
		spaceBarLabel.setBounds(0, 200, 900, 400);
		spaceBarLabel.setFont(new Font("Narkisim", Font.BOLD, 20));
		spaceBarLabel.setHorizontalAlignment(JLabel.CENTER);
		spaceBarLabel.setVerticalAlignment(JLabel.CENTER);
		spaceBarLabel.setForeground(Color.WHITE);
		spaceBarLabel.setText("Press [SPACE] to continue!");
		add(spaceBarLabel);
		spaceBarLabel.setVisible(false);

		timerLabel = new JLabel();
		messageLabel = new JLabel();
		messageLabel.setFont(new Font("Narkisim", Font.BOLD, 16));
		messageLabel.setHorizontalAlignment(JLabel.CENTER);
		messageLabel.setForeground(Color.yellow);
		messageLabel.setBackground(new Color(0, 0, 0, 150));
		messageLabel.setOpaque(true);
		add(messageLabel);
		messageLabel2 = new JLabel();
		messageLabel2.setFont(new Font("Narkisim", Font.BOLD, 16));
		messageLabel2.setHorizontalAlignment(JLabel.CENTER);
		messageLabel2.setForeground(Color.yellow);
		messageLabel2.setBackground(new Color(0, 0, 0, 150));
		messageLabel2.setOpaque(true);
		add(messageLabel2);

	}

	public void setLandGrantLabel(int round) {
		if (round < 0)
			messageLabel.setText("");
		else if (round < 3)
			messageLabel.setText("Round " + round
					+ ": Land is free this round!");
		else
			messageLabel.setText("Round " + round
					+ ": Land costs $300 this round!");
	}

	public void addLandGrantLabel(boolean add) {
		if (add) {
			messageLabel.setBounds(250, 0, 400, 30);
			messageLabel.setVisible(true);
		} else
			messageLabel.setVisible(false);
	}

	public void addLabel(JLabel label) {
		label.setBounds(0, 0, 159, 100);
		add(label);
	}

	public void updateTimerLabel(JLabel label) {
		this.timerLabel = label;
	}

	public JButton getButton() {
		return landGrantPass;
	}

	public void setButtonText(String text) {
		landGrantPass.setText(text);
	}
	// this method sets up the scoreboard which contains user datas
	public void setUpScoreboard() {

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("IMAGES/BackGround.png"));
		lblNewLabel.setBounds(741, 500, 159, 100);
		add(lblNewLabel);

		JLabel playerMoney = null;
		JLabel playerMoney_1 = null;
		JLabel playerMoney_2 = null;
		JLabel playerMoney_3 = null;

		JLabel playerEnergy = null;
		JLabel playerEnergy_1 = null;
		JLabel playerEnergy_2 = null;
		JLabel playerEnergy_3 = null;

		JLabel playerFood = null;
		JLabel playerFood_1 = null;
		JLabel playerFood_2 = null;
		JLabel playerFood_3 = null;

		JLabel playerOre = null;
		JLabel playerOre_1 = null;
		JLabel playerOre_2 = null;
		JLabel playerOre_3 = null;
		
		JLabel playerName = null;
		JLabel playerName_1 = null;
		JLabel playerName_2 = null;
		JLabel playerName_3 = null;
		
		
		if (playerList.length == 2) {
			JLabel Name = new JLabel("Name:");
			JLabel Money = new JLabel("Money:");
			JLabel Energy = new JLabel("Energy:");
			JLabel Food = new JLabel("Food:");
			JLabel Ore = new JLabel("Ore:");
			playerName = new JLabel("" + playerList[0].getName());
			playerName_1 = new JLabel("" + playerList[1].getName());
			playerMoney = new JLabel("" + "" + playerList[0].getMoney());
			playerMoney_1 = new JLabel("" + playerList[1].getMoney());
			playerEnergy = new JLabel("" + playerList[0].getEnergy());
			playerEnergy_1 = new JLabel("" + playerList[1].getEnergy());
			playerFood = new JLabel("" + playerList[0].getFood());
			playerFood_1 = new JLabel("" + playerList[1].getFood());
			playerOre = new JLabel("" + playerList[0].getOre());
			playerOre_1 = new JLabel("" + playerList[1].getOre());

			Name.setFont(new Font("Narkisim", Font.BOLD, 20));
			Money.setFont(new Font("Narkisim", Font.BOLD, 20));
			Energy.setFont(new Font("Narkisim", Font.BOLD, 20));
			Food.setFont(new Font("Narkisim", Font.BOLD, 20));
			Ore.setFont(new Font("Narkisim", Font.BOLD, 20));

			Name.setBounds(0, 460, 180, 100);
			add(Name);
			Money.setBounds(0, 480, 180, 100);
			add(Money);
			Energy.setBounds(0, 500, 180, 100);
			add(Energy);
			Food.setBounds(0, 520, 180, 100);
			add(Food);
			Ore.setBounds(0, 540, 180, 100);
			add(Ore);

			playerName.setBounds(155, 460, 180, 100);
			add(playerName);
			playerName_1.setBounds(305, 460, 180, 100);
			add(playerName_1);
			
			playerName.setForeground(stringToColor(playerList[0].getUpperLetteredColor()));
			playerName_1.setForeground(stringToColor(playerList[1].getUpperLetteredColor()));
			
			playerMoney.setBounds(155, 480, 180, 100);
			add(playerMoney);
			playerMoney_1.setBounds(305, 480, 180, 100);
			add(playerMoney_1);

			playerEnergy.setBounds(155, 500, 180, 100);
			add(playerEnergy);
			playerEnergy_1.setBounds(305, 500, 180, 100);
			add(playerEnergy_1);

			playerFood.setBounds(155, 520, 180, 100);
			add(playerFood);
			playerFood_1.setBounds(305, 520, 180, 100);
			add(playerFood_1);

			playerOre.setBounds(155, 540, 180, 100);
			add(playerOre);
			playerOre_1.setBounds(305, 540, 180, 100);
			add(playerOre_1);

		} else if (playerList.length == 3) {
			JLabel Name = new JLabel("Name:");
			JLabel Money = new JLabel("Money:");
			JLabel Energy = new JLabel("Energy:");
			JLabel Food = new JLabel("Food:");
			JLabel Ore = new JLabel("Ore:");
			playerName = new JLabel("" + playerList[0].getName());
			playerName_1 = new JLabel("" + playerList[1].getName());
			playerName_2 = new JLabel("" + playerList[2].getName());
			playerMoney = new JLabel("" + playerList[0].getMoney());
			playerMoney_1 = new JLabel("" + playerList[1].getMoney());
			playerMoney_2 = new JLabel("" + playerList[2].getMoney());
			playerEnergy = new JLabel("" + playerList[0].getEnergy());
			playerEnergy_1 = new JLabel("" + playerList[1].getEnergy());
			playerEnergy_2 = new JLabel("" + playerList[2].getEnergy());
			playerFood = new JLabel("" + playerList[0].getFood());
			playerFood_1 = new JLabel("" + playerList[1].getFood());
			playerFood_2 = new JLabel("" + playerList[2].getFood());
			playerOre = new JLabel("" + playerList[0].getOre());
			playerOre_1 = new JLabel("" + playerList[1].getOre());
			playerOre_2 = new JLabel("" + playerList[2].getOre());

			Name.setFont(new Font("Narkisim", Font.BOLD, 20));
			Money.setFont(new Font("Narkisim", Font.BOLD, 20));
			Energy.setFont(new Font("Narkisim", Font.BOLD, 20));
			Food.setFont(new Font("Narkisim", Font.BOLD, 20));
			Ore.setFont(new Font("Narkisim", Font.BOLD, 20));

			Name.setBounds(0, 460, 180, 100);
			add(Name);
			Money.setBounds(0, 480, 180, 100);
			add(Money);
			Energy.setBounds(0, 500, 180, 100);
			add(Energy);
			Food.setBounds(0, 520, 180, 100);
			add(Food);
			Ore.setBounds(0, 540, 180, 100);
			add(Ore);

			playerName.setBounds(155, 460, 180, 100);
			add(playerName);
			playerName_1.setBounds(305, 460, 180, 100);
			add(playerName_1);
			playerName_2.setBounds(455, 460, 180, 100);
			add(playerName_2);

			playerName.setForeground(stringToColor(playerList[0].getUpperLetteredColor()));
			playerName_1.setForeground(stringToColor(playerList[1].getUpperLetteredColor()));
			playerName_2.setForeground(stringToColor(playerList[2].getUpperLetteredColor()));
			
			
			playerMoney.setBounds(155, 480, 180, 100);
			add(playerMoney);
			playerMoney_1.setBounds(305, 480, 180, 100);
			add(playerMoney_1);
			playerMoney_2.setBounds(455, 480, 180, 100);
			add(playerMoney_2);

			playerEnergy.setBounds(155, 500, 180, 100);
			add(playerEnergy);
			playerEnergy_1.setBounds(305, 500, 180, 100);
			add(playerEnergy_1);
			playerEnergy_2.setBounds(455, 500, 180, 100);
			add(playerEnergy_2);

			playerFood.setBounds(155, 520, 180, 100);
			add(playerFood);
			playerFood_1.setBounds(305, 520, 180, 100);
			add(playerFood_1);
			playerFood_2.setBounds(455, 520, 180, 100);
			add(playerFood_2);

			playerOre.setBounds(155, 540, 180, 100);
			add(playerOre);
			playerOre_1.setBounds(305, 540, 180, 100);
			add(playerOre_1);
			playerOre_2.setBounds(455, 540, 180, 100);
			add(playerOre_2);
		}

		else {
			JLabel Name = new JLabel("Name:");
			JLabel Money = new JLabel("Money:");
			JLabel Energy = new JLabel("Energy:");
			JLabel Food = new JLabel("Food:");
			JLabel Ore = new JLabel("Ore:");

			playerName = new JLabel("" + playerList[0].getName());
			playerName_1 = new JLabel("" + playerList[1].getName());
			playerName_2 = new JLabel("" + playerList[2].getName());
			playerName_3 = new JLabel("" + playerList[3].getName());
			playerMoney = new JLabel("" + playerList[0].getMoney());
			playerMoney_1 = new JLabel("" + playerList[1].getMoney());
			playerMoney_2 = new JLabel("" + playerList[2].getMoney());
			playerMoney_3 = new JLabel("" + playerList[3].getMoney());

			playerEnergy = new JLabel("" + playerList[0].getEnergy());
			playerEnergy_1 = new JLabel("" + playerList[1].getEnergy());
			playerEnergy_2 = new JLabel("" + playerList[2].getEnergy());
			playerEnergy_3 = new JLabel("" + playerList[3].getEnergy());

			playerFood = new JLabel("" + playerList[0].getFood());
			playerFood_1 = new JLabel("" + playerList[1].getFood());
			playerFood_2 = new JLabel("" + playerList[2].getFood());
			playerFood_3 = new JLabel("" + playerList[3].getFood());

			playerOre = new JLabel("" + playerList[0].getOre());
			playerOre_1 = new JLabel("" + playerList[1].getOre());
			playerOre_2 = new JLabel("" + playerList[2].getOre());
			playerOre_3 = new JLabel("" + playerList[3].getOre());

			Name.setFont(new Font("Narkisim", Font.BOLD, 20));
			Money.setFont(new Font("Narkisim", Font.BOLD, 20));
			Energy.setFont(new Font("Narkisim", Font.BOLD, 20));
			Food.setFont(new Font("Narkisim", Font.BOLD, 20));
			Ore.setFont(new Font("Narkisim", Font.BOLD, 20));

			Name.setBounds(0, 460, 180, 100);
			add(Name);
			Money.setBounds(0, 480, 180, 100);
			add(Money);
			Energy.setBounds(0, 500, 180, 100);
			add(Energy);
			Food.setBounds(0, 520, 180, 100);
			add(Food);
			Ore.setBounds(0, 540, 180, 100);
			add(Ore);

			playerName.setBounds(155, 460, 180, 100);
			add(playerName);
			playerName_1.setBounds(305, 460, 180, 100);
			add(playerName_1);
			playerName_2.setBounds(455, 460, 180, 100);
			add(playerName_2);
			playerName_3.setBounds(610, 460, 180, 100);
			add(playerName_3);

			playerName.setForeground(stringToColor(playerList[0].getUpperLetteredColor()));
			playerName_1.setForeground(stringToColor(playerList[1].getUpperLetteredColor()));
			playerName_2.setForeground(stringToColor(playerList[2].getUpperLetteredColor()));
			playerName_3.setForeground(stringToColor(playerList[3].getUpperLetteredColor()));
			
			
			playerMoney.setBounds(155, 480, 180, 100);
			add(playerMoney);
			playerMoney_1.setBounds(305, 480, 180, 100);
			add(playerMoney_1);
			playerMoney_2.setBounds(455, 480, 180, 100);
			add(playerMoney_2);
			playerMoney_3.setBounds(610, 480, 180, 100);
			add(playerMoney_3);

			playerEnergy.setBounds(155, 500, 180, 100);
			add(playerEnergy);
			playerEnergy_1.setBounds(305, 500, 180, 100);
			add(playerEnergy_1);
			playerEnergy_2.setBounds(455, 500, 180, 100);
			add(playerEnergy_2);
			playerEnergy_3.setBounds(610, 500, 180, 100);
			add(playerEnergy_3);

			playerFood.setBounds(155, 520, 180, 100);
			add(playerFood);
			playerFood_1.setBounds(305, 520, 180, 100);
			add(playerFood_1);
			playerFood_2.setBounds(455, 520, 180, 100);
			add(playerFood_2);
			playerFood_3.setBounds(610, 520, 180, 100);
			add(playerFood_3);

			playerOre.setBounds(155, 540, 180, 100);
			add(playerOre);
			playerOre_1.setBounds(305, 540, 180, 100);
			add(playerOre_1);
			playerOre_2.setBounds(455, 540, 180, 100);
			add(playerOre_2);
			playerOre_3.setBounds(610, 540, 180, 100);
			add(playerOre_3);
		
		
		}

		moneyLabels[0] = playerMoney;
		moneyLabels[1] = playerMoney_1;
		if (playerMoney_2 != null)
			moneyLabels[2] = playerMoney_2;
		if (playerMoney_3 != null)
			moneyLabels[3] = playerMoney_3;

		foodLabels[0] = playerFood;
		foodLabels[1] = playerFood_1;
		if (playerMoney_2 != null)
			foodLabels[2] = playerFood_2;
		if (playerMoney_3 != null)
			foodLabels[3] = playerFood_3;

		energyLabels[0] = playerEnergy;
		energyLabels[1] = playerEnergy_1;
		if (playerMoney_2 != null)
			energyLabels[2] = playerEnergy_2;
		if (playerMoney_3 != null)
			energyLabels[3] = playerEnergy_3;

		oreLabels[0] = playerOre;
		oreLabels[1] = playerOre_1;
		if (playerMoney_2 != null)
			oreLabels[2] = playerOre_2;
		if (playerMoney_3 != null)
			oreLabels[3] = playerOre_3;
	
		
	}

	/**
	 * Sets the map and player list inside the panel. Needed to display the map
	 * and Tile characteristics
	 * 
	 * @param gameMap
	 *            map for the game
	 * @param playerList
	 *            players in the game
	 */
	public void setMapAndPlayers(MULEMap gameMap, Player[] playerList,
			Store store) {
		this.gameMap = gameMap;
		this.playerList = playerList;
		this.store = store;
		setUpScoreboard();
	}

	/**
	 * Sets the activePlayer instance variable to the input player object.
	 * 
	 * @param p
	 *            The player to set.
	 */
	public void setActivePlayer(Player p) {
		activePlayer = p;
	}

	public void disableButton() {
		if (landGrantPass.isEnabled())
			landGrantPass.setEnabled(false);
	}

	public void enableButton() {
		if (!landGrantPass.isEnabled())
			landGrantPass.setEnabled(true);
	}
	// this method updates resources in the scoreboard
	public void updateScoreboard() {
		for (int i = 0; i < playerList.length; i++) {
			moneyLabels[i].setText("" + playerList[i].getMoney());
		}
		for (int i = 0; i < playerList.length; i++) {
			energyLabels[i].setText("" + playerList[i].getEnergy());
		}
		for (int i = 0; i < playerList.length; i++) {
			foodLabels[i].setText("" + playerList[i].getFood());
		}
		for (int i = 0; i < playerList.length; i++) {
			oreLabels[i].setText("" + playerList[i].getOre());

		}
	}

	public void removeScreenLabel() {
		screenLabel1.setVisible(false);
		spaceBarLabel.setVisible(false);
	}

	public void displayPause() {
		screenLabel1.setText("PAUSED - Press any key to continue");
		screenLabel1.setVisible(true);
	}

	public void displayNextRound(int round) {
		screenLabel1.setText("Starting Round " + round);
		screenLabel1.setVisible(true);
		spaceBarLabel.setVisible(true);
	}

	public void displayNextTurn() {
		screenLabel1.setText(activePlayer.getName() + ", it is your turn!");
		screenLabel1.setVisible(true);
		spaceBarLabel.setVisible(true);
	}

	public void displayNoMoreTime() {
		screenLabel1.setText(activePlayer.getName()
				+ ", you ran out of Time! -- You're turn is over");
		screenLabel1.setVisible(true);
	}

	/**
	 * Overrides the panel's paintComponent Method This method should not be
	 * called directly. Instead use repaint()
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (this.gameMap == null)
			return;
		gameMap.draw(g);
		if (GameState.playing()) {
			activePlayer.draw(g);
			if (buildingDisplayed.equals(STORE))
				storeMenu.draw(g);
			else if (buildingDisplayed.equals(PUB))
				pubMenu.draw(g);
			else if (buildingDisplayed.equals(LAND))
				landMenu.draw(g);
		} else if (GameState.getState().equals(GameState.LANDGRANT)) {
		}
		if (screenLabel1.isVisible()) {
			g.setColor(new Color(0, 0, 0, 220));
			g.fillRect(0, 0, 900, 600);
		}
	}

	public void displayPub(int winnings) {
		buildingDisplayed = PUB;
		pubMenu.displayPub(activePlayer, winnings);
	}

	public void removeBuilding() {
		if (buildingDisplayed.equals(STORE))
			storeMenu.removeStoreMenu();
		else if (buildingDisplayed.equals(PUB))
			pubMenu.removePub();
		else if (buildingDisplayed.equals(LAND))
			landMenu.removeLandMenu();
		buildingDisplayed = NONE;
	}

	public void displayStoreMenu() {
		buildingDisplayed = STORE;
		storeMenu.displayStoreMenu(activePlayer, store);
	}
	
	public void displayLandMenu(int buy, int sell){
		buildingDisplayed = LAND;
		landMenu.displayLandMenu(buy, sell);
	}

	public void setStore(Store store) {
		this.store = store;
		storeMenu.setStore(store);
	}

	public ArrayList<Object> getMenuEntries() {
		return storeMenu.getEntries();
	}

	public JButton getMenuButton() {
		return storeMenu.getButton();
	}

	public void removeButton() {
		remove(landGrantPass);
	}

	public void addButton() {
		add(landGrantPass);
	}

	public void setErrorMessage(int cost) {
		storeMenu.setErrorMessage(cost);
	}

	/**
	 * Shows an input message in the message box at the top of the screen for a
	 * specified amount of time.
	 * 
	 * @param message
	 *            The message to be displayed.
	 * @param time
	 *            The amount of time to display the message in milliseconds.
	 */
	public void showMessage(String message, int time) {
		int locX = 0;
		messageLabel.setText(message);
		messageLabel.setSize(messageLabel.getPreferredSize());
		if (!message.equals("Game Saved"))
			locX = this.getWidth() / 2 - messageLabel.getWidth() / 2;
		messageLabel.setLocation(locX, 0);
		Timer hider = new Timer(time, new MessageLabelHider(1));
		hider.setRepeats(false);
		messageLabel.setVisible(true);
		hider.start();
	}

	/**
	 * Shows an input message in the message box at the top of the screen for 3
	 * seconds.
	 * 
	 * @param message
	 *            The message to be displayed.
	 */
	public void showMessage(String message) {
		showMessage(message, 5000);
	}

	public void showProductionMessage(String message) {
		showProductionMessage(message, 5000);
	}

	public void showProductionMessage(String message, int time) {
		messageLabel2.setText(message);
		messageLabel2.setSize(messageLabel2.getPreferredSize());
		int locX = this.getWidth() / 2 - messageLabel2.getWidth() / 2;

		messageLabel2.setLocation(locX, 25);
		Timer hider1 = new Timer(time, new MessageLabelHider(2));
		hider1.setRepeats(false);
		messageLabel2.setVisible(true);
		hider1.start();
	}

	/**
	 * A private ActionListener class used by the timer in showMessage to hide
	 * the messageLabel object.
	 * 
	 * @author Chris Jenkins (cjenkins36)
	 * 
	 */
	private class MessageLabelHider implements ActionListener {
		int id;

		private MessageLabelHider(int id) {
			this.id = id;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (id == 1) {
				GameplayPanel.this.messageLabel.setVisible(false);
				GameplayPanel.this.messageLabel.setText("");
			} else if (id == 2) {
				GameplayPanel.this.messageLabel2.setVisible(false);
				GameplayPanel.this.messageLabel2.setText("");
			}
		}
		
		//�� 
	}
	
	public JButton getLandSellButton(){
		return landMenu.getSellButton();
	}
	
	public JButton getLandBuyButton(){
		return landMenu.getBuyButton();
	}
	
	public JButton getLandMenuDoneButton(){
		return landMenuDoneBtn;
	}

	public void removeLandMenuDoneBtn() {
		remove(landMenuDoneBtn);
	}
	
	public void addLandMenuDoneBtn(){
		add(landMenuDoneBtn);
	}
	
	// converts string to color
	
	public static Color stringToColor(final String value) {
	    if (value == null) {
	      return Color.black;
	    }
	    try {
	      // get color by hex or octal value
	      return Color.decode(value);
	    } catch (NumberFormatException nfe) {
	      // if we can't decode lets try to get it by name
	      try {
	        // try to get a color by name using reflection
	        final Field f = Color.class.getField(value);

	        return (Color) f.get(null);
	      } catch (Exception ce) {
	        // if we can't get any color return black
	        return Color.black;
	      }
	    }
	  }
}
