package com.baconbao.portfolio.services.service;

import com.baconbao.portfolio.dto.UserDTO;
import com.baconbao.portfolio.model.Profile;


public interface UserService {
    UserDTO findByEmail(String email);
    UserDTO findById(Integer id);
    UserDTO getCurrentUser();
    void updateProfileByUser(Profile profile, Integer id);
}
