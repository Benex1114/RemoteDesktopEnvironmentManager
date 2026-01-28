package com.remotemanager.envmanager.repository;
 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.remotemanager.envmanager.model.Project;
 
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByNameContainingIgnoreCase(String keyword);
}
