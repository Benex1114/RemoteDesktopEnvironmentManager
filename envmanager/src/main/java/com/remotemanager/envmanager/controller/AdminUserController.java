package com.remotemanager.envmanager.controller;

import java.util.Collections;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.remotemanager.envmanager.model.Project;
import com.remotemanager.envmanager.model.User;
import com.remotemanager.envmanager.service.ProjectService;
import com.remotemanager.envmanager.service.UserService;

@Controller
public class AdminUserController {

    private final UserService userService;
    private final ProjectService projectService;

    public AdminUserController(UserService userService, ProjectService projectService) {
        this.userService = userService;
        this.projectService = projectService;
    }

    //CREATE FORM
    @GetMapping("/admin/users/create")
    public String createForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("projects", projectService.getAllProjects());
        return "users/user-create";
    }
 
    @PostMapping("/admin/users/create")
    public String createUser(@ModelAttribute User user, @RequestParam(required = false) Set<Project> projectIds,
                             @RequestParam String rawPassword) {
        if(projectIds != null) {
            user.setProjects(projectIds);
        } else {
            user.setProjects(Collections.emptySet());
        }
        userService.createUser(user, rawPassword);
        return "redirect:/admin/users";
    }
 
    @GetMapping("/admin/users/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("projects", projectService.getAllProjects());
        return "users/user-edit";
    }
 
    @PostMapping("/admin/users/edit/{id}")
    public String updateUser(@PathVariable Long id,
                             @ModelAttribute User user, @RequestParam(required = false) Set<Project> projectIds,
                             @RequestParam(required = false) String rawPassword) {
        if(projectIds != null) {
            user.setProjects(projectIds);
        } else {
            user.setProjects(Collections.emptySet());
        }
        userService.updateUser(id, user, rawPassword);
        return "redirect:/admin/users";
    }
    

    @GetMapping("/admin/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users/users";
    }

    @GetMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
