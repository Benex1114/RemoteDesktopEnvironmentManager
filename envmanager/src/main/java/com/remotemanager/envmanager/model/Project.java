package com.remotemanager.envmanager.model;
 
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
 
@Entity
public class Project {
 
    @ManyToMany(mappedBy="projects")
    private Set<User> users = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    private String name;
    private String description;
 
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval=true)
    @SuppressWarnings("unused")
    private List<Item> item;
 
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
 
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
 
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Set<User> getUsers() {return users;}
    public void setUsers(Set<User> users) {this.users = users;}
}
