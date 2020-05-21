package com.revature.battleship.scores;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ScoreKeeper {
	List<HighScore> topPlayers;
	
	public ScoreKeeper() {
		topPlayers = new ArrayList<HighScore>();
	}
	
	public  List<HighScore> getTopPlayers() {
		return this.topPlayers;
	}
	
	public void addHighScore(HighScore h) {
		topPlayers.add(h);
	}
	
	public void clearScores() {
		topPlayers.clear();
	}
	
	public void displayHighScores() {
		System.out.println("\nCurrent Top Players\n");
		DecimalFormat df = new DecimalFormat("#.##");
		for(int i = 0; i < topPlayers.size(); i++) {
			double winPercent = ((double) topPlayers.get(i).getWins() / (double) topPlayers.get(i).getGames()) * 100.00;
			double averageHits = (double) topPlayers.get(i).getHits() / (double) topPlayers.get(i).getGames();
			double averageTurns = (double) topPlayers.get(i).getRemainingTurns() / (double) topPlayers.get(i).getGames();
			System.out.println((i+1) + " " + topPlayers.get(i).getUserName());
			System.out.println("\tTotal Wins: " + topPlayers.get(i).getWins());
			System.out.println("\tWin Percent: " + df.format(winPercent) + "%");
			System.out.println("\tAverage Hits: " + df.format(averageHits));
			System.out.println("\tAverage Turns Remaining: " + df.format(averageTurns));
			System.out.println();
		}
		System.out.println();
	}
}
