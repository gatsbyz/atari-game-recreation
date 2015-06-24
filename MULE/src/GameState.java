/**
 * The GameState class creates a static singleton object which can be accessed
 * at any point in the program; it keeps track of the current state of the game.
 * 
 * MENU: The game is either in the initial setup menu or is paused. WAITING: The
 * game is in gameplay mode, but is waiting for the current player to signal
 * that he is ready to start his turn. PLAYING_MAP: The game is in regular
 * gameplay mode on the overall map, i.e. current player can move around map and
 * place mules or catch the wampus. PLAYING_TOWN: The game is in gameplay mode
 * in the town, i.e. current player can move around town and spend money or
 * gamble. LAND GRANT: The game is in land grant mode, i.e. players are each
 * choosing a plot of land on the map. AUCTION: The game is in auction mode,
 * i.e. auction screen is displayed and players are bargaining for 
 */
public class GameState {
	// GAMESTATE CONSTANTS
	public static final String MENU = "MENU";
	public static final String WAITING = "WAITING";
	public static final String PLAYING_MAP = "PLAYING_MAP";
	public static final String PLAYING_TOWN = "PLAYING_TOWN";
	public static final String LANDGRANT = "LAND_GRANT";
	public static final String AUCTION = "AUCTION";
	public static final String START_ROUND = "START_ROUND";
	public static final String START_TURN = "START_TURN";

	// Instance Data
	private static GameState gamestate = null;
	private static String state = MENU;

	// This empty protected constructor prevents instantiation of singleton
	// objects
	protected GameState() {
	}

	public GameState getInstance() {
		if (gamestate == null)
			gamestate = new GameState();
		return gamestate;
	}

	public static String getState() {
		return state;
	}

	public static void setState(String st) {
		// Only set the state if the input matches one of the states below.
		switch (st) {
		case MENU:
		case WAITING:
		case PLAYING_MAP:
		case PLAYING_TOWN:
		case LANDGRANT:
		case AUCTION:
		case START_ROUND:
		case START_TURN:
			state = st;
		}
	}

	public static boolean playing() {
		return state.contains("PLAYING");
	}
}
