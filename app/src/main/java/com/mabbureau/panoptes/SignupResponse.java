package com.mabbureau.panoptes;

public class SignupResponse {
    private String message; // Message from the server
    private boolean success; // Indicates if the signup was successful
    private String userId; // Optionally include user ID if returned by the server
    private String token; // Assuming you also want to save a token

    // Constructor
    public SignupResponse(String message, boolean success, String userId, String token) {
        this.message = message;
        this.success = success;
        this.userId = userId;
        this.token = token; // Include token in the constructor
    }

    // Getters
    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getUserId() {
        return userId;
    }

    public String getToken() { // Getter for token
        return token;
    }
}
