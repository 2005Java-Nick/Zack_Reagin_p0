package com.revature.battleship.player;

import java.io.Serializable;

/*
 * 
 */

public class Player implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1434478999142325829L;
	private String username;
	private String password;
	
	public Player() {
		username = "Anonymous";
		password = "";
	}
	
	public Player(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
