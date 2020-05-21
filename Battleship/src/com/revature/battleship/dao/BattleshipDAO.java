package com.revature.battleship.dao;

import com.revature.battleship.player.Player;
import com.revature.battleship.scores.ScoreKeeper;

public interface BattleshipDAO {

	// Saves user name and password for a new player.
	public void savePlayer(Player p);

	// Check player user name and password against file and return player data.
	public Player getPlayer(String playerName, String password);

	// Check if user name already exists.
	public boolean userNameExists(String playerName);

	// Retrieves player's past game history and stores it in player's player record list.
	public void getPlayerRecords(Player p);

	// Add the results from the most recent game to the database.
	public void updatePlayerRecords(Player p, boolean win, int remainingTurns, int hits);

	// Retrieves high scores.
	public void getHighScores(ScoreKeeper s);

}
