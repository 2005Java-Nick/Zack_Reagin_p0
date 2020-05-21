package com.revature.battleship.scores;

public class HighScore {
	private String userName;
	private int games;
	private int wins;
	private int hits;
	private int remainingTurns;
	
	public HighScore(String userName, int games, int wins, int hits, int remainingTurns) {
		this.userName = userName;
		this.games = games;
		this.wins = wins;
		this.hits = hits;
		this.remainingTurns = remainingTurns;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public int getGames() {
		return this.games;
	}
	
	public int getWins() {
		return this.wins;
	}
	
	public int getHits() {
		return this.hits;
	}
	
	public int getRemainingTurns() {
		return this.remainingTurns;
	}
}
