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

    @OneToMany
    @JoinColumn(name = "idProject", referencedColumnName = "id")
    private List<Project> projects;

    @OneToMany
    @JoinColumn(name = "idComment", referencedColumnName = "id")
    private List<Comments> comments;

    @OneToOne
    @JoinColumn(name = "idContact", referencedColumnName = "id")
    private Contact contact;

    @OneToOne
    @JoinColumn(name = "idTypeProfile", referencedColumnName = "id")
    private TypeProfile typeProfile;

    @OneToMany
    @JoinColumn(name = "idNotification", referencedColumnName = "id")
    private List<Notification> notifications;
}
