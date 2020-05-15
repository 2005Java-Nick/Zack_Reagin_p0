package com.revature.battleship.main;

import com.revature.battleship.player.Player;
import com.revature.battleship.player.PlayerRecords;
import com.revature.battleship.player.PlayerRecordsDAO;
import com.revature.battleship.dao.PlayerDAO;
import com.revature.battleship.dao.PlayerDAOSerialization;

import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.revature.battleship.game.*;

/*	Battleship Program Flow
 * 	1: Asks player if they would like to create a new profile (1.1) or log-in (1.2) to an existing profile.
 * 		1.1: If player chooses to create a new profile, prompts user for user name (1.1.1). Offers to return to previous step (1).
 * 			1.1.1: Reads player input, and prompts player for password (1.1.2).
 * 			1.1.2: Reads player password, and prompts player to re-enter password (1.1.3).
 * 			1.1.3: Passwords are checked to see if they match (1.1.3.1) or not (1.1.3.2).
 * 				1.1.3.1: If passwords match, new user profile is created, and player is brought to main menu (2).
 * 				1.1.3.2: If passwords do not match, player is prompted for password again (1.1.2).
 * 		1.2: If player chooses to login, prompts for user name (1.2.1). Offers to return to previous step (1).
 * 			1.2.1: Reads user name, then asks for password (1.2.2). Offers to return to initial screen (1).
 * 			1.2.2: Searches for user name, and checks if password matches (1.2.2.1) or not (1.2.2.2).
 * 				1.2.2.1: If user name and password match, user is brought to main menu (2).
 * 				1.2.2.2: If user name and password do not match, returns error message "Incorrect password for <user name>."
 * 					Offers option to re-enter password (1.2.2.2.1) or return to initial screen (1).
 * 					1.2.2.2.1: Choosing to re-enter password brings user to password prompt (1.2.1).
 * 					1.2.2.2.2: User is brought back to initial screen (1).
 * 	2: Player is given choice of viewing their records (2.1), viewing high scores(2.2), playing game(3), or quitting(4).
 * 		2.1: If player chooses to view their records, their records from previous games are shown, and player is prompted for next option (2).
 * 			2.1.1: Viewing high scores takes player to 2.2.
 * 			2.1.2: Playing a game brings user to 3.
 * 			2.1.3: Quitting exits the program. (4)
 * 		2.2: If player chooses to view high scores, the high scores are shown, and player is prompted for next option (2).
 * 			2.2.1: Viewing their records brings player to 2.1.
 * 			2.1.2: Playing a game brings user to 3.
 * 			2.1.3: Quitting exits the program. (4)
 * 	3: Game play begins. (3.1)
 * 		3.1: A new map is created. (3.2)
 * 		3.2: Five new ships are created. (3.3)
 * 		3.3: Ships are placed in random positions and orientations on the map. (3.4)
 * 		3.4: Player is told how many turns they have to find and sink all 5 battleships and asked to give coordinates for where they would like to fire. (3.5)
 * 		3.5: Location is checked to determine if it is an invalid choice (3.5.1), a hit (3.5.2), or a miss (3.5.3).
 * 			3.5.1: Player is given the reason their choice is invalid (out of bounds/already chosen) and brought to 3.4.
 * 			3.5.2: Player is told that they hit a ship. Ship is marked as hit in correct location. All locations damaged? Yes (3.5.2.1) or No (3.5.2.2)
 * 				3.5.2.1: Player is congratulated for sinking <ship>. (3.5.2.2)
 * 				3.5.2.2: Game checks if all ships are sunk. Y (3.5.2.2.1) or N (3.5.2.2.2)
 * 					3.5.2.2.1: Game congratulates player for winning. (3.5.2.2.1.2)
 * 						3.5.2.2.2: Final map and turns remaining are displayed. User records/high scores updated. User is taken to 2.
 * 					3.5.2.2.2: Game subtracts 1 from turns remaining. Turns > 0? (3.4) Turns = 0? (3.6)
 * 			3.5.3: Player is informed that they missed, displays map, and subtracts 1 from turns remaining. Turns > 0? (3.4) Turns = 0? (3.6)
 * 		3.6: Player is told they lost. Final map is shown. User records updated. Player is returned to 2.
 * 	4: Game exits.
 * 			
 */

public class BattleshipDriver {
	private static final BattleshipGame battleship = new BattleshipGame();
	
	private static Logger log = Logger.getLogger(BattleshipDriver.class);
	
	private static Scanner scan = new Scanner(System.in);
	
	private static PlayerDAO playerDao = new PlayerDAOSerialization();
	
	private static HighScores highScores = new HighScoresDAO();
	
	private static PlayerRecords records;
	
	public static void main(String[] args) {
		PropertyConfigurator.configure("log4j.properties");
		String playername;
		String password;
		Player player = null;
		String location;
		String choice = "";
		
		//Log-in or create new account
		System.out.println("Welcome to the Ship Sinking Game!");
		while(!choice.equalsIgnoreCase("C") && !choice.equalsIgnoreCase("L")) {
			System.out.println("Please enter 'L' if you would like to log in to an existing account,"
				+ " or 'C' if you would like to create a new account: ");
			choice = scan.nextLine();
			if(choice.equalsIgnoreCase("L")) {
				System.out.println("Please enter your user name: ");
				playername = scan.nextLine();
				System.out.println("Please enter your password: ");
				password = scan.nextLine();
				player = playerDao.getPlayer(playername, password);
				if(player == null) {
					System.out.println("Sorry. Either your username or password was incorrect.");
					log.info("Attempt to log into accout with user name " + playername + " was unsuccessful.");
					choice = "";
				} else {
					log.info("Player " + player.getUsername() + " successfully logged in.");
				}
			} else if(choice.equalsIgnoreCase("C")) {
				System.out.print("Please enter a user name: ");
				playername = scan.nextLine();
				if(playerDao.playerExists(playername)) {
					System.out.println("Sorry. That user name is already taken.");
					choice = "";
				} else {
					System.out.print("Please enter a password: ");
					password = scan.nextLine();
					player = new Player(playername, password);
					playerDao.savePlayer(player);
					log.info("New account created with user name " + player.getUsername() + ".");
				}
			} else {
				System.out.println("Invalid choice. Please enter 'L' to log in, or 'C' to create a new account:");
			}
		}
		
		// Main menu with options for viewing scores and game play
		System.out.println("Welcome " + player.getUsername() + "!");
		records = new PlayerRecordsDAO();
		String selection = "";
		while(!selection.equalsIgnoreCase("Q")) {
			System.out.println("What would you like to do?");
			System.out.println("[V] View Records\n[H] See high scores\n[P] Play a game\n[Q] Quit");
			selection = scan.nextLine();
			if (selection.equalsIgnoreCase("V")) {
				records.displayRecords(player);
				log.info("User " + player.getUsername() + " viewed their records.");
			} else if (selection.equalsIgnoreCase("H")) {
				highScores.displayRecords();
				log.info("User " + player.getUsername() + " viewed the high scores.");
			} else if (selection.equalsIgnoreCase("P")) {
				BattleshipGame.setUp();
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
				while(battleship.getRemainingTurns() > 0 && BattleshipGame.getHits() < battleship.getTOTAL_HITS()) {
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						log.error("Problem with using thread sleep method.", e);
					}
					battleship.getMap().printPlayMap();
					System.out.println("Please select a location:");
					location = scan.nextLine();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						log.error("Problem with using thread sleep method.", e);
					}
					System.out.println("Launching missile...");
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						log.error("Problem with using thread sleep method.", e);
					}
					BattleshipGame.attack(location);
				}
				if(BattleshipGame.getHits() == battleship.getTOTAL_HITS()) {
					System.out.println("Congratulations! You win!");
					System.out.println("You managed to sink all enemy ships with " + battleship.getRemainingTurns() + " missilies remaining!");
					records.updateRecords(player, true, battleship.getRemainingTurns(), BattleshipGame.getHits());
					highScores.updateRecords(player, true, battleship.getRemainingTurns(), BattleshipGame.getHits());
					log.info("User " + player.getUsername() + " just won a game with " + battleship.getRemainingTurns() + " turns remaining.");
				} else {
					System.out.println("Oh no! We're out of missiles!");
					System.out.println("You only managed to hit enemy ships " + BattleshipGame.getHits() + " times!");
					records.updateRecords(player, false, battleship.getRemainingTurns(), BattleshipGame.getHits());
					highScores.updateRecords(player, false, battleship.getRemainingTurns(), BattleshipGame.getHits());
					log.info("User " + player.getUsername() + " just lost a game with " + BattleshipGame.getHits() + " total successful hit(s).");
				}
				battleship.getMap().printMap();
			} else if (selection.equalsIgnoreCase("Q")) {
				break;
			} else {
				System.out.println("Sorry. That is not a valid choice. Please enter either 'V', 'H', 'P', or 'Q'");
			}
		}
		System.out.println("Goodbye " + player.getUsername() + "!");
	}
}
