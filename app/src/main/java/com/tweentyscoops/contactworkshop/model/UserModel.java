package com.tweentyscoops.contactworkshop.model;

public class UserModel {
    private String success;
    private String email;
    private String password;
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getSuccess() {
        return success;
    }
    public UserModel(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
