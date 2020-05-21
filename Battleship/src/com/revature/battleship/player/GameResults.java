package com.revature.battleship.player;

import java.io.Serializable;

public interface PlayerRecords extends Serializable{
	
	// Obtain unedited data from file.
	public String getRecords(Player p);
	
	// Format data obtained by getRecords method for easier readability.
	public void displayRecords(Player p);
	
	// Update the player record with results from the most recent game.
	public void updateRecords(Player p, boolean win, int remainingTurns, int hits);
}
