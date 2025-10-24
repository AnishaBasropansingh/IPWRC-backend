package com.example.webshop_backend.utils;

import com.example.webshop_backend.dao.RoleRepository;
import com.example.webshop_backend.dao.UserRepository;
import com.example.webshop_backend.model.Role;
import com.example.webshop_backend.model.RoleEnum;
import com.example.webshop_backend.model.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private boolean alreadySetup = false;

    @Value("${adminEmail}")
    private String adminEmail;

    @Value("${adminPassword}")
    private String adminPassword;

    @Autowired
    public RoleSeeder(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (alreadySetup) return;

        loadRoles();
        createAdminIfNotExists();

        alreadySetup = true;
    }

    private void loadRoles() {
        RoleEnum[] roleNames = new RoleEnum[]{RoleEnum.ROLE_ADMIN, RoleEnum.ROLE_USER};

        Map<RoleEnum, String> roleDescriptions = Map.of(
                RoleEnum.ROLE_ADMIN, "Is beheerder van de webshop",
                RoleEnum.ROLE_USER, "Is gebruiker van de webshop"
        );

        Arrays.stream(roleNames).forEach(roleEnum -> {
            Optional<Role> optionalRole = roleRepository.findByName(roleEnum);
            optionalRole.ifPresentOrElse(
                    role -> System.out.println("Bestaat al: " + role),
                    () -> {
                        Role roleToCreate = new Role();
                        roleToCreate.setName(roleEnum);
                        roleToCreate.setDescription(roleDescriptions.get(roleEnum));
                        roleRepository.save(roleToCreate);
                    }
            );
        });
    }

    private void createAdminIfNotExists() {
        Optional<CustomUser> existingAdmin = Optional.ofNullable(userRepository.findByEmail(adminEmail));
        if (existingAdmin.isEmpty()) {
            Role adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Admin role not found"));

            CustomUser admin = new CustomUser();
                admin.setUsername("admin");
                admin.setEmail(adminEmail);
                admin.setPassword(passwordEncoder.encode(adminPassword));
                admin.setRole(adminRole);

            userRepository.save(admin);
            System.out.println("Admin aangemaakt: " + adminEmail + " / " + adminPassword);
        } else {
            System.out.println("Admin bestaat al: " + adminEmail);
        }
    }
}
