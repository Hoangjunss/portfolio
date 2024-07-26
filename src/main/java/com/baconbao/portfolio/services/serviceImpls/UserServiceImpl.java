package com.baconbao.portfolio.services.serviceImpls;

import com.baconbao.portfolio.model.User;
import com.baconbao.portfolio.services.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service


public class UserServiceImpl implements UserService
{

    public User getUserByEmail(String email) {
        return null;
    }

    public User getUserById(Integer id) {
        return null;
    }


    // Bé chung viết
    @Override
    public User getCurrentUser() {
        return null;
    }
}
