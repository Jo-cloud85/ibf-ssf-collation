package sg.edu.nus.iss.day16workshop.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.day16workshop.model.Game;
import sg.edu.nus.iss.day16workshop.repository.GameRepo;

/* Because this is a simple application, the GameService might look very similar to GameRepo
 * but in the real world, this file will be calling different repositories to do certain things */

@Service
public class GameService {
    
    @Autowired
    GameRepo gameRepo;

    public void createGame(Game game) {
        gameRepo.saveGame(game);
    }

    public Map<String, Game> getGameList() {
        return gameRepo.getAllGames();
    }

    public Game getGameById(String gameId) {
        return gameRepo.getGameById(gameId);
    }

    public void updateGame(Game game) {
        gameRepo.updateGame(game);
    }

    public void deleteGame(String gameId) {
        gameRepo.deleteGame(gameId);
    }
}
