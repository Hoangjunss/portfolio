package com.baconbao.portfolio.services.serviceImpls;

import com.baconbao.portfolio.dto.ProjectDTO;
import com.baconbao.portfolio.dto.UserDTO;
import com.baconbao.portfolio.model.Profile;
import com.baconbao.portfolio.model.Project;
import com.baconbao.portfolio.model.User;
import com.baconbao.portfolio.repository.ProjectRepository;
import com.baconbao.portfolio.services.service.ProfileService;
import com.baconbao.portfolio.services.service.ProjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProfileService profileService;

    private Project save(ProjectDTO projectDTO) {
        Project project=Project.builder()
                .id(getGenerationId())
                .title(projectDTO.getTitle())
                .description(projectDTO.getDescription())
                .url(projectDTO.getUrl())
                .build();
        return projectRepository.save(project);
    }
    public Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        // Use most significant bits and ensure it's within the integer range
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }

    @Override
    public ProjectDTO saveProject(ProjectDTO projectDTO) {
        Project project=save(projectDTO);
        profileService.updateProjectByProfile(project,projectDTO.getIdProfile());
        return convertToDTO(project);
    }

    @Override
    public ProjectDTO updateProject(ProjectDTO projectDTO) {
        projectDTO.setCreateAt(findById(projectDTO).getCreateAt());
         Project project=projectRepository.save(convertToModel(projectDTO));

        return convertToDTO(project);
    }

    @Override
    public ProjectDTO findById(ProjectDTO projectDTO) {
        return convertToDTO(projectRepository.findById(projectDTO.getId())
                .orElseThrow());
    }

    @Override
    public List<ProjectDTO> getAllProjectDTOByProfile(Integer idProfile) {

        return null;
    }

    private ProjectDTO convertToDTO(Project project) {

        return modelMapper.map(project, ProjectDTO.class);
    }
    private Project convertToModel(ProjectDTO projectDTO) {
        return modelMapper.map(projectDTO, Project.class);
    }
    public List<ProjectDTO> convertToDTOList(List<Project> projects) {
        return projects.stream()
                .map(project -> modelMapper.map(project, ProjectDTO.class))
                .collect(Collectors.toList());
    }

}
