package com.baconbao.portfolio.repository;

import com.baconbao.portfolio.model.Profile;
import com.baconbao.portfolio.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

}
