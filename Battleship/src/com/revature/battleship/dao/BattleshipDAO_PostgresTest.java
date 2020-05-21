package com.revature.battleship.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.battleship.main.BattleshipDriver;
import com.revature.battleship.player.Player;
import com.revature.battleship.scores.ScoreKeeper;

public class BattleshipDAO_PostgresTest {

	BattleshipDAO_Postgres tester = new BattleshipDAO_Postgres();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		Logger log = Logger.getLogger(BattleshipDriver.class);
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		PreparedStatement stmt3 = null;
		PreparedStatement stmt4 = null;
		PreparedStatement stmt5 = null;
		PreparedStatement stmt6 = null;
		PreparedStatement stmt7 = null;
		PreparedStatement stmt8 = null;
		PreparedStatement stmt9 = null;
		PreparedStatement stmt10 = null;
		try {
			stmt1 = conn.prepareStatement("alter table battleship_game_results\r\n" + 
					"	drop constraint if exists fk_battleship_game_results_player_id;");
			stmt2 = conn.prepareStatement("drop table if exists battleship_player;\r\n" + 
					"create table battleship_player (\r\n" + 
					"	battleship_player_id bigserial not null,\r\n" + 
					"	battleship_player_username varchar(20) unique not null,\r\n" + 
					"	battleship_player_password varchar(20) not null,\r\n" + 
					"	battleship_player_total_games int default 0,\r\n" + 
					"	battleship_player_total_wins int default 0,\r\n" + 
					"	battleship_player_total_hits int default 0,\r\n" + 
					"	battleship_player_total_remaining_turns int default 0,\r\n" + 
					"	constraint pk_battleship_player primary key (battleship_player_id )\r\n" + 
					");");
			stmt3 = conn.prepareStatement("drop table if exists battleship_game_results;\r\n" + 
					"create table battleship_game_results (\r\n" + 
					"	battleship_game_id bigserial not null,\r\n" + 
					"	battleship_player_id bigint not null,\r\n" + 
					"	battleship_win bool not null,\r\n" + 
					"	battleship_remaining_turns int not null,\r\n" + 
					"	battleship_hits int not null,\r\n" + 
					"	constraint pk_battleship_game_results primary key (battleship_game_id )\r\n" + 
					");");
			stmt4 = conn.prepareStatement("alter table battleship_game_results add constraint fk_battleship_game_results_player_id\r\n" + 
					"	foreign key (battleship_player_id) references battleship_player (battleship_player_id) on delete cascade on update cascade;\r\n" + 
					"\r\n" + 
					"create index ifk_battleship_game_results_player_id on battleship_game_results (battleship_player_id);");
			stmt5 = conn.prepareStatement("create or replace function update_player_totals()\r\n" + 
					"returns trigger as $$\r\n" + 
					"declare\r\n" + 
					"	player_id bigint := new.battleship_player_id;\r\n" + 
					"	new_win int := (case when new.battleship_win then 1 else 0 end);\r\n" + 
					"	new_turns int := new.battleship_remaining_turns;	\r\n" + 
					"	new_hits int := new.battleship_hits;\r\n" + 
					"begin\r\n" + 
					"	update battleship_player\r\n" + 
					"		set battleship_player_total_games = battleship_player_total_games + 1,\r\n" + 
					"			battleship_player_total_wins = battleship_player_total_wins + new_win,\r\n" + 
					"			battleship_player_total_remaining_turns = battleship_player_total_remaining_turns + new_turns,\r\n" + 
					"			battleship_player_total_hits = battleship_player_total_hits + new_hits\r\n" + 
					"		where battleship_player_id = player_id;\r\n" + 
					"	return new;\r\n" + 
					"end; $$ language plpgsql;");
			stmt6 = conn.prepareStatement("drop trigger if exists new_record on battleship_game_results;");
			stmt7 = conn.prepareStatement("create trigger new_record\r\n" + 
					"after insert on battleship_game_results\r\n" + 
					"for each row\r\n" + 
					"execute procedure update_player_totals();");
			stmt8 = conn.prepareStatement("create or replace function get_high_scores()\r\n" + 
					"returns table(user_name varchar, total_games int, total_wins int, total_hits int, total_remaining_turns int) as $$\r\n" + 
					"begin\r\n" + 
					"	return query\r\n" + 
					"	select battleship_player_username,\r\n" + 
					"		battleship_player_total_games,\r\n" + 
					"		battleship_player_total_wins,\r\n" + 
					"		battleship_player_total_hits,\r\n" + 
					"		battleship_player_total_remaining_turns\r\n" + 
					"	from battleship_player\r\n" + 
					"	order by battleship_player_total_wins desc,\r\n" + 
					"		battleship_player_total_remaining_turns desc,\r\n" + 
					"		battleship_player_total_hits desc\r\n" + 
					"	limit 10;\r\n" + 
					"end; $$ language plpgsql;");
			stmt9 = conn.prepareStatement("insert into battleship_player(battleship_player_username, battleship_player_password )\r\n" + 
					"	values ('Phantomcat261', '}z@x.L3u~N%b3kw'), ('Amigod576', 'nH#=YCv4REQ2;8\\m#^'), ('Phantomato', 'p.{2TT]@x4J)\"c2>'), ('PlaySpike402', '+QJ5v{\"}.ac?Bc*/'),\r\n" + 
					"			('FrizzyBronco', 'n5GtS2T9,6)8yz;:'), ('Sassassin', 'vt_+VFz@DV@q7\"G?'), ('Lobsteroid19', '7VV4y?vL(<643?6#'), ('FrozenPaladin422', '5f)hb(+we.uVT&86'),\r\n" + 
					"			('Baconqueror', 'LmH-MgAmamr8}6nu'), ('Essaint229', 'TD)e;7~6xk{N5~jF'), ('StormT-Rex', '`v?*qfwUk4Rtbqe}'), ('Barracupid304', 'R8.JU^Q{q&7XCn!6');");
			stmt10 = conn.prepareStatement("insert into battleship_game_results(battleship_player_id, battleship_win, battleship_remaining_turns, battleship_hits)\r\n" + 
					"	values (1, true, 12, 20), (1, false, 0, 19), (1, false, 0, 16), (1, true, 4, 20), (2, true, 13, 20), (2, true, 6, 20),\r\n" + 
					"			(2, false, 0, 14), (3, false, 0, 18), (3, false, 0, 19), (3, true, 2, 20), (3, false, 0, 16), (3, true, 6, 20),\r\n" + 
					"			(3, true, 8, 20), (4, true, 5, 20), (4, true, 8, 20), (4, true, 12, 20), (4, true, 19, 20), (4, true, 4, 20),\r\n" + 
					"			(5, true, 0, 20), (5, false, 0, 11), (5, true, 2, 20), (6, true, 3, 20), (7, false, 0, 17),  (7, false, 0, 16),\r\n" + 
					"			(8, true, 13, 20), (8, false, 0, 19), (8, true, 11, 20), (8, false, 0, 8), (9, true, 2, 20), (9, true, 3, 20),\r\n" + 
					"			(10, false, 0, 16), (10, false, 0, 9), (10, false, 0, 14), (10, true, 3, 20), (10, true, 4, 20), (11, true, 2, 20),\r\n" + 
					"			(11, true, 4, 20), (12, true, 2, 20), (12, true, 6, 20), (12, false, 0, 12);");
		} catch (SQLException e) {
			log.error("Error preparing PostgreSQL statement.", e);
			e.printStackTrace();
		} finally {
			try {
				stmt1.execute();
				stmt2.execute();
				stmt3.execute();
				stmt4.execute();
				stmt5.execute();
				stmt6.execute();
				stmt7.execute();
				stmt8.execute();
				stmt9.execute();
				stmt10.execute();
				conn.close();
			} catch (SQLException e) {
				log.error("Error executing PostgreSQL statement.", e);
				e.printStackTrace();
			}
		}
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSavePlayerUserNameDoesNotExist1() {
		assertFalse(tester.userNameExists("Zack"));
	}
	
	@Test
	public void testSavePlayerUserNameExists1() {
		Player player = new Player("Zack", "password123");
		tester.savePlayer(player);
		assertTrue(tester.userNameExists("Zack"));
	}
	
	@Test
	public void testSavePlayerUserNameDoesNotExist2() {
		assertFalse(tester.userNameExists("Comodore"));
	}
	
	public void testSavePlayerUserNameExists2() {
		Player player = new Player("Comodore", "iluvthec");
		tester.savePlayer(player);
		assertTrue(tester.userNameExists("Comodore"));
	}

	@Test
	public void testUserNameExists1() {
		assertTrue(tester.userNameExists("Amigod576"));
	}
	
	@Test
	public void testUserNameExists2() {
		assertTrue(tester.userNameExists("Barracupid304"));
	}
	
	@Test
	public void testGetPlayer1() {
		Player player = new Player("Phantomcat261", "}z@x.L3u~N%b3kw");
		player = tester.getPlayer(player.getUsername(), player.getPassword());
		assertEquals(player.getTotalWins(), 2);
	}
	
	@Test
	public void testGetPlayer2() {
		Player player = new Player("Lobsteroid19", "7VV4y?vL(<643?6#");
		player = tester.getPlayer(player.getUsername(), player.getPassword());
		assertEquals(player.getTotalHits(), 33);
	}

	@Test
	public void testGetPlayerRecords1() {
		Player player = new Player("Lobsteroid19", "7VV4y?vL(<643?6#");
		player = tester.getPlayer(player.getUsername(), player.getPassword());
		tester.getPlayerRecords(player);
		assertEquals(player.getRecords().get(1).getHits(), 16);
	}
	
	@Test
	public void testGetPlayerRecords2() {
		Player player = new Player("FrizzyBronco", "n5GtS2T9,6)8yz;:");
		player = tester.getPlayer(player.getUsername(), player.getPassword());
		tester.getPlayerRecords(player);
		assertEquals(player.getRecords().get(2).getRemainingTurns(), 2);
	}

	@Test
	public void testUpdatePlayerRecords() {
		Player player = new Player("Phantomcat261", "}z@x.L3u~N%b3kw");
		tester.updatePlayerRecords(player, true, 16, 20);
		player = tester.getPlayer(player.getUsername(), player.getPassword());
		assertEquals(player.getTotalRemainingTurns(), 32);
	}

	@Test
	public void testGetHighScores1() {
		ScoreKeeper scoreKeeper = new ScoreKeeper();
		tester.getHighScores(scoreKeeper);
		assertEquals(scoreKeeper.getTopPlayers().get(6).getUserName(), "Essaint229");
	}
	
	@Test
	public void testGetHighScores2() {
		ScoreKeeper scoreKeeper = new ScoreKeeper();
		tester.getHighScores(scoreKeeper);
		assertEquals(scoreKeeper.getTopPlayers().get(3).getHits(), 54);
	}

}
