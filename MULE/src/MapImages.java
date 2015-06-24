import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * This class contains all the images that make up the M.U.L.E. map Created to
 * have a central location for the images
 * 
 */
public class MapImages {

	// Town Map File
	private final static String IMAGE_FILE_TOWN_MAP = "IMAGES/(new)townInside.png";
	public final static Image TOWN_MAP = new ImageIcon(IMAGE_FILE_TOWN_MAP)
			.getImage();

	// Tile Files
	private final static String IMAGE_FILE_MOUNTAIN1 = "IMAGES/mountain_1.png";
	private final static String IMAGE_FILE_MOUNTAIN2 = "IMAGES/mountain_2.png";
	private final static String IMAGE_FILE_MOUNTAIN3 = "IMAGES/mountain_3.png";
	private final static String IMAGE_FILE_PLAINS = "IMAGES/Grass.png";
	private final static String IMAGE_FILE_RIVER = "IMAGES/River.png";
	private final static String IMAGE_FILE_TOWN = "IMAGES/techTower.png";
	private final static String IMAGE_FILE_FOOD_MULE = "IMAGES/food.gif";
	private final static String IMAGE_FILE_ENERGY_MULE = "IMAGES/energy.gif";
	private final static String IMAGE_FILE_ORE_MULE = "IMAGES/ore.gif";
	private final static String IMAGE_FILE_CRYSTITE_MULE = "IMAGES/mapCrystiteMule.png";
	private final static String IMAGE_FILE_WAMPUS = "IMAGES/wampus.png";
	private final static String IMAGE_FILE_RIVER2 = "IMAGES/River2.png";
	private final static String IMAGE_FILE_RIVER3 = "IMAGES/River3.png";
	
	public final static Image MOUNTAIN1 = new ImageIcon(IMAGE_FILE_MOUNTAIN1)
			.getImage();
	public final static Image MOUNTAIN2 = new ImageIcon(IMAGE_FILE_MOUNTAIN2)
			.getImage();
	public final static Image MOUNTAIN3 = new ImageIcon(IMAGE_FILE_MOUNTAIN3)
			.getImage();
	public final static Image PLAINS = new ImageIcon(IMAGE_FILE_PLAINS)
			.getImage();
	public final static Image RIVER = new ImageIcon(IMAGE_FILE_RIVER)
			.getImage();
	public final static Image RIVER2 = new ImageIcon(IMAGE_FILE_RIVER2)
		.getImage();
	public final static Image RIVER3 = new ImageIcon(IMAGE_FILE_RIVER3)
	.getImage();
	
	public final static Image TOWN = new ImageIcon(IMAGE_FILE_TOWN).getImage();
	public final static Dimension TILE_SIZE = new Dimension(
			MOUNTAIN1.getWidth(null), MOUNTAIN1.getHeight(null));

	public final static Image FOOD_MULE = new ImageIcon(IMAGE_FILE_FOOD_MULE)
			.getImage();
	public final static Image ENERGY_MULE = new ImageIcon(
			IMAGE_FILE_ENERGY_MULE).getImage();
	public final static Image ORE_MULE = new ImageIcon(IMAGE_FILE_ORE_MULE)
			.getImage();
	public final static Image CRYSTITE_MULE = new ImageIcon(
			IMAGE_FILE_CRYSTITE_MULE).getImage();
	public final static Image WAMPUS = new ImageIcon(IMAGE_FILE_WAMPUS)
			.getImage();

}