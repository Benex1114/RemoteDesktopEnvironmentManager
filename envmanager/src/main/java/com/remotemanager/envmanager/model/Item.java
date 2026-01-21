package com.remotemanager.envmanager.model;
 
import jakarta.persistence.*;
 
@Entity
public class Item {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    private String name;
 
    private String url;
 
    @Column(columnDefinition = "TEXT")
    private String sopContent; // used if type = SOP

    private String filePath;
 
    @Enumerated(EnumType.STRING)
    private ItemType type;
 
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
 
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
 
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
 
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
 
    public String getSopContent() { return sopContent; }
    public void setSopContent(String sopContent) { this.sopContent = sopContent; }
 
    public ItemType getType() { return type; }
    public void setType(ItemType type) { this.type = type; }
 
    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }

    public String getFilePath() {return filePath;}
    public void setFilePath(String filePath) { this.filePath = filePath; }
}