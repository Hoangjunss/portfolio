package com.baconbao.portfolio.repository;

import com.baconbao.portfolio.model.Profile;
import com.baconbao.portfolio.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
   Optional<User> findByEmail(String email);
   @Query("SELECT p FROM Profile p Join User u ON p.id = u.profile.id WHERE u.name = :name")
   Profile findProfileByName(@Param("name") String name);
}
