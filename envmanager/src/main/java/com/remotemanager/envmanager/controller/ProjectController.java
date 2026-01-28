package com.remotemanager.envmanager.controller;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.remotemanager.envmanager.model.Item;
import com.remotemanager.envmanager.model.Project;
import com.remotemanager.envmanager.model.User;
import com.remotemanager.envmanager.service.ItemService;
import com.remotemanager.envmanager.service.ProjectService;
import com.remotemanager.envmanager.service.UserService;

@Controller
@RequestMapping("/projects")
public class ProjectController {
 
    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;
 
    // 1. List all projects
    @GetMapping
    public String listProjects(Model model, Authentication auth) {
        User user = userService.findByUsername(auth.getName());

        if ("ADMIN".equals(user.getRole())) {
        model.addAttribute("projects", projectService.getAllProjects());
        } else {
            model.addAttribute("projects", user.getProjects());
        }
        return "projects/projects";
    }
 
    // 2. Create form
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/new")
    public String createProjectForm(Model model) {
        model.addAttribute("project", new Project());
        return "projects/project-form";
    }
 
    // 3. Save Project
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public String saveProject(@ModelAttribute Project project) {
        projectService.saveProject(project);
        return "redirect:/projects";
    }
 
    // 4. Edit Form
    @GetMapping("/edit/{id}")
    public String editProject(@PathVariable Long id, Model model) {
        model.addAttribute("project", projectService.getProject(id));
        return "projects/project-form";
    }
 
    // 5. View Project Details
    @GetMapping("/projects/{id}/items")
    public String viewProject(@PathVariable Long id, Model model) {
        Project project = projectService.getProject(id);
        List<Item> items = itemService.getItemsByProject(id);
        model.addAttribute("project", project);
        model.addAttribute("items", items);
        return "items/items";
    }
 
    // 6. Delete project
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return "redirect:/projects";
    }

    @GetMapping("/search")
    public String searchProjects(@RequestParam String keyword, Model model) {
        model.addAttribute("projects", projectService.searchProjects(keyword));
        return "projects/projects";
    }
}
