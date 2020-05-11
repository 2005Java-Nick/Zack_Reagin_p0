package com.revature.battleship.game;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import com.revature.battleship.player.Player;

public class HighScoresDAO implements HighScores{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	File directory = new File(".");
	String fileName = "highscores.txt";
	String absolutePath = "";

	// Pull high scores from file entitled "highscores.txt". Data is obtained as a block of text to be parsed.
	@Override
	public String getRecords() {
		String fileContents = "";
		try {
			absolutePath = directory.getCanonicalPath() + File.separator + fileName;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// read the content from file
		try(BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(absolutePath))) {
		    int ch = bufferedInputStream.read();
		    while(ch != -1) {
		        fileContents += (char) ch;
		        ch = bufferedInputStream.read();
		    }
		} catch (FileNotFoundException e) {
		    // exception handling
		} catch (IOException e) {
		    // exception handling
		}
		return fileContents;
	}
	
	// Calls getRecords method, and parses the string it returns to display the information to user.
	@Override
	public void displayRecords() {
		String[] records = this.getRecords().split(",");
		System.out.println("High Scores");
		for(int i = 1; i < records.length; i++) {
			if (i % 4 == 1) {
				System.out.println("Rank " + ((i/4)+1) + ": ");
				System.out.print("\tPlayer: " + records[i]);
			} else if (i % 4 == 2) {
				System.out.print("\tResult: ");
				if(records[i].equals("true")) {
					System.out.println("Won");
				} else {
					System.out.println("Lost");
				}
			} else if (i % 4 == 3) {
				System.out.println("\tRemaining Turns: " + records[i]);
			} else {
				System.out.println("\tTotal Hits: " + records[i]);
			}
		}
	}
	
	/*
	 *  Obtains current high scores as a block of data from txt file using getRecords.
	 *  Adds the results of the players most recent game to the txt file where appropriate.
	 *  Each piece of data is separated by a comma to make parsing it easier.
	 *  Original file is overwritten with new high score rankings.
	 */
	@Override
	public void updateRecords(Player p, boolean win, int remainingTurns, int hits) {
		String[] current = this.getRecords().split(",+");
		ArrayList<String> newRecords = new ArrayList<String>();
		for(int i = 1; i < current.length; i++) {
			newRecords.add(current[i]);
		}
		if(newRecords.size() < 4) {
			newRecords.add(p.getUsername());
			newRecords.add(String.valueOf(win));
			newRecords.add(String.valueOf(remainingTurns));
			newRecords.add(String.valueOf(hits));
		} else {
			for(int j = 2; j < current.length; j+=4) {
				if(remainingTurns < Integer.parseInt(current[j+1])) {
					newRecords.add(j+2, p.getUsername());
					newRecords.add(j+3, String.valueOf(win));
					newRecords.add(j+4, String.valueOf(remainingTurns));
					newRecords.add(j+5, String.valueOf(hits));
					break;
				} else if (remainingTurns == Integer.parseInt(current[j+1])) {
					if (hits >= Integer.parseInt(current[j+1])) {
						newRecords.add(j+2, p.getUsername());
						newRecords.add(j+3, String.valueOf(win));
						newRecords.add(j+4, String.valueOf(remainingTurns));
						newRecords.add(j+5, String.valueOf(hits));
						break;
					}
				}
			}
		}
		String update = "";
		for (String s : newRecords) {
			update += ("," + s);
		}
		try {
			absolutePath = directory.getCanonicalPath() + File.separator + fileName;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		// write the content in file 
		try {
			PrintStream printer = new PrintStream(new BufferedOutputStream(new FileOutputStream(absolutePath, false)));
		    printer.print(update);
		    printer.close();
		} catch (IOException e) {
		    // exception handling
		}
	}
}
