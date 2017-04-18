import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.Test;


public class RandomTurnEventTest {
	MULEGameEngine engine;
	MULEMap map;
	String[] colors = {"Red", "Blue", "Green", "Yellow"};
	String[] race = {"Flapper", "Bonzoid", "Human", "Ugaite"};
	Point[] tileLocations;
	ArrayList<Tile> tiles;
 	
	/**
	 * Sets up dummy objects to mimic real game objects.
	 * 
	 * @param numPlayers The number of dummy players to be created.
	 * @param difficulty The difficulty for the dummy game.
	 * @param mapType The type of the dummy map.
	 */
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
	 * Tests that the game engine correctly gets the player with the lowest score.
	 */
	@Test
	public void testLowestScore() {
		init(4,"Beginner", "Standard");
		engine.setPlayerTurnOrder();
		int lowInd = engine.getLowestScore();
		assertEquals("Human", engine.getPlayers()[lowInd].getRace());
	}
	
	/**
	 * Tests that a player that is in last place can not have bad random events.
	 */
	@Test
	public void testLoserRandom200(){
		init(4, "Beginner", "Standard");
		//Set the turn order based on values of score.
		engine.setPlayerTurnOrder();
		//Get the index of the player in last place.
		int lowInd = engine.getLowestScore();
		//Set the active player to be the player in last place.
		while(engine.getActivePlayerIndex()!=lowInd)
			engine.nextActivePlayerIndex();
		Player active = engine.getActivePlayer();
		//Check to make sure the player in last place is now active.
		assertEquals(engine.getPlayers()[lowInd], active);
		active.calculateScore(engine.getStore());
		int currScore;
		GameState.setState(GameState.START_TURN);
		//Call the random turn event method 200 times, making sure a negative
		//random event never happens.
		for(int i=0; i<200; i++){
			currScore = active.getScore();
			engine.randomTurnEvent();
			active = engine.getActivePlayer();
			active.calculateScore(engine.getStore());
			assertTrue(active.getScore()>=currScore);
		}
	}
	/**
	 * Tests that a player who is not in last place can have good or bad random events.
	 */
	@Test (timeout=300) //Set a timeout value of 0.3s in case of infinite while loop.
	public void testWinnerRandom(){
		init(4, "Beginner", "Standard");
		//Set the turn order based on values of score.
		engine.setPlayerTurnOrder();
		//Get the index of the player in last place.
		int lowInd = engine.getLowestScore();
		//Set the active player to be a player NOT in last place.
		if(engine.getActivePlayerIndex()==lowInd)
			engine.nextActivePlayerIndex();
		Player active = engine.getActivePlayer();
		//Check to make sure the active player is NOT in last place.
		assertNotEquals(engine.getPlayers()[lowInd], active);
		active.calculateScore(engine.getStore());
		int currScore;
		boolean pos = false;
		boolean neg = false;
		GameState.setState(GameState.START_TURN);
		//Call the random turn event method 200 times, making sure both negative
		//and positive random events happen.
		while(!pos || !neg){
			currScore = active.getScore();
			engine.randomTurnEvent();
			active = engine.getActivePlayer();
			active.calculateScore(engine.getStore());
			if(active.getScore()>currScore) pos=true;
			if(active.getScore()<currScore) neg=true;
		}
		assertTrue(pos && neg);
	}
}
