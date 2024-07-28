package com.baconbao.portfolio.services.service;

import com.baconbao.portfolio.dto.ProfileDTO;
import com.baconbao.portfolio.dto.UserDTO;
import com.baconbao.portfolio.model.*;

import javax.xml.stream.events.Comment;
import java.util.List;

public interface ProfileService {
    ProfileDTO updateProfile(ProfileDTO profileDTO);
    ProfileDTO saveProfile(ProfileDTO profileDTO);
    ProfileDTO findById(Integer id);
    List<ProfileDTO> findProfilesByType(TypeProfile typeProfile);
     ProfileDTO convertToDTO(Profile profile);
     Profile convertToModel(ProfileDTO profileDTO);
     ProfileDTO findProfileByName(String name);
     void updateContactByProfile(Contact contact, Integer id);
     ProfileDTO getProfileByUser(Integer id);
}
