package com.revature.battleship.game;

import com.revature.battleship.gamemap.GameMap;
import com.revature.battleship.ship.Ship;

/*
 * The BattleshipGame class is used to create an instance of a game of Battleship.
 */
public class BattleshipGame {
	
	private final static int TOTAL_TURNS = 50;
	private static int turns;
	private static int hits;
	private final int TOTAL_HITS = 20;
	private static GameMap gameMap;
	private static Ship ship1;
	private static Ship ship2;
	private static Ship ship3;
	private static Ship ship4;
	private static Ship ship5;
	
	public BattleshipGame() {
		
	}
	
	/*
	 *  Create a new map and five ships, and place the ships on the map.
	 */
	public static void setUp() {
		gameMap = new GameMap(10, 10);
		ship1 = new Ship("2",2);
		ship2 = new Ship("3", 3);
		ship3 = new Ship("4", 4);
		ship4 = new Ship("5", 5);
		ship5 = new Ship("6", 6);
		gameMap.placeShip(ship1);
		gameMap.placeShip(ship2);
		gameMap.placeShip(ship3);
		gameMap.placeShip(ship4);
		gameMap.placeShip(ship5);
		hits = 0;
		turns = TOTAL_TURNS;
	}
	
	/*
	 *  Takes user input for attack location and attempts to attack that coordinate.
	 */
	public static void attack(String location) {
		String alphabet = "ABCDEFGHIJ";
		char yCoordinate = Character.toUpperCase(location.charAt(0));
		int y = alphabet.indexOf(yCoordinate);
		int x = (Integer.parseInt(location.substring(1))) - 1;
		if (gameMap.attackLocation(y,x)) {
			setTurns(getRemainingTurns() - 1);
			setHits(gameMap.getHits());
			System.out.println("There are " + getRemainingTurns() + " missiles remaining!"
					+ " You have struck enemy ships " + getHits() + " time(s)!");
		}
		else {
			System.out.println("You have already attacked that location! Try somewhere else!");
		}
	}
	
	/*
	 * Sets the number of remaining turns in a game of Battleship.
	 */
	private static void setTurns(int i) {
		turns = i;
	}

	/*
	 *  Counts turns left before game ends.
	 */
	public static int getRemainingTurns() {
		return turns;
	}
	
	/*
	 * Returns the GameMap object associated with the current game.
	 */
	public GameMap getMap() {
		return BattleshipGame.gameMap;
	}


	/*
	 * Returns total number of successful hits.
	 */
	public static int getHits() {
		return hits;
	}

	/*
	 * Sets the number of hits.
	 */
	public static void setHits(int hits) {
		BattleshipGame.hits = hits;
	}

	/*
	 * Returns the total number of hits required to end a game.
	 */
	public int getTOTAL_HITS() {
		return TOTAL_HITS;
	}
}
