package com.baconbao.portfolio.repository;

import com.baconbao.portfolio.model.Profile;
import com.baconbao.portfolio.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    @Query("SELECT p FROM Project p WHERE p.profile = :profile")
    List<Project> getProjectByProfile(@Param("profile") Profile profile);
}
