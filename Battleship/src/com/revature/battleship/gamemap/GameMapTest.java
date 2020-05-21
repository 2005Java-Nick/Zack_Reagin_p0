package com.revature.battleship.map;

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

public class MapTest {
	private static Logger log = Logger.getRootLogger();
	BattleshipGame b = new BattleshipGame();
	public Map map;
	public Ship s1 = new Ship("5", 5);
	public Ship s2 = new Ship("4", 4);
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		map = new Map(12,12);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMap() {
		assertEquals(map.getHeight(), 12);
		assertEquals(map.getWidth(), 12);
	}

	@Test
	public void testGetHeight() {
		assertEquals(map.getHeight(), 12);
	}

	@Test
	public void testSetHeight() {
		try {
			map.setHeight(6);
		} catch (HeightLessThanOneException e) {
			log.error("Height cannot be set to less than one.");
			e.printStackTrace();
		}
		assertEquals(map.getHeight(), 6);
	}

	@Test
	public void testGetWidth() {
		assertEquals(map.getWidth(), 12);
	}

	@Test
	public void testSetWidth() {
		try {
			map.setWidth(8);
		} catch (WidthLessThanOneException e) {
			log.error("Width cannot be set to less than one.");
			e.printStackTrace();
		}
		assertEquals(map.getWidth(), 8);
	}

	@Test
	public void testGetLocation() {
		try {
			assertEquals(map.getLocation(3, 5), null);
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
		assertEquals(map.getHits(), 0);
	}

	@Test
	public void testGetCoordinates() {
		assertEquals(map.getCoordinates(5, 3), "F4");
	}
	
	@Test
	public void testPlaceShip() {
		map.placeShip(s1);
		map.placeShip(s2);
		int numNulls = 0;
		int numFours = 0;
		int numFives = 0;
		for(int y = 0; y < map.getHeight(); y++) {
			for(int x = 0; x < map.getWidth(); x++) {
				try {
					if(map.getLocation(y, x) == null) {
						numNulls++;
					} else if(map.getLocation(y, x).equals("4")) {
						numFours++;
					} else if(map.getLocation(y, x).equals("5")) {
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

}
