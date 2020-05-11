package com.revature.battleship.dao;

import com.revature.battleship.player.Player;

public interface PlayerDAO {
	
	// Save player data to file
	public void savePlayer(Player p);
	
	// Check player user name and password against file and return player.
	public Player getPlayer(String playerName, String password);
	
	// Check if user name and password exist in file.
	public boolean playerExists(String playerName);

}
