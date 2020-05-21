package com.revature.battleship.gamemap;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.battleship.exception.CoordinateGreaterThanBoundaryException;
import com.revature.battleship.exception.CoordinateLessThanZeroException;
import com.revature.battleship.exception.HeightLessThanOneException;
import com.revature.battleship.exception.WidthLessThanOneException;
import com.revature.battleship.game.BattleshipGame;
import com.revature.battleship.ship.Ship;

public class GameMapTest {
	private static Logger log = Logger.getRootLogger();
	BattleshipGame b = new BattleshipGame();
	public GameMap gameMap;
	public Ship s2 = new Ship("2", 2);
	public Ship s3 = new Ship("3", 3);
	public Ship s4 = new Ship("4", 4);
	public Ship s5 = new Ship("5", 5);
	public Ship s6 = new Ship("6", 6);
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		gameMap = new GameMap(12,12);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMap() {
		assertEquals(gameMap.getHeight(), 12);
		assertEquals(gameMap.getWidth(), 12);
	}

	@Test
	public void testGetHeight() {
		assertEquals(gameMap.getHeight(), 12);
	}

	@Test
	public void testSetHeight() {
		try {
			gameMap.setHeight(6);
		} catch (HeightLessThanOneException e) {
			log.error("Height cannot be set to less than one.");
			e.printStackTrace();
		}
		assertEquals(gameMap.getHeight(), 6);
	}

	@Test
	public void testGetWidth() {
		assertEquals(gameMap.getWidth(), 12);
	}

	@Test
	public void testSetWidth() {
		try {
			gameMap.setWidth(8);
		} catch (WidthLessThanOneException e) {
			log.error("Width cannot be set to less than one.");
			e.printStackTrace();
		}
		assertEquals(gameMap.getWidth(), 8);
	}

	@Test
	public void testCheckIfValidCoordinatesValid() {
		assertTrue(gameMap.checkIfCoordinatesValid("A1"));
	}
	
	@Test
	public void testCheckIfValidCoordinatesValid2() {
		assertTrue(gameMap.checkIfCoordinatesValid("B10"));
	}
	
	@Test
	public void testCheckIfValidCoordinatesValid3() {
		assertTrue(gameMap.checkIfCoordinatesValid("J10"));
	}
	
	@Test
	public void testCheckIfValidCoordinatesValid4() {
		assertTrue(gameMap.checkIfCoordinatesValid("J1"));
	}
	
	@Test
	public void testCheckIfValidCoordinatesValid5() {
		assertTrue(gameMap.checkIfCoordinatesValid("A10"));
	}
	
	@Test
	public void testCheckIfValidCoordinatesValid6() {
		assertTrue(gameMap.checkIfCoordinatesValid("g7"));
	}
	
	@Test
	public void testCheckIfValidCoordinatesValid7() {
		assertTrue(gameMap.checkIfCoordinatesValid("D4"));
	}
	
	@Test
	public void testCheckIfValidCoordinatesValid8() {
		assertTrue(gameMap.checkIfCoordinatesValid("f6"));
	}
	
	@Test
	public void testCheckIfWrongLetterCoordinatesValid() {
		assertFalse(gameMap.checkIfCoordinatesValid("L1"));
	}
	
	@Test
	public void testCheckIfWrongLetterCoordinatesValid2() {
		assertFalse(gameMap.checkIfCoordinatesValid("(1"));
	}
	
	@Test
	public void testCheckIfWrongLetterCoordinatesValid3() {
		assertFalse(gameMap.checkIfCoordinatesValid("7D"));
	}
	
	@Test
	public void testCheckIfWrongLetterCoordinatesValid4() {
		assertFalse(gameMap.checkIfCoordinatesValid("F11"));
	}
	
	@Test
	public void testCheckIfWrongLetterCoordinatesValid5() {
		assertFalse(gameMap.checkIfCoordinatesValid("02"));
	}
	
	@Test
	public void testCheckIfWrongLetterCoordinatesValid6() {
		assertFalse(gameMap.checkIfCoordinatesValid("2"));
	}
	
	@Test
	public void testCheckIfWrongLetterCoordinatesValid7() {
		assertFalse(gameMap.checkIfCoordinatesValid("A2B3"));
	}
	
	@Test
	public void testCheckIfWrongLetterCoordinatesValid8() {
		assertFalse(gameMap.checkIfCoordinatesValid("AB2"));
	}
	
	@Test
	public void testGetLocation() {
		try {
			assertEquals(gameMap.getLocation(3, 5), null);
		} catch (CoordinateLessThanZeroException e) {
			log.error("Coordinate cannot be less than 0.");
			e.printStackTrace();
		} catch (CoordinateGreaterThanBoundaryException e) {
			log.error("Coordinate cannot exceed the height/width of the map.");
			e.printStackTrace();
		}
	}

	@Test
	public void testGetHits() {
		assertEquals(gameMap.getHits(), 0);
	}

	@Test
	public void testGetCoordinates() {
		assertEquals(gameMap.getCoordinates(5, 3), "F4");
	}
	
	@Test
	public void testPlaceShip1() {
		gameMap.placeShip(s5);
		gameMap.placeShip(s4);
		int numNulls = 0;
		int numFours = 0;
		int numFives = 0;
		for(int y = 0; y < gameMap.getHeight(); y++) {
			for(int x = 0; x < gameMap.getWidth(); x++) {
				try {
					if(gameMap.getLocation(y, x) == null) {
						numNulls++;
					} else if(gameMap.getLocation(y, x).equals("4")) {
						numFours++;
					} else if(gameMap.getLocation(y, x).equals("5")) {
						numFives++;
					}
				} catch (CoordinateLessThanZeroException e) {
					log.error("Coordinate cannot be less than 0.");
					e.printStackTrace();
				} catch (CoordinateGreaterThanBoundaryException e) {
					log.error("Coordinate cannot exceed the height/width of the map.");
					e.printStackTrace();
				}
			}
		}
		assertEquals(numNulls, 135);
		assertEquals(numFours, 4);
		assertEquals(numFives, 5);
	}
	
	@Test
	public void testPlaceShip2() {
		gameMap.placeShip(s6);
		gameMap.placeShip(s5);
		gameMap.placeShip(s4);
		gameMap.placeShip(s3);
		gameMap.placeShip(s3);
		gameMap.placeShip(s2);
		gameMap.placeShip(s2);
		gameMap.placeShip(s2);
		int numNulls = 0;
		int numTwos = 0;
		int numThrees = 0;
		int numFours = 0;
		int numFives = 0;
		int numSixes = 0;
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
					log.error("Coordinate cannot be less than 0.");
					e.printStackTrace();
				} catch (CoordinateGreaterThanBoundaryException e) {
					log.error("Coordinate cannot exceed the height/width of the map.");
					e.printStackTrace();
				}
			}
		}
		assertEquals(numNulls, 117);
		assertEquals(numTwos, 6);
		assertEquals(numThrees, 6);
		assertEquals(numFours, 4);
		assertEquals(numFives, 5);
		assertEquals(numSixes, 6);
	}

}
