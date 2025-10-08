package com.example.webshop_backend.dao;

import com.example.webshop_backend.model.Role;
import com.example.webshop_backend.model.RoleEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);
}
