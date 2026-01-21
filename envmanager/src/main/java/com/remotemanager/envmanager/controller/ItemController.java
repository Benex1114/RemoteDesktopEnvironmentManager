package com.remotemanager.envmanager.controller;
 
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.remotemanager.envmanager.model.Item;
import com.remotemanager.envmanager.model.ItemType;
import com.remotemanager.envmanager.service.ItemService;
 
@Controller
@RequestMapping("/projects/{projectId}/items")
public class ItemController {
 
    private final ItemService itemService;
 
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }
 
    @GetMapping
    public String listItems(
            @PathVariable Long projectId,
            Model model
    ) {
        model.addAttribute("items", itemService.getItemsByProject(projectId));
        model.addAttribute("projectId", projectId);
        return "items/items";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/new")
    public String showCreateForm(
            @PathVariable Long projectId,
            Model model
    ) {
        model.addAttribute("item", new Item());
        model.addAttribute("types", ItemType.values());
        model.addAttribute("projectId", projectId);
        return "items/item-form";
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public String saveItem(
            @PathVariable Long projectId,
            @ModelAttribute Item item,
            @RequestParam(required = false) MultipartFile file
    ) {
        itemService.saveItem(projectId, item, file);
        return "redirect:/projects/" + projectId + "/items";
    }
 
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{id}")
    public String showEditForm(
            @PathVariable Long projectId,
            @PathVariable Long id,
            Model model
    ) {
        model.addAttribute("item", itemService.getItem(id));
        model.addAttribute("types", ItemType.values());
        model.addAttribute("projectId", projectId);
        return "items/item-form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteItem(
            @PathVariable Long projectId,
            @PathVariable Long id
    ) {
        itemService.deleteItem(id);
        return "redirect:/projects/" + projectId + "/items";
    }

    @GetMapping("/search")
    public String searchItems(@PathVariable Long projectId, @RequestParam String keyword, Model model) {
        model.addAttribute("items",itemService.searchItems(projectId, keyword));
        model.addAttribute("projectId", projectId);
        return "items/items";
    }
}