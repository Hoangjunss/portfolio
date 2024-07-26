package com.baconbao.portfolio.services.service;

import com.baconbao.portfolio.dto.ProfileDTO;

public interface ProfileService {
    ProfileDTO updateProfile(ProfileDTO profileDTO);
    ProfileDTO saveProfile(ProfileDTO profileDTO);
}
