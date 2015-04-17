package com.guesstheurf.guesstheurf.models.riot;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Game implements Parcelable {
    @JsonProperty("MatchId")
    private String matchId;

    @JsonProperty("Participants")
    private List<Participant> participants;

    @JsonProperty("Teams")
    private List<Team> teams;

    public String getMatchId() {
        return matchId;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public List<Team> getTeams() {
        return teams;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(matchId);
        dest.writeList(participants);
        dest.writeList(teams);
    }
}
