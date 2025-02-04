package com.baconbao.portfolio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "project")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Project {
    @Id
    private Integer id;
    private String title;
    private String description;
    private LocalDateTime createAt;
    @PrePersist
    protected void onCreate() {
        createAt = LocalDateTime.now();

    }
    private String url;

    @OneToOne
    @JoinColumn(name = "idImage", referencedColumnName = "id")
    private Image image;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;
}
