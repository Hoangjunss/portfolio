package com.baconbao.portfolio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String address;
    private String contactInfo;
}
