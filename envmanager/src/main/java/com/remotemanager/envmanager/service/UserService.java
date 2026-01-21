package com.remotemanager.envmanager.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.remotemanager.envmanager.model.User;
import com.remotemanager.envmanager.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo=repo;
        this.passwordEncoder=passwordEncoder;
    }

    public void createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        repo.save(user);
    }

    public List<User> getUsers() {
        return repo.findAll();
    }

    public User saveUser(User user) {
        return repo.save(user);
    }
    
}