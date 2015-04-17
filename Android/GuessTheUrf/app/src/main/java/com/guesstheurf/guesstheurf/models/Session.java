package com.guesstheurf.guesstheurf.models;


import android.content.Context;
import android.widget.Toast;

import com.guesstheurf.guesstheurf.models.riot.Game;
import com.guesstheurf.guesstheurf.tasks.GetNewGamesTask;

import java.util.ArrayList;
import java.util.List;

public enum Session {
    INSTANCE;

    private AccessToken accessToken;
    private List<Game> games = new ArrayList<>();
    private List<Highscore> highscores = new ArrayList<>();
    private int gamesDone = 0;

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public void setHighscores(List<Highscore> highscores) {
        this.highscores.clear();
        this.highscores.addAll(highscores);
    }

    public List<Highscore> getHighscores() {
        return highscores;
    }

    public Game getNewGame(Context fromContext) {
        if (gamesDone == games.size()) {
            Toast.makeText(fromContext, "Fetching new games!", Toast.LENGTH_LONG).show();
            try {
                GetNewGamesTask task = new GetNewGamesTask(fromContext);
                task.execute();
                List<Game> response = task.get();
                if (response != null) {
                    games.addAll(response);
                }
            } catch (Exception e) {
                Toast.makeText(fromContext, "A problem occurred trying to obtain new games.", Toast.LENGTH_SHORT).show();
            }
        }

        return games.get(gamesDone++);
    }
}
