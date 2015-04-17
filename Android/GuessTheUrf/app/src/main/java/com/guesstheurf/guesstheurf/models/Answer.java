package com.guesstheurf.guesstheurf.models;

public class Answer {
    private String matchId;
    private int winningTeamId;

    public Answer(String matchId, int winningTeamId) {
        this.matchId = matchId;
        this.winningTeamId = winningTeamId;
    }

    public String getMatchId() {

        return matchId;
    }

    public int getWinningTeamId() {
        return winningTeamId;
    }
}
