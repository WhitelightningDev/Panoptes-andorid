package com.mabbureau.panoptes;

public class SignInRequest {
    private String name;
    private String surname;
    private String username;
    private String contact;
    private String email;
    private String password;

    public SignInRequest(String name, String surname, String username, String contact, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.contact = contact;
        this.email = email;
        this.password = password;
    }
}
