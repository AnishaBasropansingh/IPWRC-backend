package com.example.webshop_backend.dto;

public class UserInfoResponse {
    public Long id;
    public String username;
    public String email;
    public String role;

    public UserInfoResponse(Long id, String username, String email, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

}
