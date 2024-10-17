package com.mabbureau.panoptes;

public class LoginRequest {
    private String email;
    private String password;

    // No-argument constructor (required for some serialization libraries)
    public LoginRequest() {
    }

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
