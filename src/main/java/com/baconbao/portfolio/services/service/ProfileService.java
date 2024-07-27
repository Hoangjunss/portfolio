package com.baconbao.portfolio.services.service;

import com.baconbao.portfolio.dto.ProfileDTO;
import com.baconbao.portfolio.model.Contact;
import com.baconbao.portfolio.model.Profile;
import com.baconbao.portfolio.model.Project;
import com.baconbao.portfolio.model.TypeProfile;

import java.util.List;

public interface ProfileService {
    ProfileDTO updateProfile(ProfileDTO profileDTO);
    ProfileDTO saveProfile(ProfileDTO profileDTO);
    ProfileDTO findById(Integer id);
    ProfileDTO findByName(String name);
    List<Profile> findProfilesByType(TypeProfile typeProfile);
     ProfileDTO convertToDTO(Profile profile);
     Profile convertToModel(ProfileDTO profileDTO);
     void updateProjectByProfile(Project project,Integer id);
    void updateContactByProfile(Contact contact, Integer id);
}
