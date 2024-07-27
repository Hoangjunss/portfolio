package com.baconbao.portfolio.services.serviceImpls;

import com.baconbao.portfolio.dto.ProfileDTO;
import com.baconbao.portfolio.model.Contact;
import com.baconbao.portfolio.model.Profile;
import com.baconbao.portfolio.model.Project;
import com.baconbao.portfolio.model.TypeProfile;
import com.baconbao.portfolio.repository.ProfileRepository;
import com.baconbao.portfolio.repository.UserRepository;
import com.baconbao.portfolio.services.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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

    private Profile save(ProfileDTO profileDTO) {
        Profile profile = Profile.builder()
                .objective(profileDTO.getObjective())
                .education(profileDTO.getEducation())
                .workExperience(profileDTO.getWorkExperience())
                .skills(profileDTO.getSkills())
                .id(getGenerationId())
                .build();
        return profileRepository.save(profile);
    }

    public Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        // Use most significant bits and ensure it's within the integer range
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }

    @Override
    public ProfileDTO saveProfile(ProfileDTO profileDTO) {
        return convertToDTO(save(profileDTO));
    }

    @Override
    public ProfileDTO findById(Integer id) {
        log.info("Find Profile by id: {}", id);
        return convertToDTO(profileRepository.findById(id)
                .orElseThrow());
    }

    @Override
    public ProfileDTO findByName(String name) {
        return convertToDTO(userRepository.findProfileByName(name));
    }


    public ProfileDTO convertToDTO(Profile profile) {
        return modelMapper.map(profile, ProfileDTO.class);
    }
    public Profile convertToModel(ProfileDTO profileDTO) {
        return modelMapper.map(profileDTO, Profile.class);
    }

    @Override
    public void updateProjectByProfile(Project project, Integer id) {
     Profile profile=profileRepository.findById(id).orElseThrow();
     profile.getProjects().add(project);
     profileRepository.save(profile);

    }

    @Override
    public void updateContactByProfile(Contact contact, Integer id) {
        Profile profile=profileRepository.findById(id).orElseThrow();
        profile.setContact(contact);
        profileRepository.save(profile);
    }


    @Override
    public ProfileDTO updateProfile(ProfileDTO profileDTO) {
        return convertToDTO(profileRepository.save(convertToModel(profileDTO)));
    }

    public List<Profile> findProfilesByType(TypeProfile typeProfile) {
        return profileRepository.findByTypeProfile(typeProfile);
    }

}
