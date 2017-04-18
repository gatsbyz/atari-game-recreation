import static org.junit.Assert.*;

import org.junit.Test;



public class ProductionTest {

	@Test
	public void testCalculateProductionSimple() {
		Player player = new Player("SW", "Test", "Test", "Test", null, "HUMAN");
		Tile tile = new Tile("R", 0, 0, 0); // tile type "P" "R" "M1" "M2" "M3"
		tile.setProductionType("Food"); // prod type "Food" "Energy" "Smithore" "Crystite" "None"
		player.setEnergy(0);
		player.purchaseTile(tile, 1);
		assertEquals("No energy! Nothing was produced.", player.calculateProduction());
	}

	@Test
	public void testCalculateProductionSimple2() {
		Player player = new Player("SW", "Test", "Test", "Test", null, "HUMAN");
		Tile tile = new Tile("R", 0, 0, 0); // tile type "P" "R" "M1" "M2" "M3"
		tile.setProductionType("Food"); // prod type "Food" "Energy" "Smithore" "Crystite" "None"
		player.setEnergy(10);
		player.purchaseTile(tile, 1);
		assertEquals("Production: Food+4", player.calculateProduction());
	}
	
	@Test
	public void testCalculateProductionSimple3() {
		Player player = new Player("SW", "Test", "Test", "Test", null, "HUMAN");
		player.setEnergy(10);
		assertEquals("", player.calculateProduction());
	}
	
	@Test
	public void testCalculateProduction() {
		Player player = new Player("SW", "Test", "Test", "Test", null, "HUMAN");
		Tile tile1 = new Tile("P", 0, 0, 0); // tile type "P" "R" "M1" "M2" "M3"
		tile1.setProductionType("Food"); // prod type "Food" "Energy" "Smithore" "Crystite" "None"
		player.purchaseTile(tile1, 1);
		Tile tile2 = new Tile("R", 0, 0, 0); // tile type "P" "R" "M1" "M2" "M3"
		tile2.setProductionType("Energy"); // prod type "Food" "Energy" "Smithore" "Crystite" "None"
		player.purchaseTile(tile2, 1);
		Tile tile3 = new Tile("M1", 0, 0, 0); // tile type "P" "R" "M1" "M2" "M3"
		tile3.setProductionType("Smithore"); // prod type "Food" "Energy" "Smithore" "Crystite" "None"
		player.purchaseTile(tile3, 1);
		player.setEnergy(10);
		assertEquals("Production: Food+2 Energy+2 Ore+2", player.calculateProduction());
	}
	
	@Test
	public void testCalculateProduction2() {
		Player player = new Player("SW", "Test", "Test", "Test", null, "HUMAN");
		Tile tile1 = new Tile("P", 0, 0, 0); // tile type "P" "R" "M1" "M2" "M3"
		tile1.setProductionType("Food"); // prod type "Food" "Energy" "Smithore" "Crystite" "None"
		player.purchaseTile(tile1, 1);
		Tile tile2 = new Tile("R", 0, 0, 0); // tile type "P" "R" "M1" "M2" "M3"
		tile2.setProductionType("Smithore"); // prod type "Food" "Energy" "Smithore" "Crystite" "None"
		player.purchaseTile(tile2, 1);
		Tile tile3 = new Tile("M3", 0, 0, 0); // tile type "P" "R" "M1" "M2" "M3"
		tile3.setProductionType("Crystite"); // prod type "Food" "Energy" "Smithore" "Crystite" "None"
		player.purchaseTile(tile3, 1);
		player.setEnergy(10);
		assertEquals("Production: Food+2 Crystite+2", player.calculateProduction());
	}


}
