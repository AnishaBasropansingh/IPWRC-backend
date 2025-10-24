package com.example.webshop_backend.controller;

import com.example.webshop_backend.dao.UserDAO;
import com.example.webshop_backend.model.CustomUser;
import com.example.webshop_backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
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
