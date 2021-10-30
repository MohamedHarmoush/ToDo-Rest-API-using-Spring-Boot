package com.harmoush.security;

import org.springframework.security.core.userdetails.UserDetails;

public class JwtResponse {

    private String token;
    private UserDetails user;

    public JwtResponse(String token, UserDetails user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDetails getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
}
