package sg.edu.nus.iss.day16workshop.repository;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.day16workshop.model.Game;
import sg.edu.nus.iss.day16workshop.utils.Util;

@Repository
public class GameRepo {

    @Autowired
    @Qualifier(Util.REDIS_TWO)
    RedisTemplate<String, Game> template;
    
    HashOperations<String, String, Game> hashOps;

    // Create (in Redis Map)
    @SuppressWarnings("null")
    public void saveGame(Game game) {
        // Object key is the table name
        hashOps = template.opsForHash(); 
        hashOps.putIfAbsent(Util.KEY_GAME, game.getGameId(), game);
    }

    // Read (all) (from Redis Map)
    public Map<String, Game> getAllGames() {
        hashOps = template.opsForHash(); 
        return hashOps.entries(Util.KEY_GAME);
    }

    // Read a specific record (from Redis Map)
    @SuppressWarnings("null")
    public Game getGameById(String gameId) {
        hashOps = template.opsForHash(); 
        return hashOps.get(Util.KEY_GAME, gameId);
    }

    // Update a specific record - make sure gameId no change
    @SuppressWarnings("null")
    public void updateGame(Game game) {
        hashOps = template.opsForHash(); 
        //hashOps.delete(Util.KEY_GAME, game.getGameId());
        hashOps.put(Util.KEY_GAME, game.getGameId(), game);
    }

    // Delete operations of a record (in Redis Map)
    public void deleteGame(String gameId) {
        hashOps = template.opsForHash(); 
        hashOps.delete(Util.KEY_GAME, gameId);
    }
}
