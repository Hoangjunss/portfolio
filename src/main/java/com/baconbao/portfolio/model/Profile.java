package com.baconbao.portfolio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "profile")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
    @Id
    private Integer id;
        private String objective;
    private String education;
    private String workExperience;
    private String skills;
    @OneToOne
    @JoinColumn(name = "idImage", referencedColumnName = "id")
    private Image image;

    @OneToMany(mappedBy = "profile")
    private List<Project> projects;

    @OneToMany(mappedBy = "profile")
    private List<Comments> comments;

    @OneToOne
    @JoinColumn(name = "idContact", referencedColumnName = "id")
    private Contact contact;

    @OneToOne(mappedBy = "profile")
    private User user;

   @Enumerated(value =EnumType.STRING)
    private TypeProfile typeProfile;
}
