package com.codeoftheweb.salvo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);

		Player player = new Player("Oxipe", "thunder107th@gmail.com", "1609");

		//System.out.println(player);
	}
}
