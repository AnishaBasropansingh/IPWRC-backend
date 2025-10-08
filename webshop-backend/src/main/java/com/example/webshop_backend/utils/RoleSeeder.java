package com.example.webshop_backend.utils;

import com.example.webshop_backend.dao.RoleRepository;
import com.example.webshop_backend.model.Role;
import com.example.webshop_backend.model.RoleEnum;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.loadRoles();
    }

    public void loadRoles() {
        RoleEnum[] roleNames = new RoleEnum[]{RoleEnum.ADMIN, RoleEnum.USER};

        Map<RoleEnum, String> roleDescriptions = Map.of(
                RoleEnum.ADMIN, "Is beheerder van de webshop",
                RoleEnum.USER, "Is gebruiker van de webshop"
        );

        Arrays.stream(roleNames).forEach(roleEnum -> {
            Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.valueOf(roleEnum.name()));

            optionalRole.ifPresentOrElse(
                role -> System.out.println("Bestaat al: " + role),
                () -> {
                    Role roleToCreate = new Role();
                    roleToCreate.setName(RoleEnum.valueOf(roleEnum.name()));
                    roleToCreate.setDescription(roleDescriptions.get(roleEnum));
                    roleRepository.save(roleToCreate);
                }
            );
        });
    }
}
