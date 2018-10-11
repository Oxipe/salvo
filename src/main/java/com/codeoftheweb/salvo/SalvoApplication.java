package com.codeoftheweb.salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData (PlayerRepository playerRepo,
									   GameRepository gameRepo,
									   GamePlayerRepository gamePlayerRepo,
									   ShipRepository shipRepo) {
		return args -> {
			//Variables
			String carrier 		= "Carrier";
			String battleShip 	= "Battle Ship";
			String submarine 	= "Submarine";
			String destroyer 	= "Destroyer";
			String patrolBoat 	= "Patrol Boat";

			//Creating players and saving into repository
			Player player1 = new Player("Jack", "j.bauer@ctu.gov", "24");
			Player player2 = new Player("Chloe", "c.obrian@ctu.gov", "42");
			Player player3 = new Player("Kim", "kim_bauer#gmail.com", "kb");
			Player player4 = new Player("Tony", "t.almeida@ctu.gov", "mole");

			playerRepo.save(player1);
			playerRepo.save(player2);
			playerRepo.save(player3);
			playerRepo.save(player4);

			//Creating games and saving into repository
			Game game1 = new Game("Game 1");
			Game game2 = new Game("Game 2");
			Game game3 = new Game("Game 3");
			Game game4 = new Game("Game 4");
			Game game5 = new Game("Game 5");
			Game game6 = new Game("Game 6");
			Game game7 = new Game("Game 7");
			Game game8 = new Game("Game 8");

			//adding time to creation date
			game2.addTime(game2.getDate(), 3600);
			game3.addTime(game3.getDate(), 7200);
			game4.addTime(game4.getDate(), 10800);
			game5.addTime(game5.getDate(), 14400);
			game6.addTime(game6.getDate(), 18000);
			game7.addTime(game7.getDate(), 21600);
			game8.addTime(game8.getDate(), 25200);

			gameRepo.save(game1);
			gameRepo.save(game2);
			gameRepo.save(game3);
			gameRepo.save(game4);
			gameRepo.save(game5);
			gameRepo.save(game6);
			gameRepo.save(game7);
			gameRepo.save(game8);

			//Creating gameplayers and saving into repository
			GamePlayer gamePlayer1  = new GamePlayer(game1, player1);
			GamePlayer gamePlayer2  = new GamePlayer(game1, player2);
			GamePlayer gamePlayer3  = new GamePlayer(game2, player1);
			GamePlayer gamePlayer4  = new GamePlayer(game2, player2);
			GamePlayer gamePlayer5  = new GamePlayer(game3, player2);
			GamePlayer gamePlayer6  = new GamePlayer(game3, player4);
			GamePlayer gamePlayer7  = new GamePlayer(game4, player2);
			GamePlayer gamePlayer8  = new GamePlayer(game4, player1);
			GamePlayer gamePlayer9  = new GamePlayer(game5, player4);
			GamePlayer gamePlayer10 = new GamePlayer(game5, player1);
			GamePlayer gamePlayer11 = new GamePlayer(game6, player3);
			GamePlayer gamePlayer12 = new GamePlayer(game7, player4);
			GamePlayer gamePlayer13 = new GamePlayer(game8, player3);
			GamePlayer gamePlayer14 = new GamePlayer(game8, player4);

			gamePlayerRepo.save(gamePlayer1);
			gamePlayerRepo.save(gamePlayer2);
			gamePlayerRepo.save(gamePlayer3);
			gamePlayerRepo.save(gamePlayer4);
			gamePlayerRepo.save(gamePlayer5);
			gamePlayerRepo.save(gamePlayer6);
			gamePlayerRepo.save(gamePlayer7);
			gamePlayerRepo.save(gamePlayer8);
			gamePlayerRepo.save(gamePlayer9);
			gamePlayerRepo.save(gamePlayer10);
			gamePlayerRepo.save(gamePlayer11);
			gamePlayerRepo.save(gamePlayer12);
			gamePlayerRepo.save(gamePlayer13);
			gamePlayerRepo.save(gamePlayer14);

			//Setting ship locations
			List<String> location1 = Arrays.asList("H2", "H3", "H4");
			List<String> location2 = Arrays.asList("E1", "F1", "G1");
			List<String> location3 = Arrays.asList("B4", "B5");
			List<String> location4 = Arrays.asList("B5", "C5", "D5");
			List<String> location5 = Arrays.asList("F1", "F2");

			List<String> location6 = Arrays.asList("B5", "C5", "D5");
			List<String> location7 = Arrays.asList("C6", "C7");
			List<String> location8 = Arrays.asList("A2", "A3", "A4");
			List<String> location9 = Arrays.asList("G6", "H6");

			List<String> location10 = Arrays.asList("B5", "C5", "D5");
			List<String> location11 = Arrays.asList("C6", "C7");
			List<String> location12 = Arrays.asList("A2", "A3", "A4");
			List<String> location13 = Arrays.asList("G6", "H6");

			List<String> location14 = Arrays.asList("B5", "C5", "D5");
			List<String> location15 = Arrays.asList("C6", "C7");
			List<String> location16 = Arrays.asList("A2", "A3", "A4");
			List<String> location17 = Arrays.asList("G6", "H6");

			List<String> location18 = Arrays.asList("B5", "C5", "D5");
			List<String> location19 = Arrays.asList("C6", "C7");
			List<String> location20 = Arrays.asList("A2", "A3", "A4");
			List<String> location21 = Arrays.asList("G6", "H6");

			List<String> location22 = Arrays.asList("B5", "C5", "D5");
			List<String> location23 = Arrays.asList("C6", "C7");

			List<String> location24 = Arrays.asList("B5", "C5", "D5");
			List<String> location25 = Arrays.asList("C6", "C7");
			List<String> location26 = Arrays.asList("A2", "A3", "A4");
			List<String> location27 = Arrays.asList("C6", "C7");

			//Creating ships
			Ship ship1 = new Ship(destroyer, gamePlayer1, location1 );
			Ship ship2 = new Ship(submarine, gamePlayer1, location2 );
			Ship ship3 = new Ship(patrolBoat, gamePlayer1, location3);
			Ship ship4 = new Ship(destroyer, gamePlayer1, location4);
			Ship ship5 = new Ship(patrolBoat, gamePlayer1, location5);

			Ship ship6 = new Ship(destroyer, gamePlayer2, location6);
			Ship ship7 = new Ship(patrolBoat, gamePlayer2, location7);
			Ship ship8 = new Ship(submarine, gamePlayer2, location8);
			Ship ship9 = new Ship(patrolBoat, gamePlayer2, location9);

			Ship ship10 = new Ship(destroyer, gamePlayer3, location10);
			Ship ship11 = new Ship(patrolBoat, gamePlayer3, location11);
			Ship ship12 = new Ship (submarine, gamePlayer3, location12);
			Ship ship13 = new Ship(patrolBoat, gamePlayer3, location13);

			Ship ship14 = new Ship(destroyer, gamePlayer4, location14);
			Ship ship15 = new Ship(patrolBoat, gamePlayer4, location15);
			Ship ship16 = new Ship(submarine, gamePlayer4, location16);
			Ship ship17 = new Ship(patrolBoat, gamePlayer4, location17);

			Ship ship18 = new Ship(destroyer, gamePlayer5, location18);
			Ship ship19 = new Ship(patrolBoat, gamePlayer5, location19);
			Ship ship20 = new Ship(submarine, gamePlayer5, location20);
			Ship ship21 = new Ship(patrolBoat, gamePlayer5, location21);

			Ship ship22 = new Ship(destroyer, gamePlayer6, location22);
			Ship ship23 = new Ship(patrolBoat, gamePlayer6, location23);

			Ship ship24 = new Ship(destroyer, gamePlayer8, location24);
			Ship ship25 = new Ship(patrolBoat, gamePlayer8, location25);
			Ship ship26 = new Ship(submarine, gamePlayer8, location26);
			Ship ship27 = new Ship(patrolBoat, gamePlayer8, location27);

			shipRepo.save(ship1);
			shipRepo.save(ship2);
			shipRepo.save(ship3);
			shipRepo.save(ship4);
			shipRepo.save(ship5);
			shipRepo.save(ship6);
			shipRepo.save(ship7);
			shipRepo.save(ship8);
			shipRepo.save(ship9);
			shipRepo.save(ship10);
			shipRepo.save(ship11);
			shipRepo.save(ship12);
			shipRepo.save(ship13);
			shipRepo.save(ship14);
			shipRepo.save(ship15);
			shipRepo.save(ship16);
			shipRepo.save(ship17);
			shipRepo.save(ship18);
			shipRepo.save(ship19);
			shipRepo.save(ship20);
			shipRepo.save(ship21);
			shipRepo.save(ship22);
			shipRepo.save(ship23);
			shipRepo.save(ship24);
			shipRepo.save(ship25);
			shipRepo.save(ship26);
			shipRepo.save(ship27);

		};
	}
}
