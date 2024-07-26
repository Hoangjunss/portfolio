package com.baconbao.portfolio.services.service;

import com.baconbao.portfolio.model.Profile;

public interface ProfileService {
    void updateProfile(Profile updatedProfile);
    void deleteProfile();
}
