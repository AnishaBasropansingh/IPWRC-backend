package com.example.webshop_backend.service;

import com.example.webshop_backend.dao.RoleRepository;
import com.example.webshop_backend.dao.UserRepository;

import com.example.webshop_backend.model.CustomUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserService(UserRepository userDAO, RoleRepository roleRepository) {
        this.userRepository = userDAO;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CustomUser customUser = userRepository.findByEmail(email);
        return new User(
                customUser.getEmail(),
                customUser.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

    }
}
