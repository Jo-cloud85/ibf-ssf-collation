package sg.edu.nus.iss.day16workshop.model;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Game implements Serializable {

    // Put in necessary validation
    private String gameId;

    @NotEmpty(message="Cannot be empty")
    @Size(min=3, max=32, message="Name must be between 3 to 32 characters")
    private String homeTeam;

    @NotEmpty(message="Cannot be empty")
    @Size(min=3, max=32, message="Name must be between 3 to 32 characters")
    private String opponentTeam;

    @NotEmpty(message="Cannot be empty")
    @Size(min=3, max=32, message="Name must be between 3 to 32 characters")
    private String venue;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @NotNull(message="Must not be null") // cannot use @NotEmpty
    @FutureOrPresent(message="Must be a present or future date")
    private LocalDate gameDate;

    public Game(){
    }

    public Game(String homeTeam, String opponentTeam, String venue, LocalDate gameDate) {
        // this.gameId = UUID.randomUUID().toString();
        this.homeTeam = homeTeam;
        this.opponentTeam = opponentTeam;
        this.venue = venue;
        this.gameDate = gameDate;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getOpponentTeam() {
        return opponentTeam;
    }

    public void setOpponentTeam(String opponentTeam) {
        this.opponentTeam = opponentTeam;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public LocalDate getGameDate() {
        return gameDate;
    }

    public void setGameDate(LocalDate gameDate) {
        this.gameDate = gameDate;
    }

    @Override
    public String toString() {
        return "Game [gameId=" + gameId + ", homeTeam=" + homeTeam + ", opponentTeam=" + opponentTeam + ", venue="
                + venue + ", gameDate=" + gameDate + "]";
    }

    
}
