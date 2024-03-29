package sg.edu.nus.iss.day16workshop.restcontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.day16workshop.model.Game;
import sg.edu.nus.iss.day16workshop.service.GameService;

@RestController
@RequestMapping(path="/api/games")
public class GameRestController {
    
    @Autowired
    GameService gameService;

    // Create (PostMapping)
    @PostMapping
    public ResponseEntity<String> postMethodNameGame(@RequestBody Game requestPayload){
        // JsonReader jReader = Json.createReader(new StringReader(requestPayload));
        // JsonObject jObject = jReader.readObject();

        // Game game = new Game();
        // game.setGameId(jObject.getString("gameId"));
        // game.setHomeTeam(jObject.getString("homeTeam"));
        // game.setOpponentTeam(jObject.getString("opponentTeam"));
        // game.setVenue(jObject.getString("venue"));
        // String gameDate = jObject.getString("gameDate");
        // SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        // Date gDate = sdf.parse(gameDate);
        // game.setGameDate(gDate);

        // System.out.println("Post game:" + game);
        gameService.createGame(requestPayload);

        return ResponseEntity.ok("Game created successfully");
    }

    // Read - Get All (GetMapping)
    @GetMapping
    public ResponseEntity<List<Game>> getAllGames() {
        Map<String, Game> games = gameService.getGameList();
        List<Game> gameList = new ArrayList<>();
        for (Map.Entry<String, Game> entry : games.entrySet()) {
            gameList.add(entry.getValue());
        }
        return ResponseEntity.ok().body(gameList);
    }

    // Read - Get By Id (GetMapping)
    @GetMapping("/{game-id}")
    public ResponseEntity<Game> getGameById (@PathVariable("game-id") String gameId) {
        Game game = gameService.getGameById(gameId);
        return new ResponseEntity<Game>(game, HttpStatus.OK);
    }

    // Update (PutMapping)
    @PutMapping
    public ResponseEntity<Boolean> updateGame(@RequestBody Game payload) {
        gameService.updateGame(payload);
        return ResponseEntity.ok(true);
    }

    // Delete (DeleteMapping)
    @DeleteMapping("/{game-id}")
    public ResponseEntity<Boolean> deleteGame(@PathVariable("game-id") String gameId) {
        gameService.deleteGame(gameId);
        return ResponseEntity.ok(true);
    }
}
