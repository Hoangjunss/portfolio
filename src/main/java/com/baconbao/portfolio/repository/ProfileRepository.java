package com.baconbao.portfolio.repository;

import com.baconbao.portfolio.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    Optional<Profile> findById(Integer integer);
}
