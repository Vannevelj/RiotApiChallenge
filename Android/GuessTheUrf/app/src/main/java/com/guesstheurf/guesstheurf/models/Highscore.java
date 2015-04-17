package com.guesstheurf.guesstheurf.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Highscore {
    @JsonProperty("Username")
    private String username;

    @JsonProperty("Score")
    private int score;

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public Highscore() {

    }

    public Highscore(String username, int score) {

        this.username = username;
        this.score = score;
    }
}
