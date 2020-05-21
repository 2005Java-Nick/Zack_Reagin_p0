package com.revature.battleship.player;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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
	
	private List<PlayerRecord> playerRecords;
	private int totalWins;
	private int totalGames;
	private int totalHits;
	private int totalRemainingTurns;

	public Player() {
		this.username = "Anonymous";
		this.password = "";
		this.playerRecords = new ArrayList<PlayerRecord>();
		this.setTotalWins(0);
		this.setTotalGames(0);
		this.setTotalHits(0);
		this.setTotalRemainingTurns(0);
	}

	public Player(String username, String password) {
		this.username = username;
		this.password = password;
		this.playerRecords = new ArrayList<PlayerRecord>();
		this.setTotalWins(0);
		this.setTotalGames(0);
		this.setTotalHits(0);
		this.setTotalRemainingTurns(0);
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

	public void addRecord(PlayerRecord playerRecord) {
		this.playerRecords.add(playerRecord);
	}

	public List<PlayerRecord> getRecords() {
		return this.playerRecords;
	}

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

	public int getTotalWins() {
		return totalWins;
	}

	public void setTotalWins(int totalWins) {
		this.totalWins = totalWins;
	}

	public int getTotalGames() {
		return totalGames;
	}

	public void setTotalGames(int totalGames) {
		this.totalGames = totalGames;
	}

	public int getTotalHits() {
		return totalHits;
	}

	public void setTotalHits(int totalHits) {
		this.totalHits = totalHits;
	}

	public int getTotalRemainingTurns() {
		return totalRemainingTurns;
	}

	public void setTotalRemainingTurns(int totalRemainingTurns) {
		this.totalRemainingTurns = totalRemainingTurns;
	}
}
