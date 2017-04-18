import java.awt.Color;
import java.awt.Graphics;
//import java.awt.Image;
import java.io.Serializable;
import java.lang.reflect.Field;

import javax.swing.ImageIcon;

/**
 * Tile class for M.U.L.E. game A collection of these tiles make up the Map in
 * the game
 * 
 */
@SuppressWarnings("serial")
public class Tile implements Drawable, Serializable {

	private final int TILE_BORDER_WIDTH = 5;

	private int id; // unique ID
	private int xCoord; // x coordinate location
	private int yCoord; // y coordinate location
	private int xLoc; // x pixel location
	private int yLoc; // y pixel location
	private String type; // type of tile
	private Color ownerColor; // owner's color
	private Player owner; // player number
	private boolean vacant; // true if no owner
	private boolean wampus; // true if has wampus
	private boolean raised; // true if should be drawn raised
	@SuppressWarnings("unused")
	private char direction; // direction (used for river)
	private ImageIcon tileImage; // image for tile
	private int value;
	public String productionType;

	/**
	 * Constructor for Tile class
	 * 
	 * @param type
	 *            type of Tile ("P","R","M1","M2", or "M3")
	 * @param direction
	 *            orientation of the tile (important for river)
	 * @param id
	 *            unique id
	 */
	public Tile(String type, int id, int xCoord, int yCoord) {
		this.type = "VHB".contains(type) ? "R" : type;
		this.direction = "VHB".contains(type) ? type.charAt(0) : 'V';
		this.id = id;
		this.wampus = false;
		this.vacant = true;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.xLoc = xCoord * MapImages.TILE_SIZE.width;
		this.yLoc = yCoord * MapImages.TILE_SIZE.height;
		this.setImage();
		this.raised = false;
		this.value = 500;
		productionType = "None";
	}

	/**
	 * Set the owner of the tile
	 * 
	 * @param owner
	 *            name of the owner for the Tile
	 */
	public void setOwner(Player owner) {
		String colorname = owner.getColor();
		colorname = colorname.toUpperCase();
		this.owner = owner;
		try {
			Field field = Color.class.getField(colorname);
			this.ownerColor = (Color) field.get(null);
		} catch (Exception e) {
			this.ownerColor = null;
		}
		this.vacant = false;
	}

	/**
	 * Remove the owner of the tile. Sets the vacant value to true
	 */
	public void removeOwner() {
		this.vacant = true;
	}

	/**
	 * Set the Tile to have the wampus or to not have the wampus
	 * 
	 * @param hasWampus
	 *            true if Tile should have wampus; fals eif not
	 */
	public void setWampus(boolean hasWampus) {
		this.wampus = hasWampus;
	}

	/**
	 * Getter for the id of the Tile
	 * 
	 * @return id of Tile
	 */
	public int getID() {
		return id;
	}

	/**
	 * Getter for the type of the Tile
	 * 
	 * @return type of tile ("P","R","M1","M2", or "M3")
	 */
	public String getType() {
		if("VHBTI".contains(type)) return "R";
		return type;
	}

	/**
	 * Determines if a tile has the Wampus.
	 * 
	 * @return true if tile has wampus; false if tile does not
	 */
	public boolean hasWampus() {
		return wampus;
	}

	/**
	 * Determines if a tile is vacant
	 * 
	 * @return true if tile is vacant; false if tile is not
	 */
	public boolean isVacant() {
		return vacant;
	}

	/**
	 * Getter for the owner
	 * 
	 * @return the owner as the player #
	 */
	public Player getOwner() {
		return owner;
	}

	/**
	 * Get the y coordinate for the tile
	 * 
	 * @return y coordinate
	 */
	public int getYCoord() {
		return yCoord;
	}

	/**
	 * Get the x coordinate for the tile
	 * 
	 * @return x coordinate
	 */
	public int getXCoord() {
		return xCoord;
	}

	/**
	 * Set the tile's image based on it's type Gets the tile image from
	 * MapImages.java
	 */
	public void setImage() {
		switch (this.type) {
		case "M1":
			tileImage = new ImageIcon(MapImages.MOUNTAIN1);
			break;
		case "M2":
			tileImage = new ImageIcon(MapImages.MOUNTAIN2);
			break;
		case "M3":
			tileImage = new ImageIcon(MapImages.MOUNTAIN3);
			break;
		case "R":
			tileImage = new ImageIcon(MapImages.RIVER);
			break;
		case "T":
			tileImage = new ImageIcon(MapImages.RIVER2);
			break;	
		case "I":
			tileImage = new ImageIcon(MapImages.RIVER3);
			break;	
		
		case "Town":
			tileImage = new ImageIcon(MapImages.TOWN);
			break;
		default:
			tileImage = new ImageIcon(MapImages.PLAINS);
			break;
		}
	}

	/**
	 * Set whether the tile should be drawn raised
	 * 
	 * @param raised
	 */
	public void setRaised(boolean raised) {
		this.raised = raised;
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(tileImage.getImage(), xLoc, yLoc, null);
		if (hasMule())
			drawMule(g);
		if (!vacant)
			drawBorder(g);
		if (hasWampus())
			drawWampus(g);
		if (raised) {
			g.setColor(Color.BLACK);
			g.drawRect(xLoc, yLoc, MapImages.TILE_SIZE.width - 1,
					MapImages.TILE_SIZE.height - 1);
		}
	}

	/**
	 * Draws a border around a tile in a specified color. Used when a player
	 * owns a tile
	 * 
	 * @param color
	 *            player's color (as a String)
	 * @param xLoc
	 *            x pixel location of left side of tile
	 * @param yLoc
	 *            y pixel location of top of tile
	 * @param g
	 *            graphics object
	 */
	private void drawBorder(Graphics g) {
		g.setColor(ownerColor);
		// draw rectangle
		for (int i = 0; i < TILE_BORDER_WIDTH; i++)
			g.drawRect(xLoc + i, yLoc + i, MapImages.TILE_SIZE.width - 2 * i,
					MapImages.TILE_SIZE.height - 2 * i);
	}

	/**
	 * Sets the productionType based on how the mule is outfitted. This method
	 * also calls updateValue() to change the value of the tile Valid inputs:
	 * "Food", "Energy", "Smithore", "Crystite", "None"
	 * 
	 * @param production
	 *            the type of production
	 */
	public void setProductionType(String production) {
		productionType = production;
		updateValue();
	}

	/**
	 * Updates the value of the tile based on how it is outfitted
	 */
	private void updateValue() {
		int mule = 35;
		int startVal = 500;
		if (productionType.equalsIgnoreCase("Food"))
			value = startVal + mule + 25;
		else if (productionType.equalsIgnoreCase("Energy"))
			value = startVal + mule + 50;
		else if (productionType.equalsIgnoreCase("Smithore"))
			value = startVal + mule + 75;
		else if (productionType.equalsIgnoreCase("Crystite"))
			value = startVal + mule + 100;
		else
			value = startVal;
	}

	/**
	 * Gets the value of this tile. Value is determined by 500 + outfitPrice +
	 * (mules * 35) OutfitPrice: food 25 energy 50 smithore 75 crystite 100
	 * 
	 * @return
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Returns true if the tile has any type of mule placed on it, false if not
	 * 
	 * @return
	 */
	public boolean hasMule() {
		return (!productionType.equalsIgnoreCase("None"));
	}

	private void drawMule(Graphics g) {
		if (productionType.equalsIgnoreCase("Food"))
			g.drawImage(MapImages.FOOD_MULE, xLoc, yLoc, null);
		else if (productionType.equalsIgnoreCase("Energy"))
			g.drawImage(MapImages.ENERGY_MULE, xLoc, yLoc, null);
		else if (productionType.equalsIgnoreCase("Smithore"))
			g.drawImage(MapImages.ORE_MULE, xLoc, yLoc, null);
		else if (productionType.equalsIgnoreCase("Crystite"))
			g.drawImage(MapImages.CRYSTITE_MULE, xLoc, yLoc, null);
	}

	private void drawWampus(Graphics g) {
		int wampusX = xLoc + MapImages.TILE_SIZE.width / 2 - 12; // -12 to
																	// account
																	// for
																	// wampus
																	// width
		int wampusY = yLoc + MapImages.TILE_SIZE.height / 2 - 12; // -12 to
																	// account
																	// for
																	// wampus
																	// height
		g.drawImage(MapImages.WAMPUS, wampusX, wampusY, null);
	}

}