package com.example.parking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank; //not blank doesn't allow " " while not empty does that's why

public class LoginRequest {
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
