package com.revature.battleship.scores;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/*
 * The ScoreKeeper Class is used to keep track of HighScore objects.
 */
public class ScoreKeeper {
	List<HighScore> topPlayers;
	
	/*
	 * The constructor takes no parameters, and only instantiates the topPlayers ArrayList.
	 */
	public ScoreKeeper() {
		topPlayers = new ArrayList<HighScore>();
	}
	
	/*
	 * Getter method that returns a list of HighScores.
	 */
	public  List<HighScore> getTopPlayers() {
		return this.topPlayers;
	}
	
	/*
	 * Add a HighScore object to the topPlayer list.
	 */
	public void addHighScore(HighScore h) {
		topPlayers.add(h);
	}
	
	/*
	 * Clear all of the HighScores from the topPlayer list.
	 */
	public void clearScores() {
		topPlayers.clear();
	}
	
	/*
	 * Displays highScore objects from topPlayer list in a readable format.
	 */
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
