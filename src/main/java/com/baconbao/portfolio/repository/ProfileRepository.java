package com.baconbao.portfolio.repository;

import com.baconbao.portfolio.dto.ProfileDTO;
import com.baconbao.portfolio.model.Profile;
import com.baconbao.portfolio.model.TypeProfile;
import com.baconbao.portfolio.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    Optional<Profile> findById(Integer integer);
    List<Profile> findByTypeProfile(TypeProfile typeProfile);
    @Query("SELECT p FROM Profile p Join User u ON p.id = u.profile.id WHERE u.name = :name")
    Profile findProfileByName(@Param("name") String name);
    Profile findByUser(User user);
}
