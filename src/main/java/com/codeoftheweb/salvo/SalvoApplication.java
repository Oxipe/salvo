package com.codeoftheweb.salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);

	}

	@Bean
	public CommandLineRunner initData (PlayerRepository playerRepo,
									   GameRepository gameRepo,
									   GamePlayerRepository gamePlayerRepo) {
		return args -> {
			//Creating players and saving into repository
			Player player1 = new Player("J.Bauer", "j.bauer@ctu.gov", "");
			Player player2 = new Player("C.Obrian", "c.obrian@ctu.gov", "");
			Player player3 = new Player("T.Almaida", "t.almeida@ctu.gov", "");
			Player player4 = new Player("D.Palmer", "d.palmer@whitehouse.gov", "");

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

			//adding time to creation date
			game2.addTime(game2.getDate(), 3600);
			game3.addTime(game3.getDate(), 7200);
			game4.addTime(game4.getDate(), 10800);
			game5.addTime(game5.getDate(), 14400);
			game6.addTime(game6.getDate(), 18000);

			gameRepo.save(game1);
			gameRepo.save(game2);
			gameRepo.save(game3);
			gameRepo.save(game4);
			gameRepo.save(game5);
			gameRepo.save(game6);

			//Creating gameplayers and saving into repository
			GamePlayer gamePlayer1 = new GamePlayer(game1, player1, player4);
			GamePlayer gamePlayer2 = new GamePlayer(game2, player2, player3);
			GamePlayer gamePlayer3 = new GamePlayer(game3, player3, player1);
			GamePlayer gamePlayer4 = new GamePlayer(game4, player4, player2);

			gamePlayerRepo.save(gamePlayer1);
			gamePlayerRepo.save(gamePlayer2);
			gamePlayerRepo.save(gamePlayer3);
			gamePlayerRepo.save(gamePlayer4);

//			SalvoController salvoController = new SalvoController();
//			System.out.println(salvoController.getAllGames());
		};
	}
}
