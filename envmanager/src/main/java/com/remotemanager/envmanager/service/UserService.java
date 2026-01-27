package com.remotemanager.envmanager.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.remotemanager.envmanager.model.User;
import com.remotemanager.envmanager.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repo, PasswordEncoder encoder) {
        this.repo=repo;
        this.encoder=encoder;
    }

    public List<User> findAll() {
        return repo.findAll();
    }

    public User findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User findByUsername(String username) {
        return repo.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void createUser(User user, String rawPassword) {
        if (rawPassword == null || rawPassword.isBlank()) {
            throw new RuntimeException("Password required");
        }
    
        user.setPassword(encoder.encode(rawPassword));
        repo.save(user);
    }

    public void updateUser(Long id, User updated, String rawPassword) {
 
        User existing = repo.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
    
        existing.setUsername(updated.getUsername());
        existing.setRole(updated.getRole());
        existing.setEnabled(updated.isEnabled());
        existing.setProjects(updated.getProjects());
    
        if (rawPassword != null && !rawPassword.isBlank()) {
            existing.setPassword(encoder.encode(rawPassword));
        }
    
        repo.save(existing);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
    
}
