package com.baconbao.portfolio.services.service;

import com.baconbao.portfolio.dto.ProjectDTO;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    ProjectDTO saveProject(ProjectDTO projectDTO);
    ProjectDTO updateProject(ProjectDTO projectDTO);
    ProjectDTO findById(ProjectDTO projectDTO);
    List<ProjectDTO> getAllProjectDTOByProfile(Integer idProfile);
}
