package com.revature.battleship.dao;

import com.revature.battleship.player.Player;

public interface PlayerDAO {
	
	public void savePlayer(Player p);
	
	public Player getPlayer(String playerName, String password);
	
	public boolean playerExists(String playerName);

}
