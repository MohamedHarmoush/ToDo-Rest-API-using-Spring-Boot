package com.harmoush.security;

import javax.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "Password shouldn't be empty or null.")
    private String username;

    @NotBlank(message = "Username shouldn't be empty or null.")
    private String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
