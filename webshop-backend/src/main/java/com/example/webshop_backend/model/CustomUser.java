package com.example.webshop_backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomUser {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(nullable = false)
    private String password;
    private String email;

    @JsonBackReference
    @ManyToOne
    private Role role_id; // voor de adminpanel (admin, user)

    public CustomUser(String username, String email, String password, Role role_id) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role_id = role_id;
    }

    public CustomUser(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
