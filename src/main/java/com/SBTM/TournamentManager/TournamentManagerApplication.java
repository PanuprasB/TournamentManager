package com.SBTM.TournamentManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.SBTM.TournamentManager.Model")

public class TournamentManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TournamentManagerApplication.class, args);
	}

}
