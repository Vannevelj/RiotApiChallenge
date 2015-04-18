package com.guesstheurf.guesstheurf.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessToken {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private int expiresIn;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("as:client_id")
    private String clientId;

    @JsonProperty("username")
    private String username;

    @JsonProperty(".issued")
    private String issuedAt;

    @JsonProperty(".expires")
    private String expiresAt;

    public AccessToken() {
    }

    public AccessToken(String accessToken, String tokenType, int expiresIn, String refreshToken, String clientId, String username, String issuedAt, String expiresAt) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.clientId = clientId;
        this.username = username;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getClientId() {
        return clientId;
    }

    public String getUsername() {
        return username;
    }

    public String getIssuedAt() {
        return issuedAt;
    }

    public String getExpiresAt() {
        return expiresAt;
    }
}
