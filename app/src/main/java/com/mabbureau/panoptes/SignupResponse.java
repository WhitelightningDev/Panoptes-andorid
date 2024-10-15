package com.mabbureau.panoptes;

public class SignupResponse {
    private String message; // Message from the server
    private boolean success; // Indicates if the signup was successful
    private String userId; // Optionally include user ID if returned by the server

    // Constructor
    public SignupResponse(String message, boolean success, String userId) {
        this.message = message;
        this.success = success;
        this.userId = userId;
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
}
