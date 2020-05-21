package com.revature.battleship.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.revature.battleship.main.BattleshipDriver;
import com.revature.battleship.player.Player;
import com.revature.battleship.player.PlayerRecord;
import com.revature.battleship.scores.HighScore;
import com.revature.battleship.scores.ScoreKeeper;


public class BattleshipDAO_Postgres implements BattleshipDAO {

	private static Logger log = Logger.getLogger(BattleshipDriver.class);

	// Battleship Database Tables and Columns
	// Player Table
	private static final String PLAYER_TABLE = "battleship_player";
	private static final String PLAYER_ID = "battleship_player_id";
	private static final String PLAYER_USERNAME = "battleship_player_username";
	private static final String PLAYER_PASSWORD = "battleship_player_password";
	private static final String PLAYER_TOTAL_GAMES = "battleship_player_total_games";
	private static final String PLAYER_TOTAL_WINS = "battleship_player_total_wins";
	private static final String PLAYER_TOTAL_HITS = "battleship_player_total_hits";
	private static final String PLAYER_TOTAL_REMAINING_TURNS = "battleship_player_total_remaining_turns";
	// Results Table
	private static final String RESULTS_TABLE = "battleship_game_results";
	private static final String RESULTS_PLAYER_ID = "battleship_player_id";
	private static final String RESULTS_WIN = "battleship_win";
	private static final String RESULTS_REMAINING_TURNS = "battleship_remaining_turns";
	private static final String RESULTS_HITS = "battleship_hits";
	// High Scores Table
	private static final String HIGH_SCORES_USER_NAME = "user_name";
	private static final String HIGH_SCORES_TOTAL_GAMES = "total_games";
	private static final String HIGH_SCORES_TOTAL_WINS = "total_wins";
	private static final String HIGH_SCORES_TOTAL_HITS = "total_hits";
	private static final String HIGH_SCORES_TOTAL_REMAINING_TURNS = "total_remaining_turns";

	// Battleship Database Queries
	// Player Queries
	private static final String SAVE_PLAYER_QUERY = "insert into " + PLAYER_TABLE + "(" + PLAYER_USERNAME + ", "
			+ PLAYER_PASSWORD + ") values (?, ?)";
	private static final String GET_PLAYER_QUERY = "select * from " + PLAYER_TABLE + " where " + PLAYER_USERNAME
			+ " = ? and " + PLAYER_PASSWORD + " = ?";
	private static final String FIND_PLAYER_QUERY = "select * from " + PLAYER_TABLE + " where " + PLAYER_USERNAME
			+ " = ?";
	private static final String GET_TOP_PLAYERS = "select * from get_high_scores()";
	
	// Results Queries
	private static final String UPDATE_PLAYER_RECORDS_QUERY = "insert into " + RESULTS_TABLE + "(" + RESULTS_PLAYER_ID
			+ ", " + RESULTS_WIN + ", " + RESULTS_REMAINING_TURNS + ", " + RESULTS_HITS + ") values((select "
			+ RESULTS_PLAYER_ID + " from " + PLAYER_TABLE + " where " + PLAYER_TABLE + "." + PLAYER_USERNAME
			+ " = ?), ?, ?, ?)";
	private static final String GET_PLAYER_RECORDS_QUERY = "select " + RESULTS_WIN + ", " + RESULTS_REMAINING_TURNS + ", "
			+ RESULTS_HITS + " from " + RESULTS_TABLE + " where " + RESULTS_PLAYER_ID + " = (select " + PLAYER_ID
			+ " from " + PLAYER_TABLE + " where " + PLAYER_USERNAME + " = ?)";

	@Override
	public void savePlayer(Player p) {
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(SAVE_PLAYER_QUERY);
			stmt.setString(1, p.getUsername());
			stmt.setString(2, p.getPassword());
		} catch (SQLException e) {
			log.error("Error preparing PostgreSQL statement.", e);
			e.printStackTrace();
		} finally {
			try {
				stmt.execute();
				conn.close();
			} catch (SQLException e) {
				log.error("Error executing PostgreSQL statement.", e);
				e.printStackTrace();
			}
		}
	}

	@Override
	public Player getPlayer(String playerName, String password) {
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement stmt;
		Player p = new Player();
		try {
			stmt = conn.prepareStatement(GET_PLAYER_QUERY);
			stmt.setString(1, playerName);
			stmt.setString(2, password);

			ResultSet res = stmt.executeQuery();

			if(res.next()) {
				p.setUsername(res.getString(PLAYER_USERNAME));
				p.setPassword(res.getString(PLAYER_PASSWORD));
				p.setTotalGames(res.getInt(PLAYER_TOTAL_GAMES));
				p.setTotalWins(res.getInt(PLAYER_TOTAL_WINS));
				p.setTotalHits(res.getInt(PLAYER_TOTAL_HITS));
				p.setTotalRemainingTurns(res.getInt(PLAYER_TOTAL_REMAINING_TURNS));
				getPlayerRecords(p);
			}
			else {
				p = null;
			}

		} catch (SQLException e) {
			log.error("Error preparing and executing PostgreSQL statement.", e);
			e.printStackTrace();
		}  finally {
			try {
				conn.close();
			} catch (SQLException e) {
				log.error("Error ending database connection.", e);
				e.printStackTrace();
			}
		}

		return p;
	}

	@Override
	public boolean userNameExists(String playerName) {
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement stmt;
		boolean existingPlayer = false;
		try {
			stmt = conn.prepareStatement(FIND_PLAYER_QUERY);
			stmt.setString(1, playerName);

			ResultSet res = stmt.executeQuery();

			if(res.next()) {
				existingPlayer = true;
			}

		} catch (SQLException e) {
			log.error("Error preparing and executing PostgreSQL statement.", e);
			e.printStackTrace();
		}  finally {
			try {
				conn.close();
			} catch (SQLException e) {
				log.error("Error ending database connection.", e);
				e.printStackTrace();
			}
		}

		return existingPlayer;
	}

	@Override
	public void getPlayerRecords(Player p) {
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement stmt;
		boolean win;
		int remainingTurns;
		int hits;
		try {
			stmt = conn.prepareStatement(GET_PLAYER_RECORDS_QUERY);
			stmt.setString(1, p.getUsername());

			ResultSet res = stmt.executeQuery();
			while(res.next()) {
				win = res.getBoolean(RESULTS_WIN);
				remainingTurns = res.getInt(RESULTS_REMAINING_TURNS);
				hits = res.getInt(RESULTS_HITS);
				PlayerRecord r = new PlayerRecord(win, remainingTurns, hits);
				p.addRecord(r);
			}

		} catch (SQLException e) {
			log.error("Error preparing and executing PostgreSQL statement.", e);
			e.printStackTrace();
		}  finally {
			try {
				conn.close();
			} catch (SQLException e) {
				log.error("Error ending database connection.", e);
				e.printStackTrace();
			}
		}
	}

	@Override
	public void updatePlayerRecords(Player p, boolean win, int remainingTurns, int hits) {
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(UPDATE_PLAYER_RECORDS_QUERY);
			stmt.setString(1, p.getUsername());
			stmt.setBoolean(2, win);
			stmt.setInt(3, remainingTurns);
			stmt.setInt(4, hits);
		} catch (SQLException e) {
			log.error("Error preparing PostgreSQL statement.", e);
			e.printStackTrace();
		} finally {
			try {
				stmt.execute();
				conn.close();
			} catch (SQLException e) {
				log.error("Error executing PostgreSQL statement and ending database connection.", e);
				e.printStackTrace();
			}
		}
	}

	@Override
	public void getHighScores(ScoreKeeper s) {
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement stmt;
		String userName;
		int games;
		int wins;
		int hits;
		int remainingTurns;
		
		try {
			stmt = conn.prepareStatement(GET_TOP_PLAYERS);

			ResultSet res = stmt.executeQuery();
			while(res.next()) {
				userName = res.getString(HIGH_SCORES_USER_NAME);
				games = res.getInt(HIGH_SCORES_TOTAL_GAMES);
				wins = res.getInt(HIGH_SCORES_TOTAL_WINS);
				hits = res.getInt(HIGH_SCORES_TOTAL_HITS);
				remainingTurns = res.getInt(HIGH_SCORES_TOTAL_REMAINING_TURNS);
				HighScore h = new HighScore(userName, games, wins, hits, remainingTurns);
				s.addHighScore(h);
			}

		} catch (SQLException e) {
			log.error("Error preparing and executing PostgreSQL statement.", e);
			e.printStackTrace();
		}  finally {
			try {
				conn.close();
			} catch (SQLException e) {
				log.error("Error ending database connection.", e);
				e.printStackTrace();
			}
		}
	}
	
}