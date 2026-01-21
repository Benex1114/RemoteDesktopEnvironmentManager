package com.remotemanager.envmanager.repository;
 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.remotemanager.envmanager.model.Item;
 
public interface ItemRepository extends JpaRepository<Item, Long> {
 
    List<Item> findByProjectId(Long projectId);

    List<Item> findByProjectIdAndNameContainingIgnoreCase(Long projectId, String keyword);
}