package com.remotemanager.envmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.remotemanager.envmanager.model.User;
import com.remotemanager.envmanager.service.UserService;

@Controller
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    //CREATE FORM
    @GetMapping("/admin/users/new")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new User());
        return "users/user-form";
    }

    @PostMapping("/admin/users/save")
    public String createUser(@ModelAttribute User user) {
        userService.createUser(user);
        return "redirect:/admin/users";
    }
    

    @GetMapping("/admin/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "users/users";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}