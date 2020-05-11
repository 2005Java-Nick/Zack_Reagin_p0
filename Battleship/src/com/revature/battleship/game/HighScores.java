package com.revature.battleship.game;

import java.io.Serializable;

import com.revature.battleship.player.Player;

public interface HighScores extends Serializable{

	// Read high score data from records
	public String getRecords();
	
	// Organize data pulled by getRecords method so it is readable
	public void displayRecords();
	
	// Update high score listings and write new scores to file.
	public void updateRecords(Player p, boolean win, int remainingTurns, int hits);
}
