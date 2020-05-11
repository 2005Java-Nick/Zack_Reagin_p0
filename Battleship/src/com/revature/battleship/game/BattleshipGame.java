package com.revature.battleship.game;

import com.revature.battleship.map.Map;
import com.revature.battleship.ship.Ship;

public class BattleshipGame {
	
	private static int turns = 40;
	private static int hits = 0;
	private final int TOTAL_HITS = 20;
	private static Map map;
	private static Ship ship1;
	private static Ship ship2;
	private static Ship ship3;
	private static Ship ship4;
	private static Ship ship5;
	
	public BattleshipGame() {
		
	}
	
	// Create a new map and five ships, and place the ships on the map
	public static void setUp() {
		map = new Map(10, 10);
		ship1 = new Ship("2",2);
		ship2 = new Ship("3", 3);
		ship3 = new Ship("4", 4);
		ship4 = new Ship("5", 5);
		ship5 = new Ship("6", 6);
		map.placeShip(ship1);
		map.placeShip(ship2);
		map.placeShip(ship3);
		map.placeShip(ship4);
		map.placeShip(ship5);
	}
	
	// Takes user input for attack location and attempts to attack that coordinate
	public static void attack(String location) {
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		char yCoordinate = location.charAt(0);
		int y = alphabet.indexOf(yCoordinate);
		int x = (Integer.parseInt(location.substring(1))) - 1;
		if (map.attackLocation(y,x)) {
			turns--;
			setHits(map.getHits());
			System.out.println("There are " + turns + " missiles remaining! You have struck enemy ships " + getHits() + " time(s)!");
		}
		else {
			System.out.println("You have already attacked that location! Try somewhere else!");
		}
	}
	
	// Counts turns left before game ends
	public int getRemainingTurns() {
		return this.turns;
	}
	
	public Map getMap() {
		return this.map;
	}

	// Returns total number of successful hits
	public static int getHits() {
		return hits;
	}

	public static void setHits(int hits) {
		BattleshipGame.hits = hits;
	}
}
