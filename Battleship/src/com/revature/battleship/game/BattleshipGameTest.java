package com.revature.battleship.game;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.battleship.exception.CoordinateGreaterThanBoundaryException;
import com.revature.battleship.exception.CoordinateLessThanZeroException;
import com.revature.battleship.gamemap.GameMap;

public class BattleshipGameTest {
	private static Logger log = Logger.getRootLogger();
	BattleshipGame b;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		b = new BattleshipGame();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSetUp() {
		BattleshipGame.setUp();
		int numNulls = 0;
		int numTwos = 0;
		int numThrees = 0;
		int numFours = 0;
		int numFives = 0;
		int numSixes = 0;
		GameMap gameMap = b.getMap();
		for(int y = 0; y < gameMap.getHeight(); y++) {
			for(int x = 0; x < gameMap.getWidth(); x++) {
				try {
					if(gameMap.getLocation(y, x) == null) {
						numNulls++;
					} else if(gameMap.getLocation(y, x).equals("2")) {
						numTwos++;
					} else if(gameMap.getLocation(y, x).equals("3")) {
						numThrees++;
					} else if(gameMap.getLocation(y, x).equals("4")) {
						numFours++;
					} else if(gameMap.getLocation(y, x).equals("5")) {
						numFives++;
					} else if(gameMap.getLocation(y, x).equals("6")) {
						numSixes++;
					}
				} catch (CoordinateLessThanZeroException e) {
					log.error("Coordinate must be greater than 0.");
					e.printStackTrace();
				} catch (CoordinateGreaterThanBoundaryException e) {
					log.error("Coordinate must be less than map boundary.");
					e.printStackTrace();
				}
			}
		}
		assertEquals(numNulls, 80);
		assertEquals(numTwos, 2);
		assertEquals(numThrees, 3);
		assertEquals(numFours, 4);
		assertEquals(numFives, 5);
		assertEquals(numSixes, 6);
	}

	@Test
	public void testGetRemainingTurns() {
		assertEquals(b.getRemainingTurns(), 50);
	}

	@Test
	public void testGetHits() {
		assertEquals(BattleshipGame.getHits(), 0);
	}

	@Test
	public void testSetHits() {
		BattleshipGame.setHits(5);
		assertEquals(BattleshipGame.getHits(), 5);
	}

}
