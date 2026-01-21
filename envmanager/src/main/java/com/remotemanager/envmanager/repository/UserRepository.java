package com.remotemanager.envmanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.remotemanager.envmanager.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User>
    findByUsername(String username);

    public void save(org.springframework.security.core.userdetails.User u);
    
}
