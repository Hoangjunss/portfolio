package com.baconbao.portfolio.services.service;

import com.baconbao.portfolio.dto.UserDTO;


public interface UserService {
    UserDTO getUserByEmail(String email);
    UserDTO getUserById(Integer id);
    UserDTO getCurrentUser();
}
