package com.mabbureau.panoptes;

public class LoginResponse {
    private boolean success; // Indicates if the login was successful
    private String message;
    private String token;
    private String userName; // Add the userName field
    private String userID; // Add the userID field

    // Getters
    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getToken() {
        return token;
    }

    public String getUserName() { // Add this getter for userName
        return userName;
    }

    public String getUserId() {
        return userID;
    }
}
