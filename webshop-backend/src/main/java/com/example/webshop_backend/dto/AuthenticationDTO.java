package com.example.webshop_backend.dto;

public class AuthenticationDTO {
    public String email;
    public String password;
    public String role;

    public AuthenticationDTO(String email, String password,  String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
