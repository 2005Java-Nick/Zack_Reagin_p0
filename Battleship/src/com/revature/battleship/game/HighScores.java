package com.revature.battleship.game;

import java.io.Serializable;

import com.revature.battleship.player.Player;

public interface HighScores extends Serializable{

	public String getRecords();
	
	public void displayRecords();
	
	public void updateRecords(Player p, boolean win, int remainingTurns, int hits);
}
