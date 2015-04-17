package com.guesstheurf.guesstheurf.models.riot;


import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Participant implements Parcelable {
    @JsonProperty("TeamId")
    private int teamId;

    @JsonProperty("Spell1Id")
    private int spell1Id;

    @JsonProperty("Spell2Id")
    private int spell2Id;

    @JsonProperty("ChampionId")
    private int championId;

    public int getTeamId() {
        return teamId;
    }

    public int getSpell1Id() {
        return spell1Id;
    }

    public int getSpell2Id() {
        return spell2Id;
    }

    public int getChampionId() {
        return championId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(teamId);
        dest.writeInt(spell1Id);
        dest.writeInt(spell2Id);
        dest.writeInt(championId);
    }
}
