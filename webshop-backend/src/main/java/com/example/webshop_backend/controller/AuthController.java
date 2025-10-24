package com.example.webshop_backend.controller;

import com.example.webshop_backend.config.JWTUtil;
import com.example.webshop_backend.dao.RoleRepository;
import com.example.webshop_backend.dao.UserRepository;
import com.example.webshop_backend.dto.AuthenticationDTO;
import com.example.webshop_backend.dto.LoginResponse;
import com.example.webshop_backend.dto.UserDTO;
import com.example.webshop_backend.dto.UserInfoResponse;
import com.example.webshop_backend.model.CustomUser;
import com.example.webshop_backend.model.Role;
import com.example.webshop_backend.model.RoleEnum;
import com.example.webshop_backend.service.CredentialValidator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/auth")
class AuthController {
    private final UserRepository userDAO;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    private CredentialValidator validator;
    private RoleRepository roleRepository;

    public AuthController(UserRepository userDAO, JWTUtil jwtUtil, AuthenticationManager authManager,
                          PasswordEncoder passwordEncoder, CredentialValidator validator, RoleRepository roleRepository) {
        this.userDAO = userDAO;
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody UserDTO userDTO) {
        if (!validator.isValidEmail(userDTO.email)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "No valid email provided"
            );
        }

        if (!validator.isValidPassword(userDTO.password)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "No valid password provided"
            );
        }

        CustomUser customUser = userDAO.findByEmail(userDTO.email);

        if (customUser != null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Can not register with this email"
            );
        }
        String encodedPassword = passwordEncoder.encode(userDTO.password);

        Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Default role not found"));

        CustomUser registerdCustomCustomUser = new CustomUser(userDTO.username, userDTO.email, encodedPassword, userRole);
        userDAO.save(registerdCustomCustomUser);
        System.out.println(userRole.toString());
        String token = jwtUtil.generateToken(registerdCustomCustomUser.getEmail());
        LoginResponse loginResponse = new LoginResponse(registerdCustomCustomUser.getEmail(), token);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<UserInfoResponse> login(@RequestBody AuthenticationDTO body) {
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.email, body.password);

            authManager.authenticate(authInputToken);

            String token = jwtUtil.generateToken(body.email);

            CustomUser customUser = userDAO.findByEmail(body.email);

            UserInfoResponse userInfoResponse = new UserInfoResponse(customUser.getId(), customUser.getUsername(), customUser.getEmail(), token, customUser.getRole().getName().toString());

            return ResponseEntity.ok(userInfoResponse);

        } catch (AuthenticationException authExc) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "No valid credentials"
            );
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String email = jwtUtil.validateTokenAndRetrieveSubject(token);
            CustomUser user = userDAO.findByEmail(email);

            if (user != null) {
                return ResponseEntity.ok(new UserInfoResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        token,
                        user.getRole().getName().toString()
                ));
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
    }
}


