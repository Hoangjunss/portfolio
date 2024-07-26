package com.baconbao.portfolio.services.service;

import com.baconbao.portfolio.dto.UserDTO;


public interface UserService {
    UserDTO findByEmail(String email);
    UserDTO findById(Integer id);
    UserDTO getCurrentUser();
}
