package com.baconbao.portfolio.repository;

import com.baconbao.portfolio.dto.ProfileDTO;
import com.baconbao.portfolio.model.Profile;
import com.baconbao.portfolio.model.TypeProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    Optional<Profile> findById(Integer integer);
    List<Profile> findByTypeProfile(TypeProfile typeProfile);

}
