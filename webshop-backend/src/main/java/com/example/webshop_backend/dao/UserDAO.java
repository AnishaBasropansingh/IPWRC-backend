package com.example.webshop_backend.dao;

import com.example.webshop_backend.model.CustomUser;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDAO {
    private UserRepository userRepository;

    public UserDAO(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<CustomUser> loadUserById(Long user_id){
        return userRepository.findById(user_id);
    }
}
