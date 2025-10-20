package com.example.webshop_backend.dto;

public class UserInfoResponse {
    public Long id;
    public String email;
    public String token;
    public String role;

    public UserInfoResponse(Long id, String email, String token,  String role) {
        this.id = id;
        this.email = email;
        this.token = token;
        this.role = role;
    }
}
