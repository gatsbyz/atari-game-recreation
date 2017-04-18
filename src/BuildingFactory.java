import java.awt.Color;

import javax.swing.JPanel;


public class BuildingFactory {
	
	public BuildingMenu factory(String type, JPanel panel){
		switch (type){
		case "STORE" :
			return new StoreMenu(Color.BLUE, Color.YELLOW, panel);
		case "PUB" :
			return new PubMenu(Color.GREEN, Color.BLUE, panel);
		default:
			return new LandMenu(Color.RED, Color.GREEN, panel);
		}
	}
}