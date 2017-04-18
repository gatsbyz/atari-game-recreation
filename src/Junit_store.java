/**
 * 
 * This Junit test tests store transaction. 
 * Made dummy player and let it buy or sell everything. 
 * Checked each of good(money, ore, mule, food, enerygy) 
 * quantity in store and player after each transaction. 
 * 
 * This test let store and player can sell more than it has.
 * However, the real game does not let it do this because combo box 
 * give error msg when player try to do that.
 * 
 * Sung Hye Jeon
 */

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.Test;


public class Junit_store {
	MULEGameEngine engine;
	MULEMap map;
 	
	public void init(int numPlayers, String difficulty, String mapType){
		engine = new MULEGameEngine(difficulty, mapType, numPlayers);
		engine.addPlayer("dummy Player", "Red", "Flapper", "HUMAN");
	}

	@Test
	public void purchaseGoodsTest() {
		int cost;
	
		// Beginner
		init(1,"Beginner", "Standard");
		engine.nextRound();
		assertEquals(engine.getCurrentRound(), 1);
		assertEquals(engine.getActivePlayer().getMoney(), 1600);
		assertEquals(engine.getActivePlayer().getMule(), 0);
		assertEquals(engine.getActivePlayer().getFood(), 8);
		assertEquals(engine.getActivePlayer().getEnergy(), 4);
		assertEquals(engine.getActivePlayer().getOre(), 0);
		assertEquals(engine.getStore().getQuantity("Food"), 16);
		assertEquals(engine.getStore().getQuantity("Energy"), 16);
		assertEquals(engine.getStore().getQuantity("Smithore"), 0);
		assertEquals(engine.getStore().getQuantity("Mules"), 25);
		assertEquals(engine.getStore().getCurrentPrice("Food"), 30);
		assertEquals(engine.getStore().getCurrentPrice("Energy"), 25);
		assertEquals(engine.getStore().getCurrentPrice("Smithore"), 50);
		assertEquals(engine.getStore().getCurrentPrice("Mules"), 100);
		
		assertEquals(engine.getActivePlayer().getMoney(), 1600);	
		engine.storeTransaction(true, "Smithore", "2");
		assertEquals(engine.getStore().getQuantity("Smithore"), 0);	
		//junit goes through, but it does not have a problem in a real game because 
		//the combo box goes let it buy or sell more than store or player has
		assertEquals(engine.getActivePlayer().getOre(), 2);
		
		engine.storeTransaction(false, "Food", "10");
		assertEquals(engine.getActivePlayer().getMoney(), 1800);		
		engine.storeTransaction(true, "Mules", "Food Mule"); 	
		

		engine.storeTransaction(true, "Food", "5");	//150
		engine.storeTransaction(true, "Energy", "2");	//50
		assertEquals(engine.getActivePlayer().getFood(), 3);
		
		assertEquals(engine.getActivePlayer().getEnergy(), 6);
		assertEquals(engine.getActivePlayer().getOre(), 2);	// this should be 0 or 2
		assertEquals(engine.getActivePlayer().getMule(), 1);
		assertEquals(engine.getActivePlayer().getMoney(), 1475);	//1175

		engine.storeTransaction(false, "Food", "1");
		assertEquals(engine.getActivePlayer().getFood(), 2);	
		assertEquals(engine.getActivePlayer().getMoney(), 1505);
		engine.storeTransaction(true, "Food", "110");
		assertEquals(engine.getActivePlayer().getFood(), 2);
		assertEquals(engine.getActivePlayer().getEnergy(), 6);
		assertEquals(engine.getActivePlayer().getOre(), 2);

		
		
		//Standard
		
		init(1,"Standard", "Standard");
		engine.nextRound();
		assertEquals(engine.getCurrentRound(), 1);
		assertEquals(engine.getActivePlayer().getMoney(), 1600);
		assertEquals(engine.getActivePlayer().getMule(), 0);
		assertEquals(engine.getActivePlayer().getFood(), 4);
		assertEquals(engine.getActivePlayer().getEnergy(), 2);
		assertEquals(engine.getActivePlayer().getOre(), 0);
		
		assertEquals(engine.getStore().getQuantity("Food"), 8);
		assertEquals(engine.getStore().getQuantity("Energy"), 8);
		assertEquals(engine.getStore().getQuantity("Smithore"), 8);
		assertEquals(engine.getStore().getQuantity("Crystite"), 0);
		assertEquals(engine.getStore().getQuantity("Mules"), 14);

		assertEquals(engine.getStore().getCurrentPrice("Food"), 30);
		assertEquals(engine.getStore().getCurrentPrice("Energy"), 25);
		assertEquals(engine.getStore().getCurrentPrice("Smithore"), 50);
		assertEquals(engine.getStore().getCurrentPrice("Crystite"), 100);
		assertEquals(engine.getStore().getCurrentPrice("Mules"), 100);
		
		assertEquals(engine.getActivePlayer().getMoney(), 1600);	
		engine.storeTransaction(true, "Smithore", "2");
		assertEquals(engine.getStore().getQuantity("Smithore"), 6);	
		assertEquals(engine.getActivePlayer().getOre(), 2);
		
		engine.storeTransaction(false, "Food", "10");
		assertEquals(engine.getActivePlayer().getMoney(), 1800);		
		engine.storeTransaction(true, "Mules", "Food Mule"); 	
		

		engine.storeTransaction(true, "Food", "5");	//150
		engine.storeTransaction(true, "Energy", "2");	//50
		
		assertEquals(engine.getActivePlayer().getEnergy(), 4);
		assertEquals(engine.getActivePlayer().getOre(), 2);
		assertEquals(engine.getActivePlayer().getMule(), 1);
		assertEquals(engine.getActivePlayer().getMoney(), 1475);	

		engine.storeTransaction(false, "Food", "1");
		assertEquals(engine.getActivePlayer().getFood(), -2);	//real game does not let it do this	
		assertEquals(engine.getActivePlayer().getMoney(), 1505);
		engine.storeTransaction(true, "Food", "110");

		
		
		init(1,"Tournament", "Standard");
		engine.nextRound();
		assertEquals(engine.getCurrentRound(), 1);
		assertEquals(engine.getActivePlayer().getMoney(), 1600);
		assertEquals(engine.getActivePlayer().getMule(), 0);
		assertEquals(engine.getActivePlayer().getFood(), 4);
		assertEquals(engine.getActivePlayer().getEnergy(), 2);
		assertEquals(engine.getActivePlayer().getOre(), 0);
		
		assertEquals(engine.getStore().getQuantity("Food"), 8);
		assertEquals(engine.getStore().getQuantity("Energy"), 8);
		assertEquals(engine.getStore().getQuantity("Smithore"), 8);
		assertEquals(engine.getStore().getQuantity("Crystite"), 0);
		assertEquals(engine.getStore().getQuantity("Mules"), 14);

		assertEquals(engine.getStore().getCurrentPrice("Food"), 30);
		assertEquals(engine.getStore().getCurrentPrice("Energy"), 25);
		assertEquals(engine.getStore().getCurrentPrice("Smithore"), 50);
		assertEquals(engine.getStore().getCurrentPrice("Crystite"), 100);
		assertEquals(engine.getStore().getCurrentPrice("Mules"), 100);
		
		assertEquals(engine.getActivePlayer().getMoney(), 1600);	
		engine.storeTransaction(true, "Smithore", "2");
		assertEquals(engine.getStore().getQuantity("Smithore"), 6);	
		assertEquals(engine.getActivePlayer().getOre(), 2);
		
		engine.storeTransaction(false, "Food", "10");
		assertEquals(engine.getActivePlayer().getMoney(), 1800);		
		engine.storeTransaction(true, "Mules", "Food Mule"); 	
		

		engine.storeTransaction(true, "Food", "5");	//150
		engine.storeTransaction(true, "Energy", "2");	//50
		
		assertEquals(engine.getActivePlayer().getEnergy(), 4);
		assertEquals(engine.getActivePlayer().getOre(), 2);
		assertEquals(engine.getActivePlayer().getMule(), 1);
		assertEquals(engine.getActivePlayer().getMoney(), 1475);	

		engine.storeTransaction(false, "Food", "1");
		assertEquals(engine.getActivePlayer().getFood(), -2);	//real game does not let it do this	
		assertEquals(engine.getActivePlayer().getMoney(), 1505);
		engine.storeTransaction(true, "Food", "110");
		
	}
	
	

}
