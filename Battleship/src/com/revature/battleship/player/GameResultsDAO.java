package com.revature.battleship.player;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.apache.log4j.Logger;

public class PlayerRecordsDAO implements PlayerRecords{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getRootLogger();
	File directory = new File(".");
	String fileName = "records.txt";
	String absolutePath = "";
	
	// Pull player record from file entitled "usernamerecords.txt". Player record is obtained as a block of text to be parsed.
	@Override
	public String getRecords(Player p) {
		String fileContents = "";
		String playerName = p.getUsername();
		try {
			absolutePath = directory.getCanonicalPath() + File.separator + playerName + fileName;
		} catch (IOException e) {
			log.error("Invalid file name.", e);
		}
		
		// read the content from file
		try(BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(absolutePath))) {
		    int ch = bufferedInputStream.read();
		    while(ch != -1) {
		        fileContents += (char) ch;
		        ch = bufferedInputStream.read();
		    }
		} catch (FileNotFoundException e) {
			log.error("File not found.", e);
		} catch (IOException e) {
			log.error("Problem reading from file.", e);
		}
		return fileContents;
	}
	
	// Calls getRecords method, and parses the string it returns to display the information to user
	@Override
	public void displayRecords(Player p) {
		if(this.getRecords(p).equals("")) {
			System.out.println("No records to display.");
		} else {
			System.out.println();
			String[] records = this.getRecords(p).split(",");
			for(int i = 1; i < records.length; i++) {
				if (i % 3 == 1) {
					System.out.println("Game " + ((i/3)+1) + ": ");
					System.out.print("\tResult: ");
					if(records[i].equals("true")) {
						System.out.println("Won");
					} else {
						System.out.println("Lost");
					}
				} else if (i % 3 == 2) {
					System.out.println("\tRemaining Turns: " + records[i]);
				} else {
					System.out.println("\tTotal Hits: " + records[i]);
				}
			}
			System.out.println();
		}
	}

	/*
	 *  Adds the results of the players most recent game to the txt file containing player info.
	 *  Each piece of data is separated by a comma to make parsing it easier.
	 */
	@Override
	public void updateRecords(Player p, boolean win, int remainingTurns, int hits) {
		String playerName = p.getUsername();
		try {
			absolutePath = directory.getCanonicalPath() + File.separator + playerName + fileName;
		} catch (IOException e) {
			log.error("Invalid file name.", e);
			e.printStackTrace();
		}
		File recordFile = new File(absolutePath);
		// write the content to file 
		try {
			PrintStream printer = new PrintStream(new BufferedOutputStream(new FileOutputStream(recordFile, true)));
		    String newRecord = "," + win + "," + remainingTurns + "," + hits;
		    printer.print(newRecord);
		    printer.close();
		} catch (IOException e) {
			log.error("Problem reading from file.", e);
			e.printStackTrace();
		}
		
	}

}
