package com.guesstheurf.guesstheurf.models;


public enum Session {
    INSTANCE;

    private AccessToken accessToken;

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }
}
