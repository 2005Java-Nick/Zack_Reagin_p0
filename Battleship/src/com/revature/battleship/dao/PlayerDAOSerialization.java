package com.revature.battleship.dao;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.apache.log4j.Logger;

import com.revature.battleship.player.Player;


public class PlayerDAOSerialization implements PlayerDAO{

	private static Logger log = Logger.getRootLogger();
	File directory = new File(".");
	String fileName = "usernamepasswords.txt";
	String absolutePath = "";
	
	// Save player data to file
	@Override
	public void savePlayer(Player p) {
		try {
			absolutePath = directory.getCanonicalPath() + File.separator + fileName;
		} catch (IOException e) {
			log.error("Cannot resolve file path.", e);
			e.printStackTrace();
		}
	
		// write the content in file 
		try {
			PrintStream printer = new PrintStream(new BufferedOutputStream(new FileOutputStream(absolutePath, true)));
		    String userName = "{" + p.getUsername() + "}";
		    printer.print(userName);
		    String password = "[" + p.getPassword() + "]";
		    printer.println(password);
		    printer.close();
		} catch (IOException e) {
		    log.error("Cannot write to file.", e);
		    e.printStackTrace();
		}
	}
	
	
	// Check player user name and password against file and return player.
	@Override
	public Player getPlayer(String playerName, String password) {
		String fileContents = "";
		try {
			absolutePath = directory.getCanonicalPath() + File.separator + fileName;
		} catch (IOException e) {
			log.error("Cannot resolve file path.", e);
			e.printStackTrace();
		}
		
		// read the content from file
		try(BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(absolutePath))) {
		    int ch = bufferedInputStream.read();
		    while(ch != -1) {
		        fileContents += (char) ch;
		        ch = bufferedInputStream.read();
		    }
		} catch (FileNotFoundException e) {
			log.error("Cannot resolve file path.", e);
			e.printStackTrace();
		} catch (IOException e) {
			log.error("Cannot read from file.", e);
			e.printStackTrace();
		}
		if (fileContents.contains("{" + playerName + "}[" + password + "]")) {
			return new Player(playerName, password);
		}
		return null;
	}
	
	// Check if user name and password exist in file.
	@Override
	public boolean playerExists(String playerName) {
		String fileContents = "";
		try {
			absolutePath = directory.getCanonicalPath() + File.separator + fileName;
		} catch (IOException e) {
			log.error("Cannot resolve file path.", e);
			e.printStackTrace();
		}
		
		// read the content from file
		try(BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(absolutePath))) {
		    int ch = bufferedInputStream.read();
		    while(ch != -1) {
		        fileContents += (char) ch;
		        ch = bufferedInputStream.read();
		    }
		} catch (FileNotFoundException e) {
			log.error("Cannot resolve file path.", e);
			e.printStackTrace();
		} catch (IOException e) {
			log.error("Cannot read from file.", e);
			e.printStackTrace();
		}
		if (fileContents.contains("{" + playerName + "}")) {
			return true;
		}
		return false;
	}
}
