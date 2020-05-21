package com.revature.battleship.scores;

/*
 * The HighScore class is used to represent objects that are associated with individual players.
 * Each instance of this class contains the user name of the player it is associated with, along
 * with the total number of games the player has played, the total number of times the player
 * has won a game, the aggregate total of hits the player has achieved across all games played,
 * and the aggregate total of turns remaining at the end of all games played.
 */
public class HighScore {
	private String userName;
	private int games;
	private int wins;
	private int hits;
	private int remainingTurns;
	
	/*
	 * The HighScore constructor takes the parameters String userName, int games, int wins,
	 * and int remainingTurns. The String userName corresponds to the user name of a user
	 * who this record is associated with. The int games corresponds to the total number of
	 * games played by the user. The int wins corresponds to how many times the user has won
	 * a game. The int hits corresponds to the total number of hits the user has achieved across
	 * all of the games they played. The int remainingTurns corresponds to the aggregate number
	 * of remainingTurns at the end of all of the games the user has played.
	 */
	public HighScore(String userName, int games, int wins, int hits, int remainingTurns) {
		this.userName = userName;
		this.games = games;
		this.wins = wins;
		this.hits = hits;
		this.remainingTurns = remainingTurns;
	}
	
	/*
	 * Getter method that returns the userName of a HighScore object as a String.
	 */
	public String getUserName() {
		return this.userName;
	}
	
	/*
	 * Getter method that returns the total number of games of a HighScore object as an int.
	 */
	public int getGames() {
		return this.games;
	}
	
	/*
	 * Getter method that returns the total number of wins of a HighScore object as an int.
	 */
	public int getWins() {
		return this.wins;
	}
	
	/*
	 * Getter method that returns the total number of hits of a HighScore object as an int.
	 */
	public int getHits() {
		return this.hits;
	}
	
	/*
	 * Getter method that returns the total number of turns remaining of a HighScore object
	 * as an int.
	 */
	public int getRemainingTurns() {
		return this.remainingTurns;
	}
}
