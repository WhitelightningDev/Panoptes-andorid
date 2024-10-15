package com.mabbureau.panoptes;

public class LoginResponse {
    private boolean success; // Indicates if the login was successful
    private String message;
    private String token;

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getToken() {
        return token;
    }
}
