package com.baconbao.portfolio.services.serviceImpls;

import com.baconbao.portfolio.dto.ProfileDTO;
import com.baconbao.portfolio.model.Profile;
import com.baconbao.portfolio.repository.ProfileRepository;
import com.baconbao.portfolio.services.service.ProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.UUID;

@Service

public class ProfileServiceImpl implements ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ModelMapper modelMapper;

    private Profile save(ProfileDTO profileDTO) {
        Profile profile = Profile.builder()
                .objective(profileDTO.getObjective())
                .education(profileDTO.getEducation())
                .workExperience(profileDTO.getWorkExperience())
                .skills(profileDTO.getSkills())
                .address(profileDTO.getAddress())
                .contactInfo(profileDTO.getContactInfo())
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

    private ProfileDTO convertToDTO(Profile profile) {
        return modelMapper.map(profile, ProfileDTO.class);
    }

    @Override
    public ProfileDTO updateProfile(ProfileDTO profileDTO) {
        Optional<Profile> profile = profileRepository.findById(1);
        Profile profileToUpdate = profile.get().builder()
                    .objective(profileDTO.getObjective())
                    .education(profileDTO.getEducation())
                    .workExperience(profileDTO.getWorkExperience())
                    .skills(profileDTO.getSkills())
                    .address(profileDTO.getAddress())
                    .contactInfo(profileDTO.getContactInfo())
                    .build();
        return convertToDTO(profileRepository.save(profileToUpdate));

    }

}
