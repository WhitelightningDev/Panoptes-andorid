package com.mabbureau.panoptes;

public class UserCheckResponse {
    private boolean exists;

    public UserCheckResponse(boolean exists) {
        this.exists = exists;
    }

    // Getter
    public boolean isExists() {
        return exists;
    }

    // Setter
    public void setExists(boolean exists) {
        this.exists = exists;
    }
}
