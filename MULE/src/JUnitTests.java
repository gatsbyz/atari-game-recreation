import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.Test;


public class JUnitTests {
	
	MULEGameEngine engine;
	MULEMap map;
	String[] colors = {"Red", "Blue", "Green", "Yellow"};
	String[] race = {"Flapper", "Bonzoid", "Human", "Ugaite"};
	Point[] tileLocations;
	ArrayList<Tile> tiles;
 	
	public void init(int numPlayers, String difficulty, String mapType){
		engine = new MULEGameEngine(difficulty, mapType, numPlayers);
		for (int i = 0; i < numPlayers; i++){
			engine.addPlayer(Integer.toString(i), colors[i], race[i], "HUMAN");
		}
		map = engine.getMap();
		tiles = map.getTiles();
		tileLocations = new Point[45];
		for (int i = 0; i < tileLocations.length; i++){
			tileLocations[i] = new Point((i % 9) * 100, (i / 9) * 100);
		}
	}

	/**
	 * This tests the purchaseTile(point) method in MULEGameEngine. It assumes 
	 * a tile is buyable (does not already have an owner) because that is 
	 * checked before purchaseTile(point) is called. This method checks that a
	 * the purchaseTile(point) method purchases the correct tile, updates 
	 * the tile with its new owner, and decreases the players money if the 
	 * current round is the 3rd or higher. It also checks that a player cannot
	 * purchase the tile if he does not have enough money. 
	 * 
	 * @author John Certusi (jcertusi3)
	 */
	@Test
	public void purchaseTileTest() {
		int playerMoney;
		boolean purchased;
		
		/* test if player can buy every tile initially (round 1)*/
		init(4,"Beginner", "Standard");
		engine.nextRound();
		assertEquals(engine.getCurrentRound(), 1);
		for (int i = 0 ; i < tileLocations.length; i++){
			assertTrue(tiles.get(i).isVacant());
			assertTrue(engine.purchaseTile(tileLocations[i]));
			assertEquals(engine.getActivePlayer(), tiles.get(i).getOwner());
			assertFalse(tiles.get(i).isVacant());
		}
		
		/* test if player can buy every tile initially (round 2)*/
		init(4,"Beginner", "Standard");
		engine.nextRound();
		engine.nextRound();
		assertEquals(engine.getCurrentRound(), 2);
		for (int i = 0 ; i < tileLocations.length; i++){
			assertTrue(tiles.get(i).isVacant());
			assertTrue(engine.purchaseTile(tileLocations[i]));
			assertEquals(engine.getActivePlayer(), tiles.get(i).getOwner());
			assertFalse(tiles.get(i).isVacant());
		}
		
		/* make sure a player cannot buy a tile if he does not have enough money */
		init(4,"Beginner", "Standard");
		engine.nextRound();
		engine.nextRound();
		engine.nextRound();
		assertEquals(engine.getCurrentRound(), 3);
		for (int i = 0; i < tileLocations.length; i++){
			playerMoney = engine.getActivePlayer().getMoney();
			assertTrue(tiles.get(i).isVacant());
			purchased = engine.purchaseTile(tileLocations[i]);
			assertEquals(purchased, playerMoney >= 300);
			if (purchased){
				assertEquals(engine.getActivePlayer().getMoney(), playerMoney - 300);
				assertFalse(tiles.get(i).isVacant());
				assertEquals(engine.getActivePlayer(), tiles.get(i).getOwner());
			} else {
				assertEquals(engine.getActivePlayer().getMoney(), playerMoney);
				assertTrue(tiles.get(i).isVacant());
			}	
		}
	}

}
