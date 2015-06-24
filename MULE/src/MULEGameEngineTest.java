import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;
import org.junit.runners.JUnit4;


public class MULEGameEngineTest {
	private MULEGameEngine engine = new MULEGameEngine(null,null,0);
	Random rand = new Random();
	private int timeleft;
    private int counter = rand.nextInt(12);
    
	
	
    
    /* Testing GetGambleMoney() in MULEGameEngine Class.
     * The method generates random bonuses depending on timeleft and currentround
     * This JUnit test check if the method works correctly under random numbers in boundary 
     * for time and currentRound. 
     * @author WongooShim
     */
	@Test
	public final void testGetGambleMoney() {
		timeleft = rand.nextInt(50);
		for(int i=0; i<=counter;i++){
	    	engine.nextRound();
	    }
		int result = engine.getGambleMoney(timeleft);
		//System.out.println(timeleft);
		//System.out.println(engine.getCurrentRound());
		//System.out.println(result);
		if ((timeleft >= 37 && timeleft <= 50) && (engine.getCurrentRound() <= 3 && engine.getCurrentRound() >= 0))
			assertTrue(result<=250);
		else if ((timeleft >= 37 && timeleft <= 50) && (engine.getCurrentRound() <= 7 && engine.getCurrentRound() >= 4))
			assertTrue(result<=300);
		else if ((timeleft >= 37 && timeleft <= 50) && (engine.getCurrentRound() <= 11 && engine.getCurrentRound() >= 8))
			assertTrue(result<=350);
		else if ((timeleft >= 37 && timeleft <= 50) && (engine.getCurrentRound() >= 12))
			assertTrue(result<=400);
		else if ((timeleft >= 25 && timeleft < 37) && (engine.getCurrentRound() <= 3 && engine.getCurrentRound() >= 0))
			assertTrue(result<=200);
		else if ((timeleft >= 25 && timeleft < 37) && (engine.getCurrentRound() <= 7 && engine.getCurrentRound() >= 4))
			assertTrue(result<=250);
		else if ((timeleft >= 25 && timeleft < 37) && (engine.getCurrentRound() <= 11 && engine.getCurrentRound() >= 8))
			assertTrue(result<=300);
		else if ((timeleft >= 25 && timeleft < 37) && (engine.getCurrentRound() >= 12))
			assertTrue(result<=350);
		else if ((timeleft >= 12 && timeleft < 25) && (engine.getCurrentRound() <= 3 && engine.getCurrentRound() >= 0))
			assertTrue(result<=150);
		else if ((timeleft >= 12 && timeleft < 25) && (engine.getCurrentRound() <= 7 && engine.getCurrentRound() >= 4))
			assertTrue(result<=200);
		else if ((timeleft >= 12 && timeleft < 25) && (engine.getCurrentRound() <= 11 && engine.getCurrentRound() >= 8))
			assertTrue(result<=250);
		else if ((timeleft >= 12 && timeleft < 25) && (engine.getCurrentRound() >= 12))
			assertTrue(result<=300);
		else if ((timeleft >= 0 && timeleft < 12) && (engine.getCurrentRound() <= 3 && engine.getCurrentRound() >= 0))
			assertTrue(result<=100);
		else if ((timeleft >= 0 && timeleft < 12) && (engine.getCurrentRound() <= 7 && engine.getCurrentRound() >= 4))
			assertTrue(result<=150);
		else if ((timeleft >= 0 && timeleft < 12) && (engine.getCurrentRound() <= 11 && engine.getCurrentRound() >= 8))
			assertTrue(result<=200);
		else if ((timeleft >= 0 & timeleft < 12) && (engine.getCurrentRound() >= 12))
			assertTrue(result<=250);
			
	}

}
