package com.revature.battleship.player;

/*
 * The playerRecord Class stores the end results of a single game, including
 * whether the player won, the total number of turns remaining, and the total
 * successful hits.
 */
public class PlayerRecord {
	private boolean win;
	private int remainingTurns;
	private int hits;
	
	/*
	 * Default constructor for PlayerRecord. Win is initialized to false,
	 * remainingTurns is initialized to 50, and hits is initialized to 0.
	 */
	public PlayerRecord() {
		this.win = false;
		this.remainingTurns = 50;
		this.hits = 0;
	}
	
	/*
	 * Constructor for PlayerRecord that sets each property of a PlayerRecord
	 * to the corresponding argument.
	 */
	public PlayerRecord(boolean win, int remainingTurns, int hits) {
		this.win = win;
		this.remainingTurns = remainingTurns;
		this.hits = hits;
	}
	
	/*
	 * Returns a boolean value representing whether the game was a win or a loss.
	 */
	public boolean getWin() {
		return this.win;
	}
	
	/*
	 * Returns an int representing the total turns remaining at the end of a game.
	 */
	public int getRemainingTurns() {
		return this.remainingTurns;
	}
	
	/*
	 * Returns an int representing the total successful hits at the end of a game.
	 */
	public int getHits() {
		return this.hits;
	}
}
