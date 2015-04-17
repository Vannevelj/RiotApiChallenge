package com.guesstheurf.guesstheurf.models;

/**
 * Created by jeroen on 17/04/2015.
 */
public class LoginInfo {
    private String username;
    private String password;

    public LoginInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
