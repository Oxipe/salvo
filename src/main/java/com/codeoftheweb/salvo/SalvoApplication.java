package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SalvoApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepo,
									  GameRepository gameRepo,
									  GamePlayerRepository gamePlayerRepo,
									  ShipRepository shipRepo,
									  SalvoRepository salvoRepo,
									  ScoreRepository scoreRepo) {
		return args -> {
			//Variables
			String carrier = "Carrier";
			String battleShip = "Battle Ship";
			String submarine = "Submarine";
			String destroyer = "Destroyer";
			String patrolBoat = "Patrol Boat";

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
			GamePlayer gamePlayer1 = new GamePlayer(game1, player1);
			GamePlayer gamePlayer2 = new GamePlayer(game1, player2);
			GamePlayer gamePlayer3 = new GamePlayer(game2, player1);
			GamePlayer gamePlayer4 = new GamePlayer(game2, player2);
			GamePlayer gamePlayer5 = new GamePlayer(game3, player2);
			GamePlayer gamePlayer6 = new GamePlayer(game3, player4);
			GamePlayer gamePlayer7 = new GamePlayer(game4, player2);
			GamePlayer gamePlayer8 = new GamePlayer(game4, player1);
			GamePlayer gamePlayer9 = new GamePlayer(game5, player4);
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
			Ship ship1 = new Ship(destroyer, gamePlayer1, location1);
			Ship ship2 = new Ship(submarine, gamePlayer1, location2);
			Ship ship3 = new Ship(patrolBoat, gamePlayer1, location3);
			Ship ship4 = new Ship(destroyer, gamePlayer1, location4);
			Ship ship5 = new Ship(patrolBoat, gamePlayer1, location5);

			Ship ship6 = new Ship(destroyer, gamePlayer2, location6);
			Ship ship7 = new Ship(patrolBoat, gamePlayer2, location7);
			Ship ship8 = new Ship(submarine, gamePlayer2, location8);
			Ship ship9 = new Ship(patrolBoat, gamePlayer2, location9);

			Ship ship10 = new Ship(destroyer, gamePlayer3, location10);
			Ship ship11 = new Ship(patrolBoat, gamePlayer3, location11);
			Ship ship12 = new Ship(submarine, gamePlayer3, location12);
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

			List<String> salvoLocation1 = Arrays.asList("B5", "C5", "F1");
			List<String> salvoLocation2 = Arrays.asList("B4", "A1", "B6");
			List<String> salvoLocation3 = Arrays.asList("F2", "D5");
			List<String> salvoLocation4 = Arrays.asList("E1", "H3", "A2");
			List<String> salvoLocation5 = Arrays.asList("A2", "A4", "G6");
			List<String> salvoLocation6 = Arrays.asList("B5", "D5", "C7");
			List<String> salvoLocation7 = Arrays.asList("A3", "H6");
			List<String> salvoLocation8 = Arrays.asList("C5", "C6");
			List<String> salvoLocation9 = Arrays.asList("G6", "H6", "A4");
			List<String> salvoLocation10 = Arrays.asList("H1", "H2", "H3");
			List<String> salvoLocation11 = Arrays.asList("A2", "A3", "D8");
			List<String> salvoLocation12 = Arrays.asList("E1", "F2", "G3");
			List<String> salvoLocation13 = Arrays.asList("A3", "A4", "F7");
			List<String> salvoLocation14 = Arrays.asList("B5", "C6", "H1");
			List<String> salvoLocation15 = Arrays.asList("A2", "G6", "H6");
			List<String> salvoLocation16 = Arrays.asList("C5", "C7", "D5");
			List<String> salvoLocation17 = Arrays.asList("A1", "A2", "A3");
			List<String> salvoLocation18 = Arrays.asList("B5", "B6", "C7");
			List<String> salvoLocation19 = Arrays.asList("G6", "G7", "G8");
			List<String> salvoLocation20 = Arrays.asList("C6", "D6", "E6");
			List<String> salvoLocation21 = Arrays.asList("H1", "h8");

			Salvo salvo1 = new Salvo(1, gamePlayer1, player1.getId(), salvoLocation1);
			Salvo salvo2 = new Salvo(1, gamePlayer1, player2.getId(), salvoLocation2);
			Salvo salvo3 = new Salvo(2, gamePlayer1, player1.getId(), salvoLocation3);
			Salvo salvo4 = new Salvo(2, gamePlayer1, player2.getId(), salvoLocation4);

			Salvo salvo5 = new Salvo(1, gamePlayer2, player1.getId(), salvoLocation5);
			Salvo salvo6 = new Salvo(1, gamePlayer2, player2.getId(), salvoLocation6);
			Salvo salvo7 = new Salvo(2, gamePlayer2, player1.getId(), salvoLocation7);
			Salvo salvo8 = new Salvo(2, gamePlayer2, player2.getId(), salvoLocation8);

			Salvo salvo9 = new Salvo(1, gamePlayer3, player2.getId(), salvoLocation9);
			Salvo salvo10 = new Salvo(1, gamePlayer3, player4.getId(), salvoLocation10);
			Salvo salvo11 = new Salvo(2, gamePlayer3, player2.getId(), salvoLocation11);
			Salvo salvo12 = new Salvo(2, gamePlayer3, player4.getId(), salvoLocation12);

			Salvo salvo13 = new Salvo(1, gamePlayer4, player2.getId(), salvoLocation13);
			Salvo salvo14 = new Salvo(1, gamePlayer4, player1.getId(), salvoLocation14);
			Salvo salvo15 = new Salvo(2, gamePlayer4, player2.getId(), salvoLocation15);
			Salvo salvo16 = new Salvo(2, gamePlayer4, player1.getId(), salvoLocation16);

			Salvo salvo17 = new Salvo(1, gamePlayer5, player4.getId(), salvoLocation17);
			Salvo salvo18 = new Salvo(1, gamePlayer5, player1.getId(), salvoLocation18);
			Salvo salvo19 = new Salvo(2, gamePlayer5, player4.getId(), salvoLocation19);
			Salvo salvo20 = new Salvo(2, gamePlayer5, player1.getId(), salvoLocation20);
			Salvo salvo21 = new Salvo(3, gamePlayer3, player1.getId(), salvoLocation21);

			salvoRepo.save(salvo1);
			salvoRepo.save(salvo2);
			salvoRepo.save(salvo3);
			salvoRepo.save(salvo4);
			salvoRepo.save(salvo5);
			salvoRepo.save(salvo6);
			salvoRepo.save(salvo7);
			salvoRepo.save(salvo8);
			salvoRepo.save(salvo9);
			salvoRepo.save(salvo10);
			salvoRepo.save(salvo11);
			salvoRepo.save(salvo12);
			salvoRepo.save(salvo13);
			salvoRepo.save(salvo14);
			salvoRepo.save(salvo15);
			salvoRepo.save(salvo16);
			salvoRepo.save(salvo17);
			salvoRepo.save(salvo18);
			salvoRepo.save(salvo19);
			salvoRepo.save(salvo20);
			salvoRepo.save(salvo21);

			Score score1 = new Score(game1, player1, 1.0);
			Score score2 = new Score(game1, player2, 0.0);
			Score score3 = new Score(game2, player1, 0.5);
			Score score4 = new Score(game2, player2, 0.5);
			Score score5 = new Score(game3, player2, 1.0);
			Score score6 = new Score(game3, player4, 0.0);
			Score score7 = new Score(game4, player2, 0.5);
			Score score8 = new Score(game4, player1, 0.5);

			scoreRepo.save(score1);
			scoreRepo.save(score2);
			scoreRepo.save(score3);
			scoreRepo.save(score4);
			scoreRepo.save(score5);
			scoreRepo.save(score6);
			scoreRepo.save(score7);
			scoreRepo.save(score8);
		};
	}
}
@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

	@Autowired
	PlayerRepository playerRepository;

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(inputName-> {
			Player player = playerRepository.findByUserName(inputName);
			if (player != null) {
				return new User(player.getUserName(), player.getUserPassWord(),
						AuthorityUtils.createAuthorityList("USER"));
			} else {
				throw new UsernameNotFoundException("Unknown user: " + inputName);
			}
		});
	}
}

@Configuration
@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
//					.antMatchers("/web/home.html", "/api/**").permitAll()
//					.antMatchers("/admin/**").hasAuthority("ADMIN")
//					.antMatchers("/**").hasAuthority("USER")
				.antMatchers("/**").permitAll()
				.and()
				.formLogin();

		http.formLogin()
				.usernameParameter("userName")
				.passwordParameter("userPassWord")
				.loginPage("/api/login");

		http.logout().logoutUrl("/api/logout");

		// turn off checking for CSRF tokens
		http.csrf().disable();

		// if user is not authenticated, just send an authentication failure response
		http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if login is successful, just clear the flags asking for authentication
		http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

		// if login fails, just send an authentication failure response
		http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if logout is successful, just send a success response
		http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
	}

	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
	}
}

