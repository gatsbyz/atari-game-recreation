import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * The MULEGameEngine class handles the main game objects and their interactions
 * while the game is running (ie. players, store, map, etc.)
 *
 * 
 */
@SuppressWarnings("serial")
public class MULEGameEngine implements Serializable {
	// Instance Data
	private String difficulty;
	private MULEMap map;
	private Player[] players;
	private int activePlayerInd = 0;
	private int currentRound = 0;
	private int lowestScore;
	private ArrayList<Integer> playerTurnOrder;
	private ArrayList<Integer> landGrantOrder;
	private String nextState = "";
	private int roundBonus;
	private Store store;
	private int event;
	private int poss;
	private int possibility;
	private int lotteryIndex;
	private boolean wampusCaught = false;
	private int landSellPrice, landBuyPrice;

	/**
	 * Builds a MULEGameEngine object, setting the difficulty of the game,
	 * creating the map, and initializing the empty player list.
	 * 
	 * @param difficulty
	 *            The difficulty of the game.
	 * @param mapType
	 *            The map upon which the game is to be played.
	 * @param numPlayers
	 *            The number of players in the game.
	 */
	public MULEGameEngine(String difficulty, String mapType, int numPlayers) {
		if ((difficulty != null) && (mapType != null) && (numPlayers != 0)) {
			this.difficulty = difficulty;
			map = new MULEMap(mapType);
/*
 * 			I do not know why we need all this. 
			switch (mapType) {	
			case "Standard":
				map = new MULEMap(mapType);
				break;
			case "Random":
				map = new MULEMap(mapType);
				break;
			case "River Map":
				map = new MULEMap(mapType);
				break;
			case "Plain Map":
				map = new MULEMap(mapType);
				break;
			case "Mountain Map":
				map = new MULEMap(mapType);
				break;
			case "GangNamStyle Map":
				map = new MULEMap(mapType);
				break;
				
			}
*/
			players = new Player[numPlayers];
			store = new Store(difficulty);
			playerTurnOrder = new ArrayList<Integer>();
			landGrantOrder = new ArrayList<Integer>();
		}
	}

	/**
	 * This method adds a player to the list of players; it finds the next open
	 * spot in the list, and inserts a Player object with the specified
	 * attributes in that spot. Returns a boolean that indicates whether the
	 * player was added successfully or if the list was already full.
	 * 
	 * @param name
	 *            The player's name.
	 * @param color
	 *            The player's color.
	 * @param race
	 *            The player's race.
	 * @return True if player was inserted, false if the list is full.
	 */
	public boolean addPlayer(String name, String color, String race, String type) {
		int index = getNextPlayerSlot();
		if (index == -1)
			return false;
		players[index] = new Player(name, difficulty, race, color, store, type);
		return true;
	}

	/**
	 * Gets the next open position in the list of players and returns that
	 * index. If the list is full, it returns -1.
	 * 
	 * @return The index of the next slot, or -1 if full.
	 */
	public int getNextPlayerSlot() {
		int i = 0;
		while (i < players.length && players[i] != null)
			// Get the player number (ie. next open slot in player array)
			i++;
		if (i == players.length)
			return -1;
		return i;
	}

	/**
	 * Gets the map object from the game engine.
	 * 
	 * @return The map.
	 */
	public MULEMap getMap() {
		return map;
	}

	/**
	 * Gets the players that are in the game, excluding the store.
	 * 
	 * @return An array of Player objects representing the players in the game.
	 */
	public Player[] getPlayers() {
		return players;
	}

	/**
	 * Gets the player that is currently active (taking a turn).
	 * 
	 * @return The active player.
	 */
	public Player getActivePlayer() {
		return players[activePlayerInd];
	}

	/**
	 * Gets the index of the active player
	 * 
	 * @return The index.
	 */
	public int getActivePlayerIndex() {
		return activePlayerInd;
	}

	/**
	 * Gets the current round that the game is in.
	 * 
	 * @return The current round number.
	 */
	public int getCurrentRound() {
		return currentRound;
	}

	/**
	 * Increments the currentRound variable to signify that a new round has
	 * started.
	 */
	public int nextRound() {
		currentRound++;
		calculateLandPrices();
		return currentRound;
	}

	/**
	 * Moves the active player on the map an input distance according to the
	 * state that the game is currently in.
	 * 
	 * @param distX
	 *            The distance to move the player in the x-direction.
	 * @param distY
	 *            The distance to move the player in the y-direction.
	 */
	@SuppressWarnings("static-access")
	public void movePlayer(int distX, int distY) {
		Player active = players[activePlayerInd];
		final int slowSpeed = 3;
		final int fastSpeed = 7;
		int newX;
		int newY;
		

		if (onRiverTile() || onMountainTile()) {
			newX = active.getX() + slowSpeed * distX;
			newY = active.getY() + slowSpeed * distY;
		} else {
			newX = active.getX() + fastSpeed * distX;
			newY = active.getY() + fastSpeed * distY;
		}
		
		if (map.isValidLocation(newX, newY)) {
			if (GameState.getState().equals(GameState.PLAYING_TOWN) && // Player
																		// is
																		// leaving
																		// town.
					map.isOffMap(newX, newY)) {
				map.setActiveMap(map.BIG_MAP);
				GameState.setState(GameState.PLAYING_MAP);
				active.setLocation(map.mapSwitch(newX));
				// active.setLocation(map.mapSwitchX(newX), newY);
			}
			if (GameState.getState().equals(GameState.PLAYING_MAP) && // Player
																		// is
																		// entering
																		// town.
					map.isTownTile(newX, newY)) {
				map.setActiveMap(map.TOWN_MAP);
				GameState.setState(GameState.PLAYING_TOWN);
				active.setLocation(map.mapSwitch(newX));
				// active.setLocation(map.mapSwitchX(newX), newY);
			}
			// I did this just checking if player is in the pub

			/*
			 * if(GameState.getState().equals(GameState.PLAYING_TOWN)){ //Player
			 * is entering pub if (map.isInBuilding(newX,newY)==3) {
			 * active.addMoney(getGambleMoney(30)); } }
			 */

			if (onRiverTile() || onMountainTile()) // If player is on a river or
													// mountain tile, they move
													// slower.
				active.move(slowSpeed, distX, distY);
			else if (map.isValidLocation(active.getX() + 2 * distX,
					active.getY() + 2 * distY))
				active.move(fastSpeed, distX, distY);
		}
	}

	/**
	 * Checks to see if the active player is currently on a river tile.
	 * 
	 * @return True if on river tile, false if not.
	 */
	public boolean onRiverTile() {
		Player active = players[activePlayerInd];
		return (map.isRiverTile(active.getX(), active.getY()) || map
				.isRiverTile(active.getX() + Player.PLAYER_WIDTH, active.getY()
						+ Player.PLAYER_HEIGHT));
	}

	/**
	 * Checks to see if the active player is currently on a mountain tile.
	 * 
	 * @return True if on mountain tile, false if not.
	 */
	public boolean onMountainTile() {
		Player active = players[activePlayerInd];
		return (map.isMountainTile(active.getX(), active.getY()) || map
				.isMountainTile(active.getX() + Player.PLAYER_WIDTH,
						active.getY() + Player.PLAYER_HEIGHT));
	}

	/**
	 * Purchases the tile for the active player.
	 * 
	 * @param location
	 *            pixel location for the tile
	 * @return true if the tile was purchased; false if the player did not have
	 *         enough money
	 */
	public boolean purchaseTile(Point location) {
		Tile tile = map.getTileFromLocation(location);
		return players[activePlayerInd].purchaseTile(tile, currentRound);
	}

	/**
	 * Sets the order of the players in the game based on the order of their
	 * scores.
	 */
	public void setPlayerTurnOrder() {
		landGrantOrder.clear();
		playerTurnOrder.clear();
		for (int i = 0; i < players.length; i++)
			players[i].calculateScore(store);
		landGrantOrder.add(0);
		playerTurnOrder.add(0);
		for (int i = 1; i < players.length; i++)
			for (int j = 0; j < playerTurnOrder.size(); j++) {
				if (players[i].getScore() < players[playerTurnOrder.get(j)]
						.getScore()) {
					playerTurnOrder.add(j, i);
					landGrantOrder.add(j, i);
					break;
				}
				if (j == playerTurnOrder.size() - 1) {
					playerTurnOrder.add(i);
					landGrantOrder.add(i);
					break;
				}
			}
		lowestScore = playerTurnOrder.get(0);
		nextState = GameState.START_ROUND;
	}

	/**
	 * Gets the current index of the player with the lowest score. Must be 
	 * called after setPlayerTurnOrder method.
	 * 
	 * @return Index of the player with the lowest score.
	 */
	public int getLowestScore() {
		return lowestScore;
	}

	/**
	 * Moves to the next active player and returns that player's index.
	 * 
	 * @return The index of the next active player.
	 */
	public boolean nextActivePlayerIndex() {
		if (!landGrantOrder.isEmpty()) {
			activePlayerInd = landGrantOrder.remove(0);
			nextState = GameState.LANDGRANT;
			return true;
		} else if (!playerTurnOrder.isEmpty()) {
			activePlayerInd = playerTurnOrder.remove(0);
			nextState = GameState.PLAYING_MAP;
			return true;
		}
		setPlayerTurnOrder();
		nextState = GameState.START_ROUND;
		return false;
	}

	public String getNextState() {
		return nextState;
	}

	/**
	 * Raises a tile based on the passed in location.
	 * 
	 * @param currentLocation Location of the tile to raise.
	 * @param raise
	 */
	public void raiseTile(Point currentLocation, boolean raise) {
		if (map.isBuyable(currentLocation))
			map.raiseTile(currentLocation, raise);
		else if (map.isValidMouseClick(currentLocation))
			map.raiseTile(currentLocation, false);
	}

	/**
	 * Calculates the active player's turn time (in seconds)based on the current
	 * round and the amount of food the player has.
	 * 
	 * ROUND REQUIREMENT: Round: 1 - 4 Food: 3 5 - 8 4 9 - 12 5
	 * 
	 * TIME CALCULATION: Food: 0 Time = 5 seconds 0 < Food < Requirement 30
	 * seconds Food >= Requirement: 50 seconds
	 * 
	 * @return turn time in seconds
	 */
	public int calculateActivePlayerTurnTime() {
		int activePlayersFood = players[activePlayerInd].getFood();
		int amountReqd = ((currentRound - 1) / 4) + 3;
		if (activePlayersFood < 1)
			return 5;
		else if (activePlayersFood < amountReqd) {
			players[activePlayerInd].consumeFood(activePlayersFood);
			return 30;
		} else {
			players[activePlayerInd].consumeFood(amountReqd);
			return 50;
		}
	}

	// Method for adding money after gambling - wongoo-
	public int getGambleMoney(int timeLeft) {
		int timeBonus = 0;
		if (getCurrentRound() <= 3 && getCurrentRound() >= 0)
			roundBonus = 50;
		else if (getCurrentRound() <= 7 && getCurrentRound() >= 4)
			roundBonus = 100;
		else if (getCurrentRound() <= 11 && getCurrentRound() >= 8)
			roundBonus = 150;
		else
			roundBonus = 200;

		if (timeLeft >= 37 && timeLeft <= 50)
			timeBonus = 200;
		else if (timeLeft >= 25 && timeLeft < 37)
			timeBonus = 150;
		else if (timeLeft >= 12 && timeLeft < 25)
			timeBonus = 100;
		else if (timeLeft >= 0 && timeLeft < 12)
			timeBonus = 50;

		Random rand = new Random();
		return rand.nextInt(timeBonus) + roundBonus;
	}

	public int isInBuilding() {
		int xLoc = players[activePlayerInd].getX();
		int yLoc = players[activePlayerInd].getY();
		return map.isInBuilding(xLoc, yLoc);
	}

	public Store getStore() {
		return store;
	}

	public int storeTransaction(boolean buyOrSell, String type, String quantity) {
		int cost;
		if (buyOrSell) {
			cost = players[activePlayerInd].purchaseGoods(type, quantity,
					store.getCurrentPrice(type));
			if (cost == 0) {
				if (type.equals("Mules")) {
					store.sellGoods(type, 1);
				} else {
					store.sellGoods(type, Integer.parseInt(quantity));
				}
			} else {
				return cost;
			}
		} else {
			players[activePlayerInd].sellGoods(type,
					Integer.parseInt(quantity), store.getCurrentPrice(type));
			store.buyGoods(type, Integer.parseInt(quantity));
		}
		return 0;
	}

	/**
	 * Checks whether the active player is in last place or not and then calls
	 * the appropriate random event method.
	 * 
	 * @return The random event message as a String.
	 */
	public String randomTurnEvent() {
		if (GameState.getState().equals(GameState.START_TURN)) {
			if (activePlayerInd == lowestScore)
				return randomEventForLoser();
			else
				return randomEvent();
		}
		return null;
	}

	// public method to get determinant (round bonus) depending on the current
	// round.
	public int determinant() {
		int m;
		if (getCurrentRound() > 0 && getCurrentRound() < 4)
			m = 25;
		else if (getCurrentRound() > 3 && getCurrentRound() < 8)
			m = 50;
		else if (getCurrentRound() > 7 && getCurrentRound() < 12)
			m = 75;
		else
			m = 100;
		return m;
	}
// public method for random Round event that could happen each round
	
	public String randomRoundLotteryEvent(){
		Random rand = new Random();
		Random rand2 = new Random();
		lotteryIndex = rand.nextInt(players.length);
		Player Active = players[lotteryIndex];
		possibility = rand2.nextInt(100);
		if (possibility < 10){
			Active.randomLotteryEvent();
			return ("Congrats anonymous person behind the veil won a lottery worth " + 500 + 
					"$ for the Random Round Event");
		}
		else {
			return ("No Random Round Event Happened");
		}
	}
	/*
	 * public method for random event to happen in the gameand it is possible to
	 * happen each turn for each player.
	 */
	public String randomEvent() {
		Player active = players[activePlayerInd];
		Random rand = new Random();
		Random rand2 = new Random();
		event = rand.nextInt(7);
		poss = rand2.nextInt(100);
		int m = determinant();
		if (poss < 28) {
			switch (event) {
			case 0:
				active.randomEvent1();
				return ("YOU JUST RECEIVED A PACKAGE FROM THE GT ALUMNI CONTAINING 3 FOOD AND 2 ENERGY UNITS.");
			case 1:
				active.randomEvent2();
				return ("A WANDERING TECH STUDENT REPAID YOUR HOSPITALITY BY LEAVING TWO BARS OF ORE.");
			case 2:
				active.randomEvent3(m);
				return ("THE MUSEUM BOUGHT YOUR ANTIQUE PERSONAL COMPUTER FOR $"
						+ 8 * m + ".");
			case 3:
				active.randomEvent4(m);
				return ("YOU FOUND A DEAD MOOSE RAT AND SOLD THE HIDE FOR $"
						+ 2 * m + ".");
			case 4:
				active.randomEvent5(m);
				return ("FLYING CAT-BUGS ATE THE ROOF OFF YOUR HOUSE. REPAIRS COST $"
						+ 4 * m + ".");
			case 5:
				active.randomEvent6();
				return ("MISCHIEVOUS UGA STUDENTS BROKE INTO YOUR STORAGE SHED AND STOLE HALF YOUR FOOD.");
			default:
				active.randomEvent7(m);
				return ("YOUR SPACE GYPSY INLAWS MADE A MESS OF THE TOWN. IT COST YOU $"
						+ 6 * m + " TO CLEAN IT UP.");

			}
		} else {
			return ("no random event happened");
		}

	}

	// public method for RandomEvent that only happens to the player who has the
	// lowest score.
	public String randomEventForLoser() {
		int m = determinant();
		Player active = players[activePlayerInd];
		Random rand = new Random();
		Random rand2 = new Random();
		event = rand.nextInt(4);
		poss = rand2.nextInt(100);
		if (poss < 28) {
			if (event == 0) {
				active.randomEvent1();
				return ("YOU JUST RECEIVED A PACKAGE FROM THE GT ALUMNI CONTAINING 3 FOOD AND 2 ENERGY UNITS.");
			} else if (event == 1) {
				active.randomEvent2();
				return ("A WANDERING TECH STUDENT REPAID YOUR HOSPITALITY BY LEAVING TWO BARS OF ORE.");
			} else if (event == 2) {
				active.randomEvent3(m);
				return ("THE MUSEUM BOUGHT YOUR ANTIQUE PERSONAL COMPUTER FOR $"
						+ 8 * m + ".");
			} else {
				active.randomEvent4(m);
				return ("YOU FOUND A DEAD MOOSE RAT AND SOLD THE HIDE FOR $"
						+ 2 * m + ".");
			}

		} else
	
			return ("no random event happened");

	}

	/**
	 * Returns the boolean value that represents whether the wampus has already
	 * been caught this turn.
	 * 
	 * @return True if wampus has been caught, false if not.
	 */
	public boolean hasWampusBeenCaught() {
		return wampusCaught;
	}

	public void resetWampus() {
		wampusCaught = false;
	}

	public void updateWampus() {
		if (GameState.getState() == GameState.PLAYING_MAP
				&& !hasWampusBeenCaught()) {
			Random rand = new Random();
			if (rand.nextInt(999) == 1)
				map.randomMountainWampus();
		}
	}

	/*
	 * public boolean isWampusCaught(){ Point loc =
	 * players[activePlayerInd].getCenterPoint(); if(map.isMountainTile(loc.x,
	 * loc.y) && map.getTileFromLocation(loc).hasWampus()) return true; return
	 * false; }
	 */

	/**
	 * This method checks to see if the input point is a valid wampus click. If
	 * the point is valid, the active player is given the appropriate reward and
	 * the amount rewarded is returned as an int. If the point is invalid, 0 is
	 * returned.
	 * 
	 * @param pt
	 *            The Point to be checked.
	 * @return Either the amount rewarded or 0 if the point is invalid.
	 */
	public int tryWampusClick(Point pt) {
		Tile clickedTile = map.getTileFromLocation(pt);
		System.out.print("This tile ");
		if (clickedTile.hasWampus()) {
			clickedTile.setWampus(false);
			int reward = getWampusReward();
			players[activePlayerInd].addMoney(reward);
			System.out.println(players[activePlayerInd].getMoney());
			wampusCaught = true;
			return reward;
		}
		return 0;
	}

	/**
	 * Calculates the reward for catching the wampus depending on the current
	 * round.
	 * 
	 * @return The amount to be rewarded.
	 */
	private int getWampusReward() {
		if (currentRound >= 1 && currentRound <= 3)
			return 100;
		else if (currentRound >= 4 && currentRound <= 7)
			return 200;
		else if (currentRound >= 8 && currentRound <= 11)
			return 300;
		else
			return 400;
	}
	
	public int getLandSellPrice(){
		return landSellPrice;
	}
	
	public int getLandBuyPrice(){
		return landBuyPrice;
	}
	
	private void calculateLandPrices(){
		Random rand = new Random();
		landSellPrice = 400 + rand.nextInt(201);
		landBuyPrice = 300 + currentRound * rand.nextInt(101);
	}

	public boolean purchaseTileOffice(Point location) {
		Tile tile = map.getTileFromLocation(location);
		return players[activePlayerInd].purchaseTileOffice(tile, landBuyPrice);
	}

	public boolean owenedByActive(Point location) {
		Tile tile = map.getTileFromLocation(location);
		return players[activePlayerInd].ownsTile(tile);
	}

	public void sellTileOffice(Point location) {
		Tile tile = map.getTileFromLocation(location);
		players[activePlayerInd].sellTile(tile);
		tile.removeOwner();
		players[activePlayerInd].addMoney(landSellPrice);
	}
	
	public void fulfillPurpose(){
		switch (getActivePlayer().getPurpose()){
		case AI.BUY_ENERGY_MULE :
			storeTransaction(true, "Mules", "Energy Mule");
			break;
		case AI.BUY_FOOD_MULE :
			storeTransaction(true, "Mules", "Food Mule");
			break;
		case AI.BUY_ORE_MULE :
			storeTransaction(true, "Mules", "Smithore Mule");
			break;
		}
	}
	
	public void updatePlayerScores(){
		for(Player p:players){
			p.calculateScore(store);
		}
	}
}
