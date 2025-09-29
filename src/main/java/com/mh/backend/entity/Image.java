package com.mh.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filePath;
    private String imageName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "tour_id")
    private Tour tour;

    // Constructors, getters, and setters...
    public Image () { }

    public Image(Long id, String filePath, String imageName, Tour tour) {
        this.id = id;
        this.filePath = filePath;
        this.imageName = imageName;
        this.tour = tour;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public String getImageName() { return imageName; }
    public void setImageName(String imageName) { this.imageName = imageName; }

    public Tour getTour() { return tour; }
    public void setTour(Tour tour) { this.tour = tour; }
}
