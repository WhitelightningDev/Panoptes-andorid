package com.mabbureau.panoptes;

public class SignInRequest {
    private String name;
    private String surname;
    private String contact;
    private String email;
    private String password;

    public SignInRequest(String name, String surname, String contact, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.contact = contact;
        this.email = email;
        this.password = password;  // Fixed assignment here
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getContact() {
        return contact;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
