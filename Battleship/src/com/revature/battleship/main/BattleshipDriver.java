package com.revature.battleship.main;

import com.revature.battleship.player.Player;
import com.revature.battleship.scores.ScoreKeeper;
import com.revature.battleship.dao.BattleshipDAO;
import com.revature.battleship.dao.BattleshipDAO_Postgres;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.revature.battleship.game.*;



public class BattleshipDriver {
	private static final BattleshipGame battleship = new BattleshipGame();
	
	private static Logger log = Logger.getLogger(BattleshipDriver.class);
	
	private static Scanner scan = new Scanner(System.in);
	
	private static BattleshipDAO battleshipDAO = new BattleshipDAO_Postgres();
	
	private static Prompter prompter = new Prompter();
	
	private static ScoreKeeper scoreKeeper = new ScoreKeeper();
	
	public static void main(String[] args) {
		PropertyConfigurator.configure("log4j.properties");
		
		Player player = null;
		
		
		//Log-in or create new account
		prompter.printWelcomeMessage();
		player = prompter.runLogInScript(player);
		
		// Main menu with options for viewing scores and game play
		String selection = "";
		while(!selection.equalsIgnoreCase("L")) {
			System.out.println("What would you like to do?");
			System.out.println("[V] View Records\n[H] See High Scores\n[P] Play Game\n[L] Log Out");
			selection = scan.nextLine();
			if (selection.equalsIgnoreCase("V")) {
				player.showRecords();
				log.info("User " + player.getUsername() + " viewed their records.");
			} else if (selection.equalsIgnoreCase("H")) {
				battleshipDAO.getHighScores(scoreKeeper);
				scoreKeeper.displayHighScores();
				log.info("User " + player.getUsername() + " viewed the high scores.");
			} else if (selection.equalsIgnoreCase("P")) {
				BattleshipGame.setUp();
//				prompter.printInstructions();
				prompter.playGame(battleship);
				if(BattleshipGame.getHits() == battleship.getTOTAL_HITS()) {
					System.out.println("Congratulations! You win!");
					System.out.println("You managed to sink all enemy ships with " + battleship.getRemainingTurns() + " missiles remaining!");
					battleshipDAO.updatePlayerRecords(player, true, battleship.getRemainingTurns(), BattleshipGame.getHits());
					player = battleshipDAO.getPlayer(player.getUsername(), player.getPassword());
					log.info("User " + player.getUsername() + " just won a game with " + battleship.getRemainingTurns() + " turns remaining.");
				} else {
					System.out.println("Oh no! We're out of missiles!");
					System.out.println("You only managed to hit enemy ships " + BattleshipGame.getHits() + " times!");
					battleshipDAO.updatePlayerRecords(player, false, battleship.getRemainingTurns(), BattleshipGame.getHits());
					player = battleshipDAO.getPlayer(player.getUsername(), player.getPassword());
					log.info("User " + player.getUsername() + " just lost a game with " + BattleshipGame.getHits() + " total successful hit(s).");
				}
				battleship.getMap().printMap();
			} else if (selection.equalsIgnoreCase("L")) {
				prompter.logOut(player);
			} else {
				System.out.println("Sorry. That is not a valid choice. Please enter either 'V', 'H', 'P', or 'L'");
			}
		}
	}
}
