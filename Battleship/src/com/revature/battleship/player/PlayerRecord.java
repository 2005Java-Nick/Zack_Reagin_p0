package com.revature.battleship.player;

public class PlayerRecord {
	private boolean win;
	private int remainingTurns;
	private int hits;
	
	public PlayerRecord() {
		this.win = false;
		this.remainingTurns = 40;
		this.hits = 0;
	}
	
	public PlayerRecord(boolean win, int remainingTurns, int hits) {
		this.win = win;
		this.remainingTurns = remainingTurns;
		this.hits = hits;
	}
	
	public boolean getWin() {
		return this.win;
	}
	
	public int getRemainingTurns() {
		return this.remainingTurns;
	}
	
	public int getHits() {
		return this.hits;
	}
}
