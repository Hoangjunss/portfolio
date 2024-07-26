package com.baconbao.portfolio.dto;

import com.baconbao.portfolio.model.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO {
    private Integer id;
    private String name;
    private String objective;
    private String education;
    private String workExperience;
    private String skills;
    private ImageDTO imageDTO;
    private List<CommentsDTO> commentsDTO;
    private List<ProjectDTO> projectsDTO;
    private ContactDTO contactDTO;
    private MultipartFile imageFile;
}
