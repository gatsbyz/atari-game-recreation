import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * 
 * @author Yuna Lee (ylee385)
 * 
 */

@SuppressWarnings("serial")
public class Player implements Drawable, Serializable {
	// Constants
	public static final int PLAYER_WIDTH = 50;
	public static final int PLAYER_HEIGHT = 50;

	public static final int NO_MULE = 0;
	public static final int FOOD_MULE = 1;
	public static final int ENERGY_MULE = 2;
	public static final int ORE_MULE = 3;
	public static final String HUMAN = "HUMAN";
	public static final String AI = "AI";

	// Instance Data
	private String name;
	private String level;
	private String race;
	private String color;
	private String type;
	private Point goal;
	private String goalMap;

	private String message;

	private int money;
	private int food;
	private int energy;
	private int ore;
	private int crystite;
	private ImageIcon pImage;
	private ImageIcon mImage = new ImageIcon("IMAGES/mulePlaceholder.png");
	private int xCoord = 500;
	private int yCoord = 250;
	private int score;
	private ArrayList<Tile> ownedTiles;
	private int mule;
	private int muleX = xCoord;
	private int muleY = yCoord;
	private String purpose;

	public Player(String name, String level, String race, String color,
			Store store, String type) {
		this.name = name;
		this.level = level;
		this.race = race;
		this.color = color;
		this.ore = 0;
		this.ownedTiles = new ArrayList<Tile>();
		this.type = type;
		mule = NO_MULE;
		
		if (!"Test".equals(level)){
		setMoney();
		setResources();
		setImage();
		calculateScore(store);}
	}

	public void setMoney() {
		switch (race) {
		case "Flapper":
			money = 1600;
			break;
		case "Bonzoid": // All 3 of these races have same initial money, so no
						// break lines needed.
		case "Ugaite":
		case "Buzzite":
			money = 1000;
			break;
		case "Human": // Human and all "team member" races have same initial
						// money value, so don't want a break line here.
		default:
			money = 600;
			break;
		}
	}

	public void addMoney(int money) {
		this.money += money;

	}

	public void setResources() {
		switch (level) {
		case "Beginner":
			food = 8;
			energy = 4;
			break;
		case "Standard":
		case "Tournament":
			food = 4;
			energy = 2;
			break;
		}
	}

	/**
	 * Sets the player's image based on the player's race and color.
	 */
	public void setImage() {
		String imgStr = "IMAGES/" + race + "_" + color.toLowerCase()
				+ ".png";
		if(new File(imgStr).isFile())
			pImage = new ImageIcon(imgStr);
		else
			pImage = null;
	}

	/**
	 * Gets the player's image.
	 * 
	 * @return The image associated with the player.
	 */
	public ImageIcon getImage() {
		return pImage;
	}

	public String getName() {
		return name;
	}

	public String getLevel() {
		return level;
	}

	public String getRace() {
		return race;
	}

	public String getColor() {
		return color;
	}
	
	public Color getColorObject(){
		try {
			Field field = Color.class.getField(color.toUpperCase());
			return (Color) field.get(null);
		} catch (Exception e) {
			return null;
		}
	}

	public String getUpperLetteredColor() {
		return color.toUpperCase();
	}
	
	public int getMoney() {
		return money;
	}

	public int getFood() {
		return food;
	}

	public int getEnergy() {
		return energy;
	}

	public int getOre() {
		return ore;
	}

	/**
	 * Gets the current x-position of the player.
	 * 
	 * @return Current x-position.
	 */
	public int getX() {
		return xCoord;
	}

	/**
	 * Gets the current y-position of the player.
	 * 
	 * @return Current y-position.
	 */
	public int getY() {
		return yCoord;
	}

	public Point getCenterPoint() {
		return (new Point(xCoord + PLAYER_WIDTH / 2, yCoord + PLAYER_HEIGHT / 2));
	}

	/**
	 * Draws the player image at the current location with the set width and
	 * height.
	 */
	public void draw(Graphics g) {
		if (hasMule())
			g.drawImage(mImage.getImage(), muleX, muleY, PLAYER_WIDTH,
					PLAYER_HEIGHT, null, null);
		if(pImage!=null)
			g.drawImage(pImage.getImage(), xCoord, yCoord, PLAYER_WIDTH,
					PLAYER_HEIGHT, null, null);
		else{
			g.setColor(Color.BLACK);
			g.fillRect(xCoord, yCoord, PLAYER_WIDTH, PLAYER_HEIGHT);
			g.setColor(Color.WHITE);
			g.drawString("NO IMG", xCoord+5, yCoord+30);
		}
	}

	/**
	 * Sets the player's location to the input x-y coordinates.
	 * 
	 * @param x
	 *            The new x coordinate.
	 * @param y
	 *            The new y coordinate.
	 */
	public void setLocation(int x, int y) {
		xCoord = x;
		yCoord = y;
	}

	/**
	 * Set's the player's location to the input point.
	 * 
	 * @param p
	 *            The new point at which to put the player.
	 */
	public void setLocation(Point p) {
		setLocation(p.x, p.y);
	}

	/**
	 * Moves the player to a new location based on some input distance and
	 * speed.
	 * 
	 * @param speed
	 *            Desired speed by which to move the player.
	 * @param distX
	 *            Base distance to move the player in the x-direction.
	 * @param distY
	 *            Base distance to move the player in the y-direction.
	 */
	public void move(int speed, int distX, int distY) {
		int totX = speed * distX;
		int totY = speed * distY;
		xCoord += totX;
		yCoord += totY;
		// In the following lines, "tot/abs(tot)" is used to get the sign.
		if (totX != 0)
			muleX = xCoord - PLAYER_WIDTH * (totX / Math.abs(totX));
		else
			muleX = xCoord;
		if (totY != 0)
			muleY = yCoord - PLAYER_HEIGHT * (totY / Math.abs(totY));
		else
			muleY = yCoord;
	}

	public void subtractMoney(int i) {
		money -= i;
		if(money<0) money=0;
	}

	/**
	 * Purchases a tile on the map and updates the player and the purchased
	 * tile. If the round < 2 the tile is free, if not it costs 300
	 * 
	 * @param tile
	 *            tile to be purchased
	 * @param round
	 *            current round
	 * @return true if player has enough money to buy the tile; false if not
	 */
	public boolean purchaseTile(Tile tile, int round) {
		if (money >= 300 || round < 3) {
			tile.setOwner(this);
			ownedTiles.add(tile);
			money = round < 3 ? money : money - 300;
			return true;
		}
		return false;
	}
	
	public boolean purchaseTileOffice(Tile tile, int price){
		if (money >= price){
			tile.setOwner(this);
			ownedTiles.add(tile);
			money -= price;
			return true;
		}
		return false;
	}

	/**
	 * calculateScore sets the score based on: money: $1 = 1 point Land: 1 plot
	 * = 500 + outfit price; Goods: 1 mule = 35 points food, energy, smithore,
	 * crystite = current price
	 * 
	 * @param store
	 *            is used to determine the current price
	 */
	public void calculateScore(Store store) {
		score = food * store.getCurrentPrice("Food") + energy
				* store.getCurrentPrice("Energy") + ore
				* store.getCurrentPrice("Smithore") + crystite
				* store.getCurrentPrice("Crystite");
		for (int i = 0; i < ownedTiles.size(); i++)
			score += ownedTiles.get(i).getValue();
		score += money;
	}

	public int getScore() {
		return score;
	}

	public void resetPosition() {
		xCoord = 550;
		yCoord = 250;
	}

	/**
	 * This method returns the int value representing the mule that the player
	 * currently has (not placed on property). If the player currently has no
	 * unplaced mule, the method returns 0; otherwise it returns 1, 2, or 3 for
	 * food, energy, or ore mule, respectively.
	 * 
	 * @return 0 for no mule or 1, 2, or 3 for food, energy or ore mule.
	 */
	public int getMule() {
		return mule;
	}

	/**
	 * This method returns true if the player currently has any of the three
	 * types of mule. If not, returns false.
	 * 
	 * @return True if player has an unplaced mule, false if not.
	 */
	public boolean hasMule() {
		return (getMule() != NO_MULE);
	}

	public int getGoods(String selection) {
		switch (selection) {
		case "Food":
			return food;
		case "Smithore":
			return ore;
		case "Energy":
			return energy;
		}
		return -1;
	}

	/**
	 * Sets the mule variable representing the mule that is currently following
	 * the player. Input must be one of the mule constants defined in this
	 * class.
	 * 
	 * @param mule
	 *            An int value representing one of the four mule constants.
	 */
	public void setMule(int mule) {
		if (mule == NO_MULE || mule == FOOD_MULE || mule == ENERGY_MULE
				|| mule == ORE_MULE) {
			this.mule = mule;
			muleX = xCoord;
			muleY = yCoord;
		}
	}

	public int purchaseGoods(String type, String quantity, int currentPrice) {
		int cost;
		if (!type.equals("Mules")) {
			cost = Integer.parseInt(quantity) * currentPrice;
			if (money >= cost)
				money -= cost;
			else
				return cost;
		}
		if (type.equals("Food"))
			food += Integer.parseInt(quantity);
		else if (type.equals("Smithore"))
			ore += Integer.parseInt(quantity);
		else if (type.equals("Energy"))
			energy += Integer.parseInt(quantity);
		else if (type.equals("Mules")) {
			if (quantity.equals("Food Mule")) {
				if (money >= 125)
					money -= 125;
				else
					return 125;
				mule = FOOD_MULE;
			} else if (quantity.equals("Energy Mule")) {
				if (money >= 150)
					money -= 150;
				else
					return 150;
				mule = ENERGY_MULE;
			} else if (quantity.equals("Smithore Mule")) {
				if (money >= 175)
					money -= 175;
				else
					return 175;
				mule = ORE_MULE;
			}
		}
		return 0;
	}

	public void sellGoods(String type, int quantity, int currentPrice) {
		money += quantity * currentPrice;
		if (type.equals("Food"))
			food -= quantity;
		else if (type.equals("Smithore"))
			ore -= quantity;
		else if (type.equals("Energy"))
			energy -= quantity;
		else if (type.equals("Mules"))
			;
		mule = NO_MULE;
	}
	
	/**
	 * Subtracts a given quantity of the player's food; in the game, this is
	 * done when calculating the time of the player's turn.
	 * 
	 * @param quantity The amount to be consumed.
	 */
	public void consumeFood(int quantity){
		food-=quantity;
		if(food<0) food=0;
	}

	public boolean ownsTile(Tile tile) {
		return (ownedTiles.contains(tile));
	}

	public String calculateProduction() {
		String type = null;
		message = "Production: ";
		int foodP = 0;
		int energyP = 0;
		int oreP = 0;
		int crystiteP = 0;
		for (int i = 0; i < ownedTiles.size(); i++) {
			type = ownedTiles.get(i).productionType;
			if (energy > 0) {
				switch (type) {
				case "Food":
					energy--;
					switch (ownedTiles.get(i).getType()) {
					case "R":
						foodP += 4;
						break;
					case "P":
						foodP += 2;
						break;
					case "M1":
						foodP += 1;
						break;
					case "M2":
						foodP += 1;
						break;
					case "M3":
						foodP += 1;
						break;
					}
					break;
				case "Energy":
					energy--;
					switch (ownedTiles.get(i).getType()) {
					case "R":
						energyP += 2;
						break;
					case "P":
						energyP += 3;
						break;
					case "M1":
						energyP += 1;
						break;
					case "M2":
						energyP += 1;
						break;
					case "M3":
						energyP += 1;
						break;
					}
					break;
				case "Smithore":
					energy--;
					switch (ownedTiles.get(i).getType()) {
					case "P":
						oreP += 1;
						break;
					case "M1":
						oreP += 2;
						break;
					case "M2":
						oreP += 3;
						break;
					case "M3":
						oreP += 4;
						break;
					}
					break;
				case "Crystite":
					energy--;
					switch (ownedTiles.get(i).getType()) {
					case "P":
						crystiteP += 2;
						break;
					case "M1":
						crystiteP += 2;
						break;
					case "M2":
						crystiteP += 2;
						break;
					case "M3":
						crystiteP += 2;
						break;
					}
					break;
				}
			} else if (i == 0)
				return "No energy! Nothing was produced.";
		}
		if (foodP > 0) {
			food += foodP;
			message += "Food+" + foodP;
		}
		if (energyP > 0) {
			energy += energyP;
			message += " Energy+" + energyP;
		}
		if (oreP > 0) {
			ore += oreP;
			message += " Ore+" + oreP;
		}
		
		if(crystiteP>0){
			crystite+=crystiteP;
			message+=" Crystite+"+crystiteP;
		}
		if (!message.equals("Production: "))
			return message;
		return "";
	}
	public void setEnergy(int energy){
		this.energy = energy;
	}
	// list of random Round event, and turn event that could happen
	public void randomLotteryEvent(){
		money += 500;
	}
	
	public void randomEvent1() {
		food += 3;
		energy += 2;
	}

	public void randomEvent2() {
		ore += 2;
	}

	public void randomEvent3(int m) {
		money += (8 * m);
	}

	public void randomEvent4(int m) {
		money += (2 * m);
	}

	public void randomEvent5(int m) {
		money -= (4 * m);
	}

	public void randomEvent6() {
		food = food / 2;
	}

	public void randomEvent7(int m) {
		money -= (6 * m);
	}

	public void sellTile(Tile tile) {
		ownedTiles.remove(tile);
	}

	public boolean isAI(){
		return AI.equals(type);
	}
	
	public ArrayList<Tile> getOwnedTiles(){
		return ownedTiles;
	}
	
	public void setGoal(Point point, String map){
		goal = point;
		goalMap = map;
	}
	
	public Point getGoal(){
		return goal;
	}
	
	public String getGoalMap(){
		return goalMap;
	}

	public void setPurpose(String task) {
		purpose = task;
	}
	
	public String getPurpose() {
		return purpose;
	}

	
}
