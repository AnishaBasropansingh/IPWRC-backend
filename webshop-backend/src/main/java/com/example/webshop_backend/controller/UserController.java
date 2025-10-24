package com.example.webshop_backend.controller;

import com.example.webshop_backend.dao.UserDAO;
import com.example.webshop_backend.model.CustomUser;
import com.example.webshop_backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/user")
class UserController {
    private final UserDAO userDAO;
    private UserService userService;

    UserController(UserService userService, UserDAO userDAO) {
        this.userService = userService;
        this.userDAO = userDAO;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomUser> getUserById(@PathVariable(name = "id") Long user_id){
        Optional<CustomUser> user = userDAO.loadUserById(user_id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
