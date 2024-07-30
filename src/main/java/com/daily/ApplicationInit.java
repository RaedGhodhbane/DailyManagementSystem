package com.daily;

import com.daily.models.Role;
import com.daily.models.User;
import com.daily.enums.ERole;
import com.daily.repositories.RoleRepository;
import com.daily.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Initializes roles and the admin user if they do not exist at server startup.
 * Author: Majd Selmi
 */
@Component
public class ApplicationInit {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationInit.class);

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${application.admin.username}")
    private String adminUsername;

    @Value("${application.admin.email}")
    private String adminEmail;

    @Value("${application.admin.password}")
    private String adminPassword;

    @Value("${application.admin.create}")
    private boolean createAdmin;

    @Bean
    InitializingBean init() {
        return () -> {
            // Create roles if they do not exist
            createRoleIfNotExists(ERole.ROLE_ADMIN);
            createRoleIfNotExists(ERole.ROLE_USER);

            // Create admin user if the user does not exist
            if (createAdmin && !userRepository.existsByUsername(adminUsername)) {
                createAdminUser();
            }
        };
    }

    private void createRoleIfNotExists(ERole role) {
        if (!roleRepository.existsByName(role)) {
            logger.info("Saving role: {}", role);
            roleRepository.save(new Role(role));
        }
    }

    private void createAdminUser() {
        logger.info("Creating Admin Account");
        logger.info("Username: {}", adminUsername);
        logger.info("Email: {}", adminEmail);
        logger.info("Password: {}", adminPassword);

        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN);
        if (adminRole != null) {
            User adminUser = new User();
            adminUser.setUsername(adminUsername);
            adminUser.setEmail(adminEmail);
            adminUser.setPassword(passwordEncoder.encode(adminPassword));
            adminUser.setRoles(Collections.singleton(adminRole));

            userRepository.save(adminUser);
            logger.info("Admin account created successfully");
        } else {
            logger.error("Admin role not found, cannot create admin user");
        }
    }
}
