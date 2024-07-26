package com.baconbao.portfolio.services.serviceImpls;

import com.baconbao.portfolio.dto.UserDTO;
import com.baconbao.portfolio.model.User;
import com.baconbao.portfolio.repository.UserRepository;
import com.baconbao.portfolio.services.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j

public class UserServiceImpl implements UserService
{
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;

    public UserDTO findByEmail(String email) {

        return convertToDTO(userRepository.findByEmail(email)
                .orElseThrow());
    }

    public UserDTO findById(Integer id) {
        log.info("Get user by id: {}", id);

        return convertToDTO(userRepository.findById(id)
                .orElseThrow());
    }


    // Bé chung viết
    @Override
    public UserDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            User user = (User) authentication.getPrincipal();
            UserDTO userDTO = convertToDTO(user);
            return userDTO;
        }
        return null;
    }
    private UserDTO convertToDTO(User user) {

         return modelMapper.map(user, UserDTO.class);
    }
}
