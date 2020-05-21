package com.revature.battleship.player;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/*
 * The Player class represents a user, and keeps track of the user name and password,
 * as well as aggregated totals representing the results of previous games the user
 * has played.
 */

public class Player implements Serializable{

	private static final long serialVersionUID = -1434478999142325829L;
	private String username;
	private String password;
	
	private List<PlayerRecord> playerRecords;
	private int totalWins;
	private int totalGames;
	private int totalHits;
	private int totalRemainingTurns;
	
	/*
	 * Constructor method for creating a new Player if no parameters are supplied.
	 * User name is set to anonymous, and password is set to an empty String. The
	 * player's list of previous game records is initialized as an empty list. All
	 * other values are initialized to 0.
	 */
	public Player() {
		this.username = "Anonymous";
		this.password = "";
		this.playerRecords = new ArrayList<PlayerRecord>();
		this.setTotalWins(0);
		this.setTotalGames(0);
		this.setTotalHits(0);
		this.setTotalRemainingTurns(0);
	}

	/*
	 * Constructor method for creating a new Player if user name and password are
	 * supplied. The player's list of previous game records is initialized as an
	 * empty list. All other values are initialized to 0.
	 */
	public Player(String username, String password) {
		this.username = username;
		this.password = password;
		this.playerRecords = new ArrayList<PlayerRecord>();
		this.setTotalWins(0);
		this.setTotalGames(0);
		this.setTotalHits(0);
		this.setTotalRemainingTurns(0);
	}

	/*
	 * Getter method to return the user name of a Player object.
	 */
	public String getUsername() {
		return this.username;
	}

	/*
	 * Setter method to set the user name of a Player object to a String
	 * given as an argument.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/*
	 * Getter method that returns the password of a Player object.
	 */
	public String getPassword() {
		return this.password;
	}

	/*
	 * Setter method that sets the password of a Player object to a String given as
	 * an argument.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/*
	 * Adds a playerRecord object to a Player object's playerRecord list.
	 */
	public void addRecord(PlayerRecord playerRecord) {
		this.playerRecords.add(playerRecord);
	}

	/*
	 * Getter method that returns a Player object's playerRecord list.
	 */
	public List<PlayerRecord> getRecords() {
		return this.playerRecords;
	}

	/*
	 * Method used to display statistics and results for previous games the player has
	 * played in an easily readable format.
	 */
	public void showRecords() {
		System.out.println();
		if(this.getTotalGames() == 0) {
			System.out.println("You have no records to display. Try playing a game first.");
		} else {
			DecimalFormat df = new DecimalFormat("#.##");
			double winPercent = ((double) this.getTotalWins() / (double) this.getTotalGames()) * 100.00;
			double averageHits = (double) this.getTotalHits() / (double) this.getTotalGames();
			double averageTurns = (double) this.getTotalRemainingTurns() / (double) this.getTotalGames();
			System.out.println("Total Games Played: " + this.getTotalGames());
			System.out.println("Total Wins: " + this.getTotalWins());
			System.out.println("Win Percentage: " + df.format(winPercent) + "%");
			System.out.println("Average Hits Per Game: " + df.format(averageHits));
			System.out.println("Average Turns Remaining Per Game: " + df.format(averageTurns));
			System.out.println("\nGame History:\n");
			for(int i = 0; i < playerRecords.size(); i++) {
				System.out.println("Game " + (i + 1) + ":");
				System.out.print("\tResult: ");
				if(playerRecords.get(i).getWin()) {
					System.out.println("Win");
				} else {
					System.out.println("Loss");
				}
				System.out.println("\tTurns Remaining: " + playerRecords.get(i).getRemainingTurns());
				System.out.println("\tTotal Hits: " + playerRecords.get(i).getHits());
			}
		}
		System.out.println();
	}

	/*
	 * Getter method that returns the total wins from all games played by the Player.
	 */
	public int getTotalWins() {
		return totalWins;
	}

	/*
	 * Setter method that sets total wins.
	 */
	public void setTotalWins(int totalWins) {
		this.totalWins = totalWins;
	}

	/*
	 * Getter method that returns the total number of games played by the Player.
	 */
	public int getTotalGames() {
		return totalGames;
	}

	/*
	 * Setter method that sets total games.
	 */
	public void setTotalGames(int totalGames) {
		this.totalGames = totalGames;
	}

	/*
	 * Getter method that returns the total number of successful hits from all
	 * games played by the Player.
	 */
	public int getTotalHits() {
		return totalHits;
	}

	/*
	 * Setter method that sets total hits.
	 */
	public void setTotalHits(int totalHits) {
		this.totalHits = totalHits;
	}

	/*
	 * Getter method that returns the total number of turns remaining at the
	 * end of every game played by the Player.
	 */
	public int getTotalRemainingTurns() {
		return totalRemainingTurns;
	}

	/*
	 * Setter method that sets total turns remaining.
	 */
	public void setTotalRemainingTurns(int totalRemainingTurns) {
		this.totalRemainingTurns = totalRemainingTurns;
	}
}
