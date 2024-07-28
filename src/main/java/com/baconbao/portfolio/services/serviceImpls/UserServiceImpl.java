package com.baconbao.portfolio.services.serviceImpls;

import com.baconbao.portfolio.dto.ProfileDTO;
import com.baconbao.portfolio.dto.UserDTO;
import com.baconbao.portfolio.exception.CustomException;
import com.baconbao.portfolio.exception.Error;
import com.baconbao.portfolio.exception.UserNotFoundException;
import com.baconbao.portfolio.model.Notification;
import com.baconbao.portfolio.model.Profile;
import com.baconbao.portfolio.model.User;
import com.baconbao.portfolio.repository.ProfileRepository;
import com.baconbao.portfolio.repository.UserRepository;
import com.baconbao.portfolio.services.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileRepository profileRepository;

    public UserDTO findByEmail(String email) {
        log.info("Finding user by email: {}", email);
        return convertToDTO(userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(Error.USER_NOT_FOUND)));
    }

    public UserDTO findById(Integer id) {
        log.info("Get user by id: {}", id);
        return convertToDTO(userRepository.findById(id)
                .orElseThrow(()->new CustomException(Error.USER_NOT_FOUND)));
    }

    // Bé chung viết
    @Override
    public UserDTO getCurrentUser() {
        try {
            log.info("Getting current user");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
                User user = (User) authentication.getPrincipal();
                return convertToDTO(user);
            }
        } catch (Exception e) {
            log.error("Error getting current user: ", e);
        }
        return null;
    }

    @Override
    public void updateProfileByUser(Profile profile, Integer id) {
        try{
            log.info("Updating profile by id: {}", id);
            User user = userRepository.findById(id).orElseThrow();
            user.setProfile(profile);
            userRepository.save(user);
        } catch (DataIntegrityViolationException d){
            throw new CustomException(Error.USER_UNABLE_TO_UPDATE);
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }


    @Override
    public UserDTO convertToDTO(User user) {
         return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public  User convertToModel(UserDTO userDTO){
        return  modelMapper.map(userDTO, User.class);
    }

}
