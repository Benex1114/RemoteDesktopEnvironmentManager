package com.remotemanager.envmanager.service;
 
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.remotemanager.envmanager.model.Item;
import com.remotemanager.envmanager.model.ItemType;
import com.remotemanager.envmanager.model.Project;
import com.remotemanager.envmanager.repository.ItemRepository;
import com.remotemanager.envmanager.repository.ProjectRepository;
 
@Service
public class ItemService {
 
    private final ItemRepository itemRepository;
    private final ProjectRepository projectRepository;
 
    private static final String UPLOAD_DIR = "uploads";
 
    public ItemService(ItemRepository itemRepository,
                       ProjectRepository projectRepository) {
        this.itemRepository = itemRepository;
        this.projectRepository = projectRepository;
    }
 
    public List<Item> getItemsByProject(Long projectId) {
        return itemRepository.findByProjectId(projectId);
    }
 
    public Item getItem(Long id) {
        return itemRepository.findById(id).orElseThrow();
    }
 
    public void saveItem(Long projectId,Item item,MultipartFile file) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
 
        item.setProject(project);
 
        try {
            if (item.getType() == ItemType.SOP && file != null && !file.isEmpty()) {
                Path uploadPath = Paths.get(UPLOAD_DIR).toAbsolutePath().normalize();
                Files.createDirectories(uploadPath);
 
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName);
 
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                item.setFilePath("uploads/" + fileName);
            }
 
            itemRepository.save(item);
 
        } catch (IOException e) {
            throw new RuntimeException("Failed to save item", e);
        }
    }
 
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    public List<Item> searchItems(Long projectId, String keyword) {
        return itemRepository.findByProjectIdAndNameContainingIgnoreCase(projectId, keyword);
    }
}