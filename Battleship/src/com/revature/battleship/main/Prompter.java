package com.revature.battleship.main;

import java.util.Scanner;

import org.apache.log4j.Logger;
import com.revature.battleship.dao.BattleshipDAO;
import com.revature.battleship.dao.BattleshipDAO_Postgres;
import com.revature.battleship.game.BattleshipGame;
import com.revature.battleship.player.Player;

public class Prompter {
	
	private static Scanner scan = new Scanner(System.in);
	
	private static BattleshipDAO battleshipDAO = new BattleshipDAO_Postgres();
	
	private static Logger log = Logger.getLogger(BattleshipDriver.class);
	

	public void printWelcomeMessage() {
		System.out.println("Welcome to the Naval Warfare Game!");

	}

	public void printLogInMessage() {
		System.out.println("What would you like to do?"
				+ "\n[L] Log In\n[C] Create New Account.\n[Q] Quit.");
	}

	public Player runLogInScript(Player player) {
		String choice = "";
		String playername;
		String password;
		while(!choice.equalsIgnoreCase("C") && !choice.equalsIgnoreCase("L") && !choice.equalsIgnoreCase("Q")) {
			this.printLogInMessage();
			choice = scan.nextLine();
			if(choice.equalsIgnoreCase("L")) {
				System.out.print("Please enter your user name: ");
				playername = scan.nextLine();
				System.out.print("Please enter your password: ");
				password = scan.nextLine();
				player = battleshipDAO.getPlayer(playername, password);
				if(player == null) {
					System.out.println("Sorry. Either your username or password was incorrect.");
					log.info("Attempt to log into accout with user name " + playername + " was unsuccessful.");
					choice = "";
				} else {
					System.out.println("Welcome back, " + player.getUsername() + "!");
					log.info("Player " + player.getUsername() + " successfully logged in.");
				}
			} else if(choice.equalsIgnoreCase("C")) {
				System.out.print("Please enter a user name: ");
				playername = scan.nextLine();
				if(battleshipDAO.userNameExists(playername)) {
					System.out.println("Sorry. That user name is already taken.");
					choice = "";
				} else {
					System.out.print("Please enter a password: ");
					password = scan.nextLine();
					player = new Player(playername, password);
					battleshipDAO.savePlayer(player);
					System.out.println("Welcome, " + player.getUsername() + "!");
					log.info("New account created with user name " + player.getUsername() + ".");
				}
			} else if(choice.equalsIgnoreCase("Q")) {
				System.out.println("Exiting program...");
				System.exit(0);
			} else {
				System.out.println("Invalid choice.");
			}
		}
		return player;
	}

	public void printInstructions() {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("We have received intelligence that there are five enemy ships hidden somewhere off the coast.");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			log.error("Problem with using thread sleep method.", e);
		}
		System.out.println("We have created a map of the area, which we have divided up into grids.");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			log.error("Problem with using thread sleep method.", e);
		}
		System.out.println("Each of our missiles will destroy an area approximately the size of one of the squares.");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			log.error("Problem with using thread sleep method.", e);
		}
		System.out.println("The ships themselves take up 2 squares, 3 squares, 4 squares, 5 squares, or 6 squares, respectively.");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			log.error("Problem with using thread sleep method.", e);
		}
		System.out.println("In order to destroy the ships, you must hit each section, but we only have 40 missiles, so aim carefully.");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			log.error("Problem with using thread sleep method.", e);
		}
		System.out.println("Enter the coordinates for the square you would like to hit by entering the letter and number.");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			log.error("Problem with using thread sleep method.", e);
		}
		System.out.println("For example, to hit the top left square, enter A1.");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			log.error("Problem with using thread sleep method.", e);
		}
		System.out.println("OK, it's time to begin. Good luck.");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			log.error("Problem with using thread sleep method.", e);
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
	
	public void playGame(BattleshipGame battleship) {
		while(battleship.getRemainingTurns() > 0 && BattleshipGame.getHits() < battleship.getTOTAL_HITS()) {
			String location;
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				log.error("Problem with using thread sleep method.", e);
//			}
			battleship.getMap().printPlayMap();
			System.out.println("Please select a location:");
			location = scan.nextLine();
			while (!battleship.getMap().checkIfCoordinatesValid(location)) {
				System.out.println("Invalid coordinates.");
				System.out.println("Please select a location:");
				location = scan.nextLine();
			}
//			try {
//				Thread.sleep(500);
//			} catch (InterruptedException e) {
//				log.error("Problem with using thread sleep method.", e);
//			}
			System.out.println("Launching missile...");
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				log.error("Problem with using thread sleep method.", e);
//			}
			BattleshipGame.attack(location);
		}
	}
	
	public void logOut(Player player) {
		System.out.println("Goodbye, " + player.getUsername() + "!");
		player = this.runLogInScript(player);
	}
}
