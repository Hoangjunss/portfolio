package com.baconbao.portfolio.services;

import com.baconbao.portfolio.model.Profile;
import com.baconbao.portfolio.repository.ProfileRepository;
import com.baconbao.portfolio.services.service.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class ProfileServiceTest {
    @MockBean
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileService profileService;

    private Profile profile;

    @BeforeEach
    void initDAta(){
        profile = Profile.builder()
                .id(1)
                .address("ABC")
                .contactInfo("test")
                .education("UNIVERSITY")
                .build();
    }

    @Test
    public void saveProfile_ThrownException(){
        profileService.updateProfile(profile);
        verify(profileRepository, times(1)).save(profile);

    }

}
