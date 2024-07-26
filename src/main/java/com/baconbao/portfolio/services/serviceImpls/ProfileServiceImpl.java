package com.baconbao.portfolio.services.serviceImpls;

import com.baconbao.portfolio.model.Profile;
import com.baconbao.portfolio.repository.ProfileRepository;
import com.baconbao.portfolio.services.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service

public class ProfileServiceImpl implements ProfileService
{
    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public void updateProfile(Profile updatedProfile){
        Optional<Profile> profile = profileRepository.findById(1);
        if(profile.isPresent()){
            Profile profileToUpdate = profile.get().builder()
                    .objective(updatedProfile.getObjective())
                    .education(updatedProfile.getEducation())
                    .workExperience(updatedProfile.getWorkExperience())
                    .skills(updatedProfile.getSkills())
                    .address(updatedProfile.getAddress())
                    .contactInfo(updatedProfile.getContactInfo())
                    .build();
        }
        else
        {
            throw new RuntimeException("Profile not found");
        }

    }

    @Override
    public void deleteProfile() {

    }

}
