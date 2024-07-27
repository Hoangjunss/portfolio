package com.baconbao.portfolio.services.service;

import com.baconbao.portfolio.dto.ProfileDTO;
import com.baconbao.portfolio.model.*;

import java.util.List;

public interface ProfileService {
    ProfileDTO updateProfile(ProfileDTO profileDTO);
    ProfileDTO saveProfile(ProfileDTO profileDTO);
    ProfileDTO findById(Integer id);
    List<ProfileDTO> findProfilesByType(TypeProfile typeProfile);
     ProfileDTO convertToDTO(Profile profile);
     Profile convertToModel(ProfileDTO profileDTO);
     ProfileDTO findProfileByName(String name);
     void updateProjectByProfile(Project project,Integer id);
     void updateContactByProfile(Contact contact, Integer id);
}
