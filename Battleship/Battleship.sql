/****************************************************************
 * Battleship Database											*
 * Script: Battleship.sql										*
 * Description: Creates the Battleship Database and associated	*
 * 		functions.												*
 * DB Server: PostgreSql										*
 * Author: Zack Reagin											*
 ****************************************************************/

/****************************************************************
 * Create Tables
 ****************************************************************/

alter table battleship_game_results
	drop constraint if exists fk_battleship_game_results_player_id;

--Stores player data (user name, password, totals from games played)
drop table if exists battleship_player;
create table battleship_player (
	battleship_player_id bigserial not null,
	battleship_player_username varchar(20) unique not null,
	battleship_player_password varchar(20) not null,
	battleship_player_total_games int default 0,
	battleship_player_total_wins int default 0,
	battleship_player_total_hits int default 0,
	battleship_player_total_remaining_turns int default 0,
	constraint pk_battleship_player primary key (battleship_player_id )
);

--Stores results of each game played
drop table if exists battleship_game_results;
create table battleship_game_results (
	battleship_game_id bigserial not null,
	battleship_player_id bigint not null,
	battleship_win bool not null,
	battleship_remaining_turns int not null,
	battleship_hits int not null,
	constraint pk_battleship_game_results primary key (battleship_game_id )
);

/****************************************************************
 * Create Foreign Keys
 ****************************************************************/
alter table battleship_game_results add constraint fk_battleship_game_results_player_id
	foreign key (battleship_player_id) references battleship_player (battleship_player_id) on delete cascade on update cascade;

create index ifk_battleship_game_results_player_id on battleship_game_results (battleship_player_id);

/****************************************************************
 * Create functions
 ****************************************************************/
--Called when a new row is inserted into battleship_game_results
--by new_record trigger to update data in battleship_player table
create or replace function update_player_totals()
returns trigger as $$
declare
	player_id bigint := new.battleship_player_id;
	new_win int := (case when new.battleship_win then 1 else 0 end);
	new_turns int := new.battleship_remaining_turns;	
	new_hits int := new.battleship_hits;
begin
	update battleship_player
		set battleship_player_total_games = battleship_player_total_games + 1,
			battleship_player_total_wins = battleship_player_total_wins + new_win,
			battleship_player_total_remaining_turns = battleship_player_total_remaining_turns + new_turns,
			battleship_player_total_hits = battleship_player_total_hits + new_hits
		where battleship_player_id = player_id;
	return new;
end; $$ language plpgsql;

--drops trigger if previously created
drop trigger if exists new_record on battleship_game_results;

--creates trigger that is activated by inserting a new row into battleship_game_results
create trigger new_record
after insert on battleship_game_results
for each row
execute procedure update_player_totals();

--returns a table listing the ten players with the most wins
create or replace function get_high_scores()
returns table(user_name varchar, total_games int, total_wins int, total_hits int, total_remaining_turns int) as $$
begin
	return query
	select battleship_player_username,
		battleship_player_total_games,
		battleship_player_total_wins,
		battleship_player_total_hits,
		battleship_player_total_remaining_turns
	from battleship_player
	order by battleship_player_total_wins desc,
		battleship_player_total_remaining_turns desc,
		battleship_player_total_hits desc
	limit 10;
end; $$ language plpgsql;

/*Fake data for testing purposes*/

insert into battleship_player(battleship_player_username, battleship_player_password )
	values ('Phantomcat261', '}z@x.L3u~N%b3kw'), ('Amigod576', 'nH#=YCv4REQ2;8\m#^'), ('Phantomato', 'p.{2TT]@x4J)"c2>'), ('PlaySpike402', '+QJ5v{"}.ac?Bc*/'),
			('FrizzyBronco', 'n5GtS2T9,6)8yz;:'), ('Sassassin', 'vt_+VFz@DV@q7"G?'), ('Lobsteroid19', '7VV4y?vL(<643?6#'), ('FrozenPaladin422', '5f)hb(+we.uVT&86'),
			('Baconqueror', 'LmH-MgAmamr8}6nu'), ('Essaint229', 'TD)e;7~6xk{N5~jF'), ('StormT-Rex', '`v?*qfwUk4Rtbqe}'), ('Barracupid304', 'R8.JU^Q{q&7XCn!6');

insert into battleship_game_results(battleship_player_id, battleship_win, battleship_remaining_turns, battleship_hits)
	values (1, true, 12, 20), (1, false, 0, 19), (1, false, 0, 16), (1, true, 4, 20), (2, true, 13, 20), (2, true, 6, 20),
			(2, false, 0, 14), (3, false, 0, 18), (3, false, 0, 19), (3, true, 2, 20), (3, false, 0, 16), (3, true, 6, 20),
			(3, true, 8, 20), (4, true, 5, 20), (4, true, 8, 20), (4, true, 12, 20), (4, true, 19, 20), (4, true, 4, 20),
			(5, true, 0, 20), (5, false, 0, 11), (5, true, 2, 20), (6, true, 3, 20), (7, false, 0, 17),  (7, false, 0, 16),
			(8, true, 13, 20), (8, false, 0, 19), (8, true, 11, 20), (8, false, 0, 8), (9, true, 2, 20), (9, true, 3, 20),
			(10, false, 0, 16), (10, false, 0, 9), (10, false, 0, 14), (10, true, 3, 20), (10, true, 4, 20), (11, true, 2, 20),
			(11, true, 4, 20), (12, true, 2, 20), (12, true, 6, 20), (12, false, 0, 12);
			
