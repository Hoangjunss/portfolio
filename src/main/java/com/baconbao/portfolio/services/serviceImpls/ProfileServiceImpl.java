package com.baconbao.portfolio.services.serviceImpls;

import com.baconbao.portfolio.dto.ProfileDTO;

import com.baconbao.portfolio.dto.UserDTO;
import com.baconbao.portfolio.exception.CustomException;
import com.baconbao.portfolio.exception.Error;
import com.baconbao.portfolio.model.*;
import com.baconbao.portfolio.repository.ProfileRepository;
import com.baconbao.portfolio.repository.UserRepository;
import com.baconbao.portfolio.services.service.ImageService;
import com.baconbao.portfolio.services.service.ProfileService;
import com.baconbao.portfolio.services.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Service;


import javax.xml.stream.events.Comment;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ImageService imageService;

    private Profile save(ProfileDTO profileDTO) {
        try{
            log.info("Saving profile");
            Profile profile = Profile.builder()
                    .objective(profileDTO.getObjective())
                    .education(profileDTO.getEducation())
                    .workExperience(profileDTO.getWorkExperience())
                    .skills(profileDTO.getSkills())
                    .typeProfile(TypeProfile.valueOf(profileDTO.getTypeProfile()))
                    .image(imageService.saveImage(profileDTO.getImageFile()))
                    .id(getGenerationId())
                    .build();
            return profileRepository.save(profile);
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
    public ProfileDTO saveProfile(ProfileDTO profileDTO) {
        try{
            log.info("Save profile");
            Profile profile = save(profileDTO);
            userService.updateProfileByUser(profile, profileDTO.getUserId());
            return convertToDTO(profile);
        } catch (DataIntegrityViolationException e){
            throw new CustomException(Error.PROFILE_UNABLE_TO_SAVE);
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public ProfileDTO findById(Integer id) {
        log.info("Find Profile by id: {}", id);
        return convertToDTO(profileRepository.findById(id)
                .orElseThrow(()-> new CustomException(Error.PROFILE_NOT_FOUND)));
    }

    public ProfileDTO convertToDTO(Profile profile) {
        return modelMapper.map(profile, ProfileDTO.class);
    }
    public Profile convertToModel(ProfileDTO profileDTO) {
        return modelMapper.map(profileDTO, Profile.class);
    }


    @Override
    public void updateContactByProfile(Contact contact, Integer id) {
        try {
            log.info("Updating contact by profile id: {}", id);
            Profile profile=profileRepository.findById(id).orElseThrow(()-> new CustomException(Error.PROFILE_NOT_FOUND));
            profile.setContact(contact);
            profileRepository.save(profile);
        } catch (DataIntegrityViolationException e){
            throw new CustomException(Error.PROFILE_UNABLE_TO_UPDATE);
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public ProfileDTO getProfileByUser(Integer id) {
        try {
            log.info("Get profile by user id: {}", id);
            User user= userService.convertToModel(userService.findById(id));
            return convertToDTO(profileRepository.findByUser(user));
        }  catch (InvalidDataAccessResourceUsageException i){
            log.error("Get profile by user failed: {}", i.getMessage());
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
        return null;
    }

    @Override
    public ProfileDTO findProfileByName(String name) {
        try{
            log.info("Get profile by name: {}", name);
            return convertToDTO(profileRepository.findProfileByName(name));
        } catch (InvalidDataAccessResourceUsageException i){
            log.error("Get profile by name failed: {}", i.getMessage());
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
        return null;
    }

    @Override
    public ProfileDTO updateProfile(ProfileDTO profileDTO) {
        try{
            log.info("Update profile");
            return convertToDTO(profileRepository.save(convertToModel(profileDTO)));
        } catch (DataIntegrityViolationException e){
            throw new CustomException(Error.PROFILE_UNABLE_TO_UPDATE);
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    public List<ProfileDTO> findProfilesByType(TypeProfile typeProfile) {
        try {
            log.info("Get profile by type: {}", typeProfile);
            return convertToDTOList(profileRepository.findByTypeProfile(typeProfile));
        } catch (InvalidDataAccessResourceUsageException i){
            log.error("Get profile by type failed: {}", i.getMessage());
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
        return null;
    }
    public List<ProfileDTO> convertToDTOList(List<Profile> profiles) {
        return profiles.stream()
                .map(project -> modelMapper.map(project, ProfileDTO.class))
                .collect(Collectors.toList());
    }
}
