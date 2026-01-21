package com.remotemanager.envmanager.config;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.remotemanager.envmanager.model.User;
import com.remotemanager.envmanager.repository.UserRepository;
 
@Configuration
public class DataInitializer {

    @Autowired
    private PasswordEncoder encoder;
    @Bean
    @SuppressWarnings("unused")
CommandLineRunner initUsers(UserRepository repo) {
    return args -> {
        if (repo.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(encoder.encode("admin123"));
            admin.setRole("ADMIN");
            admin.setEnabled(true);
            repo.save(admin);
        }
        if (repo.findByUsername("testuser1").isEmpty()) {
            User u = new User();
            u.setUsername("testuser1");
            u.setPassword(encoder.encode("testuser@123"));
            u.setRole("USER");
            u.setEnabled(true);
            repo.save(u);
        }
    };
}
}