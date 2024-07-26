package com.baconbao.portfolio.services.service;

import com.baconbao.portfolio.dto.ProjectDTO;
import com.baconbao.portfolio.model.Project;

public interface ProjectService {
    ProjectDTO saveProject(ProjectDTO projectDTO);
}
