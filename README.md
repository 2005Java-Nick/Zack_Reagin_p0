# Zack_Reagin_p0

/*	Battleship Program Flow (Initial Program Flow Logic)
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