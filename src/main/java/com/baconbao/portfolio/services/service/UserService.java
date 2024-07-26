package com.baconbao.portfolio.services.service;

import com.baconbao.portfolio.dto.UserDTO;
import com.baconbao.portfolio.model.User;

import java.util.List;

public interface UserService {
    User getUserByEmail(String email);
    User getUserById(Integer id);
    UserDTO getCurrentUser();
}
