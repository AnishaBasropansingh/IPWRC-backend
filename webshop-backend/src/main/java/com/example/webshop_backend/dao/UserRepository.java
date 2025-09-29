package com.example.webshop_backend.dao;

import com.example.webshop_backend.model.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<CustomUser, Long> {
     CustomUser findByEmail(String email);
}
