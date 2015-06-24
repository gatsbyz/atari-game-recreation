import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

/**
 * MULEMainPanel is the main panel in the game which controls the current
 * display in the JFrame.
 * 
 */
@SuppressWarnings("serial")
public class MULEMainPanel extends JPanel {
	// Instance Data
	private MULEGameEngine engine;
	private Timer updater, turnTimer, screenTimer, aiTimer;
	// game startup screen.
	private StartScreen startPanel = new StartScreen();
	// The panel that allows choice of game type.
	private GameSetup gameSetupPanel = new GameSetup();
	// The panel that allows each player to make a character.
	private PlayerSetup playerSetupPanel = new PlayerSetup();
	// The panel which displays main gameplay.
	private GameplayPanel gameplayPanel = new GameplayPanel();
	// The panel displayed at the end of the game.
	private EndGamePanel endGamePanel = new EndGamePanel();
	// The panel that shows the user how to play the game.
	private HowToPlayScreen howToPlayScreen = new HowToPlayScreen();
	// CardLayout allows us to easily switch between screens.
	private CardLayout cardLayout = new CardLayout();
	private JLabel turnTime = new JLabel();
	private int countDown;
	// The screen IDs are used to tell the CardLayout which screen to display,
	// and to tell the button listeners which button was pressed.
	private final String startID = "START";
	private final String loadID = "LOAD";
	private final String gameSetupID = "GAMESETUP";
	private final String playerSetupID = "PLAYERSETUP";
	private final String gameplayID = "GAMEPLAY";
	private final String endGameID = "ENDGAME";
	private final String howToPlayID = "HOWTOPLAY";
	private final String toStartScreenID = "TOSTARTSCREEN";

	private final String SAVE_FILE = "savedGame.dat";

	private Mouse landGrantMouse = new Mouse();
	private PlayerControls arrowKeys;
	private NextScreen spaceBar;

	private JButton landGrantBtn;
	private JButton menuButton;
	private JButton landMenuSellBtn, landMenuBuyBtn, landMenuDoneBtn;
	private StorePurchaseAction storeListener;
	private NextListener landGrantListener;
	
	private LandMenuButtons landMenuListener;
	private String landMenuState = "NONE";

	/**
	 * Constructs a MULEMainPanel with a set size, adds the 4 game screens, and
	 * assigns listeners to the necessary buttons.
	 */
	public MULEMainPanel() {
		setLayout(cardLayout);
		setPreferredSize(new Dimension(900, 600));

		add(startPanel, startID);
		add(gameSetupPanel, gameSetupID);
		add(playerSetupPanel, playerSetupID);
		add(gameplayPanel, gameplayID);
		add(endGamePanel, endGameID);
		add(howToPlayScreen, howToPlayID);

		turnTime.setFont(new Font("Narkisim", Font.BOLD, 20));
		turnTime.setForeground(Color.RED);
		gameplayPanel.addLabel(turnTime);

		JButton startBtn = startPanel.getNewGameButton();
		JButton loadBtn = startPanel.getLoadGameButton();
		JButton gameSetupBtn = gameSetupPanel.getButton();
		JButton playerSetupBtn = playerSetupPanel.getButton();
		landGrantBtn = gameplayPanel.getButton();
		menuButton = gameplayPanel.getMenuButton();
		JButton mainMenuBtn = endGamePanel.getBtnMainMenu();
		JButton mainMenuFromHowToBtn = howToPlayScreen.getToStartScreenButton();
		JButton toHowToFromMainMenuBtn = startPanel.getHowToPlayButton();

		storeListener = new StorePurchaseAction();
		
		landMenuListener = new LandMenuButtons();
		landMenuSellBtn = gameplayPanel.getLandSellButton();
		landMenuBuyBtn = gameplayPanel.getLandBuyButton();
		landMenuDoneBtn = gameplayPanel.getLandMenuDoneButton();
		landMenuSellBtn.addActionListener(landMenuListener);
		landMenuBuyBtn.addActionListener(landMenuListener);
		landMenuDoneBtn.addActionListener(landMenuListener);

		startBtn.addActionListener(new NextListener(startID));
		loadBtn.addActionListener(new NextListener(loadID));
		gameSetupBtn.addActionListener(new NextListener(gameSetupID));

		playerSetupBtn.addActionListener(new NextListener(playerSetupID));
		landGrantBtn.addActionListener(new NextListener(gameplayID));
		menuButton.addActionListener(storeListener);
		mainMenuBtn.addActionListener(new NextListener(endGameID));
		mainMenuFromHowToBtn.addActionListener(new NextListener(toStartScreenID));
		toHowToFromMainMenuBtn.addActionListener(new NextListener(howToPlayID));

		startBtn.addKeyListener(new EnterKeyListener(startID));
		gameSetupBtn.addKeyListener(new EnterKeyListener(gameSetupID));

		playerSetupBtn.addKeyListener(new EnterKeyListener(playerSetupID));
		landGrantBtn.addKeyListener(new EnterKeyListener(gameplayID));
		landGrantBtn.addActionListener(new LandGrantButton());

		arrowKeys = new PlayerControls();
		spaceBar = new NextScreen();
		setFocusable(true);
	}

	/**
	 * The runGameLoop method uses an ActionListener attached to a timer to run
	 * a continual loop allowing the player to move around the screen.
	 */
	private void runGameLoop() {
		updater = new Timer(1000 / 60, new GameUpdater()); // 1000/60 means we
															// are updating at
															// 60 FPS.
		turnTimer = new Timer(1000, new TurnUpdater());
		turnTimer.start();
		if (GameState.playing())
			updater.start();
	}

	/**
	 * KeyListener class for menu screens which allows the user to press enter
	 * instead of clicking the next button.
	 * 
	 * @author Yuna Lee
	 */
	private class EnterKeyListener implements KeyListener {
		String ID2;

		public EnterKeyListener(String id) {
			ID2 = id;
		}

		public void keyTyped(KeyEvent e) {
		}

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				NextListener go = new NextListener(ID2);
				go.actionPerformed(null);
			}
		}

		public void keyReleased(KeyEvent e) {
		}
	}

	/**
	 * The NextListener class creates button listeners for the "Next" buttons on
	 * the menu screens, allowing users to switch between screens and start the
	 * game.
	 * 
	 * @author Chris Jenkins
	 */
	private class NextListener implements ActionListener {
		String ID;

		public NextListener(String id) {
			ID = id;
		}

		public void actionPerformed(ActionEvent e) {
			switch (ID) {
			case startID: // If the start button is pressed, display the game
							// setup screen.
				cardLayout.show(MULEMainPanel.this, gameSetupID);
				gameSetupPanel.focusNameBox();
				break;
			case loadID:
				engine = load(SAVE_FILE);
				// GameState.setState(GameState.PLAYING_MAP);
				if(engine!=null){
					gameplayPanel.setMapAndPlayers(engine.getMap(),
							engine.getPlayers(), engine.getStore());
					gameplayPanel.setStore(engine.getStore());
					startRoundOrTurn(false);
				}
				break;
			case gameSetupID: // If the game setup button is pressed, create
								// game engine and show player screen.
				engine = new MULEGameEngine(gameSetupPanel.getDifficulty(),
						gameSetupPanel.getMapType(),
						gameSetupPanel.getPlayerCount());
				gameplayPanel.setStore(engine.getStore());
				playerSetupPanel
						.setPlayerNumber(engine.getNextPlayerSlot() + 1);
				cardLayout.show(MULEMainPanel.this, playerSetupID);
				playerSetupPanel.focusNameBox();
				break;
			case playerSetupID: // If player button is pressed, check that
								// fields are filled, then go to next or game
								// screen.
				if (playerSetupPanel.getPlayerName().equals("")) {
					playerSetupPanel.showNoInputLabel();
					break;
				}
				playerSetupPanel.clearNoInputLabel();
				engine.addPlayer(playerSetupPanel.getPlayerName(),
						playerSetupPanel.getPlayerColor(),
						playerSetupPanel.getPlayerRace(), 
						playerSetupPanel.getPlayerType());
				int currPlayer = engine.getNextPlayerSlot();
				if (currPlayer != -1) {
					playerSetupPanel.removeColor(playerSetupPanel
							.getPlayerColor());
					playerSetupPanel.clearPlayerName();
					playerSetupPanel
							.setPlayerNumber(engine.getNextPlayerSlot() + 1);
					cardLayout.show(MULEMainPanel.this, playerSetupID);
					playerSetupPanel.focusNameBox();
				} else {
					GameState.setState(GameState.START_ROUND);
					gameplayPanel.setMapAndPlayers(engine.getMap(),
							engine.getPlayers(), engine.getStore());
					gameplayPanel.repaint();
					startRoundOrTurn(true);
				}
				break;
			case gameplayID: // if gameplay button is pushed
				if (GameState.getState().equals(GameState.LANDGRANT)) {
					GameState.setState(GameState.WAITING);
					endLandGrant();
					engine.nextActivePlayerIndex();
					startNextTurn();
				}
				break;
			case endGameID: //if main menu button is pressed on the game over screen
				gameplayPanel = new GameplayPanel();
				add(gameplayPanel, gameplayID);
				cardLayout.show(MULEMainPanel.this, startID);
				break;
			case toStartScreenID:
				cardLayout.show(MULEMainPanel.this, startID);
				break;
			case howToPlayID:
				cardLayout.show(MULEMainPanel.this, howToPlayID);
				break;
			}
		}
	}

	/**
	 * PlayerControls is a private inner class that acts as a KeyListener for
	 * the gameplayPanel; it allows the player to move around the screen using
	 * the arrow keys or WASD, as well as pause/un-pause the game.
	 * 
	 * @author Chris Jenkins (cjenkins36)
	 */
	private class PlayerControls implements KeyListener {
		@SuppressWarnings("static-access")
		public void keyPressed(KeyEvent e) {
			if ((GameState.playing()) && (!engine.getActivePlayer().isAI())) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
				case KeyEvent.VK_W:
					if (engine.isInBuilding() == -1) {
						engine.movePlayer(0, -1); // Y coords start at upper
													// left, so up is negative.
						if (engine.isInBuilding() == 2)
							gameplayPanel.displayStoreMenu();
						else if (engine.isInBuilding() == 1)
							gameplayPanel.displayLandMenu(engine.getLandBuyPrice(),
									engine.getLandSellPrice());
					}
					break;
				case KeyEvent.VK_DOWN:
				case KeyEvent.VK_S:
					if (engine.isInBuilding() == 2 || engine.isInBuilding() == 1)
						gameplayPanel.removeBuilding();
					engine.movePlayer(0, 1); // Y coords start at upper left, so
												// down is positive.
					break;
				case KeyEvent.VK_LEFT:
				case KeyEvent.VK_A:
					engine.movePlayer(-1, 0);
					break;
				case KeyEvent.VK_RIGHT:
				case KeyEvent.VK_D:
					engine.movePlayer(1, 0);
					break;
				case KeyEvent.VK_ESCAPE:
					save(SAVE_FILE);
					gameplayPanel.showMessage("Game Saved", 2000);
				case KeyEvent.VK_SPACE:
					tryPlaceMule();
				}
			}
			if (GameState.getState() == GameState.WAITING) {
				if (engine.getMap().getActiveMap()
						.equals(engine.getMap().BIG_MAP))
					GameState.setState(GameState.PLAYING_MAP);
				else
					GameState.setState(GameState.PLAYING_TOWN);
				updater.stop();
				runGameLoop();

			}

		}

		// The following methods are unused in this application but have to be
		// implemented.
		public void keyReleased(KeyEvent e) {
		}

		public void keyTyped(KeyEvent e) {
		}
	}

	/**
	 * GameUpdater is a private inner class that acts as an ActionListener. An
	 * instance of this class is meant to be attached to a timer that is used
	 * for creating a game loop.
	 * 
	 * @author Chris Jenkins (cjenkins36)
	 * 
	 */
	private class GameUpdater implements ActionListener {
		int count = 0;
		@Override
		public void actionPerformed(ActionEvent e) {
			Point aiMove;
			if (GameState.playing()) {
				if (engine.getActivePlayer().isAI()){
					if (count == 0){
						aiMove = AI.movePlayer(engine.getActivePlayer(), 
								engine.getMap().getActiveMap());
						engine.movePlayer(aiMove.x, aiMove.y);
						System.out.print(engine.getActivePlayer().getY() + " " + AI.STORE_POINT.y + "\n");
						if (aiMove.x == 0 && aiMove.y == 0){
							if (!engine.getActivePlayer().getGoal().equals(AI.PUB_POINT) && turnTimer.isRunning()){
								fulfillPurpose();
							}
						}
						count = 2;
					} else {
						count--;
					}
				}
				engine.updateWampus();
				gameplayPanel.updateScoreboard();
				gameplayPanel.repaint();
				// Change to Black Screen - Lauren
				if (3 == engine.getMap().isInBuilding(
						engine.getActivePlayer().getX(),
						engine.getActivePlayer().getY())) {
					updater.stop();
					endTurn(true);
				}
			} else {
				updater.stop();
			}
		}
	}

	/**
	 * ActionListener class used to keep track of the remaining turn time and
	 * display it on the screen.
	 * 
	 * @author John Certusi
	 */
	private class TurnUpdater implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (countDown > 0) {
				turnTime.setText(String.valueOf(countDown--));
				gameplayPanel.updateTimerLabel(turnTime);
			} else {
				turnTimer.stop();
				if (landMenuState != "NONE"){
					landMenuDoneBtn.doClick();
				}
				endTurn(false);
			}
		}
	}

	/**
	 * ActionListener class used to make the game automatically move to the next
	 * player's turn.
	 * 
	 * @author John Certusi
	 */
	private class ScreenDelay implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (countDown > 0) {
				countDown--;
			} else {
				screenTimer.stop();
				if (GameState.getState().equals(GameState.LANDGRANT)) {
					endLandGrant();
				} else if (GameState.playing()) {
					gameplayPanel.removeBuilding();
					gameplayPanel.repaint();
					startRoundOrTurn(true);
				}
			}
		}
	}

	/**
	 * The ActionListener for the transaction button in the store menu.
	 * Initiates the actual transaction between the player and store.
	 * 
	 * @author John Certusi
	 */
	private class StorePurchaseAction implements ActionListener {
		ArrayList<Object> menuEntries;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			int cost;
			menuEntries = gameplayPanel.getMenuEntries();
			cost = engine.storeTransaction(menuEntries.get(0).equals("Buy"),
					menuEntries.get(1).toString(),
					(menuEntries.get(2).toString()));
			if (cost > 0) {
				gameplayPanel.setErrorMessage(cost);
			} else {
				gameplayPanel.updateScoreboard();
			}
		}
	}

	/**
	 * This class is a mouseInputListener that is used to detect mouse actions.
	 * Currently, it is only used for the land grant state of the game
	 * 
	 * @author John Certusi (jcertusi3)
	 * 
	 */
	private class Mouse implements MouseInputListener {
		public boolean pickedTile = false;

		@Override
		public void mouseClicked(MouseEvent arg0) {
			Point location = arg0.getPoint();
			System.out.print("landMenu: " + landMenuState + "\n");
			System.out.print(pickedTile);
			if (GameState.getState().equals(GameState.LANDGRANT) && 
					engine.getMap().isValidMouseClick(location)) {
				if (landMenuState == "NONE" && !pickedTile){
					if (engine.getMap().isBuyable(location)) {
						if (engine.purchaseTile(location)) {
							pickedTile = true;
							gameplayPanel.updateScoreboard();
							gameplayPanel.removeButton();
							engine.raiseTile(location, true);
							gameplayPanel.repaint();
							screenTimer = new Timer(1000, new ScreenDelay());
							countDown = 1;
							screenTimer.start();
						}
					}
				} else if (landMenuState == "BUY"){
					if (engine.getMap().isBuyable(location)){
						if (engine.purchaseTileOffice(location)){
							engine.purchaseTileOffice(location);
							gameplayPanel.updateScoreboard();
							engine.raiseTile(location, true);
							gameplayPanel.repaint();
						}
					}
				} else if (landMenuState == "SELL"){
					if (engine.owenedByActive(location)){
						engine.sellTileOffice(location);
						gameplayPanel.updateScoreboard();
						engine.raiseTile(location, false);
						gameplayPanel.repaint();
					}
				}
			} else if (GameState.getState().equals(GameState.PLAYING_MAP)) {
				int reward = engine.tryWampusClick(location);
				if (reward > 0)
					showWampusMessage(reward);
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}

		@Override
		public void mouseDragged(MouseEvent arg0) {
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			Point location = arg0.getPoint();
			if (!pickedTile) {
				engine.raiseTile(location, true);
				gameplayPanel.repaint();
			}
		}
	}

	/**
	 * displayNextRound displays the turnStartPanel, but displays which round is
	 * about to begin. It also sets up the next round in the game engine.
	 */
	private void startNextRound() {
		if(engine.nextRound()>12)
			endGame();
		else{
			GameState.setState(GameState.START_ROUND);
			engine.setPlayerTurnOrder();
			gameplayPanel.displayNextRound(engine.getCurrentRound());
			cardLayout.show(MULEMainPanel.this, gameplayID);
			addKeyListener(spaceBar);
		}
	}
	
	/**
	 * Ends the game by setting the game state
	 */
	private void endGame(){
		GameState.setState(GameState.MENU);
		engine.updatePlayerScores(); //make sure player scores are current.
		endGamePanel.fillInfo(engine.getPlayers());
		cardLayout.show(MULEMainPanel.this, endGameID);
	}

	/**
	 * endTurn ends the player's turn while he/she is playing the map. Anytime
	 * the player's turn should end, this should be called. It calculates and
	 * applies any money gained from gambling if the player entered the Pub.
	 * 
	 * @param gamble
	 *            true if player gambled; false if otherwise (timer ran out)
	 */
	public void endTurn(boolean gamble) {
		removeKeyListener(arrowKeys);
		removeMouseListener(landGrantMouse);
		turnTime.setText("");
		turnTimer.stop();
		int gamblingMoney = 0;
		engine.getActivePlayer().setMule(Player.NO_MULE);
		engine.resetWampus();
		if (engine.getActivePlayer().isAI()){
			engine.getActivePlayer().setGoal(null, null);
		}
		if (gamble) {
			gamblingMoney = engine.getGambleMoney(countDown);
			engine.getActivePlayer().addMoney(gamblingMoney);
			gameplayPanel.displayPub(gamblingMoney);
			countDown = 3;
			screenTimer = new Timer(1000, new ScreenDelay());
			screenTimer.start();
			// turnStartPanel.setPubLabel(engine.getActivePlayer(),
			// gamblingMoney);
		} else {
			gameplayPanel.displayNoMoreTime();
			gameplayPanel.repaint();
			countDown = 2;
			screenTimer = new Timer(1000, new ScreenDelay());
			screenTimer.start();
		}
		gameplayPanel.updateScoreboard();
		gameplayPanel.repaint();
	}

	/**
	 * displayNextTurn displays the turnStartPanel and shows which player's turn
	 * is about to begin.
	 */
	private void startNextTurn() {
		removeKeyListener(spaceBar);
		System.out.print(engine.getActivePlayer().getScore() + "\n");
		GameState.setState(GameState.START_TURN);
		gameplayPanel.setActivePlayer(engine.getActivePlayer());
		gameplayPanel.displayNextTurn();
		gameplayPanel.repaint();
		cardLayout.show(MULEMainPanel.this, gameplayID);
		addKeyListener(spaceBar);
	}

	/**
	 * startLandGrant initiates the land grant stage of the turn. It must be
	 * called for each player. It displays the map and add's the MouseListeners.
	 */
	private void startLandGrant() {
		removeKeyListener(spaceBar);
		GameState.setState(GameState.LANDGRANT);
		gameplayPanel.removeScreenLabel();
		if (engine.getActivePlayer().isAI()){
			aiTimer = new Timer(1000, new AITimerListener());
			aiTimer.start();
		} else {
			if (landMenuState == "NONE"){
				gameplayPanel.setLandGrantLabel(engine.getCurrentRound());
				gameplayPanel.addLandGrantLabel(true);
				gameplayPanel.addButton();
				addMouseListener(landGrantMouse);
				addMouseMotionListener(landGrantMouse);
			} else {
				gameplayPanel.addLandMenuDoneBtn();
				landGrantBtn.addActionListener(landMenuListener);
			}
			gameplayPanel.enableButton();
			landGrantMouse.pickedTile = false;
		}
		gameplayPanel.repaint();
	}

	/**
	 * endLandGrant stops the land grant stage of a turn. It removes the
	 * MouseListeners
	 */
	private void endLandGrant() {
		removeMouseListener(landGrantMouse);
		removeMouseMotionListener(landGrantMouse);
		if (screenTimer != null)
			screenTimer.stop();
		gameplayPanel.addLandGrantLabel(false);
		gameplayPanel.removeButton();
		engine.raiseTile(new Point(0, 0), false);
		startRoundOrTurn(true);			
	}

	/**
	 * startGameLoop initializes the game loop (player moving around map). It
	 * adds the keyListener, displays the map, and calculates production.
	 */
	private void startGameLoop() {
		removeKeyListener(spaceBar);
		GameState.setState(GameState.PLAYING_MAP);
		engine.getMap().setActiveMap(MULEMap.BIG_MAP);
		gameplayPanel.removeScreenLabel();
		gameplayPanel.setActivePlayer(engine.getActivePlayer());
		engine.getActivePlayer().resetPosition();
		setFocusable(true);
		addKeyListener(arrowKeys);
		addMouseListener(landGrantMouse);
		cardLayout.show(MULEMainPanel.this, gameplayID);
		gameplayPanel.showProductionMessage(engine.getActivePlayer()
				.calculateProduction());
		countDown = engine.calculateActivePlayerTurnTime();
		turnTime.setText("" + countDown);
		gameplayPanel.updateScoreboard();

		runGameLoop();
	}

	/**
	 * Tries to place a mule on a player's property. If the player does not have
	 * a mule nothing happens, if the player is on an unowned property, the mule
	 * is lost.
	 */
	private void tryPlaceMule() {
		if (GameState.getState() == GameState.PLAYING_MAP) {
			Player active = engine.getActivePlayer();
			if (active.hasMule()) {
				Tile currTile = engine.getMap().getTileFromLocation(
						active.getCenterPoint());
				if (active.ownsTile(currTile) && !currTile.hasMule()) {
					switch (active.getMule()) {
					case Player.FOOD_MULE:
						currTile.setProductionType("Food");
						break;
					case Player.ENERGY_MULE:
						currTile.setProductionType("Energy");
						break;
					case Player.ORE_MULE:
						currTile.setProductionType("Smithore");
						break;
					}
					active.setMule(Player.NO_MULE);
				} else
					active.setMule(Player.NO_MULE);
			}
		}
	}

	/**
	 * Initiates a random turn event for the active player and prints the
	 * message to the gameplayPanel message label.
	 */
	private void randomTurnEvent() {
		gameplayPanel.showMessage(engine.randomTurnEvent());
	}
	/**
	 * Initiates a random round event for the active players and prints the
	 * message to the gameplayPanel message label.
	 */
	private void randomRoundEvent() {
		gameplayPanel.showMessage(engine.randomRoundLotteryEvent());
	}

	/**
	 * Starts the next turn/round if the input is true. If the input is false,
	 * initiates the beginning of the current round (this case is meant to be
	 * used for loading a saved game).
	 * 
	 * @param next
	 */
	private void startRoundOrTurn(boolean next) {
		System.out.println(GameState.getState());
		if (next)
			engine.nextActivePlayerIndex();
		if (engine.getNextState().equals(GameState.START_ROUND)) {
			startNextRound();
		} else {
			startNextTurn();
		}
	}

	/**
	 * Ends the land grant turn when the user presses the pass button.
	 * 
	 * @author John Certusi
	 */
	private class LandGrantButton implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			endLandGrant();
		}
	}

	/**
	 * Performs the necessary action when the user presses space to start turn.
	 * 
	 * @author John Certusi
	 */
	private class NextScreen implements KeyListener {

		@Override
		public void keyPressed(KeyEvent arg0) {
			String currState = GameState.getState();
			switch (arg0.getKeyCode()) {
			case KeyEvent.VK_SPACE:
				if (currState.equals(GameState.START_ROUND)) {
					startRoundOrTurn(true);
					randomRoundEvent();
				} else if (currState.equals(GameState.START_TURN)) {
					if (engine.getNextState().equals(GameState.LANDGRANT)) {
						startLandGrant();
					} else {
						randomTurnEvent();
						startGameLoop();
					}
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
		}
	}

	/**
	 * Shows the wampus message to the player using the gameplay panel's
	 * showMessage method.
	 * 
	 * @param reward
	 *            The amount of money the player received.
	 */
	private void showWampusMessage(int reward) {
		gameplayPanel.showMessage("You caught the wambuzz! He gave you $"
				+ reward + " as a reward!");
	}

	/**
	 * Saves the game using serialization.
	 * 
	 * @param The
	 *            name of the save file to be written to.
	 */
	private void save(String filename) {
		try {
			/*
			 * Create the object output stream for serialization. We are
			 * wrapping a FileOutputStream since we want to save to disk. You
			 * can also save to socket streams or any other kind of stream.
			 */
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(filename));

			/*
			 * The only real call we need. The stream buffers the output and
			 * reuses data, so if you are serializing very frequently, then the
			 * object values might not actually change because the old
			 * serialized object is being reused.
			 * 
			 * To fix this you can try writeUnshared() or you can reset the
			 * stream. out.reset();
			 */
			out.writeObject(engine);
			out.close();
		} catch (FileNotFoundException e) {
			gameplayPanel.showMessage("Error: Save file not found!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads a saved game from the designated save file via serialization.
	 * 
	 * @param filename
	 *            The file from which to get the saved data.
	 * @return The game engine object saved to the file.
	 */
	private MULEGameEngine load(String filename) {
		MULEGameEngine eng = null;
		try {
			// Create the input stream. Since we want to read from the disk, we
			// wrap a file stream.
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(
					filename));
			// Now read the saved game engine in with one call.
			eng = (MULEGameEngine) in.readObject();
			in.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,
					"No saved game file found, error:\n" + e, "Loading Error",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"Could not find valid load file.", "Loading Error", //e.getStackTrace()
					JOptionPane.INFORMATION_MESSAGE);
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null,
					"Game engine object not found on loading, "
							+ "error:\n" + e, "Loading Error",
					JOptionPane.INFORMATION_MESSAGE);
		}
		return eng;
	}
	
	private class LandMenuButtons implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(landMenuBuyBtn)){
				landMenuState = "BUY";
				addMouseMotionListener(landGrantMouse);
				gameplayPanel.removeBuilding();
				updater.stop();
				startLandGrant();
			} else if (e.getSource().equals(landMenuSellBtn)) {
				landMenuState = "SELL";
				addMouseMotionListener(landGrantMouse);
				gameplayPanel.removeBuilding();
				updater.stop();
				startLandGrant();
			} else if (e.getSource().equals(landMenuDoneBtn)) {
				removeMouseMotionListener(landGrantMouse);
				gameplayPanel.removeLandMenuDoneBtn();
				gameplayPanel.addLandGrantLabel(false);
				engine.raiseTile(new Point(0,0), false);
				landMenuState = "NONE";
				GameState.setState(GameState.PLAYING_TOWN);
				if (turnTimer.isRunning()){
					gameplayPanel.displayLandMenu(engine.getLandBuyPrice(), 
							engine.getLandSellPrice());
				} 
				updater.restart();
				//gameplayPanel.repaint();
			}
		}
	}	
	
	private class AITimerListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			aiTimer.stop();
			if (GameState.getState().equals(GameState.LANDGRANT)){
				AI.landGrant(engine.getActivePlayer(), engine.getMap(), 
						engine.getCurrentRound());
				gameplayPanel.repaint();
				screenTimer = new Timer(1000, new ScreenDelay());
				countDown = 1;
				screenTimer.start();
			}
		}
	}
	
	private void fulfillPurpose(){
		switch (engine.getActivePlayer().getPurpose()) {
		case AI.PLACE_MULE :
			tryPlaceMule();
			break;
		default :
			engine.fulfillPurpose();
		}
		engine.getActivePlayer().setGoal(null, null);
	}
}
