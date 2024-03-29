package sg.edu.nus.iss.day16workshop.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import sg.edu.nus.iss.day16workshop.model.Game;
import sg.edu.nus.iss.day16workshop.service.GameService;

@Controller
@RequestMapping(path="/games")
public class GameController {
    
    @Autowired
    GameService gameService;

    // Get the form to add new game
    @GetMapping
    public String addGame(Model model) {
        Game game = new Game();
        model.addAttribute("newGame", game);
        return "gameadd";
    }

    // Post the form with the new game data
    @PostMapping("/add")
    public String postGame(
        @ModelAttribute("newGame") @Valid Game game, 
        BindingResult result) {
        
        game.setGameId(UUID.randomUUID().toString().substring(0,8));

        if (result.hasErrors()) {
            return "gameadd";
        }
        gameService.createGame(game);

        return "success";
    }

    @GetMapping("/list")
    public String gameListing(Model model) {
        Map<String, Game> games = gameService.getGameList();
        List<Game> gameList = new ArrayList<>();
        for (Map.Entry<String, Game> entry : games.entrySet()) {
            gameList.add(entry.getValue());
        }
        model.addAttribute("games", gameList);
        return "gamelist";
    }

    /* The validation for this POST method does not work. Why?
     * Spring doesn't perform validation because MultiValueMap is just a generic container for HTTP request 
     * parameters and doesn't have any built-in validation support like a model class does. On the other 
     * hand, when you use @Valid with @ModelAttribute, Spring MVC performs validation on the Game object 
     * before binding the request parameters to it. It checks for any validation constraints defined in the 
     * Game class using annotations such as @NotNull, @Size, @Pattern, etc. */

    // Post the form with the new game data
    // @PostMapping("/add")
    // public String postGame(
    //     @RequestBody @Valid MultiValueMap<String, String> payload,
    //     BindingResult result) throws ParseException {
        
    //     if (result.hasErrors()) {
    //         return "gameadd";
    //     }
        
    //     String homeTeam = payload.getFirst("homeTeam");
    //     String opponentTeam = payload.getFirst("opponentTeam");
    //     String venue = payload.getFirst("venue");
    //     String gameDateStr = payload.getFirst("gameDate");
    //     SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    //     Date gameDate = sdf.parse(gameDateStr);
    //     Game game = new Game(homeTeam, opponentTeam, venue, gameDate);
    //     gameService.createGame(game);
    //     return "success";
    // }
}
