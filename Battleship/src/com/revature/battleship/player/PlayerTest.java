package com.revature.battleship.player;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PlayerTest {

	Player p1;
	Player p2;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		p1 = new Player();
		p2 = new Player("Name", "Password");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPlayerWithoutArguments() {
		assertEquals(p1.getUsername(), "Anonymous");
		assertEquals(p1.getPassword(), "");
	}
	
	@Test
	public void testPlayerWithArguments() {
		assertEquals(p2.getUsername(), "Name");
		assertEquals(p2.getPassword(), "Password");
	}

	@Test
	public void testGetUsernameWithoutArgs() {
		assertEquals(p1.getUsername(), "Anonymous");
	}
	
	@Test
	public void testGetUsernameWithArgs() {
		assertEquals(p2.getUsername(), "Name");
	}

	@Test
	public void testSetUsername() {
		p1.setUsername("NewName");
		assertEquals(p1.getUsername(), "NewName");
	}

	@Test
	public void testGetPassword() {
		assertEquals(p2.getPassword(), "Password");
	}

	@Test
	public void testSetPassword() {
		p1.setPassword("Password123");
		assertEquals(p1.getPassword(), "Password123");
	}

}
