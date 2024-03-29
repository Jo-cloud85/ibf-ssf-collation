package sg.edu.nus.iss.day16workshop;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import sg.edu.nus.iss.day16workshop.model.Game;
import sg.edu.nus.iss.day16workshop.service.GameService;

@SpringBootApplication
public class Day16workshopApplication implements CommandLineRunner {

	@Autowired
	GameService gameService;

	public static void main(String[] args) {
		SpringApplication.run(Day16workshopApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// Creating and adding the game manually here
		// You do not need to do that if you create an endpoint that talks to the html to add new game

		// Game game = new Game("Tampines", "Rovers", "CCK", new Date());
		// gameService.createGame(game);
		
		// Just for printing out in the console
		Map<String, Game> games = gameService.getGameList();
		for (Map.Entry<String, Game> entry : games.entrySet()) {
			System.out.println(entry.getValue());
		}
	}

}
