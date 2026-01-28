package com.remotemanager.envmanager.service;
 
import java.util.List;

import org.springframework.stereotype.Service;

import com.remotemanager.envmanager.model.Project;
import com.remotemanager.envmanager.model.User;
import com.remotemanager.envmanager.repository.ProjectRepository;
 
@Service
public class ProjectService {
 
    private final ProjectRepository projectRepository;
 
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
 
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
 
    public Project getProject(Long id) {
        return projectRepository.findById(id).orElse(null);
    }
 
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }
 
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project Not Found"));

        for (User user : project.getUsers()) {
            user.getProjects().remove(project);
        }
        project.getUsers().clear();
        projectRepository.delete(project);
    }

    public List<Project> searchProjects(String keyword) {
        return projectRepository.findByNameContainingIgnoreCase(keyword);
    }
}
