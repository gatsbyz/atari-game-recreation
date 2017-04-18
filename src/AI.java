import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;


public class AI {
	private static final String BIG_MAP = "BIGMAP";
	private static final String TOWN_MAP = "TOWNMAP";
	public static final String BUY_ENERGY_MULE = "BUY_ENERGY_MULE";
	public static final String BUY_FOOD_MULE = "BUY_FOOD_MULE";
	public static final String BUY_ORE_MULE = "BUY_ORE_MULE";
	public static final String PLACE_MULE = "PLACE_MULE";
	private static final Point TOWN_POINT = new Point(450,250);
	public static final Point STORE_POINT = 
			new Point(562, 256 - Player.PLAYER_HEIGHT / 2);
	public static final Point PUB_POINT = 	new Point(786 , 256 - Player.PLAYER_HEIGHT);
	private static final int Y_TOLERANCE = 5;
	private static final int X_TOLERANCE = 3;
	
	public static boolean landGrant(Player player, MULEMap map, int round){
		Tile tileToBuy;
		String tilePickMethod = "RANDOM";
			
		if ((round > 2) && (player.getMoney() < 600))
			return false;
		else 
			tileToBuy = pickTile(player, map, tilePickMethod);
		if (tileToBuy == null)
			return false;
		return player.purchaseTile(tileToBuy, round);
	} 

	private static Tile pickTile(Player player, MULEMap map, String type) {
		if (type.equals("RANDOM")){
			return pickTileRandom(player, map, type);
		}
		return null;
	}

	private static Tile pickTileRandom(Player player, MULEMap map, String type) {
		Random rand = new Random();
		ArrayList<Tile> tileList = map.getTiles();
		ArrayList<Tile> availableTiles = new ArrayList<Tile>();
		
		for (Tile t : tileList)
			if ((!t.getType().equals("Town")) && (t.isVacant()))
				availableTiles.add(t);
			
		return availableTiles.get(rand.nextInt(availableTiles.size()));
	}

	public static Point movePlayer(Player activePlayer, String currentMap) {
		Point move;
		if (activePlayer.getGoal() == null)
			findNextGoal(activePlayer);
		if (currentMap.equals(BIG_MAP)){
			if (activePlayer.getGoalMap().equals(BIG_MAP)){
				move =  moveToPoint(activePlayer.getGoal(), activePlayer);
			} else {
				move =  moveToPoint(TOWN_POINT, activePlayer);
			}
		} else {
			if (activePlayer.getGoalMap().equals(TOWN_MAP)){
				move =  moveToPointInTown(activePlayer.getGoal(), activePlayer);
			} else {
				move =  moveToBigMap(activePlayer.getGoal(),activePlayer);
			}
		} 
		return move;
	}

	private static Point moveToBigMap(Point goal, Player activePlayer) {
		Point move = new Point(0,0);
		if (activePlayer.getY() > 375 + Y_TOLERANCE){
			move.y = -1;
		} else if (activePlayer.getY() < 375 - Y_TOLERANCE) {
			move.y = 1;
		} else if (goal.x < 450){
			move.x = -1;
		} else if (goal.x >= 450){
			move.x = 1;
		}
		return move;
	}

	private static Point moveToPoint(Point goal, Player activePlayer) {
		Point move = new Point(0,0);
		if (activePlayer.getY() + Player.PLAYER_HEIGHT/2 > goal.y + Y_TOLERANCE){
			move.y = -1;
		} else if (activePlayer.getY() + Player.PLAYER_HEIGHT/2 < goal.y - Y_TOLERANCE) {
			move.y = 1;
		} else if (activePlayer.getX() + Player.PLAYER_WIDTH/2 > goal.x + X_TOLERANCE){
			move.x = -1;
		} else if (activePlayer.getX() + Player.PLAYER_WIDTH/2 < goal.x - X_TOLERANCE){
			move.x = 1;
		}
		return move;
	}
	
	private static Point moveToPointInTown(Point goal, Player activePlayer) {
		Point move = new Point(0,0);

		if (activePlayer.getX() + Player.PLAYER_WIDTH/2 > goal.x + X_TOLERANCE){
			move.x = -1;
		} else if (activePlayer.getX() + Player.PLAYER_WIDTH/2 < goal.x - X_TOLERANCE){
			move.x = 1;
		} else if (activePlayer.getY() > goal.y + Y_TOLERANCE){
			move.y = -1;
		} else if (activePlayer.getY() < goal.y - Y_TOLERANCE) {
			move.y = 1;
		}
		return move;
	}

	private static void findNextGoal(Player activePlayer) {
		Random rand = new Random();
		Tile noMuleTile;
		Point nextPoint = new Point();
		ArrayList<Tile> tileList = activePlayer.getOwnedTiles();
		int numEnergyTiles = 0;
		int numFoodTiles = 0;
		noMuleTile = checkAllTilesMule(activePlayer);
		if (noMuleTile != null && (activePlayer.getMoney() >= 200 || activePlayer.hasMule())){
			if (activePlayer.hasMule()){
				nextPoint.x = noMuleTile.getXCoord() * 100 + 50;
				nextPoint.y = noMuleTile.getYCoord() * 100 + 50;
				activePlayer.setGoal(nextPoint, BIG_MAP);
				activePlayer.setPurpose(PLACE_MULE);
			} else {
				activePlayer.setGoal(STORE_POINT, TOWN_MAP);
				if (noMuleTile.getType().equals("P")){
					for (Tile t : tileList)
						if (t.productionType.equals("Energy")) numEnergyTiles++;
						else if (t.productionType.equals("Food")) numFoodTiles++;
					if (numFoodTiles == numEnergyTiles)
						if (rand.nextInt(2) == 1) numFoodTiles++;
						else numEnergyTiles++;
					if (numFoodTiles > numEnergyTiles) 
						activePlayer.setPurpose(BUY_ENERGY_MULE);
					else 
						activePlayer.setPurpose(BUY_FOOD_MULE);
				} else if (noMuleTile.getType().equals("R")){
					activePlayer.setPurpose(BUY_FOOD_MULE);
				} else {
					activePlayer.setPurpose(BUY_ORE_MULE);
				}
			}
		} else {
			activePlayer.setGoal(PUB_POINT, TOWN_MAP);
		}
	}

	private static Tile checkAllTilesMule(Player activePlayer) {
		ArrayList<Tile> aiTiles = activePlayer.getOwnedTiles();
		for (Tile t : aiTiles){
			if (!t.hasMule())
				return t;
		}
		return null;
	}

}
