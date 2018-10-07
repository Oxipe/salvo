package com.codeoftheweb.salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SalvoApplication {

	private	Game game1 = new Game("Game 1");
	private Game game2 = new Game("Game 2");
	private Game game3 = new Game("Game 3");

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);

	}

	@Bean
	public CommandLineRunner initPlayerData (PlayerRepository repository) {
		return args -> {
			repository.save(new Player("Oxipe", "thunder107th@gmail.com", "1609"));
		};
	}

	@Bean
	public CommandLineRunner initGameData (GameRepository repository) {
		return args -> {
			game2.addTime(game2.getDate(), 3600);
			game3.addTime(game3.getDate(), 7200);

			repository.save(game1);
			repository.save(game2);
			repository.save(game3);
		};
	}
}
