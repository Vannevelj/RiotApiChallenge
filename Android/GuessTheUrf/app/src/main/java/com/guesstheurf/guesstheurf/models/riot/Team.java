package com.guesstheurf.guesstheurf.models.riot;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Team implements Parcelable {
    @JsonProperty("TeamId")
    private int teamId;

    @JsonProperty("Winner")
    private boolean isWinner;

    public int getTeamId() {
        return teamId;
    }

    public boolean isWinner() {
        return isWinner;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(teamId);
        dest.writeValue(isWinner);
    }
}
