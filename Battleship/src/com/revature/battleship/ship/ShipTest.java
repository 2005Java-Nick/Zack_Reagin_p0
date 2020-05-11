package com.revature.battleship.ship;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ShipTest {

	Ship s;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		s = new Ship("testID", 4);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testShip() {
		assertEquals(s.getID(),"testID");
		assertEquals(s.getLength(), 4);
	}

	@Test
	public void testGetID() {
		assertEquals(s.getID(),"testID");
	}

	@Test
	public void testGetLength() {
		assertEquals(s.getLength(), 4);
	}

	@Test
	public void testSetLength() {
		s.setLength(12);
		assertEquals(s.getLength(), 12);
	}

	@Test
	public void testGetOrientation() {
		assertTrue((s.getOrientation() == "horizontal" || s.getOrientation() == "vertical"));
	}

}
