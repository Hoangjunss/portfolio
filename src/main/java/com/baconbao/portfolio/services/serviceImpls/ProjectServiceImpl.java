package com.baconbao.portfolio.services.serviceImpls;

import com.baconbao.portfolio.dto.ProjectDTO;
import com.baconbao.portfolio.exception.CustomException;
import com.baconbao.portfolio.exception.Error;
import com.baconbao.portfolio.model.Profile;
import com.baconbao.portfolio.model.Project;
import com.baconbao.portfolio.repository.ProjectRepository;
import com.baconbao.portfolio.services.service.ImageService;
import com.baconbao.portfolio.services.service.ProfileService;
import com.baconbao.portfolio.services.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ImageService imageService;

    private Project save(ProjectDTO projectDTO) {
        try{
            log.info("Saving project");
            Profile profile = profileService.convertToModel(profileService.findById(projectDTO.getIdProfile()));
            Project project=Project.builder()
                    .id(getGenerationId())
                    .title(projectDTO.getTitle())
                    .description(projectDTO.getDescription())
                    .url(projectDTO.getUrl())
                    .image(imageService.saveImage(projectDTO.getImageFile()))
                    .profile(profile)
                    .build();
            return projectRepository.save(project);
        } catch (DataIntegrityViolationException e){
            throw new CustomException(Error.PROFILE_UNABLE_TO_SAVE);
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }
    public Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }

    @Override
    public ProjectDTO saveProject(ProjectDTO projectDTO) {
        try{
            log.info("Save project");
            Project project=save(projectDTO);
            return convertToDTO(project);
        } catch (DataIntegrityViolationException e){
            throw new CustomException(Error.PROFILE_UNABLE_TO_SAVE);
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public ProjectDTO updateProject(ProjectDTO projectDTO) {
        try{
            log.info("Update project");
            projectDTO.setCreateAt(findById(projectDTO.getId()).getCreateAt());
            Project project=projectRepository.save(convertToModel(projectDTO));
            return convertToDTO(project);
        } catch (DataIntegrityViolationException e){
            throw new CustomException(Error.PROFILE_UNABLE_TO_SAVE);
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public ProjectDTO findById(Integer id) {
        log.info("Find project by id: {}", id);
        return convertToDTO(projectRepository.findById(id)
                .orElseThrow(()-> new CustomException(Error.PROFILE_NOT_FOUND)));
    }

    @Override
    public List<ProjectDTO> getAllProjectDTOByProfile(Integer idProfile) {
        try{
            log.info("Find all projects by idProfile: {}", idProfile);
            Profile profile=profileService.convertToModel(profileService.findById(idProfile));
            List<Project> projects=projectRepository.getProjectByProfile(profile);
            return convertToDTOList(projects);
        } catch (InvalidDataAccessResourceUsageException i){
            log.error("Get all projects by profile failed: {}", i.getMessage());
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
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
