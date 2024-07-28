package com.baconbao.portfolio.services.service;

import com.baconbao.portfolio.dto.UserDTO;
import com.baconbao.portfolio.model.Notification;
import com.baconbao.portfolio.model.Profile;
import com.baconbao.portfolio.model.User;
import jakarta.persistence.criteria.CriteriaBuilder;


public interface UserService {
    UserDTO findByEmail(String email);
    UserDTO findById(Integer id);
    UserDTO getCurrentUser();
    void updateProfileByUser(Profile profile, Integer id);
    UserDTO convertToDTO(User user);
    User convertToModel(UserDTO userDTO);
}
